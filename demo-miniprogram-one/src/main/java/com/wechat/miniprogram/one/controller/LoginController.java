package com.wechat.miniprogram.one.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wechat.demo.core.config.exception.LoginFailException;
import com.wechat.demo.core.constant.ErrorEnum;
import com.wechat.demo.core.entity.ResponseEntity;
import com.wechat.demo.core.enums.LoginType;
import com.wechat.demo.core.utils.CommonUtil;
import com.wechat.miniprogram.one.config.shiro.WechatPasswordToken;
import com.wechat.miniprogram.one.service.WechatService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author zhangjw
 * @Date 2020/4/10 12:36
 */
@RestController
@Slf4j
@Api(tags = "登录退出")
@RequestMapping("/login")
public class LoginController {

	@Resource
	private WechatService wechatService;

	/**
	 * 登录
	 * @return
	 */
	@GetMapping("/openId/login")
	@ApiImplicitParam(name = "code", type = "String", required = true)
	public ResponseEntity openIdLogin(@RequestParam String code, HttpServletRequest request) {
		ResponseEntity responseEntity = wechatService.code2Session(code, request);
		if (responseEntity.getCode() != ErrorEnum.SUCCESS_CODE.getErrorCode()) {
			return responseEntity;
		}

		//shiro进行登录操作
		String openId = (String) responseEntity.getData();
		Subject subject = SecurityUtils.getSubject();
		WechatPasswordToken token = new WechatPasswordToken(openId, "123456", LoginType.OPENID.getType());

		try {
			// 登录
			subject.login(token);
			//} catch (UnknownAccountException uae) {
			//	// 用户名未知...
			//	return new ReturnMap().fail().message("用户不存在！");
			//} catch (IncorrectCredentialsException ice) {
			//	// 凭据不正确，例如密码不正确 ...
			//	return new ReturnMap().fail().message("密码不正确！");
			//} catch (LockedAccountException lae) {
			//	// 用户被锁定，例如管理员把某个用户禁用...
			//	return new ReturnMap().fail().message("用户被锁定！");
			//} catch (ExcessiveAttemptsException eae) {
			//	// 尝试认证次数多余系统指定次数 ...
			//	return new ReturnMap().fail().message("尝试认证次数过多，请稍后重试！");
			//} catch (AuthenticationException ae) {
			//	// 其他未指定异常
			//	return new ReturnMap().fail().message("未知异常！");
			//}
		} catch (Exception e) {
			throw new LoginFailException();
		}
		return CommonUtil.successJson("登录成功");
	}

	/**
	 * 登录
	 * @return
	 */
	@GetMapping("/phone/login")
	@ApiImplicitParams({ @ApiImplicitParam(name = "phone", type = "String", required = true),
			@ApiImplicitParam(name = "password", type = "String", required = true) })
	public ResponseEntity phoneLogin(@RequestParam String phone, @RequestParam String password) {
		//shiro进行登录操作

		Subject subject = SecurityUtils.getSubject();
		WechatPasswordToken token = new WechatPasswordToken(phone, password, LoginType.PHONE.getType());

		try {
			// 登录
			subject.login(token);
			//} catch (UnknownAccountException uae) {
			//	// 用户名未知...
			//	return new ReturnMap().fail().message("用户不存在！");
			//} catch (IncorrectCredentialsException ice) {
			//	// 凭据不正确，例如密码不正确 ...
			//	return new ReturnMap().fail().message("密码不正确！");
			//} catch (LockedAccountException lae) {
			//	// 用户被锁定，例如管理员把某个用户禁用...
			//	return new ReturnMap().fail().message("用户被锁定！");
			//} catch (ExcessiveAttemptsException eae) {
			//	// 尝试认证次数多余系统指定次数 ...
			//	return new ReturnMap().fail().message("尝试认证次数过多，请稍后重试！");
			//} catch (AuthenticationException ae) {
			//	// 其他未指定异常
			//	return new ReturnMap().fail().message("未知异常！");
			//}
		} catch (Exception e) {
			throw new LoginFailException();
		}
		return CommonUtil.successJson("登录成功");
	}
}
