package com.wechat.demo.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * @Description mybatis工具类
 * @Author zhangjw
 * @Date 2020/3/5 15:21
 */
@Configuration
public class MybatisPlusConfig {

	/**
	 * 分页插件
	 * @return
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}
	
}
