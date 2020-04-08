package com.standard.demo.webapp.one.config.exception;

import com.standard.demo.web.core.constant.ErrorEnum;
import com.standard.demo.web.core.entity.ResponseEntity;

/**
 * @author: zjw
 * @description: 本系统使用的自定义错误类
 * 比如在校验参数时,如果不符合要求,可以抛出此错误类
 * 拦截器可以统一拦截此错误,将其中json返回给前端
 * @date: 2020/03/24 10:29
 */
public class CommonJsonException extends RuntimeException {

	private ResponseEntity resultJson;

	public CommonJsonException(String message) {
		resultJson = new ResponseEntity(message, ErrorEnum.E_10000.getErrorCode());
	}

	public ResponseEntity getResultJson() {
		return resultJson;
	}
}
