package com.wechat.miniprogram.one.config.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @Description 父级的realm
 * @Author zhangjw
 * @Date 2020/4/10 11:35
 */
public abstract class ParentRealm extends AuthorizingRealm {

	/**
	 * 重写方法,清除当前用户的的授权缓存
	 */
	public void clearCachedAuthorizationInfo() {
		super.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
	}

	/**
	 * 重写方法，清除当前用户的认证缓存
	 */
	public void clearCachedAuthenticationInfo() {
		super.clearCachedAuthenticationInfo(SecurityUtils.getSubject().getPrincipals());
	}

	/**
	 * 清除某个用户认证和授权缓存
	 */
	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	/**
	 * 自定义方法：清除所有授权缓存
	 */
	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	/**
	 * 自定义方法：清除所有认证缓存
	 */
	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	/**
	 * 自定义方法：清除所有的认证缓存和授权缓存
	 */
	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}
}
