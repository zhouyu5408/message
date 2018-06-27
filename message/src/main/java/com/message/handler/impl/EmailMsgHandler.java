package com.message.handler.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.message.BasicMsg;
import com.message.entity.MsgParam;
import com.message.entity.MsgTitleModelAttach;
import com.message.entity.SubscribeInfo;
import com.message.enums.SendEnum;
import com.message.exception.BaseException;
import com.message.mail.MailSender;
import com.message.mail.MailSenderInfo;

/**
 * 邮件消息处理器 . <br/>
 * 创建: 2014年6月4日 上午9:23:35 <br/>
 * 
 * @author zhouyu
 * @version 1.0 Copyright (深圳市雁联计算系统有限公司) 2014, All Rights Reserved.
 * @since JDK 1.6
 */
public class EmailMsgHandler extends BaseMsgHandler {
	/**
	 * Log4j日志logger
	 */
	private Logger logger = Logger.getLogger(EmailMsgHandler.class);

	public String handleMessage(BasicMsg msg, SubscribeInfo subscribeInfo) throws BaseException {
		// super.handleMessage(msg, subscribeInfo);
		logger.debug("开始进行邮件发送!");
		// 从数据库中获取对应的邮件服务信息（可缓存）
		String mailServerHost = null, mailServerPort = null, mailUserName = null, mailPassword = null;
		MailSenderInfo mailInfo = new MailSenderInfo();
		MsgParam param = null;
		// 获取邮件服务器
		try {
			param = getService().getParamByName("mail_server_host");
		} catch (Exception e) {
			throw new BaseException("邮件服务器参数未定义[参数名称:mail_server_host]!");
		}
		mailServerHost = param.getParamValue();
		mailInfo.setMailServerHost(mailServerHost);
		// 获取邮件服务器端口
		try {
			param = getService().getParamByName("mail_server_port");
		} catch (Exception e) {
			throw new BaseException("邮件服务器端口参数未定义[参数名称:mail_server_port]!");
		}
		mailServerPort = param.getParamValue();
		mailInfo.setMailServerPort(mailServerPort);
		mailInfo.setValidate(true);
		// 获取发送邮件的帐号
		try {
			param = getService().getParamByName("mail_user_name");
		} catch (Exception e) {
			throw new BaseException("邮件地址参数未定义[参数名称:mail_user_name]!");
		}
		mailUserName = param.getParamValue();
		mailInfo.setUserName(mailUserName);
		// 获取发送邮件的密码
		try {
			param = getService().getParamByName("mail_password");
		} catch (Exception e) {
			throw new BaseException("邮件密码参数未定义[参数名称:mail_password]!");
		}
		mailPassword = param.getParamValue();
		mailInfo.setPassword(mailPassword);
		mailInfo.setFromAddress(mailUserName);
		mailInfo.setValidate(true);
		mailInfo.setToAddress(subscribeInfo.getEmail());
		mailInfo.setSubject(getService().getTitleByTitleId(msg.getTitleModel().getTitleId()).getTitleName());
		mailInfo.setContent(msg.getTitleModel().getTitleModel());

		// 获取邮件发送的附件:1、获取附件组编号；2、通过附件组编号获取附件列表;3、将附件添加到邮件中
		List<File> fileList = new ArrayList<File>();
		try {
			String attachGroupId = msg.getTitleModel().getAttachGroupId();
			List<MsgTitleModelAttach> attachList = null;
			try {
				attachList = getService().getAttachList(attachGroupId);
				if (null != attachList && attachList.size() > 0) {
					for (MsgTitleModelAttach attach : attachList) {
						// String address = attach.getAttachAddress();
						// String fileName = attach.getAttachName();
						// String path = address + "\\" + fileName;
						// File file = new File(path);
						// 从邮件服务器获取对应的file
						File file = getService().getFileByKey(attach.getFileKey());
						fileList.add(file);
					}
					mailInfo.setFileList(fileList);
				}
			} catch (Exception e) {
				logger.error("获取附件组为【" + attachGroupId + "】的附件失败", e);
				return SendEnum.FAIL.getCode();
			}
		} catch (Exception e) {
			logger.error("获取主题名称【" + msg.getTitleModel().getTitleId() + "】订阅方式【" + msg.getTitleModel().getDeliveryWay()
					+ "】的附件组失败", e);
			return SendEnum.FAIL.getCode();
		}

		// 这个类主要来发送邮件
		MailSender sms = new MailSender();
		// 发送html格式
		try {
			if (!sms.sendHtmlMail(mailInfo)) {
				return SendEnum.FAIL.getCode();
				// throw new BaseException("邮件发送失败!请检查邮件参数配置!");
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("邮件发送时转码异常", e);
		}
		return SendEnum.SUCCESS.getCode();
	}

}