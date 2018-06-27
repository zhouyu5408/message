package com.message.cache;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.message.enums.BaseEnum;
import com.message.exception.BaseException;
import com.message.util.Maps;

/**
 * 缓存对象类 . <br/>
 * 创建: 2017年11月24日 下午3:49:00 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (深圳市雁联计算系统有限公司) , All Rights Reserved.
 * @param <V>
 * @param <K>
 * @since JDK 1.7
 */
public class MessageCache<K, V> {
	private ConcurrentMap<K, V> data = Maps.newConcurrentMap();
	private BaseEnum name;
	/**
	 * 默认刷新间隔是1个小时 单位是毫秒
	 */
	public static final long REFRESHGAP = 60 * 60 * 1000L;
	/**
	 * 默认不自动刷新
	 */
	private boolean isAutoRefresh = false;

	/**
	 * 是否运行时添加数据
	 */
	private boolean isRuntimeFill = false;

	private long refreshTime = REFRESHGAP;

	private CacheLoader<K, V> loader;
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	// private final Lock readLock = lock.readLock();
	private final Lock writeLock = lock.writeLock();
	private long lastUpdateTime = 0L;// 当前数据刷新时间

	/**
	 * 
	 * Creates a new instance of MessageCache.
	 * 
	 */
	public MessageCache(BaseEnum name) {
		this.name = name;
	}

	/**
	 * . lastUpdateTime.
	 * 
	 * @return the lastUpdateTime
	 * @since JDK 1.6
	 */
	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * 
	 * (这里用一句话描述这个方法的作用). <br/>
	 * 
	 * @return
	 */
	public boolean isNeedResfresh() {

		return isAutoRefresh && lastUpdateTime != 0L && refreshTime <= (System.currentTimeMillis() - lastUpdateTime);

	}

	/**
	 * 
	 */
	private Thread refreshRunner = null;

	/**
	 * 
	 * (这里用一句话描述这个方法的作用). <br/>
	 * 
	 * @return
	 */
	public MessageCache<K, V> build() {
		this.refresh();

		if (this.isAutoRefresh) {
			if (refreshRunner == null) {
				refreshRunner = new Thread(new CacheRefreshThread<K, V>(this));
				refreshRunner.start();
			}
		}
		return this;
	}

	/**
	 * . name.
	 * 
	 * @return the name
	 * @since JDK 1.6
	 */
	public BaseEnum getName() {
		return name;
	}

	/**
	 * . name.
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(BaseEnum name) {
		this.name = name;
	}

	/**
	 * . isAutoRefresh.
	 * 
	 * @return the isAutoRefresh
	 */
	public boolean isAutoRefresh() {
		return isAutoRefresh;
	}

	/**
	 * . isAutoRefresh.
	 * 
	 * @param isAutoRefresh
	 *            the isAutoRefresh to set
	 */
	public MessageCache<K, V> autoRefresh() {
		this.isAutoRefresh = true;
		return this;
	}

	public MessageCache<K, V> runtimeFill() {
		this.isRuntimeFill = true;
		return this;
	}

	/**
	 * . isAutoRefresh.
	 * 
	 * @param refresh
	 *            自动刷新时间间隔 - 单位 毫秒
	 */
	public MessageCache<K, V> autoRefresh(long refresh) {
		this.isAutoRefresh = true;
		this.refreshTime = refresh;
		return this;
	}

	/**
	 * . isAutoRefresh.
	 * 
	 * @param isAutoRefresh
	 *            the isAutoRefresh to set
	 * @since JDK 1.6
	 */
	public MessageCache<K, V> noAutoRefresh() {
		this.isAutoRefresh = false;
		return this;
	}

	/**
	 * . refreshTime.
	 * 
	 * @return the refreshTime
	 */
	public long getRefreshTime() {
		return refreshTime;
	}

	/**
	 * . refreshTime.
	 * 
	 * @param refreshTime
	 *            the refreshTime to set
	 */
	public MessageCache<K, V> setRefreshTime(long refresh) {
		this.refreshTime = refresh;
		return this;
	}

	/**
	 * . loader.
	 * 
	 * @return the loader
	 */
	public CacheLoader<K, V> getLoader() {
		return loader;
	}

	/**
	 * . loader.
	 * 
	 * @param loader
	 *            the loader to set
	 */
	public MessageCache<K, V> setLoader(CacheLoader<K, V> cacheLoader) {
		this.loader = cacheLoader;
		return this;
	}

	/**
	 * . data.
	 * 
	 * @return the data
	 */
	public Map<K, V> getData() {
		return Collections.unmodifiableMap(data);
	}

	/**
	 * . data.
	 * 
	 * @param data
	 *            the data to set
	 * @since JDK 1.6
	 */
	private void setData(ConcurrentMap<K, V> data) {
		writeLock.lock();
		try {
			this.data = data;
			this.lastUpdateTime = System.currentTimeMillis();

		} finally {
			writeLock.unlock();
		}

	}

	/**
	 * 
	 * 刷新缓存. <br/>
	 * 
	 * @author zkh
	 */
	public void refresh() {
		ConcurrentMap<K, V> newData = Maps.newConcurrentMap();
		if (!this.isRuntimeFill) {// 允许时填充不用初始化
			newData = getLoader().load();
		}
		this.setData(newData);

	}

	/**
	 * 
	 * 获取对象. <br/>
	 * 
	 * @author zkh
	 * @param key
	 * @return
	 */
	public V get(K key) {
		return this.data.get(key);
	}

	/**
	 * 运行时填充cache
	 * 
	 * @param key
	 * @param value
	 */
	public void put(K key, V value) {
		if (isRuntimeFill) {
			this.data.put(key, value);
		} else {
			throw new BaseException("运行时填充cache不支持此方法！！！");
		}
	}

}
