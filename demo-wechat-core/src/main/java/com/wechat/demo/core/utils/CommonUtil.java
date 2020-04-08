package com.wechat.demo.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.wechat.demo.core.constant.ErrorEnum;
import com.wechat.demo.core.entity.ResponseEntity;

/**
 * @author: zjw
 * @description: 本后台接口系统常用的json工具类
 * @date: 2020/03/23 10:12
 */
public class CommonUtil {

	/**
	 * 返回一个info为空对象的成功消息的json
	 */
	public static ResponseEntity successJson() {
		return successJson(new JSONObject());
	}

	/**
	 * 返回一个返回码为100的json
	 */
	public static ResponseEntity successJson(Object info) {
		return new ResponseEntity(ErrorEnum.SUCCESS_CODE.getErrorCode(), ErrorEnum.SUCCESS_CODE.getErrorMsg(), info);
	}

	/**
	 * 返回错误信息JSON
	 */
	public static ResponseEntity errorJson(ErrorEnum errorEnum) {
		return new ResponseEntity(errorEnum.getErrorCode(), errorEnum.getErrorMsg(), new JSONObject());
	}

	/**
	 * 返回错误信息
	 * @param errorEnum
	 * @return
	 */
	public static ResponseEntity errorJson(String errorEnum) {
		return new ResponseEntity(ErrorEnum.E_400.getErrorCode(), errorEnum, new JSONObject());
	}
}
