package com.message.popup;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.message.constants.MessageConstants;
import com.message.enums.SendEnum;

/**
 * 处理弹窗用的处理器
 * 
 * @Date: 2017年12月26日 上午9:13:59 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public class PopupHandler implements WebSocketHandler {
	private static final Logger logger = Logger.getLogger(PopupHandler.class);
	// 在线用户列表
	private static final Map<String, WebSocketSession> users;

	static {
		users = new ConcurrentHashMap<String, WebSocketSession>();
	}

	/**
	 * 建立连接时调用
	 */
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String userId = getUserIdFormSession(session);
		if (null != userId) {
			users.put(userId, session);
		}
	}

	/**
	 * 发送消息时调用
	 */
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		System.out.println("---------  模拟数据发送  ---------");
		// 模拟给A10001和A10002的用户发送消息
		WebSocketSession s1 = users.get("A10001");
		if (null != s1) {
			s1.sendMessage(message);
		}
		WebSocketSession s2 = users.get("A10002");
		if (null != s2) {
			s2.sendMessage(message);
		}
		
	}

	/**
	 * 发生错误时调用
	 */
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		logger.error(exception);
	}

	/**
	 * 连接关闭时调用
	 */
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		String userId = getUserIdFormSession(session);
		users.remove(userId);
	}

	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 获取用户登录的Id
	 * 
	 * @param session
	 */
	private String getUserIdFormSession(WebSocketSession session) {
		try {
			String userId = (String) session.getAttributes().get(MessageConstants.USER_ID);
			return userId;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 给某个人用户发送消息
	 * 
	 * @return 是否已经发送给指定用户
	 */
	@SuppressWarnings("rawtypes")
	public String sendMessage(String userId, String msg) {
		// 发送异常则返回false；没有发送或者发送成功则返回true
		WebSocketSession session = users.get(userId);
		// 发送的是文本类型
		WebSocketMessage message = new TextMessage(msg);
		if (null != session && session.isOpen()) {
			try {
				session.sendMessage(message);
			} catch (IOException e) {
				logger.error(e);
				return SendEnum.FAIL.getCode();
			}
		} else {
			return SendEnum.NOT.getCode();
		}
		return SendEnum.SUCCESS.getCode();
	}
}
