package com.message;

import com.message.entity.MsgTitleModel;

/**
 * 邮件消息对象实体. <br/>
 * 
 * @Date: 2017年11月27日 下午1:44:00 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public class EmailMsg extends BasicMsg {

	private String msgHandler = "com.message.handler.impl.EmailMsgHandler";

	/**
	 * 
	 * 构造邮件消息.
	 *
	 * @param msg
	 */
	public EmailMsg(MsgTitleModel titleModel) {
		super(titleModel);
	}

	public EmailMsg() {

	}

	public String getMsgHandler() {
		return msgHandler;
	}
}
