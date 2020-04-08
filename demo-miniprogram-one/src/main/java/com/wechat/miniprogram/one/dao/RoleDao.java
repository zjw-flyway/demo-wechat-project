package com.standard.demo.webapp.one.dao;

import java.util.List;

import com.standard.demo.core.config.base.BaseDataMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.alibaba.fastjson.JSONObject;
import com.standard.demo.webapp.one.entity.Role;

/**
 * @Description 角色dao类
 * @Author zhangjw
 * @Date 2020/3/17 16:37
 */
public interface RoleDao extends BaseDataMapper<Role> {

	/**
	 * 新增角色
	 */
	int insertRole(@Param("role") Role role);

	/**
	 * 插入角色的权限
	 *
	 * @param roleId
	 * 角色ID
	 */
	@Insert("insert into sys_role_permission (role_id,permission_id,create_time,update_time,del_flag,update_by,create_by)"
			+ " values(#{roleId},#{permissionId},now(),now(),1,#{createBy},#{updateBy})")
	int insertRolePermission(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId,
			@Param("createBy") Integer createBy, @Param("updateBy") Integer updateBy);

	/**
	 * 移除角色和权限的关系
	 * 
	 * @param roleId
	 * @param permissions
	 * @return
	 */
	int removeOldPermission(@Param("roleId") Integer roleId, @Param("permissions") List<Integer> permissions,
			@Param("updateBy") Integer updateBy);

	/**
	 * 删除角色
	 */
	@Update("update sys_role set del_flag=2,update_time=now(),update_by=#{updateBy} where id=#{id}")
	int removeRole(@Param("id") Integer id, @Param("updateBy") Integer updateBy);

	/**
	 * 删除本角色全部权限
	 */
	@Update("update sys_role_permission set del_flag=2,update_time=now(),update_by=#{updateBy} where role_id=#{roleId}")
	int removeRoleAllPermission(@Param("roleId") Integer roleId, @Param("updateBy") Integer updateBy);

	/**
	 * 查询所有的角色
	 * 
	 * @return
	 */
	@Select("select * from sys_role where del_flag=1")
	List<Role> getAllRoles();

	/**
	 * 根据用户id查询角色信息
	 * @param userId
	 * @return
	 */
	@Select("select * from sys_usr_role where user_id=#{userId} and del_flag=1")
	List<Role> findRoleByUserId(@Param("userId") Integer userId);
}
