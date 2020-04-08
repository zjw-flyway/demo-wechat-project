package com.standard.demo.webapp.one.config.shiro;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.scheduling.annotation.Async;

import com.alibaba.fastjson.JSONObject;
import com.standard.demo.core.constant.CommonConstants;
import com.standard.demo.web.core.dto.UserDto;
import com.standard.demo.web.core.shiro.RedisSessionDao;
import com.standard.demo.web.core.utils.UserUtils;
import com.standard.demo.webapp.one.entity.User;
import com.standard.demo.webapp.one.service.LoginService;
import com.standard.demo.webapp.one.service.PermissionService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: zjw
 * @description: 自定义Realm
 * @date: 2020/03/24 10:06
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

	@Resource
	private LoginService loginService;

	@Resource
	private PermissionService permissionService;

	@Resource
	private RedisSessionDao redisSessionDao;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Session session = SecurityUtils.getSubject().getSession();
		// 查询用户的权限
		UserDto permission = (UserDto) session.getAttribute(CommonConstants.SESSION_USER_INFO);
		log.info("permission的值为:{}", JSONObject.toJSONString(permission));
		// 为当前用户设置角色和权限
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.addStringPermissions(permission.getPermissionList());
		return authorizationInfo;
	}

	/**
	 * 验证当前登录的Subject LoginController.login()方法中执行Subject.login()时 执行此方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		String loginName = (String) authcToken.getPrincipal();
		String password = new String((char[]) authcToken.getCredentials());
		// 获取用户密码
		User user = loginService.getUser(loginName);
		if (user == null || !user.getPassword().equals(UserUtils.encrytPassword(password))) {
			// 没找到帐号
			throw new UnknownAccountException("用户名或密码错误");
		}

		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();

		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUsername(),
				user.getPassword(),
				// ByteSource.Util.bytes("salt"), salt=username+salt,采用明文访问时，不需要此句
				getName());
		// session中不需要保存密码
		user.setPassword("");
		// 将用户信息放入session中
		UserDto userPermission = permissionService.getUserPermission(loginName);
		log.info("用户{}对应的用户信息为：{}", loginName, JSONObject.toJSONString(userPermission));
		session.setAttribute(CommonConstants.SESSION_USER_INFO, userPermission);

		//只允许一个帐号在一个地方登陆
		Collection<Session> sessions = redisSessionDao.getActiveSessions();
		deleteOtherSession(session, loginName, sessions);

		return authenticationInfo;
	}

	/**
	 * 同一个用户只能在一个地方登陆，删除他其他的session
	 */
	@Async
	public void deleteOtherSession(Session session, String loginName, Collection<Session> sessions) {
		if (sessions != null && sessions.size() > 0) {
			sessions.stream()
					.filter(oldSession -> ((UserDto) oldSession.getAttribute(CommonConstants.SESSION_USER_INFO))
							.getUsername().equals(loginName) && !oldSession.getId().equals(session.getId()))
					.forEach(oldSession -> {
						redisSessionDao.delete(oldSession);
						log.info("删除session:{}成功", JSONObject.toJSONString(oldSession));
					});
		}
	}
}
