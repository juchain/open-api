package com.blockshine.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blockshine.api.dao.AddressDao;
import com.blockshine.api.dao.ChainDao;
import com.blockshine.api.domain.AddressDO;
import com.blockshine.api.domain.ChainDO;
import com.blockshine.api.util.HttpClientUtils;
import com.blockshine.common.constant.CodeConstant;
import com.blockshine.common.exception.BusinessException;
import com.blockshine.common.exception.InvalidTokenBusinessException;

import com.blockshine.common.util.JedisUtil;
import com.blockshine.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.*;

@Service
public class DataService {

	@Value("${bswurl}")
	private String bswurl;

	@Autowired
	private ChainDao chainDao;

	@Autowired
	private AddressDao addressDao;

	// 1G=1024M,1M=1024KB,1KB=1024Byte.
	private static int oneM = 1048576;

	@Transactional
	public JSONObject writeDataToChain(String data, String token) {

		int dataLength = data.getBytes().length;
		if (oneM < dataLength) {
			throw new BusinessException("上传数据不可以超出1M", CodeConstant.NOT_GT_ONEM_ERROR);
		}

		if (!JedisUtil.hasKey(token)) {
			throw new InvalidTokenBusinessException("token 不存在", CodeConstant.NOT_TOKEN);
		}
		String appKey = JedisUtil.getByKey(token);
		Map<String, Object> parms = new HashMap<>(1);
		parms.put("appKey", appKey);
		List<AddressDO> list = addressDao.list(parms);
		if (list == null || list.size() == 0) {
			throw new BusinessException("账户信息不存在", CodeConstant.NOT_EXIST_ADDRESS_ERROR);
		}

		AddressDO addressDO = list.get(0);

		// 企业 上链请求nonce
		String nonce = getNonce(addressDO.getAddressFrom());

		String jsonData = jsonData(addressDO.getAddressFrom(), addressDO.getAddressTo(), addressDO.getPassword(), data,
				nonce);

		ChainDO chainDO = generateChainDo(addressDO, jsonData, nonce);

		int save = chainDao.save(chainDO);
		if (save <= 0) {
			throw new BusinessException("数据服务错误", CodeConstant.DATA_SERVICE_ERROR);
		}

		JSONObject jo = HttpClientUtils.httpPost(bswurl + "data/write", JSONObject.parseObject(jsonData));

		ChainDO updataChainDo = new ChainDO();
		if ("0".equals(jo.get("code"))) {
			updataChainDo.setReceipt(jo.get("receipt").toString());
		}
		updataChainDo.setId(chainDO.getId());
		updataChainDo.setUpdated(new Date());
		updataChainDo.setMessage(jo.get("message").toString());
		chainDao.update(updataChainDo);
		return jo;
	}

	@Transactional
	public JSONObject readDataFromChain(String receipt, String token) {
		if (!JedisUtil.hasKey(token)) {
			throw new InvalidTokenBusinessException("token 不存在", CodeConstant.NOT_TOKEN);
		}
		String appKey = JedisUtil.getByKey(token);
		Map<String, Object> parms = new HashMap<>(1);
		parms.put("appKey", appKey);
		List<AddressDO> list = addressDao.list(parms);
		if (list == null || list.size() == 0) {
			throw new BusinessException("账户信息不存在", CodeConstant.NOT_EXIST_ADDRESS_ERROR);
		}
		JSONObject jo = HttpClientUtils.httpGet(bswurl + "data/info?hash=" + receipt);
		return jo;
	}

	private ChainDO generateChainDo(AddressDO addressDO, String data, String nonce) {
		ChainDO chainDO = new ChainDO();
		chainDO.setAddressFrom(addressDO.getAddressFrom());
		chainDO.setAddressTo(addressDO.getAddressTo());
		chainDO.setDataStatus(CodeConstant.DATA_CHAIN_STATUS.PENDING);
		chainDO.setData(data);
		chainDO.setNonce(nonce);
		chainDO.setCreated(new Date());
		chainDO.setStatus(1);

		return chainDO;
	}

	// 获取nonce
	private String getNonce(String address) {
		// 从区块链去取数据
		JSONObject jo = HttpClientUtils.httpGet(bswurl + "data/nonce?address=" + address);
		String nonceStr = jo.get("nonce").toString();
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("nonce", nonceStr);
		List<ChainDO> chainDOs = chainDao.list(map);
		if (chainDOs != null && chainDOs.size() >= 0) {// 不为null
			List<ChainDO> list = chainDao.list(new HashMap<>());
			if (list != null & list.size() != 0) {
				BigInteger maxNonceStr = findMaxBigIntegerNonce(list);
				BigInteger noncePlusOne = maxNonceStr.add(BigInteger.ONE);
				// 十六进制
				nonceStr = "0x" + noncePlusOne.toString(16);
			}
		}
		return nonceStr;
	}

	private BigInteger findMaxBigIntegerNonce(List<ChainDO> list) {
		BigInteger maxIntNonce = BigInteger.ZERO;
		for (ChainDO chainDO : list) {
			String nonce = chainDO.getNonce();
			// 十进制
			BigInteger tenNonce = new BigInteger(nonce.substring(2), 16);
			if (maxIntNonce.compareTo(tenNonce) < 0) {
				maxIntNonce = tenNonce;
			}
		}
		return maxIntNonce;
	}

	private String jsonData(String from, String to, String password, String data, String nonce) {
		Map<String, String> m = new HashMap<>();
		m.put("from", from);
		m.put("to", to);
		m.put("password", password);
		m.put("data", data);
		m.put("nonce", nonce);
		return JSON.toJSONString(m);
	}

}
