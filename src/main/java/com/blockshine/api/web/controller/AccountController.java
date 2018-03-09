package com.blockshine.api.web.controller;

import com.blockshine.common.util.R;
import com.blockshine.common.web.BaseController;
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

//	TODO
	@GetMapping("/balance")
	public R getAccountBalance(String address){

	    return new R();

    }


	
}
