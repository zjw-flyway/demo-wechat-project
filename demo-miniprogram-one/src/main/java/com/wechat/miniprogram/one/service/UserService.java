package com.standard.demo.webapp.one.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.standard.demo.core.config.base.IService;
import com.standard.demo.web.core.entity.ResponseEntity;
import com.standard.demo.webapp.one.entity.User;

/**
 * @author: zjw
 * @description: 用户/角色/权限
 * @date: 2020/03/02 10:18
 */
public interface UserService extends IService<User> {
	/**
	 * 用户列表
	 */
	IPage listPageUser(User jsonObject);

	/**
	 * 添加用户
	 */
	ResponseEntity addUser(User jsonObject);

	/**
	 * 修改用户
	 */
	ResponseEntity updateUser(User jsonObject);
}
