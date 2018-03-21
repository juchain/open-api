package com.blockshine.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blockshine.api.dao.AddressDao;
import com.blockshine.api.dao.ChainDao;
import com.blockshine.api.domain.AddressDO;
import com.blockshine.api.domain.ChainDO;
import com.blockshine.api.util.HttpClientUtils;
import com.blockshine.common.config.JedisService;
import com.blockshine.common.constant.CodeConstant;
import com.blockshine.common.exception.BusinessException;
import com.blockshine.common.exception.InvalidTokenBusinessException;
import com.blockshine.common.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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


	@Autowired
	JedisService jedisService;




	public JSONObject wirteDataToChain(String data, String token) {

		if(!JedisUtil.hasKey(token)){
			throw  new InvalidTokenBusinessException("token 不存在",CodeConstant.NOT_TOKEN);
		}
		String appKey = JedisUtil.getByKey(token);
		Map<String, Object> parms = new HashMap<>(1);
		parms.put("appKey",appKey);
		List<AddressDO> list = addressDao.list(parms);
		if(!Optional.ofNullable(list).isPresent()){
			throw  new BusinessException("开户地址不存在",CodeConstant.NOT_EXIST_ADDRESS_ERROR);
		}
		AddressDO addressDO = list.get(0);
		// 企业 上链请求nonce
		String nonce = getNonce(addressDO.getAddressFrom());

		String jsonData = jsonData(addressDO.getAddressFrom(), addressDO.getAddressTo(), addressDO.getAddressTo(), data, nonce);

		ChainDO chainDO = gennerateChainDo(addressDO, jsonData, nonce);

		int save = chainDao.save(chainDO);

		JSONObject jo = HttpClientUtils.httpPost(bswurl + "data/write", JSONObject.parseObject(jsonData));

		ChainDO updataChainDo= new ChainDO();
		if(jo.get("code").equals(0)){
			updataChainDo.setReceipt(jo.get("receipt").toString());
		}
		updataChainDo.setId(chainDO.getId());
		updataChainDo.setUpdated(new Date());
		updataChainDo.setMessage(jo.get("message").toString());
		chainDao.update(updataChainDo);
		return jo;
	}

	private ChainDO gennerateChainDo(AddressDO addressDO, String data, String nonce) {
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


		Map map = new HashMap(1);
		map.put("nonce",nonceStr);
		List<ChainDO> chainDOs= chainDao.list(map);
		if(Optional.ofNullable(chainDOs).isPresent()
				&& Optional.ofNullable(chainDOs.get(0)).isPresent()){//不为null
			List<ChainDO> list = chainDao.list(new HashMap<>());
			if(Optional.ofNullable(list).isPresent()){

				BigInteger  maxNonceStr = findMaxBigIntegerNonce(list);

				BigInteger noncePlusOne = maxNonceStr.add(BigInteger.ONE);
				//十六进制
				nonceStr = "0x" + noncePlusOne.toString(16);
			}


		}
			return nonceStr;

	}

	private BigInteger findMaxBigIntegerNonce(List<ChainDO> list) {
		BigInteger maxIntNonce =BigInteger.ZERO;
		for ( ChainDO  chainDO:list) {
			String nonce = chainDO.getNonce();
			//十进制
			BigInteger tenNonce = new BigInteger(nonce.substring(2), 16);
			if(maxIntNonce.compareTo(tenNonce)<0){
				maxIntNonce =tenNonce;
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
