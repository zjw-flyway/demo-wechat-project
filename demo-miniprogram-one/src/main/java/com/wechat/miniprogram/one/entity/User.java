package com.wechat.miniprogram.one.entity;

import com.wechat.demo.core.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

/**
 * @Description
 * @Author zhangjw
 * @Date 2020/3/17 14:03
 */
@Data
@FieldDefaults(level = AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户")
public class User extends BaseEntity {

	/**
	 * 用户名
	 */
	String nickName;

	/**
	 * 密码
	 */
	String password;

	/**
	 * 昵称链接
	 */
	String avatarUrl;

	/**
	 * 性别，0为未知，1为男，2为女
	 */
	Integer gender;

	/**
	 * 手机号码
	 */
	Integer phone;

	/**
	 * openId
	 */
	String openId;
}
