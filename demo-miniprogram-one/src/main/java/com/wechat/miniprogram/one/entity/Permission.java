package com.standard.demo.webapp.one.entity;

import com.standard.demo.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

/**
 * @Description
 * @Author zhangjw
 * @Date 2020/3/17 16:05
 */
@Data
@FieldDefaults(level = AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = true)
@ApiModel("菜单权限")
public class Permission extends BaseEntity {

    /**
     * 菜单编码
     */
    String menuCode;

    /**
     * 菜单名称
     */
    String menuName;

    /**
     * 权限编码
     */
    String permissionCode;

    /**
     * 权限名称
     */
    String permissionName;

    /**
     * 是否本菜单必须选权限
     */
    Integer requiredPermission;
}
