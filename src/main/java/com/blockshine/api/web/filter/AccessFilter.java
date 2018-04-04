package com.blockshine.api.web.filter;

import com.alibaba.fastjson.JSONObject;
import com.blockshine.common.config.JedisService;
import com.blockshine.common.constant.CodeConstant;

import com.blockshine.common.util.SpringContextHolder;
import com.blockshine.common.util.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class AccessFilter implements Filter {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AccessFilter.class);
	/**
	 * 封装，不需要过滤的list列表
	 */
	private static List<String> patterns = new ArrayList<String>();
	private WebApplicationContext wac;

	// private JedisService jedisService= SpringContextHolder.getBean("jedisService");

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		wac = (WebApplicationContext) filterConfig.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

		// 不验证放行 url
		// patterns.add("/token/apply");

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

		String url = httpRequest.getRequestURI();
		logger.info("dofilter...info" + url);

		// 过滤不验证的url
		boolean checkUrl = isInclude(url);
		if (checkUrl) {
			chain.doFilter(httpRequest, httpResponse);
			return;
		} else {
			processFilter(chain, httpRequest, httpResponse);
			return;
		}
	}

	private void processFilter(FilterChain chain, HttpServletRequest httpRequest, HttpServletResponse httpResponse)
			throws IOException, ServletException {
		String token = httpRequest.getHeader("token");
		JedisService jedisService = wac.getBean(JedisService.class);
		if (StringUtils.isEmpty(token)) {
			doPrintWriter(httpResponse, CodeConstant.PARAM_LOST, "token参数丢失");
		} else if (!jedisService.hasKey(token)) {
			doPrintWriter(httpResponse, CodeConstant.EXPIRED_TOKEN, "token终止，请重新获取!");
		} else {
			chain.doFilter(httpRequest, httpResponse);
		}
	}

	private void doPrintWriter(HttpServletResponse response, int code, String message) {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");

		PrintWriter out = null;
		JSONObject res = new JSONObject();

		res.put("msg", message);
		res.put("code", code);
		try {
			out = response.getWriter();
		} catch (Exception e) {
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
	 * 
	 * @param url
	 * @return
	 */
	private boolean isInclude(String url) {
		return patterns.contains(url);
	}

}
