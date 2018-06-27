package com.message;

import com.message.entity.MsgTitleModel;

/**
 * 弹出消息对象实体. <br/>
 * 
 * @Date: 2017年11月27日 下午1:43:13 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public class PopupMsg extends BasicMsg {

	private String msgHandler = "com.message.handler.impl.PopupMsgHandler";

	/**
	 * 
	 * 构造邮件消息.
	 *
	 * @param msg
	 */
	public PopupMsg(MsgTitleModel titleModel) {
		super(titleModel);
	}

	public PopupMsg() {

	}

	public String getMsgHandler() {
		return msgHandler;
	}
}
