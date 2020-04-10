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
	 * @param username
	 * @return
	 */
	User findByUsername(String username);

	/**
	 * 根据昵称查找用户
	 * @param nickName
	 * @return
	 */
	User findByNickname(String nickName);
}
