package com.standard.demo.webapp.one.service;

import com.standard.demo.core.config.base.IService;
import com.standard.demo.web.core.entity.ResponseEntity;
import com.standard.demo.webapp.one.entity.User;

/**
 * @author: hxy
 * @description: 登录Service
 * @date: 2017/10/24 11:02
 */
public interface LoginService extends IService<User> {
	/**
	 * 登录表单提交
	 */
	ResponseEntity authLogin(User jsonObject);

	/**
	 * 根据用户名和密码查询对应的用户
	 *
	 * @param username
	 *            用户名
	 */
	User getUser(String username);

	/**
	 * 查询当前登录用户的权限等信息
	 */
	ResponseEntity getInfo();

	/**
	 * 退出登录
	 */
	ResponseEntity logout();
}
