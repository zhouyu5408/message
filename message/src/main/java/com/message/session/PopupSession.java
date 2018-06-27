package com.message.session;

import java.util.Map;

import javax.websocket.Session;

/**
 * 将WebSocket的Session和用户ID封装<br/>
 * 
 * @Date: 2017年11月29日 下午2:10:06 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public class PopupSession implements IMessageSession {
	private String userId;
	private Session session;

	public PopupSession(String userId, Session session) {
		this.userId = userId;
		this.session = session;
	}

	public String getOperator() {
		return this.userId;
	}

	public String getMessageType() {
		return null;
	}

	public void setMessageType(String messageType) {

	}

	public void setAttribute(String name, Object value) {

	}

	public void setAttributes(Map<String, Object> attributes) {

	}

	public Object getAttribute(String name) {
		return null;
	}

	public boolean isInvalidated() {
		return false;
	}

	public Session getSession() {
		return this.session;
	}

}
