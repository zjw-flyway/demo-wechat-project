package com.standard.demo.webapp.one.service;

import com.standard.demo.core.config.base.IService;
import com.standard.demo.web.core.entity.ResponseEntity;
import com.standard.demo.webapp.one.entity.Role;

/**
 * @Description 角色service
 * @Author zhangjw
 * @Date 2020/3/17 16:53
 */
public interface RoleService extends IService<Role> {

	/**
	 * 查询所有的角色 在添加/修改用户的时候要使用此方法
	 */
	ResponseEntity getAllRoles();

	/**
	 * 添加角色
	 */
	ResponseEntity addRole(Role jsonObject);

	/**
	 * 修改角色
	 */
	ResponseEntity updateRole(Role jsonObject);

	/**
	 * 删除角色
	 */
	ResponseEntity deleteRole(Role jsonObject);

}
