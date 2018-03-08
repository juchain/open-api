package com.blockshine.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.blockshine.api.util.HttpClientUtils;

@Service
public class BlockShineWebCallService {
	
	@Value("${bswurl}")
    private String bswurl;

	public String bsw_accounts() {
		JSONObject jo = HttpClientUtils.httpGet(bswurl+"accounts");
		return jo.toJSONString();
	}

	public String bsw_getBalance(String address, String blockId) {
		// TODO Auto-generated method stub
		return "123123123";
	}

	public String bsw_newAddress(String secret, String name) {
		// TODO Auto-generated method stub
		return "123123123";
	}
	
	
}
