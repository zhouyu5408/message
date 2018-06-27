package com.message.entity;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 订阅方式表
 * 
 * @Date: 2017年12月28日 下午5:14:30 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
@JsonInclude(Include.NON_NULL)
public class MsgDeliveryWay extends MsgBaseEntity implements Serializable {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 订阅方式编号
	 */
	private String deliveryWay;
	/**
	 * 处理订阅方式的实体类
	 */
	private String msgObject;

	private static final long serialVersionUID = 1L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getDeliveryWay() {
		return deliveryWay;
	}

	public void setDeliveryWay(String deliveryWay) {
		this.deliveryWay = deliveryWay == null ? null : deliveryWay.trim();
	}

	public String getMsgObject() {
		return msgObject;
	}

	public void setMsgObject(String msgObject) {
		this.msgObject = msgObject == null ? null : msgObject.trim();
	}
}