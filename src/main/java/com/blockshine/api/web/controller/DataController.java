package com.blockshine.api.web.controller;

import com.blockshine.common.exception.InvalidTokenBusinessException;
import com.blockshine.common.util.JedisUtil;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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

	// 查询交易信息
	@RequestMapping(value = "/write", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public R dataWrite(HttpServletRequest httpRequest, HttpServletResponse httpServletResponse, String data) throws Exception {

        String token = httpRequest.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new InvalidTokenBusinessException("token参数丢失",CodeConstant.PARAM_LOST);
        }

		log.info("method:dataWrite data:" + data);
		if (StringUtils.isEmpty(data)) {
			throw new BusinessException("No data", CodeConstant.PARAM_LOST);
		}
		JSONObject result = dataService.wirteDataToChain(data,token);
		R r = new R();
		r.put("chainData", result);
		return r;
	}
}
