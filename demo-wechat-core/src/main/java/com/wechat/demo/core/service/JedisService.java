package com.standard.demo.core.service;

import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.JedisCluster;


/**
 * @Description redis服务类
 * @Author zhangjw
 * @Date 2019/8/19 20:10
 */
@Service
public class JedisService {

	private static Logger logger = LoggerFactory.getLogger(JedisService.class);

	private static final Long ONE = 1L;

	@Autowired
	private JedisCluster jedisCluster;

	/**
	 * 判断key是否存在
	 * @param key
	 * @return
	 */
	public boolean exists(String key) {
		return jedisCluster.exists(key);
	}

	/**
	 * 添加数据
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	public String set(String key, String value, int seconds) {
		String responseResult = jedisCluster.set(key, value);
		if (seconds != 0) {
			jedisCluster.expire(key, seconds);
		}
		return responseResult;
	}

	/**
	 * 得到set类型的数据
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	public String getSet(String key, String value, int seconds) {
		String jedisClusterSet = jedisCluster.getSet(key, value);
		jedisCluster.expire(key, seconds);
		return jedisClusterSet;
	}

	/**
	 * 获取数据
	 * @param key
	 * @return
	 */
	public String get(String key) {
		String str = jedisCluster.get(key);
		return str;
	}

	/**
	 * 删除key
	 * @param key
	 */
	public void delKey(String key) {
		jedisCluster.del(key);
	}

	/**
	 * 得到map类型的数据
	 * @param key
	 * @return
	 */
	public Map<String, Object> getMapData(String key) {
		String str = jedisCluster.get(key);
		Map<String, Object> map = JSON.parseObject(str, Map.class);
		return map;
	}

	/**
	 * redis分布式锁加锁
	 * @param key
	 * @param requestId
	 * @param expireTime
	 * @return
	 */
	public boolean lock(String key, String requestId, int expireTime) {
		String result = jedisCluster.set(key, requestId, "NX", "PX", expireTime);
		if ("OK".equals(result)) {
			logger.info("redis分布式锁key:{}加锁{}成功", key, requestId);
			return true;
		}

		logger.info("redis分布式锁获key{}加锁{}失败", key, requestId);
		return false;
	}

	/**
	 * redis分布式锁，解锁
	 * 用lua脚本执行解锁，保证判断和解锁是在一个事件中执行的
	 * @return
	 */
	public boolean unLock(String key, String requestId) {
		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		Object result = jedisCluster.eval(script, Collections.singletonList(key), Collections.singletonList(requestId));
		if (ONE.equals(result)) {
			logger.info("redis分布式锁key{}解锁{}成功", key, requestId);
			return true;
		}

		logger.info("redis分布式锁key{}解锁{}失败", key, requestId);
		return false;
	}

	/**
	 * 累加1
	 * @param key
	 * @param expireTime
	 */
	public void incr(String key, Integer expireTime) {
		jedisCluster.incr(key);
		if (expireTime != null) {
			jedisCluster.expire(key, expireTime);
		}
	}

	/**
	 * redis往set中添加值
	 * @param key
	 * @param value
	 * @param expireTime
	 */
	public void sadd(String key, String value, Integer expireTime) {
		jedisCluster.sadd(key, value);
		if (expireTime != null) {
			jedisCluster.expire(key, expireTime);
		}
	}

	/**
	 * 取set集群中的数据的个数
	 * @param key
	 * @return
	 */
	public Long scard(String key) {
		if (jedisCluster.exists(key)) {
			return jedisCluster.scard(key);
		}
		return 0L;
	}

	/**
	 * 判断数据是否在redis set中
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean sismember(String key, String value) {
		if (!jedisCluster.exists(key)) {
			return false;
		}

		if (jedisCluster.sismember(key, value)) {
			return true;
		} else {
			return false;
		}
	}
}
