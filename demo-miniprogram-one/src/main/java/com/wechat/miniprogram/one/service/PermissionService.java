package com.standard.demo.webapp.one.service;

import com.standard.demo.core.config.base.IService;
import com.standard.demo.web.core.dto.UserDto;
import com.standard.demo.web.core.entity.ResponseEntity;
import com.standard.demo.webapp.one.entity.Permission;

/**
 * @author: zjw
 * @date: 2020/03/30 13:10
 */
public interface PermissionService extends IService<Permission> {
	/**
	 * 查询某用户的角色菜单列表权限列表
	 */
	UserDto getUserPermission(String username);

	/**
	 * 查询所有权限, 给角色分配权限时调用
	 */
	ResponseEntity listAllPermission();
}
