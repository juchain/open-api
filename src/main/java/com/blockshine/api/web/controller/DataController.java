package com.blockshine.api.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blockshine.api.service.DataService;
import com.blockshine.common.constant.CodeConstant;
import com.blockshine.common.exception.BusinessException;
import com.blockshine.common.util.R;
import com.blockshine.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j(topic = "data-controller")
@RequestMapping("/data")
public class DataController {

	@Autowired
	DataService dataService;

	// 数据写入链中
	@RequestMapping(value = "/write", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public R dataWrite(HttpServletRequest httpRequest, HttpServletResponse httpServletResponse,
			@RequestBody String data) throws Exception {
		String token = httpRequest.getHeader("token");
		log.info("method:dataWrite data:" + data);
		if (StringUtils.isEmpty(data)) {
			throw new BusinessException("No data", CodeConstant.PARAM_LOST);
		}
		JSONObject result = dataService.writeDataToChain(data, token);
		R r = new R();
		r.put("chainData", result);
		return r;
	}
	// 查询链中数据
	@RequestMapping(value = "/read", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public R dataRead(HttpServletRequest httpRequest, HttpServletResponse httpServletResponse,
			@RequestBody String receipt) throws Exception {
		String token = httpRequest.getHeader("token");
		log.info("method:dataRead receipt:" + receipt);
		if (StringUtils.isEmpty(receipt) && JSON.parseObject(receipt).get("receipt") != null) {
			throw new BusinessException("No receipt", CodeConstant.PARAM_LOST);
		}
		receipt = JSON.parseObject(receipt).get("receipt").toString();
		JSONObject result = dataService.readDataFromChain(receipt, token);
		R r = new R();
		r.put("chainData", result);
		return r;
	}
}
