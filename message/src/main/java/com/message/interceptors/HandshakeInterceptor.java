package com.message.interceptors;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.message.constants.MessageConstants;

/**
 * 在websocket握手之前的拦截器
 * 
 * @Date: 2017年12月26日 上午9:23:15 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {
	private static final Logger logger = Logger.getLogger(HandshakeInterceptor.class);

	/**
	 * 用于用户登录的标识（userId）的记录，以便于后面向指定用户进行发送
	 */
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		// 握手之前从用户的登录信息中获取用户的userId
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			HttpSession session = servletRequest.getServletRequest().getSession(false);
			if (session != null) {
				// 使用userId区分WebSocketHandler，以便定向发送消息
				String userId = (String) session.getAttribute(MessageConstants.USER_ID);
				if (null != userId && !userId.equals("")) {
					attributes.put(MessageConstants.USER_ID, userId);
				}
			} else {
				logger.error("httpsession is null");
			}
		}
		return true;
		// return super.beforeHandshake(request, response, wsHandler,
		// attributes);
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		super.afterHandshake(request, response, wsHandler, ex);
	}

}
