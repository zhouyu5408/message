package com.message.handler;

import com.message.BasicMsg;
import com.message.entity.SubscribeInfo;
import com.message.service.IMsgService;

/**
 * 消息处理器接口. <br/>
 * 
 * @Date: 2017年11月27日 上午9:36:37 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public interface IMsgHandler {

	/**
	 * 
	 * 处理消息. <br/>
	 *
	 * @param basicMsg
	 * @param subscribeInfo
	 */
	public String handleMessage(BasicMsg basicMsg, SubscribeInfo subscribeInfo);

	/**
	 * 
	 * 获取消息处理逻辑实现类. <br/>
	 *
	 * @return
	 */
	public IMsgService getService();

}
