package com.blockshine.api.web.controller;

import com.blockshine.api.dto.ContractDTO;
import com.blockshine.common.util.R;
import com.blockshine.common.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j(topic = "api")
@RequestMapping("/contract")
public class ContractController extends BaseController {


    @RequestMapping("/compile")
    public R compileContract(@RequestBody ContractDTO dto){

        return new R();

    }

    @RequestMapping("/deploy")
    public R deployContract(@RequestBody ContractDTO dto){

        return new R();

    }

    @RequestMapping("/invoke")
    public R invokeContract(@RequestBody ContractDTO dto){

        return new R();

    }

    @RequestMapping("/info")
    public R queryContract(@RequestBody ContractDTO dto){

        return new R();

    }





}
