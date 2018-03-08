package com.blockshine.api.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.blockshine.api.service.BlockShineWebCallService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j(topic = "api")
public class AccountsController {

	@Autowired
	BlockShineWebCallService bswCallService;

	// 创建账户
	@RequestMapping(value = "/account/create", method = RequestMethod.GET)
	@ResponseBody
	public String bsw_newAddress(String secret, String name) {
		log.info("bsw_newAddress");
		return bswCallService.bsw_newAddress(secret, name);
	}
	
	// 账户清单
	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	@ResponseBody
	public String bsw_accounts() {
		log.info("bsw_newAddress");
		return 	bswCallService.bsw_accounts();
	}


	
}
