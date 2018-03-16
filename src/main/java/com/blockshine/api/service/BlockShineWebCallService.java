package com.blockshine.api.service;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
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
		JSONObject jo = HttpClientUtils.httpGet(bswurl + "account/balance?address=" + address);
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

	// 测试------------

	// 测试用list
	private static LinkedList<String> nonceList = new LinkedList<>();

	public Map<String, String> test(int size) {
		Map<String, String> map = new LinkedHashMap<>();
		String da = createData(size);
		String data = "{\n" + "  \"from\":\"0x5a2838D95f35e907a934b9f739512E92F198f133\",\n"
				+ "  \"to\":\"0x42c5151C45661d10C7b72367721179263258857B\",\n" + "  \"password\":\"123456\",\n"
				+ "  \"data\":\"" + da + "\"\n" + ",  \"nonce\":\""
				+ nonce("0x5a2838D95f35e907a934b9f739512E92F198f133") + "\"\n" + "}";
		byte[] buf = da.toString().getBytes();
		map.put("1数据长度", da.length() + "");
		map.put("2数据大小", buf.length / 1024 + "KB " + buf.length / 1024 / 1024 + "MB");
		Instant inst1 = Instant.now();
		map.put("3写链开始时间", inst1.toString());
		JSONObject jo = HttpClientUtils.testPost("http://localhost:8090/data/write", JSONObject.parseObject(data));
		Instant inst2 = Instant.now();
		map.put("4写链结束时间", inst2.toString());
		map.put("5写链时间差", Duration.between(inst1, inst2).getSeconds() + "");
		map.put("6上链结果", jo.toJSONString());

		// JSONObject onchain = null;
		// int i = 0 ;
		// while (onchain == null) {
		// i++;
		// onchain =
		// HttpClientUtils.httpGet("http://bctest.blockshine.net/data//info?hash="+jo.get("receipt"));
		// }
		// Instant inst3 = Instant.now();
		// map.put("成功上链时间差", Duration.between(inst2, inst3).getSeconds()+"");
		// map.put("上链查询次数", i+"");
		// map.put("上链数据", onchain.get("data")+"");
		return map;
		// return null;
	}

	//动态创建字符串 data
	private String createData(int size) {
		StringBuffer sb = new StringBuffer("");
		String str = "a";
		for (int i = 0; i < size; i++) {
			sb.append(str);
		}
		return sb.toString();
		// byte[] buf = a.getBytes();
		// System.out.println("length"+buf.length+" Byte="+buf.length/1024+"KB"+"
		// Byte="+buf.length/1024/1024+"MB" );
	}

	//获取nonce
	private String nonce(String address) {
		JSONObject jo = HttpClientUtils.httpGet("http://localhost:8090/data/nonce?address=" + address);
		String nonceStr = jo.get("nonce").toString();
		BigInteger nonce = new BigInteger(nonceStr.substring(2), 16);
		String chainNonce = "0x" + nonce.toString(16);
		if (nonceList.size() == 0) {
			nonceList.add(chainNonce);
		} else {
			// 如果服务器获取的已经在list中存在 取出list中的最新的nonce
			if (nonceList.contains(chainNonce)) {
				String topNonceStr = nonceList.getLast();
				// 类型转换
				BigInteger topNonce = new BigInteger(topNonceStr.substring(2), 16);
				// nonce+1
				chainNonce = "0x" + topNonce.add(new BigInteger("1", 16)).toString(16);
				nonceList.add(chainNonce);
			} else {
				nonceList.add(chainNonce);
			}
		}
		return chainNonce;
	}
	
	public static void main(String[] args) {
		// StringBuffer sb = new StringBuffer("");
		// String str = "a";
		// for(int i=0;i<1000000;i++){
		// sb.append(str);
		// }
		// byte[] buf = sb.toString().getBytes();
		// System.out.println("length"+buf.length+" Byte="+buf.length/1024+"KB"+"
		// Byte="+buf.length/1024/1024+"MB" );
		// JSONObject jo = HttpClientUtils
		// .httpGet("http://localhost:8090/data/nonce?address=" +
		// "0x1ACcCdCf54234635AA56114a08bABD60b416E03B");
		// String nonceStr = jo.get("nonce").toString();
		// BigInteger nonce;
		// System.out.println(nonceStr);
		// nonce = new BigInteger(nonceStr.substring(2), 16);
		// for (int i = 0; i < 100; i++) {
		// nonce = nonce.add(new BigInteger("1", 16));
		// System.out.println(i + "-----0x" + nonce.toString(16));
		// }

		// JSONObject jo =
		// HttpClientUtils.httpGet("http://localhost:8090/data/nonce?address=" +
		// "0x1ACcCdCf54234635AA56114a08bABD60b416E03B");
		// String nonceStr = jo.get("nonce").toString();
		// BigInteger nonce = new BigInteger(nonceStr.substring(2), 16);
		// String chainNonce = "0x"+nonce.toString(16);
		// if (nonceList.size() == 0) {
		// nonceList.add(chainNonce);
		// }else {
		// nonceList.contains(chainNonce);
		// //nonce+1
		// chainNonce = "0x"+nonce.add(new BigInteger("1", 16)).toString(16);
		// nonceList.add(chainNonce);
		// }
		// System.out.println(chainNonce);

		// LinkedList<String> nonceList = new LinkedList<>();
		// nonceList.add("0x0");
		// nonceList.add("0x2");
		// nonceList.add("0x3");
		// nonceList.add("0x9");
		//
		// JSONObject jo =
		// HttpClientUtils.httpGet("http://localhost:8090/data/nonce?address=0x1ACcCdCf54234635AA56114a08bABD60b416E03B"
		// );
		// String nonceStr = jo.get("nonce").toString();
		// BigInteger nonce = new BigInteger(nonceStr.substring(2), 16);
		// String chainNonce = "0x"+nonce.toString(16);
		// if (nonceList.size() == 0) {
		// nonceList.add(chainNonce);
		// }else {
		// //如果服务器获取的已经在list中存在 取出list中的最新的nonce
		// if (nonceList.contains(chainNonce)) {
		// String topNonceStr = nonceList.getLast();
		// //类型转换
		// BigInteger topNonce = new BigInteger(topNonceStr.substring(2), 16);
		// //nonce+1
		// chainNonce = "0x"+topNonce.add(new BigInteger("1", 16)).toString(16);
		// nonceList.add(chainNonce);
		// }else {
		// nonceList.add(chainNonce);
		// }
		// }
		//
		// for (int i = 0; i < nonceList.size(); i++) {
		// System.out.println(nonceList.get(i)+"---");
		// }
		String x = "0xc";
		if (!x.startsWith("0x")) {
			System.out.println("111");
		} else {
			x = x.substring(2);
			System.out.println(Long.parseLong(x, 16));
		}
	}

}
