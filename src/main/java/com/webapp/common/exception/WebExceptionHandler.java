package com.webapp.common.exception;

import com.webapp.common.utils.BaseResponce;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 * 
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2016年10月27日 下午10:16:19
 */
@RestControllerAdvice
public class WebExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(WebException.class)
	public BaseResponce handleRRException(WebException e){
		BaseResponce r = new BaseResponce();
		r.put("code", e.getCode());
		r.put("msg", e.getMessage());

		return r;
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public BaseResponce handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return BaseResponce.error("数据库中已存在该记录");
	}

	@ExceptionHandler(AuthorizationException.class)
	public BaseResponce handleAuthorizationException(AuthorizationException e){
		logger.error(e.getMessage(), e);
		return BaseResponce.error("没有权限，请联系管理员授权");
	}

	@ExceptionHandler(Exception.class)
	public BaseResponce handleException(Exception e){
		logger.error(e.getMessage(), e);
		return BaseResponce.error();
	}
}
