package com.message.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.message.session.SessionManager;

/**
 * 当HttpSession失效时，删除程序中管理对应的WebScoketSession
 * 
 * @Date: 2017年11月29日 上午10:42:58 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public class SocketHttpSessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent event) {

	}

	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		String userId = (String) session.getAttribute("userId");
		if (null != userId && !"".equals(userId)) {
			SessionManager.getInstace().removeSession(userId);
		}
	}

}
