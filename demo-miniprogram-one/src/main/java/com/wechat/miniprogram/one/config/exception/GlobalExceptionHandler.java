package com.standard.demo.webapp.one.config.exception;

import com.alibaba.fastjson.JSONObject;
import com.standard.demo.web.core.constant.ErrorEnum;
import com.standard.demo.web.core.entity.ResponseEntity;
import com.standard.demo.web.core.utils.CommonUtil;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zjw
 * @description: 统一异常拦截
 * @date: 2020/03/20 10:31
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	/**
	 * 统一的错误处理方法
	 * 如果系统中出现了位置的没有被catch的错误，则统一通过这个方法进行报错，在前端展示相应的错误内容
	 * @param req
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity defaultErrorHandler(HttpServletRequest req, Exception e) {
		String errorPosition = "";
		// 如果错误堆栈信息存在
		if (e.getStackTrace().length > 0) {
			StackTraceElement element = e.getStackTrace()[0];
			String fileName = element.getFileName() == null ? "未找到错误文件" : element.getFileName();
			int lineNumber = element.getLineNumber();
			errorPosition = fileName + ":" + lineNumber;
		}

		JSONObject errorObject = new JSONObject();
		errorObject.put("errorLocation", e.toString() + " 错误位置:" + errorPosition);

		logger.error("异常", e);
		return new ResponseEntity(ErrorEnum.E_400.getErrorCode(), ErrorEnum.E_400.getErrorMsg(), errorObject);
	}

	/**
	 * GET/POST请求方法错误的拦截器，因为开发时可能比较常见,而且发生在进入controller之前,上面的拦截器拦截不到这个错误，所以定义了这个拦截器
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity httpRequestMethodHandler() {
		return CommonUtil.errorJson(ErrorEnum.E_500);
	}

	/**
	 * 本系统自定义错误的拦截器，拦截到此错误之后，就返回这个类里面的json给前端，常见使用场景是参数校验失败,抛出此错,返回错误信息给前端
	 */
	@ExceptionHandler(CommonJsonException.class)
	public ResponseEntity commonJsonExceptionHandler(CommonJsonException commonJsonException) {
		return commonJsonException.getResultJson();
	}

	/**
	 * 权限不足报错拦截
	 */
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity unauthorizedExceptionHandler() {
		return CommonUtil.errorJson(ErrorEnum.E_502);
	}

	/**
	 * 未登录报错拦截 在请求需要权限的接口,而连登录都还没登录的时候,会报此错
	 */
	@ExceptionHandler(UnauthenticatedException.class)
	public ResponseEntity unauthenticatedException() {
		return CommonUtil.errorJson(ErrorEnum.E_20011);
	}
}
