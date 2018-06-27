package com.message.handler.impl;

import org.apache.log4j.Logger;

import com.message.BasicMsg;
import com.message.entity.SubscribeInfo;
import com.message.enums.SendEnum;
import com.message.exception.BaseException;
import com.message.handler.IMsgHandler;
import com.message.service.IMsgService;
import com.message.spring.SpringManager;

/**
 * 默认消息处理器
 * 
 * @Date: 2017年12月27日 上午9:45:29 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public class BaseMsgHandler implements IMsgHandler {
	private BasicMsg msg;
	private SubscribeInfo subscribeInfo;

	/**
	 * Log4j日志logger
	 */
	private Logger logger = Logger.getLogger(BaseMsgHandler.class);

	/**
	 * 消息处理逻辑入口.
	 */
	public String handleMessage(BasicMsg msg, SubscribeInfo subscribeInfo) throws BaseException {
		this.msg = msg;
		this.subscribeInfo = subscribeInfo;
		// 将主题发送记录下来
		try {
			getService().saveTitleSendRecord(msg.getTitleModel());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("主题[" + msg.getTitleModel().getId() + "]入库失败!", e);
			throw new BaseException("主题[" + msg.getTitleModel().getTitleId() + "]入库失败!");
		}
		logger.debug("消息[" + msg.getTitleModel().getTitleId() + "]入库成功");
		return SendEnum.SUCCESS.getCode();
	}

	public IMsgService getService() {
		return SpringManager.getBeanByType(IMsgService.class);
	}

	public BasicMsg getMsg() {
		return msg;
	}

	public SubscribeInfo getSubscribeInfo() {
		return subscribeInfo;
	}
}