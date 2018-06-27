package com.message.handler.impl;

import org.apache.log4j.Logger;

import com.message.BasicMsg;
import com.message.entity.SubscribeInfo;
import com.message.exception.BaseException;
import com.message.popup.PopupHandler;
import com.message.spring.SpringManager;

/**
 * 弹出消息处理器 . <br/>
 * 
 * @Date: 2017年11月27日 上午9:42:41 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
@SuppressWarnings("unused")
public class PopupMsgHandler extends BaseMsgHandler {
	private Logger logger = Logger.getLogger(PopupMsgHandler.class);

	@Override
	public String handleMessage(BasicMsg msg, SubscribeInfo subscribeInfo) throws BaseException {
		//super.handleMessage(msg, subscribeInfo);
		logger.debug("开始进行弹窗处理!");
		/**
		 * 1、获取推送的用户编号用户指定推送消息； 2、获取推送信息的主题（弹窗仅仅只是一个窗口提示）； 3、使用webSocket进行弹窗推送；
		 */
		String userId = subscribeInfo.getUserId();
		String titleId = msg.getTitleModel().getTitleId();
		// 获取发送状态：成功/失败/未发送
		// 发送的仅仅是一个提示：您有新的消息请查看！！！
		String sendStatus = getPopupHandler().sendMessage(userId, "您有新的消息,请查看");
		return sendStatus;
	}

	public PopupHandler getPopupHandler() {
		return SpringManager.getBeanByType(PopupHandler.class);
	}
}
