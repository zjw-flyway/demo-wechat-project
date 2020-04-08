package com.standard.demo.core.config.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 
 * 包含数据权限的DataMapper，如果有数据权限才要集成这个Mapper
 * @Author zhangjw
 * @Date 2020/3/5 16:33
 */
public interface DataScopeMapper<T> extends BaseMapper<T> {

	/**
	 * 根据 ID 查询
	 */
	T selectById(Serializable id, DataScope dataScope);

	/**
	 * 查询（根据ID 批量查询）
	 */
	List<T> selectBatchIds(@Param("coll") Collection<? extends Serializable> idList, DataScope dataScope);

	/**
	 * 查询（根据 columnMap 条件）
	 */
	List<T> selectByMap(@Param("cm") Map<String, Object> columnMap, DataScope dataScope);

	/**
	 * 根据 entity 条件，查询一条记录
	 */
	T selectOne(@Param("ew") Wrapper<T> queryWrapper, DataScope dataScope);

	/**
	 * 根据 Wrapper 条件，查询总记录数
	 */
	Integer selectCount(@Param("ew") Wrapper<T> queryWrapper, DataScope dataScope);

	/**
	 * 根据 entity 条件，查询全部记录
	 */
	List<T> selectList(@Param("ew") Wrapper<T> queryWrapper, DataScope dataScope);

	/**
	 * 根据 Wrapper 条件，查询全部记录
	 */
	List<Map<String, Object>> selectMaps(@Param("ew") Wrapper<T> queryWrapper, DataScope dataScope);

	/**
	 * 根据 Wrapper 条件，查询全部记录
	 * <p>注意： 只返回第一个字段的值</p>
	 */
	List<Object> selectObjs(@Param("ew") Wrapper<T> queryWrapper, DataScope dataScope);

	/**
	 * 根据 entity 条件，查询全部记录（并翻页）
	 *
	 */
	IPage<T> selectPage(IPage<T> page, @Param("ew") Wrapper<T> queryWrapper, DataScope dataScope);

	/**
	 * 根据 Wrapper 条件，查询全部记录（并翻页）
	 *
	 * @param page         分页查询条件
	 * @param queryWrapper 实体对象封装操作类
	 */
	IPage<Map<String, Object>> selectMapsPage(IPage<T> page, @Param("ew") Wrapper<T> queryWrapper, DataScope dataScope);

}
