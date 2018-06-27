package com.message.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 订阅人中间类，用于封装订阅人需要的信息
 * 
 * @Date: 2017年12月28日 下午5:25:10 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
@JsonInclude(Include.NON_NULL)
public class SubscribeInfo {
	/**
	 * 用户编号
	 */
	private String userId;
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 电话号码
	 */
	private String phoneNum;
	/**
	 * 订阅方式（邮件/短信/弹窗）
	 */
	private String deliveryWay;
	/**
	 * 订阅的处理类
	 */
	private String msgObject;

	private String id;// 便于前端取数据之用
	private String text;// 便于前端取数据之用

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
		this.id = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
		this.text = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getDeliveryWay() {
		return deliveryWay;
	}

	public void setDeliveryWay(String deliveryWay) {
		this.deliveryWay = deliveryWay;
	}

	public String getMsgObject() {
		return msgObject;
	}

	public void setMsgObject(String msgObject) {
		this.msgObject = msgObject;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubscribeInfo other = (SubscribeInfo) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
}
