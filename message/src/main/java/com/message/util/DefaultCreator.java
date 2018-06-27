package com.message.util;

import org.apache.log4j.Logger;

/**
 * 默认的bean factory
 * 
 * @Date: 2017年11月27日 上午9:32:16 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public class DefaultCreator {
	private static Logger logger = Logger.getLogger(DefaultCreator.class);

	@SuppressWarnings("unchecked")
	public static <T> T getComponent(String beanName) {
		T t = null;
		try {
			t = (T) Class.forName(beanName).newInstance();
		} catch (InstantiationException e) {
			logger.info("获取对象[" + beanName + "]实例化异常", e);
		} catch (IllegalAccessException e) {
			logger.info("获取对象[" + beanName + "]访问异常", e);
		} catch (ClassNotFoundException e) {
			logger.info("获取对象[" + beanName + "]未找到", e);
		} catch (Exception e) {
			logger.info("获取对象[" + beanName + "]异常出错", e);
		}
		return t;
	}
}
