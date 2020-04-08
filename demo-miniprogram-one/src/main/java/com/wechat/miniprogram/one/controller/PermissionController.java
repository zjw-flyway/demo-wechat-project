package com.standard.demo.webapp.one.controller;

import javax.annotation.Resource;

import com.standard.demo.web.core.entity.ResponseEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.standard.demo.webapp.one.service.PermissionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author zhangjw
 * @Date 2020/3/17 18:19
 */
@RequestMapping("/permission")
@RestController
@Slf4j
@Api(tags = "权限")
public class PermissionController {

	@Resource
	private PermissionService permissionService;

	/**
	 * 查询所有权限, 给角色分配权限时调用
	 */
	@RequiresPermissions("role:list")
	@GetMapping("/listAllPermission")
	@ApiOperation("查询所有权限")
	public ResponseEntity listAllPermission() {
		return permissionService.listAllPermission();
	}
}
