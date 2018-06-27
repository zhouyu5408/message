package com.message.job;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.message.BasicMsg;
import com.message.entity.MsgDeliveryWay;
import com.message.entity.MsgTitle;
import com.message.entity.MsgTitleModel;
import com.message.entity.SubscribeInfo;
import com.message.enums.DeliveryWayEnum;
import com.message.enums.SendEnum;
import com.message.exception.BaseException;
import com.message.service.IMsgService;
import com.message.spring.SpringManager;
import com.message.util.DefaultCreator;

/**
 * @Date: 2017年11月24日 下午5:35:00 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public class MsgPublishJob implements Callable<String> {
	/**
	 * 订阅人
	 */
	private SubscribeInfo subscribeInfo;

	/**
	 * 订阅主题
	 */
	private MsgTitle title;

	/**
	 * log4j日志logger
	 */
	private Logger logger = Logger.getLogger(MsgPublishJob.class);

	public MsgPublishJob(SubscribeInfo subscribeInfo, MsgTitle title) {
		super();
		this.subscribeInfo = subscribeInfo;
		this.title = title;
	}

	/**
	 * 进行消息发布.
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	public String call() throws Exception {
		String sendStatus = SendEnum.SUCCESS.getCode();
		try {
			logger.debug("开始处理消息[内容:" + title.getTitleContent() + "]的发布,订阅用户ID[" + subscribeInfo.getUserId() + "],发布类型["
					+ DeliveryWayEnum.parseOf(subscribeInfo.getDeliveryWay()).getDesc() + "]");
			// 第一步获取订阅方式:phone("phone", "手机短信"), email("email", "电子邮件"),popup("popup", "桌面弹窗");
			String deliveryWay = subscribeInfo.getDeliveryWay();
			// 如果主题编码不为空，则根据订阅方式获取对应的模版类型
			MsgTitleModel msgTitleModel = null;
			if (title.getTitleId() != null) {
				msgTitleModel = getService().getTitleModel(title.getTitleId(), deliveryWay);
			}
			if (subscribeInfo.getMsgObject() == null) {
				MsgDeliveryWay mdw = getService().getDeliverWay(subscribeInfo.getDeliveryWay());
				subscribeInfo.setMsgObject(mdw.getMsgObject());
			}
			// 获取消息处理器
			BasicMsg basicMsg = DefaultCreator.getComponent(subscribeInfo.getMsgObject());
			basicMsg.setTitleModel(msgTitleModel);
			sendStatus = basicMsg.publish(subscribeInfo);
			logger.debug("消息[" + title.getId() + "],订阅用户ID[" + subscribeInfo.getUserId() + "],发布类型["
					+ DeliveryWayEnum.parseOf(subscribeInfo.getDeliveryWay()).getDesc() + "] 发布完毕");
		} catch (BaseException e) {
			throw e;
		} catch (Exception e) {
			logger.debug("消息[" + title.getId() + "],订阅用户ID[" + subscribeInfo.getUserId() + "],发布类型["
					+ DeliveryWayEnum.parseOf(subscribeInfo.getDeliveryWay()).getDesc() + "] 发布失败", e);
			logger.error(e);
		}
		// 主题推送失败，需要再次推送；未推送，需要在上线以后进行推送。
		getService().saveMsgList(title, subscribeInfo, sendStatus,subscribeInfo.getDeliveryWay());
		return null;
	}

	public IMsgService getService() {
		return SpringManager.getBeanByType(IMsgService.class);
	}
}