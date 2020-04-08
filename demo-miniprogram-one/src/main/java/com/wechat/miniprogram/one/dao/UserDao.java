package com.standard.demo.webapp.one.dao;

import com.standard.demo.core.config.base.BaseDataMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.standard.demo.webapp.one.entity.User;

import java.util.List;

/**
 * @author: zjw
 * @description: 用户/角色/权限
 * @date: 2020-03-17 15:08:45
 */
public interface UserDao extends BaseDataMapper<User> {

	/**
	 * 根据用户名和密码查询对应的用户
	 */
	@Select("select * from sys_user where username=#{username} and del_flag=1")
	User getUser(@Param("username") String username);

	/**
	 * 校验用户名是否已存在
	 */
	@Select("select count(1) from sys_user WHERE username=#{username} AND del_flag=1")
	int queryExistUsername(@Param("username") String userName);

	/**
	 * 新增用户
	 */
	int addUser(@Param("user") User user);

	/**
	 * 更新用户的创建人和更新人
	 * @param id
	 * @return
	 */
	@Update("update sys_user set create_by=#{id},update_by=#{id} where id=#{id}")
	int updateUserCreateAndUpdateBy(@Param("id") Integer id);

	/**
	 * 根据角色id查找用户id
	 * @param roleId
	 * @return
	 */
	@Select("select id from sys_user_role where role_id=#{roleId} and del_flag=1")
	List<Integer> findUserIdByRoleId(@Param("roleId") Integer roleId);
}
