package com.blockshine.api.web.controller;

import com.alibaba.fastjson.JSON;
import com.blockshine.api.dto.AuthorizationDTO;
import com.blockshine.api.service.TokenService;
import com.blockshine.common.constant.CodeConstant;
import com.blockshine.common.util.R;
import com.blockshine.common.util.StringUtils;
import com.blockshine.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * token生成与刷新
 *
 * @author maxiaodong
 */
@RestController
@RequestMapping("/token")
public class TokenController extends BaseController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping("/apply")
    public R generateToken(@RequestBody AuthorizationDTO dto){
        if(StringUtils.isEmpty(dto.getAppKey()) || StringUtils.isEmpty(dto.getAppSecret())){
            return R.error(CodeConstant.PARAM_LOST, "appId or appSecret lost");
        }
       return tokenService.applyToken(dto);

    }

    @RequestMapping("/refresh")
    public R refreshToken(@RequestBody AuthorizationDTO dto){

        if(StringUtils.isEmpty(dto.getRefreshToken())){
            return R.error(CodeConstant.PARAM_LOST, "refreshToken lost!");
        }

        return tokenService.refreshToken(dto);

    }

    @RequestMapping("/check")
    public R checkToken(@RequestBody String token){
        if(StringUtils.isEmpty(token) && JSON.parseObject(token).get("token").toString() != null){
            return R.error(CodeConstant.PARAM_LOST, "token lost");
        }
        token = JSON.parseObject(token).get("token").toString();

        return tokenService.checkToken(token);
    }

}
