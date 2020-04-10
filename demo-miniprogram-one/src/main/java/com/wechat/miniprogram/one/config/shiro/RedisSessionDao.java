//package com.wechat.miniprogram.one.config.shiro;
//
//import java.io.Serializable;
//
//import javax.annotation.Resource;
//
//import org.apache.shiro.session.Session;
//import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
//import org.springframework.beans.factory.annotation.Value;
//
//import com.alibaba.fastjson.JSONObject;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * @Description redisSessionDao
// * @Author zhangjw
// * @Date 2020/3/30 18:28
// */
//@Slf4j
//public class RedisSessionDao extends EnterpriseCacheSessionDAO {
//
//	@Value("${spring.shiro.session.timeout}")
//	private Integer shiroTimeout;
//
//	@Resource
//	private JedisClusterSessionCache jedisClusterSessionCache;
//
//	/**
//	 * 创建session，保存到redis数据库
//	 *
//	 * @param session
//	 * @return
//	 */
//	@Override
//	protected Serializable doCreate(Session session) {
//		Serializable sessionId = super.doCreate(session);
//		try {
//			jedisClusterSessionCache.putCacheWithExpireTime(sessionId.toString(), session, shiroTimeout);
//		} catch (Exception e) {
//			log.error("doCreate添加session到redis失败，原因为：", e);
//		}
//
//		log.info("redis中添加session:{},内容为：{}成功", sessionId.toString(), JSONObject.toJSONString(session));
//		return sessionId;
//	}
//
//	/**
//	 * 获取session
//	 *
//	 * @param sessionId
//	 * @return
//	 */
//	@Override
//	protected Session doReadSession(Serializable sessionId) {
//		// 先从缓存中获取session，如果没有再去数据库中获取
//		Session session = super.doReadSession(sessionId);
//		if (session == null) {
//			try {
//				session = jedisClusterSessionCache.getCache(sessionId.toString());
//			} catch (Exception e) {
//				log.error("doReadSession获取session失败，原因为：", e);
//			}
//		}
//
//		return session;
//	}
//
//	/**
//	 * 更新session的最后一次访问时间
//	 *
//	 * @param session
//	 */
//	@Override
//	protected void doUpdate(Session session) {
//		super.doUpdate(session);
//		try {
//			jedisClusterSessionCache.putCacheWithExpireTime(session.getId().toString(), session, shiroTimeout);
//		} catch (Exception e) {
//			log.error("doUpdate更新session失败，原因为：", e);
//		}
//	}
//
//	/**
//	 * 删除session
//	 *
//	 * @param session
//	 */
//	@Override
//	protected void doDelete(Session session) {
//		super.doDelete(session);
//		try {
//			jedisClusterSessionCache.delCache(session.getId().toString());
//		} catch (Exception e) {
//			log.error("doDelete删除session失败，原因为：", e);
//		}
//	}
//}
