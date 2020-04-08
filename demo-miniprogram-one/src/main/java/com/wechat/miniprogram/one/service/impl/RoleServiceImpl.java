package com.standard.demo.webapp.one.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.standard.demo.core.config.base.BaseServiceImpl;
import com.standard.demo.web.core.constant.ErrorEnum;
import com.standard.demo.web.core.entity.ResponseEntity;
import com.standard.demo.webapp.one.dao.PermissionDao;
import com.standard.demo.webapp.one.dao.RoleDao;
import com.standard.demo.webapp.one.dao.UserDao;
import com.standard.demo.webapp.one.entity.Role;
import com.standard.demo.webapp.one.service.RoleService;
import com.standard.demo.web.core.utils.CommonUtil;
import com.standard.demo.web.core.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @Description
 * @Author zhangjw
 * @Date 2020/3/17 16:54
 */
@Service
@Slf4j
public class RoleServiceImpl extends BaseServiceImpl<RoleDao, Role> implements RoleService {

	@Resource
	private RoleDao roleDao;

	@Resource
	private PermissionDao permissionDao;

	@Resource
	private UserDao userDao;

	/**
	 * 查询所有的角色 在添加/修改用户的时候要使用此方法
	 */
	@Override
	public ResponseEntity getAllRoles() {
		List<Role> roles = roleDao.getAllRoles();
		return CommonUtil.successJson(roles);
	}

	/**
	 * 添加角色
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResponseEntity addRole(Role jsonObject) {
		LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(Role::getRoleEname, jsonObject.getRoleEname()).or().eq(Role::getRoleEname,
				jsonObject.getRoleEname());
		Role role = roleDao.selectOne(lambdaQueryWrapper);
		if (role != null) {
			log.info("新增角色失败，因为角色名或角色英文名重复，{}", JSONObject.toJSONString(jsonObject));
			return CommonUtil.errorJson("角色名称或角色英文名称重复");
		}

		jsonObject.setCreateTime(LocalDateTime.now());
		jsonObject.setUpdateTime(LocalDateTime.now());
		jsonObject.setUpdateBy(UserUtils.getCurrentUserId());
		jsonObject.setCreateBy(UserUtils.getCurrentUserId());
		roleDao.insertRole(jsonObject);
		Integer userId = UserUtils.getCurrentUserId();
		for (Integer permissionId : jsonObject.getPermissionIdList()) {
			roleDao.insertRolePermission(jsonObject.getId(), permissionId, userId, userId);
		}
		log.info("插入角色成功，角色为：{}", JSONObject.toJSONString(jsonObject));
		return CommonUtil.successJson();
	}

	/**
	 * 修改角色
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResponseEntity updateRole(Role jsonObject) {
		Integer roleId = jsonObject.getId();
		List<Integer> newPerms = jsonObject.getPermissionIdList();
		List<Integer> oldPerms = permissionDao.findPermissionIdByRoleId(roleId);

		jsonObject.setUpdateBy(UserUtils.getCurrentUserId());
		jsonObject.setCreateTime(LocalDateTime.now());
		roleDao.updateById(jsonObject);
		// 添加新权限
		saveNewPermission(roleId, newPerms, oldPerms);
		// 移除旧的不再拥有的权限
		removeOldPermission(roleId, newPerms, oldPerms);
		return CommonUtil.successJson();
	}

	/**
	 * 为角色添加新权限
	 */
	private void saveNewPermission(Integer roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
		for (Integer newPerm : newPerms) {
			if (!oldPerms.contains(newPerm)) {
				roleDao.insertRolePermission(roleId, newPerm, UserUtils.getCurrentUserId(),
						UserUtils.getCurrentUserId());
			}
		}
	}

	/**
	 * 删除角色旧的不再拥有的权限
	 */
	private void removeOldPermission(Integer roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
		List<Integer> waitRemove = new ArrayList<>();
		for (Integer oldPerm : oldPerms) {
			if (!newPerms.contains(oldPerm)) {
				waitRemove.add(oldPerm);
			}
		}
		if (waitRemove.size() > 0) {
			roleDao.removeOldPermission(roleId, waitRemove, UserUtils.getCurrentUserId());
		}
	}

	/**
	 * 删除角色
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResponseEntity deleteRole(Role jsonObject) {
		List<Integer> userIdList = userDao.findUserIdByRoleId(jsonObject.getId());
		if (userIdList != null && userIdList.size() > 0) {
			log.error("删除角色{}失败，原因为角色被用户{}关联", jsonObject.getId(), JSONObject.toJSONString(userIdList));
			return CommonUtil.errorJson(ErrorEnum.E_10008);
		}
		roleDao.removeRole(jsonObject.getId(), UserUtils.getCurrentUserId());
		roleDao.removeRoleAllPermission(jsonObject.getId(), UserUtils.getCurrentUserId());
		return CommonUtil.successJson();
	}
}
