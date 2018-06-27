package com.message.entity;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 订阅主题表
 * 
 * @Date: 2017年12月28日 下午5:18:30 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public class MsgTitle extends MsgBaseEntity implements Serializable {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 主题编号
	 */
	@NotBlank(message = "主题编号不能为空")
	private String titleId;
	/**
	 * 主题名称
	 */
	@NotBlank(message = "主题名称不能为空")
	private String titleName;
	/**
	 * 类型编号
	 */
	private String titleType;
	/**
	 * 类型名称
	 */
	private String typeName;
	/**
	 * 主题内容
	 */
	private String titleContent;
	/**
	 * 主题的邮件模版
	 */
	@NotBlank(message = "邮件模版不能为空")
	private String emailModel;
	/**
	 * 主题的短信模版
	 */
	@NotBlank(message = "短信模版不能为空")
	private String phoneModel;
	/**
	 * 主题的弹窗模版
	 */
	@NotBlank(message = "弹窗模版不能为空")
	private String popupModel;
	/**
	 * 新增的附件
	 */
	private String[] addKeys;
	/**
	 * 删除的附件
	 */
	private String[] delKeys;
	/**
	 * 主题已经拥有的附件
	 */
	private List<String> allKeys;

	private String[] deliveryWays;

	private List<SubscribeInfo> users;
	private List<Organization> orgs;
	private List<Role> roles;
	private String[] userIds;
	private String[] orgIds;
	private String[] roleIds;

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

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName == null ? null : titleName.trim();
	}

	public String getTitleType() {
		return titleType;
	}

	public void setTitleType(String titleType) {
		this.titleType = titleType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTitleContent() {
		return titleContent;
	}

	public void setTitleContent(String titleContent) {
		this.titleContent = titleContent == null ? null : titleContent.trim();
	}

	public String getEmailModel() {
		if (null == emailModel || "".equals(emailModel.trim())) {
			return this.titleContent;
		}
		return emailModel;
	}

	public void setEmailModel(String emailModel) {
		this.emailModel = emailModel;
	}

	public String getPhoneModel() {
		if (null == phoneModel || "".equals(phoneModel.trim())) {
			return this.titleContent;
		}
		return phoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}

	public String getPopupModel() {
		if (null == popupModel || "".equals(popupModel.trim())) {
			return this.titleContent;
		}
		return popupModel;
	}

	public void setPopupModel(String popupModel) {
		this.popupModel = popupModel;
	}

	public String[] getAddKeys() {
		return addKeys;
	}

	public void setAddKeys(String[] addKeys) {
		this.addKeys = addKeys;
	}

	public String[] getDelKeys() {
		return delKeys;
	}

	public void setDelKeys(String[] delKeys) {
		this.delKeys = delKeys;
	}

	public List<String> getAllKeys() {
		return allKeys;
	}

	public void setAllKeys(List<String> allKeys) {
		this.allKeys = allKeys;
	}

	public String[] getDeliveryWays() {
		return deliveryWays;
	}

	public void setDeliveryWays(String[] deliveryWays) {
		this.deliveryWays = deliveryWays;
	}

	public List<SubscribeInfo> getUsers() {
		return users;
	}

	public void setUsers(List<SubscribeInfo> users) {
		this.users = users;
	}

	public List<Organization> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<Organization> orgs) {
		this.orgs = orgs;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String[] getUserIds() {
		return userIds;
	}

	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
	}

	public String[] getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(String[] orgIds) {
		this.orgIds = orgIds;
	}

	public String[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

}