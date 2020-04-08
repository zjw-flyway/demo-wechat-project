package com.standard.demo.webapp.one.controller;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.standard.demo.web.core.entity.ResponseEntity;
import com.standard.demo.webapp.one.entity.Role;
import com.standard.demo.webapp.one.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author zhangjw
 * @Date 2020/3/17 18:16
 */
@RestController
@RequestMapping("/role")
@Slf4j
@Api(tags = "角色")
public class RoleController {

	@Resource
	private RoleService roleService;

	/**
	 * 查询所有的角色
	 * @return
	 */
	@RequiresPermissions(value = { "user:add", "user:update" }, logical = Logical.OR)
	@GetMapping("/getAllRoles")
	@ApiOperation("查询所有角色")
	public ResponseEntity getAllRoles() {
		return roleService.getAllRoles();
	}

	/**
	 * 修改角色
	 */
	@RequiresPermissions("role:update")
	@PostMapping("/updateRole")
	@ApiOperation("修改角色")
	public ResponseEntity updateRole(@RequestBody Role requestJson) {
		return roleService.updateRole(requestJson);
	}

	/**
	 * 新增角色
	 */
	@RequiresPermissions("role:add")
	@PostMapping("/addRole")
	@ApiOperation("新增角色")
	public ResponseEntity addRole(@RequestBody Role requestJson) {
		return roleService.addRole(requestJson);
	}

	/**
	 * 删除角色
	 */
	@RequiresPermissions("role:delete")
	@PostMapping("/deleteRole")
	@ApiOperation("删除角色")
	public ResponseEntity deleteRole(@RequestBody Role requestJson) {
		return roleService.deleteRole(requestJson);
	}
}
