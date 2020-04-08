package com.standard.demo.webapp.one.service.impl;

import javax.annotation.Resource;

import com.standard.demo.core.config.base.BaseServiceImpl;
import com.standard.demo.core.constant.CommonConstants;
import com.standard.demo.web.core.constant.ErrorEnum;
import com.standard.demo.web.core.dto.UserDto;
import com.standard.demo.web.core.entity.ResponseEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.standard.demo.webapp.one.dao.UserDao;
import com.standard.demo.webapp.one.entity.User;
import com.standard.demo.webapp.one.service.LoginService;
import com.standard.demo.web.core.utils.CommonUtil;
import com.standard.demo.web.core.utils.UserUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: zjw
 * @description: 登录service实现类
 * @date: 2020/03/17 11:53
 */
@Service
@Slf4j
public class LoginServiceImpl extends BaseServiceImpl<UserDao, User> implements LoginService {

	@Resource
	private UserDao userDao;

	/**
	 * 登录表单提交
	 */
	@Override
	public ResponseEntity authLogin(User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		JSONObject info = new JSONObject();
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			currentUser.login(token);
			info.put("result", "success");
			log.info("用户：{}登录成功", user.getUsername());
			return CommonUtil.successJson(info);
		} catch (AuthenticationException e) {
			info.put("result", "fail");
			log.error("用户{}登录失败，用户名或密码错误", user.getUsername());
			return CommonUtil.errorJson(ErrorEnum.E_90004);
		}
	}

	/**
	 * 根据用户名和密码查询对应的用户
	 */
	@Override
	public User getUser(String username) {
		return userDao.getUser(username);
	}

	/**
	 * 查询当前登录用户的权限等信息
	 */
	@Override
	public ResponseEntity getInfo() {
		return CommonUtil.successJson(UserUtils.getUser());
	}

	/**
	 * 退出登录
	 */
	@Override
	public ResponseEntity logout() {
		try {
			Session session = SecurityUtils.getSubject().getSession();
			UserDto userInfo = (UserDto) session.getAttribute(CommonConstants.SESSION_USER_INFO);
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.logout();
			log.info("用户{}退出成功", JSONObject.toJSONString(userInfo));
		} catch (Exception e) {

		}
		return CommonUtil.successJson();
	}
}
