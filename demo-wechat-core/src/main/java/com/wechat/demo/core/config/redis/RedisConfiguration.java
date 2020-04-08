package com.standard.demo.core.config.redis;

import java.time.Duration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import com.google.common.collect.Maps;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * @Description redis配置文件
 * @Author zhangjw
 * @Date 2019/8/19 20:08
 */
@Configuration
@EnableCaching
public class RedisConfiguration {

	@Autowired
	private RedisConfigurationProperties redisConfigurationProperties;

	/**
	 * jedisCluster配置
	 * @return
	 */
	@Bean
	public JedisCluster jedisCluster() {
		Set<HostAndPort> nodeSet = new HashSet<>();
		for (String node : redisConfigurationProperties.getNodes()) {
			String[] split = node.split(":");
			nodeSet.add(new HostAndPort(split[0], Integer.valueOf(split[1])));
		}

		GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
		genericObjectPoolConfig.setMaxIdle(Integer.parseInt(redisConfigurationProperties.getMaxIdle()));
		genericObjectPoolConfig.setMaxTotal(Integer.parseInt(redisConfigurationProperties.getMaxTotal()));
		genericObjectPoolConfig.setMaxWaitMillis(Integer.parseInt(redisConfigurationProperties.getMaxWait()));
		genericObjectPoolConfig.setTestOnBorrow(true);
		return new JedisCluster(nodeSet);
	}

	/**
	 * redis工厂类配置，其实这个bean都可以不用配置
	 * 按RedisClusterConfiguration里面的代码，只要在配置文件中配置了redis的配置，就会用redis作为缓存
	 * 具体实现可以看RedisClusterConfiguration的源码
	 * @return
	 */
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		Map<String, Object> source = Maps.newHashMap();
		source.put("spring.redis.cluster.nodes", redisConfigurationProperties.getNodes().get(0));
		source.put("spring.redis.cluster.timeout", redisConfigurationProperties.getMaxWait());
		RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(
				new MapPropertySource("RedisClusterConfiguration", source));
		JedisConnectionFactory cf = new JedisConnectionFactory(redisClusterConfiguration);
		cf.afterPropertiesSet();
		return cf;
	}

	/**
	 * 将redis配置到@cacheable注解上
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		//定义缓存时间为30分钟
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(30))
				.disableCachingNullValues();
		RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
		return new RedisCacheManager(redisCacheWriter, config);
	}
}
