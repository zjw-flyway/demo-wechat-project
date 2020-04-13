package com.wechat.demo.core.config.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alibaba.fastjson.JSONObject;
import com.wechat.demo.core.constant.ErrorEnum;
import com.wechat.demo.core.entity.ResponseEntity;
import com.wechat.demo.core.utils.CommonUtil;

/**
 * @author: zjw
 * @description: 统一异常拦截
 * @date: 2020/03/20 10:31
 */
@RestControllerAdvice
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
	 * sessionKey不存在或超时报错
	 * @param sessionKeyTimeoutException
	 * @return
	 */
	@ExceptionHandler(SessionKeyTimeoutException.class)
	public ResponseEntity sessionKeyTimeoutException(SessionKeyTimeoutException sessionKeyTimeoutException) {
		return sessionKeyTimeoutException.getResultJson();
	}

	/**
	 * session中不存在openId的情况
	 * 前端应该封装调用http请求的，如果返回的是10001和10002错误的话，就应该再次调用wx.login方法，之后在调用本来请求的方法
	 * @param openIdMissingException
	 * @return
	 */
	@ExceptionHandler(OpenIdMissingException.class)
	public ResponseEntity openIdMissingException(OpenIdMissingException openIdMissingException) {
		return openIdMissingException.getResultJson();
	}

	/**
	 * 登录失败报错
	 * @param loginFailException
	 * @return
	 */
	@ExceptionHandler(LoginFailException.class)
	public ResponseEntity loginFailException(LoginFailException loginFailException) {
		return loginFailException.getResultJson();
	}

}
