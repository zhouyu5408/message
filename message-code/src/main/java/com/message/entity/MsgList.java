package com.message.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.message.annotation.EnumCodeToDic;
import com.message.enums.DeliveryWayEnum;
import com.message.enums.SendEnum;

/**
 * 消息推送明细表
 * 
 * @Date: 2017年12月28日 下午5:15:06 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
@JsonInclude(Include.NON_NULL)
public class MsgList extends MsgBaseEntity implements Serializable {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 主题编号
	 */
	private String titleId;
	/**
	 * 主题名称
	 */
	private String titleName;
	/**
	 * 用户编号
	 */
	private String userId;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 发送方式
	 */
	@EnumCodeToDic(DeliveryWayEnum.class)
	private String deliveryWay;
	/**
	 * 发送状态（已发送/发送失败/未发送）
	 */
	@EnumCodeToDic(SendEnum.class)
	private String sendStatus;
	/**
	 * 接受状态（是否已经浏览过）
	 */
	private String recvStatus;
	/**
	 * 发送时间
	 */
	private Date sendTime;
	/**
	 * 重发次数
	 */
	private Integer resendCount;
	/**
	 * 查询起始时间
	 */
	private Date startTime;
	/**
	 * 查询结束时间
	 */
	private Date endTime;

	private static final long serialVersionUID = 1L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId == null ? null : titleId.trim();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public String getDeliveryWay() {
		return deliveryWay;
	}

	public void setDeliveryWay(String deliveryWay) {
		this.deliveryWay = deliveryWay;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus == null ? null : sendStatus.trim();
	}

	public String getRecvStatus() {
		return recvStatus;
	}

	public void setRecvStatus(String recvStatus) {
		this.recvStatus = recvStatus == null ? null : recvStatus.trim();
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getResendCount() {
		return resendCount;
	}

	public void setResendCount(Integer resendCount) {
		this.resendCount = resendCount;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}