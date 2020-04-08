package com.wechat.demo.core.constant;

/**
 * @author: zjw 前台请求错误枚举类
 * @date: 2020/03/25 10:16
 */
public enum ErrorEnum {

	SUCCESS_CODE(200, "请求成功"),

	E_400(400, "请求处理异常，请稍后再试"),

	E_500(500, "请求方式有误,请检查 GET/POST"),

	E_404(404, "请求路径不存在"),

	E_502(502, "权限不足"),

	E_10008(10008, "角色删除失败,尚有用户属于此角色"),

	E_10009(10009, "账户已存在"),

	E_20011(20011, "登陆已过期,请重新登陆"),

	E_90004(90004, "用户名或密码错误"),

	E_10000(10000, "自定义错误");

	private Integer errorCode;

	private String errorMsg;

	ErrorEnum(Integer errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

}