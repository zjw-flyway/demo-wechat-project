package com.wechat.miniprogram.one.controller;

import com.wechat.miniprogram.one.config.shiro.WechatPasswordToken;
import com.wechat.demo.core.entity.ResponseEntity;
import com.wechat.demo.core.utils.CommonUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	/**
	 * 登录
	 * @param username
	 * @param rememberMe
	 * @param password
	 * @param loginType
	 * @return
	 */
	@GetMapping("/login")
	public ResponseEntity login(String username, Boolean rememberMe, String password, String loginType) {
		Subject subject = SecurityUtils.getSubject();
		WechatPasswordToken token = new WechatPasswordToken(username, password, loginType);

		if (rememberMe != null) {
			token.setRememberMe(rememberMe);
		}

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
			log.error("登录失败");
		}
		return CommonUtil.successJson("登录成功");
	}
}
