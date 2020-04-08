package com.standard.demo.webapp.one.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.standard.demo.core.config.base.BaseDataMapper;
import com.standard.demo.web.core.dto.UserDto;
import com.standard.demo.webapp.one.entity.Permission;

/**
 * @author: zjw
 * @date: 2020/03/30 13:28
 */
public interface PermissionDao extends BaseDataMapper<Permission> {

	/**
	 * 查询用户的角色 菜单 权限
	 */
	UserDto getUserPermission(String username);

	/**
	 * 查询所有的菜单
	 */
	@Select("select menu_code from sys_permission where del_flag=1")
	Set<String> getAllMenu();

	/**
	 * 查询所有的权限
	 */
	@Select("select permission_code from sys_permission where del_flag=1")
	Set<String> getAllPermission();

	/**
	 * 查询所有权限, 给角色分配权限时调用
	 */
	List<Permission> listAllPermission();

	/**
	 * 根据角色id查找对应的权限信息
	 * @param roleId
	 * @return
	 */
	@Select("select permission_id from sys_role_permission where role_id =#{roleId} and del_flag=1")
	List<Integer> findPermissionIdByRoleId(@Param("roleId") Integer roleId);
}
