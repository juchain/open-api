package com.blockshine.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.blockshine.api.util.HttpClientUtils;

@Service
public class BlockShineWebCallService {

	@Value("${bswurl}")
	private String bswurl;

	//账户清单
	public String bsw_accounts() {
		JSONObject jo = HttpClientUtils.httpGet(bswurl + "accounts");
		return jo.toJSONString();
	}

	// 创建账户
	public String bsw_newAddress(String secret, String name) {
		JSONObject jo = HttpClientUtils.httpGet(bswurl + "account/create?name=" + name + "&secret=" + secret);
		return jo.toJSONString();
	}

	//交易总数
	public String bsw_transactionCounts() {
		JSONObject jo = HttpClientUtils.httpGet(bswurl + "block/transactionCount?address=111&blockId=111");
		return jo.toJSONString();
	}

	//交易详情 by hash
	public String bsw_transactionInfo(String hash) {
		JSONObject jo = HttpClientUtils.httpGet(bswurl + "transaction/info?hash=" + hash);
		return jo.toJSONString();
	}

	//交易回执
	public String bsw_transactionReceipt(String hash) {
		JSONObject jo = HttpClientUtils.httpGet(bswurl + "transaction/receipt?hash=" + hash);
		return jo.toJSONString();
	}
	
	
	public String bsw_getBalance(String address, String blockId) {
		return "123123123";
	}

}
