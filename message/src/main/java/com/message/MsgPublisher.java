package com.message;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.message.entity.MsgTitle;
import com.message.entity.SubscribeInfo;
import com.message.enums.MsgTypeEnum;
import com.message.exception.BaseException;
import com.message.job.MsgPublishJob;
import com.message.service.IMsgService;
import com.message.spring.SpringManager;

/**
 * 消息发布器. <br/>
 * 创建: 2017年11月24日 下午4:57:12 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (深圳市雁联计算系统有限公司) , All Rights Reserved.
 * @since JDK 1.7
 */
public class MsgPublisher {
	private static final Logger logger = Logger.getLogger(MsgPublisher.class);
	private static MsgPublisher msgPublisher;
	private ExecutorService pool = Executors.newCachedThreadPool();
	// private CompletionService<String> completion = new
	// ExecutorCompletionService<String>(pool);

	private MsgPublisher() {
		pool = Executors.newCachedThreadPool();
		// completion = new ExecutorCompletionService<String>(pool);
	}

	public static MsgPublisher getInstance() {
		if (msgPublisher == null) {
			msgPublisher = new MsgPublisher();
		}
		return msgPublisher;
	}

	public void publish(MsgTitle title) {
		/**
		 * 判断消息类别<br/>
		 * 如果是公告，则直接保存，不用推送 否则为一般类型消息，根据主题获取对应的订阅用户信息（包括订阅方式）
		 */
		if (MsgTypeEnum.ANNOUNCEMENT.getCode().equals(title.getTitleType())) {
			try {
				getService().saveTitleSendRecord(title);
			} catch (Exception e) {
				logger.error("公告保存失败",e);
				throw new BaseException("公告保存失败");
			}
		} else {
			// 获取订阅用户信息表
			Set<SubscribeInfo> subscribers = getService().getSubscriberSet(title);
			// 使用线程池发布消息
			for (SubscribeInfo subscribeInfo : subscribers) {
				pool.submit(new MsgPublishJob(subscribeInfo, title));
			}
		}
	}

	public void publish(MsgTitle title, List<SubscribeInfo> subscribers) {
		// 判断消息类型
		if (MsgTypeEnum.ANNOUNCEMENT.getCode().equals(title.getTitleType())) {
			getService().saveTitleSendRecord(title);
		} else {
			// 使用线程池发布消息
			for (SubscribeInfo subscribeInfo : subscribers) {
				pool.submit(new MsgPublishJob(subscribeInfo, title));
			}
		}
	}

	public IMsgService getService() {
		return (IMsgService) SpringManager.getBeanByType(IMsgService.class);
	}
}
