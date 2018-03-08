package com.blockshine.api.web.controller;


import com.blockshine.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.blockshine.api.service.BlockShineWebCallService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j(topic = "api")
@RequestMapping("/block")
public class BlockController extends BaseController {
	@Autowired
	BlockShineWebCallService bswCallService;


//	// 查询余额
//	@RequestMapping(value = "/account/balance", method = RequestMethod.GET)
//	@ResponseBody
//	public String bsw_getBalance(String address, String blockId) throws Exception {
//		log.info("call accounts");
//		return bswCallService.bsw_getBalance(address, blockId);
//	}

	//查询区块信息
	//查询区块信息分页
	//区间查询区块
	
	
	
	
}
