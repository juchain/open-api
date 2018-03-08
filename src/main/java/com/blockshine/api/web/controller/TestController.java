package com.blockshine.api.web.controller;

import com.blockshine.api.dto.AccountDTO;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j(topic = "api")
public class TestController {

	//注册接口
	@RequestMapping(value = "/test/register", method = RequestMethod.GET, consumes = "application/json")
	public String register(String  sss) {

		return "hello register"+sss;
	}

	//登录接口
	@RequestMapping(value = "/test/login", method = RequestMethod.POST, consumes = "application/json")
	public String login(@RequestBody AccountDTO accountDTO) {
        return "hello login";
	}




}
