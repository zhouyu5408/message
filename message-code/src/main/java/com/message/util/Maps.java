package com.message.util;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 创建: 2017年11月24日 下午3:47:03 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (深圳市雁联计算系统有限公司) , All Rights Reserved.
 * @since JDK 1.7
 */
public class Maps {

	public static <K, V> HashMap<K, V> newHashMap() {
		return new HashMap<K, V>();
	}

	public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
		return new ConcurrentHashMap<K, V>();
	}

}