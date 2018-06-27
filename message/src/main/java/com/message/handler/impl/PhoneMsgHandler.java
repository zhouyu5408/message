package com.message.handler.impl;

import org.apache.log4j.Logger;

import com.message.BasicMsg;
import com.message.entity.SubscribeInfo;
import com.message.exception.BaseException;

/**
 * 短信处理器
 * 
 * @Date: 2017年12月27日 上午9:45:51 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public class PhoneMsgHandler extends BaseMsgHandler {
	private Logger logger = Logger.getLogger(PhoneMsgHandler.class);

	@Override
	public String handleMessage(BasicMsg msg, SubscribeInfo subscribeInfo) throws BaseException {
		// super.handleMessage(msg, subscribeInfo);
		logger.debug("开始进行短信发送!");
		return "";
	}

}
