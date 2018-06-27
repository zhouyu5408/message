package com.message;

import com.message.entity.MsgTitleModel;

/**
 * 短信通知实体
 * 
 * @Date: 2017年11月30日 上午8:56:45 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public class PhoneMsg extends BasicMsg {

	private String msgHandler = "com.message.handler.impl.PopupMsgHandler";

	/**
	 * 
	 * 构造邮件消息.
	 *
	 * @param msg
	 */
	public PhoneMsg(MsgTitleModel titleModel) {
		super(titleModel);
	}

	public PhoneMsg() {

	}

	public String getMsgHandler() {
		return msgHandler;
	}
}
