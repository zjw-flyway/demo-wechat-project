package com.standard.demo.webapp.one.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.standard.demo.web.core.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.standard.demo.core.config.base.BaseServiceImpl;
import com.standard.demo.web.core.entity.ResponseEntity;
import com.standard.demo.web.core.utils.CommonUtil;
import com.standard.demo.webapp.one.dao.PermissionDao;
import com.standard.demo.webapp.one.entity.Permission;
import com.standard.demo.webapp.one.service.PermissionService;

/**
 * @author: zjw
 * @date: 2020/03/30 13:15
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionDao, Permission> implements PermissionService {

	@Value("${admin.user.id}")
	private Integer adminUserId;

	@Resource
	private PermissionDao permissionDao;

	/**
	 * 从数据库查询用户权限信息
	 */
	public UserDto getUserPermission(String username) {
		UserDto userPermission = permissionDao.getUserPermission(username);

		// 如果是管理员
		if (userPermission != null && userPermission.getRoleIdList().contains(adminUserId)) {
			// 查询所有菜单 所有权限
			Set<String> menuList = permissionDao.getAllMenu();
			Set<String> permissionList = permissionDao.getAllPermission();
			userPermission.setMenuList(menuList);
			userPermission.setPermissionList(permissionList);
		}
		return userPermission;
	}

	/**
	 * 查询所有权限, 给角色分配权限时调用
	 */
	@Override
	public ResponseEntity listAllPermission() {
		List<Permission> permissions = permissionDao.listAllPermission();
		return CommonUtil.successJson(permissions);
	}
}
