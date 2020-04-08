package com.wechat.demo.core.constant;

/**
 * @Description 状态码
 * @Author zhangjw
 * @Date 2020/3/5 16:21
 */
public interface CommonConstants {
	/**
	 * 前台接口路径前缀
	 */
	String FRONT = "/f";

	/**
	 * 后台接口路径前缀
	 */
	String ADMIN = "/a";

	/**
	 * 编码
	 */
	String UTF8 = "UTF-8";

	/**
	 * JSON 资源
	 */
	String CONTENT_TYPE = "application/json; charset=utf-8";

	/**
	 * 成功标记
	 */
	Integer SUCCESS = 200;

	/**
	 * 失败标记
	 */
	Integer FAIL = 1;

	/**
	 * 参数校验失败
	 */
	Integer VALIDATION_FAILED = 2;

	/**
	 * ID 分隔符
	 */
	String ID_SEPARATOR = ",";

	/**
	 * 默认当前页数
	 */
	Integer PAGE_NO = 1;

	/**
	 * 默认页大小
	 */
	Integer PAGE_SIZE = 15;

	/**
	 * session中存放用户信息的key值
	 */
	String SESSION_USER_INFO = "userInfo";
}
