package com.standard.demo.core.config.base;

import java.util.HashMap;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

/**
 * @Description 数据权限，用来从webapp传递数据到dubbo服务
 * @Author zhangjw
 * @Date 2020/3/5 16:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataScope extends HashMap {

	/**
	 * 该数据权限的名称
	 */
	String scopeName;

	/**
	 * 具体的数据范围，这里可以定制化，可以定义多个list，也可以加上userId，部门，公司等
	 */
	List<Integer> scopeList;

	/**
	 * 是否可以查看所有数据
	 */
	Boolean isAllData = false;

}
