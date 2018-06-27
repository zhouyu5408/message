package com.message.session;

import java.util.Map;

import javax.websocket.Session;

public interface IMessageSession {
	/**
	 * 获取操作员
	 * 
	 * @return
	 */
	String getOperator();

	/**
	 * 获取消息类型
	 * 
	 * @return
	 */
	String getMessageType();

	/**
	 * 设置消息类型
	 * 
	 * @param messageType
	 * @return
	 */
	void setMessageType(String messageType);

	/**
	 * 设置操作员相关属性
	 * 
	 * @param name
	 * @param value
	 */
	void setAttribute(String name, Object value);

	/**
	 * 设置所有相关属性
	 * 
	 * @param attributes
	 */
	void setAttributes(Map<String, Object> attributes);

	/**
	 * 获取操作员指定属性
	 * 
	 * @param name
	 * @return
	 */
	Object getAttribute(String name);

	/**
	 * 是否有效
	 * 
	 * @return
	 */
	boolean isInvalidated();

	Session getSession();
}
