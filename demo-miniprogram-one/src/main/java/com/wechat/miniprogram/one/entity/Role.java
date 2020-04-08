package com.standard.demo.webapp.one.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.standard.demo.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @Description
 * @Author zhangjw
 * @Date 2020/3/17 16:35
 */
@Data
@FieldDefaults(level = AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = true)
@ApiModel("角色")
@TableName("sys_role")
public class Role extends BaseEntity {

	/**
	 * 角色名称
	 */
	String roleName;

	/**
	 * 角色英文名称
	 */
	String roleEname;

	/**
	 * 该角色对应的权限信息
	 */
	@TableField(exist = false)
	List<Integer> permissionIdList;
}
