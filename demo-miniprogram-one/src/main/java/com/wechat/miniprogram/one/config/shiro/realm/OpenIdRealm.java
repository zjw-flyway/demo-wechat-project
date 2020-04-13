package com.wechat.miniprogram.one.config.shiro.realm;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

import com.wechat.miniprogram.one.entity.User;
import com.wechat.miniprogram.one.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author zhangjw
 * @Date 2020/4/10 11:46
 */
@Slf4j
public class OpenIdRealm extends ParentRealm {

	@Resource
	public UserService userService;

	/**
	 * 权限设置
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		return simpleAuthorizationInfo;
	}

	/**
	 * 身份验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		String openId = usernamePasswordToken.getUsername();
		User user = userService.findByOpenId(openId);

		if (user == null) {
			userService.addUser(openId);
		}

		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(openId, "123456", getName());
		return simpleAuthenticationInfo;
	}
}
