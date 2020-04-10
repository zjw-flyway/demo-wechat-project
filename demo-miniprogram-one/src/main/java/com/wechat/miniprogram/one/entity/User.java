package com.wechat.miniprogram.one.entity;

import com.baomidou.mybatisplus.annotation.TableName;

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
@TableName("sys_user")
public class User extends BaseEntity {

	/**
	 * 用户名
	 */
	String username;

	/**
	 * 密码
	 */
	String password;

	/**
	 * 昵称
	 */
	String nickname;
}
