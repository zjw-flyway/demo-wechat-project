package com.wechat.miniprogram.one.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.wechat.demo.core.utils.WechatUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.demo.core.constant.ErrorEnum;
import com.wechat.demo.core.constant.WechatConstants;
import com.wechat.demo.core.entity.ResponseEntity;
import com.wechat.demo.core.utils.CommonUtil;
import com.wechat.demo.core.utils.HttpUtils;
import com.wechat.miniprogram.one.service.WechatService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author zhangjw
 * @Date 2020/4/8 16:32
 */
@Service
@Slf4j
public class WechatServiceImpl implements WechatService {

	@Value("${wechat.appId}")
	private String appId;

	@Value("${wechat.secret}")
	private String secret;

	@Value("${wechat.url.getUserInfo}")
	private String wechatUrlGetUserInfo;

	@Override
	public ResponseEntity code2Session(String code, HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("grant_type", "authorization_code");
		paramMap.put("appid", appId);
		paramMap.put("secret", secret);
		paramMap.put("js_code", code);

		String result = HttpUtils.sendGet(wechatUrlGetUserInfo, paramMap);
		if (StringUtils.isBlank(result)) {
			log.error("code:{}登录失败", code);
			return CommonUtil.errorJson(ErrorEnum.E_10011);
		}

		JSONObject resultObj = JSONObject.parseObject(result);
		if (result.contains(WechatConstants.errorCode)) {
			log.error("code:{}登录失败，原因为：{}", code, result);
			return CommonUtil.errorJson(ErrorEnum.E_10011);
		}

		String openId = resultObj.getString(WechatConstants.openId);
		String sessionKey = resultObj.getString(WechatConstants.sessionKey);
		String unionId = resultObj.getString(WechatConstants.unionid);

		HttpSession session = request.getSession();
		session.setAttribute(WechatConstants.openId, openId);
		session.setAttribute(WechatConstants.sessionKey, sessionKey);
		session.setAttribute(WechatConstants.unionid, unionId);

		log.info("code:{}登录成功，获取的信息为：{}，返回的sessionid为：{}", code, result, session.getId());
		return CommonUtil.successJson(session.getId());
	}

	@Override
	public ResponseEntity getPhoneNumber(String iv, String encryptedData, HttpServletRequest request) {
		String resultData = WechatUtils.decrypt(appId, encryptedData, WechatUtils.getSessionKey(request), iv);
		log.info("iv：{}和encryptedData：{}解压出来的数据为：{}", iv, encryptedData, resultData);
		return CommonUtil.successJson(resultData);
	}
}
