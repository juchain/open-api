package com.blockshine.common.web;

import com.blockshine.common.constant.CodeConstant;
import com.blockshine.common.exception.BusinessException;
import com.blockshine.common.exception.InvalidTokenBusinessException;
import com.blockshine.common.util.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基本异常处理类
 *
 * @author maxiaodong
 */
@RestController
public abstract class BaseController {

	@ExceptionHandler
	public R handleException(Exception e) {
		if (e instanceof BusinessException) {
			BusinessException businessException = (BusinessException) e;
			R r = new R();
			r.put("code", businessException.getCode());
			r.put("msg", businessException.getMessage());
			return r;
		} else if (e instanceof InvalidTokenBusinessException) {
			InvalidTokenBusinessException exception = (InvalidTokenBusinessException) e;
			R r = new R();
			r.put("code", exception.getCode());
			r.put("msg", exception.getMessage());
			return r;
		} else {

			R r = new R();
			r.put("code", CodeConstant.INTERAL_ERROR);
			r.put("msg", e.getMessage());
			return r;
		}
	}

}
