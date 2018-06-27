package com.message.cache;

import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;

import com.message.enums.BaseEnum;
import com.message.util.Maps;

/**
 * message缓存工厂 . <br/>
 * 创建: 2017年11月24日 下午3:44:46 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (深圳市雁联计算系统有限公司) , All Rights Reserved.
 * @since JDK 1.7
 */
public final class CacheManager {
	private static Logger logger = Logger.getLogger(CacheManager.class);
	/**
	 * 系统所有缓存.<BR>
	 */
	@SuppressWarnings("rawtypes")
	public static ConcurrentMap<String, MessageCache> caches = Maps.newConcurrentMap();

	/**
	 * 
	 * (这里用一句话描述这个方法的作用). <br/>
	 *
	 * @author zkh
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> MessageCache<K, V> getCache(BaseEnum cacheName) {
		MessageCache<K, V> cache = null;
		if (caches.containsKey(CacheManager.getKeyByEnum(cacheName))) {
			cache = CacheManager.caches.get(CacheManager.getKeyByEnum(cacheName));
		}
		return cache;

	}

	/**
	 * 
	 * 创建cache并放入CacheManager. <br/>
	 *
	 * @author zkh
	 * @return
	 */
	public static <K, V> MessageCache<K, V> createCache(BaseEnum name) {
		MessageCache<K, V> cache = new MessageCache<K, V>(name);
		if (caches.containsKey(CacheManager.getKeyByEnum(name))) {
			// throw new BaseException("不能创建重复的缓存！！！");
			logger.warn("重复创建缓存" + CacheManager.getKeyByEnum(name));
		}
		CacheManager.caches.put(CacheManager.getKeyByEnum(name), cache);
		return cache;
	}

	/**
	 * 
	 * 获取key . <br/>
	 *
	 * @author kevin
	 * @param name
	 * @return
	 */
	public static String getKeyByEnum(BaseEnum name) {
		return name.getClass().getName() + "." + name.getCode();
	}

}
