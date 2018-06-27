package com.message.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringManager implements ApplicationContextAware {
	private static ApplicationContext context = null;

	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	public static <T> T getBean(String name, Class<T> cls) {
		return (T) context.getBean(name, cls);
	}

	public static <T> T getBeanByType(Class<T> cls) {
		return (T) context.getBean(cls);
	}
}
