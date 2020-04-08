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
 * @Description 基础 baseMapper类
 * @Author zhangjw
 * @Date 2020/3/5 16:32
 */
public interface BaseDataMapper<T> extends BaseMapper<T> {

	List<T> selectBatchIds(@Param("coll") Collection<? extends Serializable> idList, DataScope dataScope);

	List<T> selectByMap(@Param("cm") Map<String, Object> columnMap, DataScope dataScope);

	T selectOne(@Param("ew") Wrapper<T> queryWrapper, DataScope dataScope);

	Integer selectCount(@Param("ew") Wrapper<T> queryWrapper, DataScope dataScope);

	List<T> selectList(@Param("ew") Wrapper<T> queryWrapper, DataScope dataScope);

	List<Map<String, Object>> selectMaps(@Param("ew") Wrapper<T> queryWrapper, DataScope dataScope);

	List<Object> selectObjs(@Param("ew") Wrapper<T> queryWrapper, DataScope dataScope);

	IPage<T> selectPage(IPage<T> page, @Param("ew") Wrapper<T> queryWrapper, DataScope dataScope);

	IPage<Map<String, Object>> selectMapsPage(IPage<T> page, @Param("ew") Wrapper<T> queryWrapper, DataScope dataScope);
}
