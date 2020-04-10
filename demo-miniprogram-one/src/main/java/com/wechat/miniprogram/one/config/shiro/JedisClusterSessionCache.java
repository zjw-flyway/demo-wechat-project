//package com.wechat.miniprogram.one.config.shiro;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import lombok.extern.slf4j.Slf4j;
//import redis.clients.jedis.JedisCluster;
//
///**
// * @Description redis 集群session配置类
// * @Author zhangjw
// * @Date 2020/3/30 18:32
// */
//@Component
//@Slf4j
//public class JedisClusterSessionCache {
//
//	@Autowired
//	private JedisCluster jedisCluster;
//
//	/**
//	 * 序列化
//	 * @param object
//	 * @return
//	 */
//	private static byte[] serializeObj(Object object) {
//		ObjectOutputStream oos;
//		ByteArrayOutputStream baos;
//		try {
//			baos = new ByteArrayOutputStream();
//			oos = new ObjectOutputStream(baos);
//			oos.writeObject(object);
//			byte[] bytes = baos.toByteArray();
//			return bytes;
//		} catch (Exception e) {
//			throw new RuntimeException("序列化失败!", e);
//		}
//	}
//
//	/**
//	 * 反序列化
//	 * @param bytes
//	 * @return
//	 */
//	private static Object deserializeObj(byte[] bytes) {
//		if (bytes == null) {
//			return null;
//		}
//		ByteArrayInputStream bais;
//		try {
//			bais = new ByteArrayInputStream(bytes);
//			ObjectInputStream ois = new ObjectInputStream(bais);
//			return ois.readObject();
//		} catch (Exception e) {
//			throw new RuntimeException("反序列化失败!", e);
//		}
//	}
//
//	/**
//	 * 添加缓存数据
//	 * @param key
//	 * @param obj
//	 * @param <T>
//	 * @return
//	 * @throws Exception
//	 */
//	public <T> String putCache(String key, T obj) throws Exception {
//		final byte[] bkey = key.getBytes();
//		final byte[] bvalue = serializeObj(obj);
//		return jedisCluster.set(bkey, bvalue);
//	}
//
//	/**
//	 * 添加缓存数据，设定缓存失效时间
//	 * @param key
//	 * @param obj
//	 * @param expireTime 秒
//	 * @param <T>
//	 * @throws Exception
//	 */
//	public <T> String putCacheWithExpireTime(String key, T obj, final int expireTime) {
//		final byte[] bkey = key.getBytes();
//		final byte[] bvalue = serializeObj(obj);
//		String result = jedisCluster.setex(bkey, expireTime, bvalue);
//		return result;
//	}
//
//	/**
//	 * 根据key取缓存数据
//	 * @param key
//	 * @param <T>
//	 * @return
//	 * @throws Exception
//	 */
//	public <T> T getCache(final String key) {
//		byte[] result = jedisCluster.get(key.getBytes());
//		return (T) deserializeObj(result);
//	}
//
//	/**
//	 * 根据key删除缓存数据
//	 * @return
//	 * @throws Exception
//	 */
//	public void delCache(final String key) {
//		jedisCluster.del(key.getBytes());
//	}
//}
