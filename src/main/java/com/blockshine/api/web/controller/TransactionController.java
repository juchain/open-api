package com.blockshine.api.web.controller;

import com.blockshine.common.util.R;
import com.blockshine.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.blockshine.api.service.BlockShineWebCallService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/transaction")
@Slf4j(topic = "transactionApi")
public class TransactionController extends BaseController{
	@Autowired
	BlockShineWebCallService bswCallService;

	// 查询交易总数
	@RequestMapping(value = "/counts", method = RequestMethod.GET)
	@ResponseBody
	public R transactionCounts() throws Exception {
		log.info("call trans counts");
		
		String result = bswCallService.bsw_transactionCounts();
		R r = new R();
		r.put("chainData", result);
		
		return r;
	}

	// 查询交易信息
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ResponseBody
	public R transactionInfo(String hash) throws Exception {
		log.info("call trans info");
		String result = bswCallService.bsw_transactionInfo(hash);
		R r = new R();
		r.put("chainData", result);
		return new R();
	}

	// 交易回执
	@RequestMapping(value = "/receipt", method = RequestMethod.GET)
	@ResponseBody
	public R transactionReceipt(String hash) throws Exception {
		log.info("call trans info");
		String result = bswCallService.bsw_transactionReceipt(hash);
		R r = new R();
		r.put("chainData", result);
		return new R();
	}
	
	// 非法交易 ？？？？

}
