package com.standard.demo.core.config.redis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @Description redis配置获取地址
 * @Author zhangjw
 * @Date 2019/8/19 19:31
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class RedisConfigurationProperties {

	/**
	 * redis集群链接
	 */
	private List<String> nodes = new ArrayList<>();

	/**
	 * 最大空闲连接数
	 */
	private String maxIdle;

	/**
	 * 最大等待时间ms
	 */
	private String maxWait;

	/**
	 *  最大连接数
	 */
	private String maxTotal;

	public List<String> getNodes() {
		return nodes;
	}

	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}
}
