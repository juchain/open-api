package com.blockshine.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.blockshine.api.util.HttpClientUtils;
import com.blockshine.common.constant.CodeConstant;
import com.blockshine.common.exception.BusinessException;

@Service
public class BlockShineWebCallService {

	@Value("${bswurl}")
	private String bswurl;

	// 账户清单
	public String bsw_accounts() {
		JSONObject jo = HttpClientUtils.httpGet(bswurl + "accounts");
		return chainReturn(jo);
	}

	// 创建账户
	public String bsw_newAddress(String secret, String name) {
		JSONObject jo = HttpClientUtils.httpGet(bswurl + "account/create?name=" + name + "&secret=" + secret);
		return chainReturn(jo);
	}

	// 交易总数
	public String bsw_transactionCounts() {
		JSONObject jo = HttpClientUtils.httpGet(bswurl + "block/transactionCount?address=111&blockId=111");
		return chainReturn(jo);
	}

	// 交易详情 by hash
	public String bsw_transactionInfo(String hash) {
		JSONObject jo = HttpClientUtils.httpGet(bswurl + "transaction/info?hash=" + hash);
		return chainReturn(jo);
	}

	// 交易回执
	public String bsw_transactionReceipt(String hash) {
		JSONObject jo = HttpClientUtils.httpGet(bswurl + "transaction/receipt?hash=" + hash);
		return chainReturn(jo);
	}

	public String bsw_getBalance(String address, String blockId) {
		return "123123123";
	}

	public String getBestBlockNumber() {
		JSONObject jo = HttpClientUtils.httpGet(bswurl + "/block/number");

		return chainReturn(jo);
	}

	public String getBlockInfo(String bnOrId, boolean fullTransactionObjects) {
		JSONObject jo = HttpClientUtils
				.httpGet(bswurl + "?bnOrId=" + bnOrId + "&fullTransactionObjects=" + fullTransactionObjects);
		return null;
	}

	public String getBlocksEndWith(byte[] hash, Long qty) {
		JSONObject jo = HttpClientUtils.httpGet(bswurl + "?hash=" + hash + "&qty=" + qty);

		return jo.toString();
	}

	private String chainReturn(JSONObject jo) {
		if (jo!=null) {
			return jo.toJSONString();
		}else {
			throw new BusinessException("No ChainData",CodeConstant.CHAIN_NODATA);
		}
	}

}
