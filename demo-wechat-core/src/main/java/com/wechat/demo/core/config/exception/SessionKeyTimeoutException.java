package com.wechat.demo.core.config.exception;

import com.wechat.demo.core.constant.ErrorEnum;
import com.wechat.demo.core.entity.ResponseEntity;

/**
 * @author: zjw
 * @description: sessionKey解密数据失败自定义错误
 * @date: 2020/03/24 10:29
 */
public class SessionKeyTimeoutException extends RuntimeException {

	private ResponseEntity resultJson;

	public SessionKeyTimeoutException() {
		resultJson = new ResponseEntity(ErrorEnum.E_10001.getErrorMsg(), ErrorEnum.E_10001.getErrorCode());
	}

	public ResponseEntity getResultJson() {
		return resultJson;
	}
}
