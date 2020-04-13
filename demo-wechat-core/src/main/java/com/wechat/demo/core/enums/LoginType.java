package com.wechat.demo.core.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * @Description
 * @Author zhangjw
 * @Date 2020/4/10 15:39
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum LoginType {

	OPENID("OpenId"),

	PHONE("Phone");

	String type;
}
