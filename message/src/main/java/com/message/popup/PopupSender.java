package com.message.popup;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.apache.log4j.Logger;

import com.message.entity.MsgTitleModel;
import com.message.exception.BaseException;
import com.message.session.IMessageSession;
import com.message.session.PopupSession;
import com.message.session.SessionManager;

/**
 * 使用webSocket实现弹出消息
 * 
 * @Date: 2017年11月29日 下午5:45:33 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 *                 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
// @ServerEndpoint(value = "/websocket", configurator = HttpSessionConfig.class)
public class PopupSender {
	private static final Logger logger = Logger.getLogger(PopupSender.class);
	private String id;

	/**
	 * 连接建立成功调用的方法
	 * 
	 * @param session
	 *            可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		System.out.println(this);
		try {
			HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
			String userId = (String) httpSession.getAttribute("userId");
			if (null != userId && !"".equals(userId)) {
				this.id = userId;
				PopupSession messageSession = new PopupSession((String) httpSession.getAttribute("userId"), session);
				SessionManager.getInstace().addSession(messageSession);
			}
		} catch (Exception e) {
			logger.equals(e);
		}
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		try {
			String userId = this.id;
			if (null != userId && !"".equals(userId)) {
				SessionManager.getInstace().removeSession(userId);
			}
		} catch (Exception e) {
			logger.error(e);
		}

	}

	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @param message
	 *            客户端发送过来的消息
	 * @param session
	 *            可选的参数
	 */
	@OnMessage
	public void onMessage(String message, Session session) {

	}

	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		logger.error(error);
	}

	/**
	 * 发出弹窗指令
	 * 
	 * @author zhouyu
	 * @param msg
	 * @param operator
	 */
	public boolean sendMessage(MsgTitleModel model, String operator) throws BaseException {
		// 消息是否已经提示过了
		boolean flag = true;
		try {
			// 根据接受的Id从Session缓存中获取对应的websocket的session
			IMessageSession messageSession = SessionManager.getInstace().getSession(operator);
			// String message = JSONObject.toJSONString(msg);
			// 订阅消息的人如果在线就提示，不在线就等登录的时候提示
			if (null != messageSession) {
				messageSession.getSession().getBasicRemote().sendText(model.getTitleModel());
			} else {
				flag = false;
			}
		} catch (IOException e) {
			flag = false;
			logger.error(e);
		}
		return flag;
	}
}
