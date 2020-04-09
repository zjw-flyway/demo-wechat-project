package com.wechat.miniprogram.one.service;

import com.wechat.demo.core.entity.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author zhangjw
 * @Date 2020/4/8 16:31
 */
public interface WechatService {

	/**
	 * 获取用户信息
	 * @param code
	 * @return
	 */
	ResponseEntity code2Session(String code, HttpServletRequest request);

	/**
	 * 获取手机号码
	 * @param iv
	 * @param encryptedData
	 * @param request
	 * @return
	 */
	ResponseEntity getPhoneNumber(String iv, String encryptedData, HttpServletRequest request);
}
