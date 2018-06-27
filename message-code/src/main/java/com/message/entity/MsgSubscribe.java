package com.message.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.message.annotation.EnumCodeToDic;
import com.message.enums.DeliveryWayEnum;
import com.message.enums.SubTypeEnum;

/**
 * 订阅关系表
 * 
 * @Date: 2017年12月28日 下午5:18:00 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
@JsonInclude(Include.NON_NULL)
public class MsgSubscribe extends MsgBaseEntity implements Serializable {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 用户编号
	 */
	private String userId;
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 主题编号
	 */
	private String titleId;
	/**
	 * 主题名称
	 */
	private String titleName;
	/**
	 * 订阅方式
	 */
	@EnumCodeToDic(DeliveryWayEnum.class)
	private String deliveryWay;
	/**
	 * 订阅关系是否有效
	 */
	private String flag;
	/**
	 * 订阅组编号
	 */
	private String groupId;
	/**
	 * 订阅组名称
	 */
	private String groupName;
	/**
	 * 订阅者类型（全部/组/角色/机构/用户）
	 */
	@EnumCodeToDic(SubTypeEnum.class)
	private String subType;

	private String[] deliveryWays;
	private static final long serialVersionUID = 1L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId == null ? null : titleId.trim();
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName == null ? null : titleName.trim();
	}

	public String getDeliveryWay() {
		return deliveryWay;
	}

	public void setDeliveryWay(String deliveryWay) {
		this.deliveryWay = deliveryWay == null ? null : deliveryWay.trim();
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag == null ? null : flag.trim();
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId == null ? null : groupId.trim();
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName == null ? null : groupName.trim();
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType == null ? null : subType.trim();
	}

	public String[] getDeliveryWays() {
		return deliveryWays;
	}

	public void setDeliveryWays(String[] deliveryWays) {
		this.deliveryWays = deliveryWays;
	}
}