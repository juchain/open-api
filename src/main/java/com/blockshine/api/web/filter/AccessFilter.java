package com.blockshine.api.web.filter;

import com.alibaba.fastjson.JSONObject;
import com.blockshine.common.config.JedisService;
import com.blockshine.common.constant.CodeConstant;
import com.blockshine.common.exception.InvalidTokenBusinessException;
import com.blockshine.common.util.JedisUtil;
import com.blockshine.common.util.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;



public class AccessFilter implements Filter{

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AccessFilter.class);
    /**
     * 封装，不需要过滤的list列表
     */
    private static List<String> patterns = new ArrayList<String>();


//    private JedisService jedisService;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        //不存在的url 直接跳404
//        patterns.add("/token/apply");
//        patterns.add("/token/refresh");
//        patterns.add("/");
//        patterns.add("");
//        patterns.add("/api/account/login");
//        patterns.add("/api/account/register");
//        ServletContext sc = filterConfig.getServletContext();
//
//        AnnotationConfigEmbeddedWebApplicationContext cxt = (AnnotationConfigEmbeddedWebApplicationContext) WebApplicationContextUtils.getWebApplicationContext(sc);
//
//        if(cxt != null && cxt.getBean("jedisService") != null && jedisService == null)
//            jedisService = (JedisService) cxt.getBean("jedisService");



    }





    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String url = httpRequest.getRequestURI();
        logger.info("dofilter...info" + url);

        //过滤不验证的url
        boolean checkUrl = isInclude(url);

        //过滤资源文件  没有网页不检查
//        String[] sufix = {".jsp", ".jpg", ".png", ".css", ".js", ".img", ".gif", "ico", ".woff", ".otf", ".eot", ".svg", ".ttf", ".html"};
//        for (String s : sufix) {
//            if (s.equals(".js")) {
//                if (url.contains(s) && (!url.contains(".jsp") || !url.contains(".html"))) {
//                    checkUrl = true;
//                }
//            } else {
//                if (url.contains(s)) {
//                    checkUrl = true;
//                }
//            }
//
//        }

        if (checkUrl){
            chain.doFilter(httpRequest, httpResponse);
            return;
        } else {
            processFilter(chain, httpRequest, httpResponse);
            return;
        }

    }

    private void processFilter(FilterChain chain, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
        String token = httpRequest.getHeader("token");
        if(StringUtils.isEmpty(token)){
            doPrintWriter(httpResponse,CodeConstant.PARAM_LOST,"token参数丢失");
            //throw new InvalidTokenBusinessException("token参数丢失", CodeConstant.PARAM_LOST);
        }

        String appId = JedisUtil.getJedis().get(token);
        String redisToken = JedisUtil.getJedis().get(CodeConstant.TOKEN + appId);

        if(StringUtils.isEmpty(token) || StringUtils.isEmpty(appId)){
            doPrintWriter(httpResponse,CodeConstant.PARAM_LOST,"token或者appId参数丢失");
            //throw new InvalidTokenBusinessException("token或者appId参数丢失",CodeConstant.PARAM_LOST);
        }else if(StringUtils.isEmpty(redisToken)){
            doPrintWriter(httpResponse,CodeConstant.NOT_TOKEN,"无效token");
            //throw new InvalidTokenBusinessException("token不存在",CodeConstant.NOT_TOKEN);
        }else if(!redisToken.equals(token)){
            doPrintWriter(httpResponse,CodeConstant.EXCEPTION_TOKEN,"异常token");
            //throw new InvalidTokenBusinessException("异常token",CodeConstant.EXCEPTION_TOKEN);
        }else {
            chain.doFilter(httpRequest, httpResponse);
            return;
        }
    }



    private void doPrintWriter(HttpServletResponse response,int code,String message){

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        PrintWriter out = null ;
        JSONObject res = new JSONObject();

        res.put("msg",message);
        res.put("code",code);
        try {
            out = response.getWriter();
        }catch (Exception e){
            e.printStackTrace();
        }

        out.append(res.toString());

        out.flush();
        out.close();
    }


    @Override
    public void destroy() {

    }


    /**
     * 是否需要过滤
     * @param url
     * @return
     */
    private boolean isInclude(String url) {
        return patterns.contains(url);
    }



}
