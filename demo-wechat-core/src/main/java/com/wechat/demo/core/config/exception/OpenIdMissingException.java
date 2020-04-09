package com.wechat.demo.core.config.exception;

import com.wechat.demo.core.constant.ErrorEnum;
import com.wechat.demo.core.entity.ResponseEntity;

/**
 * @author: zjw
 * @description: session中不存在openId的报错
 * @date: 2020/03/24 10:29
 */
public class OpenIdMissingException extends RuntimeException {

	private ResponseEntity resultJson;

	public OpenIdMissingException() {
		resultJson = new ResponseEntity(ErrorEnum.E_10001.getErrorMsg(), ErrorEnum.E_10001.getErrorCode());
	}

	public ResponseEntity getResultJson() {
		return resultJson;
	}
}
