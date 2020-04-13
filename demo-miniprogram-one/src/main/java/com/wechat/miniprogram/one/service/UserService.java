package com.wechat.miniprogram.one.service;

import com.wechat.miniprogram.one.entity.User;

/**
 * @Description
 * @Author zhangjw
 * @Date 2020/4/10 12:41
 */
public interface UserService {

	/**
	 * 根据用户名查找用户
	 * @param openId
	 * @return
	 */
	User findByOpenId(String openId);

	/**
	 * 根据手机号码查找用户
	 * @param phone
	 * @return
	 */
	User findByPhone(Integer phone);

	/**
	 * 添加用户
	 * @param openId
	 * @return
	 */
	int addUser(String openId);
}
