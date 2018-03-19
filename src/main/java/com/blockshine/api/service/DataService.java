package com.blockshine.api.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blockshine.api.util.HttpClientUtils;

@Service
public class DataService {

	@Value("${bswurl}")
	private String bswurl;

	public JSONObject wirteDataToChain(String data) {
		// 企业 address from
		String from = "";
		// 企业 address to
		String to = "";
		// 企业 password
		String password = "";
		// 企业 上链数据
		data = "";
		// 企业 上链请求nonce
		String nonce = nonce(from);

		String jsonData = jsonData(from, to, password, data, nonce);

		JSONObject jo = HttpClientUtils.httpPost(bswurl + "data/write", JSONObject.parseObject(jsonData));
		// 交易回执写入数据库 数据上链 流水表 todo
		return jo;
	}

	// 获取nonce
	private String nonce(String address) {
		// 从区块链去取数据
		JSONObject jo = HttpClientUtils.httpGet(bswurl + "data/nonce?address=" + address);
		String nonceStr = jo.get("nonce").toString();
		BigInteger nonce = new BigInteger(nonceStr.substring(2), 16);
		String chainNonce = "0x" + nonce.toString(16);

		// LinkedList<String> nonceList = new LinkedList<>();
		// if (nonceList.size() == 0) {
		// nonceList.add(chainNonce);
		// } else {
		// // 如果服务器获取的已经在数据库list中存在 取出list中的最新的nonce
		// if (nonceList.contains(chainNonce)) {
		// String topNonceStr = nonceList.getLast();
		// // 类型转换
		// BigInteger topNonce = new BigInteger(topNonceStr.substring(2), 16);
		// // nonce+1
		// chainNonce = "0x" + topNonce.add(new BigInteger("1", 16)).toString(16);
		// nonceList.add(chainNonce);
		// } else {
		// nonceList.add(chainNonce);
		// }
		// }

		// 数据库查询 企业nonce todo
		// if (condition) {//数据库有
		// //top1 + 1
		// String topNonceStr = 查数据库
		// chainNonce = "0x" + topNonce.add(new BigInteger("1", 16)).toString(16);
		// }
		// 如果没有 直接用
		// 写入数据库

		return chainNonce;
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
