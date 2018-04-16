package com.blockshine.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blockshine.api.dto.AuthorizationDTO;
import com.blockshine.api.util.HttpClientUtils;
import com.blockshine.common.util.R;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class TokenService {

    private static Logger log = Logger.getLogger(TokenService.class);



    @Value("${open_platform_test}")
    private String openPlateformUrl;



    public R applyToken(AuthorizationDTO dto) {
        log.info(openPlateformUrl+"token/apply:"+JSON.toJSONString(dto));

        JSONObject jsonObject = HttpClientUtils.httpPostJsonString(openPlateformUrl+"token/apply", JSON.toJSONString(dto));
        R r= new R();
        r.put("code",jsonObject.get("code"));
        r.put("msg",jsonObject.get("msg"));
        if(jsonObject.get("code")!=null&&jsonObject.get("code").equals(0)){
            r.put("tokenObject",jsonObject.get("tokenObject"));
        }
        return r;
    }

    public R refreshToken(AuthorizationDTO dto) {
        log.info(openPlateformUrl+"token/refresh:"+JSON.toJSONString(dto));
        JSONObject jsonObject = HttpClientUtils.httpPostJsonString(openPlateformUrl+"token/refresh", JSON.toJSONString(dto));
        R r= new R();
        r.put("code",jsonObject.get("code"));
        r.put("msg",jsonObject.get("msg"));
        if(jsonObject.get("code")!=null&&jsonObject.get("code").equals(0)){
            r.put("tokenObject",jsonObject.get("tokenObject"));
        }
        return r;
    }

    public R checkToken(String token) {
        log.info(openPlateformUrl+"token/check:"+JSON.toJSONString(token));
        Map<String, String> parm = new HashMap<String, String>(1);
        parm.put("token",token);
        JSONObject jsonObject = HttpClientUtils.httpPostJsonString(openPlateformUrl+"token/check", JSON.toJSONString(parm));
        R r= new R();
        r.put("code",jsonObject.get("code"));
        r.put("msg",jsonObject.get("msg"));
        return r;
    }
}
