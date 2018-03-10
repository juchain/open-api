package com.blockshine.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blockshine.api.util.HttpClientUtils;
import com.blockshine.common.constant.CodeConstant;
import com.blockshine.common.exception.BusinessException;

@Service
public class BlockShineWebCallService {

	@Value("${bswurl}")
	private String bswurl;

	// 账户清单
	public JSONObject bsw_accounts() {
		JSONObject jo = HttpClientUtils.httpGet(bswurl + "accounts");
		return chainReturn(jo);
	}

	// 创建账户
	public JSONObject bsw_newAddress(String secret, String name) {
		JSONObject jo = HttpClientUtils.httpGet(bswurl + "account/create?name=" + name + "&secret=" + secret);
		return chainReturn(jo);
	}

	// 交易总数
	public JSONObject bsw_transactionCounts(String address, String blockId) {
		JSONObject jo = HttpClientUtils
				.httpGet(bswurl + "block/transactionCount?address=" + address + "&blockId=" + blockId);
		return chainReturn(jo);
	}

	// 交易详情 by hash
	public JSONObject bsw_transactionInfo(String hash) {
		JSONObject jo = HttpClientUtils.httpGet(bswurl + "transaction/info?hash=" + hash);
		return chainReturn(jo);
	}

	// 交易回执
	public JSONObject bsw_transactionReceipt(String hash) {
		JSONObject jo = HttpClientUtils.httpGet(bswurl + "transaction/receipt?hash=" + hash);
		return chainReturn(jo);
	}

	public JSONObject bsw_getBalance(String address, String blockId) {
		return null;
	}

	public JSONObject getBestBlockNumber() {
		JSONObject jo = HttpClientUtils.httpGet(bswurl + "block/number");
		return chainReturn(jo);
	}

	public JSONObject getBlockInfo(String bnOrId, boolean fullTransactionObjects) {
		JSONObject jo = HttpClientUtils
				.httpGet(bswurl + "block/info?bnOrId=" + bnOrId + "&fullTransactionObjects=" + fullTransactionObjects);
		return chainReturn(jo);
	}

	public JSONArray getBlocksEndWith(byte[] hash, Long qty) {
		String hashStr = "";
		String qtyStr = "";
		if (hash != null) {
			hashStr = "&hash=" + hash;
		}
		if (qty != null) {
			qtyStr = "&qty=" + qty;
		}
		JSONArray jo = HttpClientUtils.httpGetList(bswurl + "block/headers?1=1" + hashStr + qtyStr);
		return chainReturn(jo);
	}

	public JSONObject bsw_getBalances(String address) {
		JSONObject jo = HttpClientUtils.httpGet(bswurl + "account/balance?address="+address);
		return chainReturn(jo);
	}

	// 链返回空实体
	private JSONObject chainReturn(JSONObject jo) {
		if (jo != null) {
			return jo;
		} else {
			throw new BusinessException("No ChainData", CodeConstant.CHAIN_NODATA);
		}
	}

	// 链返回空集合
	private JSONArray chainReturn(JSONArray jo) {
		if (jo != null) {
			return jo;
		} else {
			throw new BusinessException("No ChainData", CodeConstant.CHAIN_NODATA);
		}
	}

}
