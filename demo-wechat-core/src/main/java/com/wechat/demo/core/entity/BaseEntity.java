package com.wechat.demo.core.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import com.wechat.demo.core.constant.CommonConstants;
import com.wechat.demo.core.enums.DelFlag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @Description 基础实体类
 * @Author zhangjw
 * @Date 2020/3/17 14:04
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel("基础实体类")
public class BaseEntity {

	@TableId
	Integer id;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty("更新时间")
	LocalDateTime updateTime;

	/**
	 * 更新人
	 */
	@ApiModelProperty("更新人")
	Integer updateBy;

	/**
	 * 创建人
	 */
	@ApiModelProperty("创建人")
	Integer createBy;

	/**
	 * 删除标识
	 */
	@ApiModelProperty("删除标识")
	Integer delFlag = DelFlag.NORMAL.getCode();

	/**
	 * 页数
	 */
	@TableField(exist = false)
	@ApiModelProperty("页码")
	Integer pageNo = CommonConstants.PAGE_NO;

	/**
	 * 每页大小
	 */
	@TableField(exist = false)
	@ApiModelProperty("每页的大小")
	Integer pageSize = CommonConstants.PAGE_SIZE;
}
