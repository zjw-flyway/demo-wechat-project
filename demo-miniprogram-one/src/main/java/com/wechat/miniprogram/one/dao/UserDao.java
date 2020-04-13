package com.wechat.miniprogram.one.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wechat.miniprogram.one.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Description
 * @Author zhangjw
 * @Date 2020/4/10 15:21
 */
public interface UserDao extends BaseMapper<User> {

	/**
	 * 根据openId查找用户
	 * @param openId
	 * @return
	 */
	@Select("select * from user where open_id=#{openId}")
	User findByOpenId(@Param("openId") String openId);

	/**
	 * 根据手机号码查找用户
	 * @param phone
	 * @return
	 */
	@Select("select * from user where phone=#{phone}")
	User findByPhone(@Param("phone") Integer phone);

	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	int addUser(@Param("user") User user);
}
