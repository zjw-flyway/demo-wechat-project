package com.wechat.miniprogram.one.service.impl;

import com.wechat.miniprogram.one.entity.User;
import com.wechat.miniprogram.one.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description
 * @Author zhangjw
 * @Date 2020/4/10 12:43
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Override
	public User findByUsername(String username) {
		User user = new User();
		if ("user1".equals(username)) {
			Set<String> roleList = new HashSet<>();
			Set<String> permissionsList = new HashSet<>();
			roleList.add("admin");
			permissionsList.add("admin");
			user.setUsername("user1");
			user.setPassword("4280d89a5a03f812751f504cc10ee8a5");
			return user;
		} else {
			return null;
		}
	}

	@Override
	public User findByNickname(String nickName) {
		User user = new User();
		if ("user1".equals(nickName)) {
			Set<String> roleList = new HashSet<>();
			Set<String> permissionsList = new HashSet<>();
			roleList.add("user");
			permissionsList.add("user");
			user.setNickname("user1");
			user.setPassword("4280d89a5a03f812751f504cc10ee8a5");
			return user;
		} else {
			return null;
		}
	}
}
