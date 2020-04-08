package com.standard.demo.webapp.one.controller;

import com.standard.demo.web.core.entity.ResponseEntity;
import com.standard.demo.web.core.utils.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.standard.demo.webapp.one.entity.User;
import com.standard.demo.webapp.one.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author: zjw
 * @description: 用户/角色/权限相关controller
 * @date: 2020/03/02 10:19
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 查询用户列表
	 */
	@RequiresPermissions("user:list")
	@GetMapping("/pageList")
	@ApiOperation("查询用户列表")
	public ResponseEntity listUser(User user) {
		return CommonUtil.successJson(userService.listPageUser(user));
	}

	@RequiresPermissions("user:add")
	@PostMapping("/addUser")
	@ApiOperation("新增用户")
	public ResponseEntity addUser(@RequestBody User requestJson) {
		return userService.addUser(requestJson);
	}

	@RequiresPermissions("user:update")
	@PostMapping("/updateUser")
	@ApiOperation("更新用户")
	public ResponseEntity updateUser(@RequestBody User requestJson) {
		return userService.updateUser(requestJson);
	}
}
