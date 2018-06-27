package com.message.cache;

public class CacheRefreshThread<K, V> implements Runnable {
	private MessageCache<K, V> cache;
	/**
	 * 默认线程中断时间
	 */
	private final static long DEFAULTSLEEPTIME = 1000L;

	public CacheRefreshThread(MessageCache<K, V> cache) {
		this.cache = cache;
	}

	private synchronized void refreshCache() {
		if (cache.isNeedResfresh()) {
			cache.refresh();
		}
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while (true) {
			refreshCache();
			try {
				Thread.sleep(DEFAULTSLEEPTIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
