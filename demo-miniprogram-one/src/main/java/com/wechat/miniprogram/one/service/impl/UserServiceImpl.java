package com.standard.demo.webapp.one.service.impl;

import java.time.LocalDateTime;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.standard.demo.core.config.base.BaseServiceImpl;
import com.standard.demo.web.core.constant.ErrorEnum;
import com.standard.demo.web.core.entity.ResponseEntity;
import com.standard.demo.web.core.utils.CommonUtil;
import com.standard.demo.web.core.utils.UserUtils;
import com.standard.demo.webapp.one.dao.UserDao;
import com.standard.demo.webapp.one.entity.User;
import com.standard.demo.webapp.one.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: zjw
 * @description: 用户/角色/权限
 * @date: 2020/03/02 10:18
 */
@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<UserDao, User> implements UserService {

	@Resource
	private UserDao userDao;

	/**
	 * 分页查询用户列表
	 */
	@Override
	public IPage listPageUser(User jsonObject) {
		LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(jsonObject.getUsername())) {
			lambdaQueryWrapper.eq(User::getUsername, jsonObject.getUsername());
		}

		Page<User> page = new Page<>(jsonObject.getPageNo(), jsonObject.getPageSize());
		IPage<User> userIPage = userDao.selectPage(page, lambdaQueryWrapper);
		return userIPage;
	}

	/**
	 * 添加用户
	 */
	@Override
	public ResponseEntity addUser(User jsonObject) {
		int exist = userDao.queryExistUsername(jsonObject.getUsername());
		if (exist > 0) {
			log.info("创建用户:{}失败，原因为用户名重复", jsonObject.getUsername());
			return CommonUtil.errorJson(ErrorEnum.E_10009);
		}

		jsonObject.setPassword(UserUtils.encrytPassword(jsonObject.getPassword()));
		userDao.addUser(jsonObject);
		userDao.updateUserCreateAndUpdateBy(jsonObject.getId());

		log.info("创建用户{}成功，用户id为:{}", jsonObject.getUsername(), jsonObject.getId());
		return CommonUtil.successJson();
	}

	/**
	 * 修改用户
	 */
	@Override
	public ResponseEntity updateUser(User jsonObject) {
		jsonObject.setUpdateBy(UserUtils.getCurrentUserId());
		jsonObject.setUpdateTime(LocalDateTime.now());
		userDao.updateById(jsonObject);
		return CommonUtil.successJson();
	}
}
