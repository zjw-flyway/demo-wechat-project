package com.wechat.demo.core.config.exception;

import com.wechat.demo.core.constant.ErrorEnum;
import com.wechat.demo.core.entity.ResponseEntity;

/**
 * @Description 登录失败
 * @Author zhangjw
 * @Date 2020/4/10 15:52
 */
public class LoginFailException extends RuntimeException {

	private ResponseEntity resultJson;

	public LoginFailException() {
		resultJson = new ResponseEntity(ErrorEnum.E_10011.getErrorMsg(), ErrorEnum.E_10011.getErrorCode());
	}

	public ResponseEntity getResultJson() {
		return resultJson;
	}
}