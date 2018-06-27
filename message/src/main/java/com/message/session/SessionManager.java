package com.message.session;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户管理当前在线的用户
 * 
 * @Date: 2017年11月29日 下午2:09:44 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public class SessionManager {
	private static SessionManager instance;
	private ConcurrentHashMap<String, IMessageSession> sessions = new ConcurrentHashMap<String, IMessageSession>();

	private SessionManager() {
	}

	public static SessionManager getInstace() {
		if (null == instance) {
			instance = new SessionManager();
		}
		return instance;
	}

	public void addSession(IMessageSession session) {
		Set<String> keySet = sessions.keySet();
		for (String key : keySet) {
			IMessageSession messageSession = sessions.get(key);
			String id = messageSession.getOperator();
			if (session.getOperator().equals(id)) {
				removeSession(id);
				System.out.println("销毁了userId为 " + id + " 的httpSession");
				break;
			}
		}
		sessions.put(session.getOperator(), session);
		System.out.println("新增了userId为 " + session.getOperator() + " 的httpSession");
	}

	public IMessageSession getSession(String operator) {
		return sessions.get(operator);
	}

	public void removeSession(IMessageSession session) {
		removeSession(session.getOperator());
	}

	public void removeSession(String operator) {
		sessions.remove(operator);
		System.out.println("销毁了userId为 " + operator + " 的httpSession");
	}

	public int getSize() {
		return sessions.size();
	}
}
