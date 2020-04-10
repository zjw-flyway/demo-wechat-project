package com.wechat.miniprogram.one.config.shiro.realm;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
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
 * @Date 2020/4/10 11:48
 */
@Slf4j
public class UsernameRealm extends ParentRealm {

	@Resource
	public UserService userService;

	/**
	 * 权限设置
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();
		User user = userService.findByUsername(username);
		// 获取用户角色
		Set<String> roles = new HashSet<>();
		// 获取用户权限
		Set<String> permissions = new HashSet<>();

		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		// 设置权限
		simpleAuthorizationInfo.setStringPermissions(permissions);
		// 设置角色
		simpleAuthorizationInfo.setRoles(roles);
		return simpleAuthorizationInfo;
	}

	/**
	 * 身份验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		String username = usernamePasswordToken.getUsername();// 用户输入用户名
		User user = userService.findByUsername(username);// 根据用户输入用户名查询该用户.
		if (user == null) {
			throw new UnknownAccountException();
		}
		//if ("2".equals(user.getState())) {
		//	throw new LockedAccountException();
		//}
		String password = user.getPassword();
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, password, getName());
		return simpleAuthenticationInfo;
	}
}
