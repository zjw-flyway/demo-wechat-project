package com.wechat.miniprogram.one.service.impl;

import java.time.LocalDateTime;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.demo.core.config.base.BaseServiceImpl;
import com.wechat.miniprogram.one.dao.UserDao;
import com.wechat.miniprogram.one.entity.User;
import com.wechat.miniprogram.one.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author zhangjw
 * @Date 2020/4/10 12:43
 */
@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<UserDao, User> implements UserService {

	@Resource
	private UserDao userDao;

	@Override
	public User findByOpenId(String openId) {
		return userDao.findByOpenId(openId);
	}

	@Override
	public User findByPhone(String phone) {
		return userDao.findByPhone(phone);
	}

	@Override
	public int addUser(String openId) {
		User user = new User();
		user.setOpenId(openId);
		user.setCreateTime(LocalDateTime.now());
		user.setUpdateTime(LocalDateTime.now());

		log.info("添加用户:{}成功", JSONObject.toJSONString(user));
		return userDao.addUser(user);
	}
}
