package com.wechat.miniprogram.one.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wechat.demo.core.entity.ResponseEntity;
import com.wechat.demo.core.utils.WechatUtils;
import com.wechat.miniprogram.one.service.WechatService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author zhangjw
 * @Date 2020/4/8 16:31
 */
@RestController
@Api(tags = "微信操作")
@Slf4j
@RequestMapping("/wechat")
public class WechatController {

	@Resource
	private WechatService wechatService;

	/**
	 * 用code来登录
	 * @param code
	 * @param request
	 * @return
	 */
	@GetMapping("code2Session")
	@ApiImplicitParam(name = "code", type = "String", required = true)
	public ResponseEntity code2Session(String code, HttpServletRequest request) {
		return wechatService.code2Session(code, request);
	}

	/**
	 * 获得用户的openId
	 * @return
	 */
	@GetMapping("getOpenId")
	public ResponseEntity getOpenId(HttpServletRequest request) {
		return new ResponseEntity(WechatUtils.getOpenId(request));
	}

	/**
	 * 获取手机号码
	 * @param iv
	 * @param encryptedData
	 * @param request
	 * @return
	 */
	@GetMapping("getPhoneNumber")
	@ApiImplicitParams({ @ApiImplicitParam(name = "iv", value = "iv", required = true),
			@ApiImplicitParam(name = "encryptedData", value = "encryptedData", required = true) })
	public ResponseEntity getPhoneNumber(String iv, String encryptedData, HttpServletRequest request) {
		return wechatService.getPhoneNumber(iv, encryptedData, request);
	}
}
