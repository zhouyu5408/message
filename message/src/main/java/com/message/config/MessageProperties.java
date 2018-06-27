package com.message.config;

/**
 * 消息推送数据库的配置类(需要注入)
 * 
 * @Date: 2017年12月28日 下午4:15:31 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public class MessageProperties {
	/**
	 * 数据库URL
	 */
	private String url;
	/**
	 * 驱动类
	 */
	private String driverClass;
	/**
	 * 数据库用户名
	 */
	private String username;
	/**
	 * 数据库密码
	 */
	private String password;
	/**
	 * 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
	 */
	private Integer initialSize = 3;
	/**
	 * 最大连接池数量
	 */
	private Integer maxActive = 5;
	/**
	 * 最大空闲数，数据库连接的最大空闲时间。超过空闲时间，数据库连接将被标记为不可用，然后被释放。设为0表示无限制(貌似已废弃)
	 */
	private Integer maxIdle = 2;
	/**
	 * 最小连接池数量
	 */
	private Integer minIdle = 2;
	/**
	 * 获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，
	 * 如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
	 */
	private Integer maxWait = 60000;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(Integer initialSize) {
		this.initialSize = initialSize;
	}

	public Integer getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
	}

	public Integer getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}

	public Integer getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}

	public Integer getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(Integer maxWait) {
		this.maxWait = maxWait;
	}

}
