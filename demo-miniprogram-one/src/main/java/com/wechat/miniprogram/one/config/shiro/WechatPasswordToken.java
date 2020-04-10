package com.wechat.miniprogram.one.config.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @Description 自定义的usernamePasswordToken
 * @Author zhangjw
 * @Date 2020/4/10 11:59
 */
public class WechatPasswordToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	//登录类型
	private String loginType;

	public WechatPasswordToken(String username, final String password, String loginType) {
		super(username, password);
		this.loginType = loginType;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
}
