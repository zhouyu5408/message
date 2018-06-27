package com.message;

import com.message.entity.MsgTitleModel;
import com.message.entity.SubscribeInfo;
import com.message.exception.BaseException;
import com.message.handler.IMsgHandler;
import com.message.util.DefaultCreator;

/**
 * 系统消息对象实体. <br/>
 * 创建: 2017年11月24日 下午5:19:36 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (深圳市雁联计算系统有限公司) , All Rights Reserved.
 * @since JDK 1.7
 */
public class BasicMsg {
	private MsgTitleModel titleModel;
	private String msgHandler = "com.message.handler.impl.BaseMsgHandler";

	public MsgTitleModel getTitleModel() {
		return titleModel;
	}

	public void setTitleModel(MsgTitleModel titleModel) {
		this.titleModel = titleModel;
	}

	public String getMsgHandler() {
		return msgHandler;
	}

	public void setMsgHandler(String msgHandler) {
		this.msgHandler = msgHandler;
	}

	public BasicMsg(MsgTitleModel titleModel) {
		this.titleModel = titleModel;
	}

	public BasicMsg() {

	}

	/**
	 * 
	 * 进行消息发布. <br/>
	 *
	 * @param msg
	 * @param subscribeInfo
	 */
	public String publish(SubscribeInfo subscribeInfo) throws BaseException {
		// 获取消息处理器
		IMsgHandler msgHandler = DefaultCreator.getComponent(getMsgHandler());
		// 获取消息的发送状态
		String sendStatus = msgHandler.handleMessage(this, subscribeInfo);
		return sendStatus;
	}
}