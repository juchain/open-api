package com.blockshine.api.web.controller;

import com.blockshine.common.util.R;
import com.blockshine.common.web.BaseController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.blockshine.api.dto.AccountDTO;
import com.blockshine.api.service.BlockShineWebCallService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j(topic = "accountsApi")
@RequestMapping("/account")
public class AccountController extends BaseController {

	@Autowired
	BlockShineWebCallService bswCallService;

	// 创建账户 是否改成post
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public R addNewAddress(@RequestBody AccountDTO adto ) {
		log.info("create account address");
		JSONObject result = bswCallService.bsw_newAddress(adto.getSecret(), adto.getName());
		R r = new R();
		r.put("chainData", result);
		return r;
	}
	
	// 账户清单
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public R getAccountList() {
		log.info("get account list");
		JSONObject result = bswCallService.bsw_accounts();
		R r = new R();
		r.put("chainData", result);
		return r;
	}

	@RequestMapping(value = "/balance", method = RequestMethod.GET)
	@ResponseBody
	public R getAccountBalance(String address){
		log.info("get account balance");
		JSONObject result = bswCallService.bsw_getBalances(address);
		R r = new R();
		r.put("chainData", result);
		return r;
    }

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> test(String size) throws InterruptedException{
		Map<String, Object> mm = new HashMap<>();
		List<Map<String, String>> list = new ArrayList<>();
		Integer s = Integer.valueOf(size);
		for (int i = 0; i < 10; i++) {
			System.out.println("第"+(i+1)+"次执行");
//			s=s+1;
			Map<String, String> result = bswCallService.test(s);
			list.add(result);
//			Thread.sleep(5000);
		}
		double xAvg = list.stream().mapToDouble(item -> Integer.valueOf(item.get("5写链时间差")).doubleValue()).average().getAsDouble();
		mm.put("采集数据", list);
		mm.put("平均上链时差", xAvg);
		return mm;
    }
	
}
