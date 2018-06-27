package com.message.cache;

import java.util.concurrent.ConcurrentMap;

/**
 * 数据转载器 . <br/>
 * 创建: 2017年11月24日 下午3:52:27 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (深圳市雁联计算系统有限公司) , All Rights Reserved.
 * @since JDK 1.7
 */
public abstract class CacheLoader<K, V> {
	/**
	 * 
	 * 装载数据. <br/>
	 *
	 * @return
	 */
	public abstract ConcurrentMap<K, V> load();

}
