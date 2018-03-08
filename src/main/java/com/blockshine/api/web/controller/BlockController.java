package com.blockshine.api.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.blockshine.api.service.BlockShineWebCallService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j(topic = "api")
public class BlockController {
	@Autowired
	BlockShineWebCallService bswCallService;


	//查询区块信息
	//查询区块信息分页
	//区间查询区块
	
	
	
	
}
