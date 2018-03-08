package com.blockshine.api.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.blockshine.api.service.BlockShineWebCallService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j(topic = "transactionApi")
public class TransactionController {
	@Autowired
	BlockShineWebCallService bswCallService;

	// 查询交易总数
	@RequestMapping(value = "/transaction/counts", method = RequestMethod.GET)
	@ResponseBody
	public String bsw_transactionCounts() throws Exception {
		log.info("call trans counts");
		return bswCallService.bsw_transactionCounts();
	}

	// 查询交易信息
	@RequestMapping(value = "/transaction/info", method = RequestMethod.GET)
	@ResponseBody
	public String bsw_transactionInfo(String hash) throws Exception {
		log.info("call trans info");
		return bswCallService.bsw_transactionInfo(hash);
	}

	// 交易回执
	@RequestMapping(value = "/transaction/receipt", method = RequestMethod.GET)
	@ResponseBody
	public String bsw_transactionReceipt(String hash) throws Exception {
		log.info("call trans info");
		return bswCallService.bsw_transactionReceipt(hash);
	}
	
	// 非法交易 ？？？？

}
