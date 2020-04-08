package com.standard.demo.webapp.one.controller;

import javax.annotation.Resource;

import com.standard.demo.web.core.entity.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.standard.demo.webapp.one.entity.User;
import com.standard.demo.webapp.one.service.LoginService;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * @author: zjw
 * @description: 登录相关Controller
 * @date: 2020/03/10 10:33
 */
@RestController
@RequestMapping("/login")
@Slf4j
@Api(tags = "登录")
public class LoginController {

	@Resource
	private LoginService loginService;

	/**
	 * 登录
	 */
	@PostMapping("/login")
	@ApiOperation("登录")
	public ResponseEntity authLogin(@RequestBody User user) {
		return loginService.authLogin(user);
	}

	/**
	 * 查询当前登录用户的信息
	 */
	@GetMapping("/getInfo")
	@ApiOperation("获取当前登录人信息")
	public ResponseEntity getInfo() {
		return loginService.getInfo();
	}

	/**
	 * 登出
	 */
	@PostMapping("/logout")
	@ApiOperation("退出")
	public ResponseEntity logout() {
		return loginService.logout();
	}
}
