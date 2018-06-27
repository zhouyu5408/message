package com.message.entity;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 订阅组表
 * 
 * @Date: 2017年12月28日 下午5:16:32 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
@JsonInclude(Include.NON_NULL)
public class MsgSubGroup extends MsgBaseEntity implements Serializable {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 组编号
	 */
	@NotBlank(message = "订阅组编号不能为空")
	private String subGroupId;
	/**
	 * 组名称
	 */
	@NotBlank(message = "订阅组名称不能为空")
	private String subGroupName;
	/**
	 * 组描述
	 */
	private String subGroupDesc;
	/**
	 * 用户组
	 */
	private List<String> userIds;
	/**
	 * 机构组
	 */
	private List<String> orgIds;
	/**
	 * 角色组
	 */
	private List<String> roleIds;
	private List<SubscribeInfo> userList;
	private List<Organization> orgList;
	private List<Role> roleList;
	private String[] userArray;
	private String[] orgArray;
	private String[] roleArray;

	private static final long serialVersionUID = 1L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getSubGroupId() {
		return subGroupId;
	}

	public void setSubGroupId(String subGroupId) {
		this.subGroupId = subGroupId == null ? null : subGroupId.trim();
	}

	public String getSubGroupName() {
		return subGroupName;
	}

	public void setSubGroupName(String subGroupName) {
		this.subGroupName = subGroupName == null ? null : subGroupName.trim();
	}

	public String getSubGroupDesc() {
		return subGroupDesc;
	}

	public void setSubGroupDesc(String subGroupDesc) {
		this.subGroupDesc = subGroupDesc;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	public List<String> getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(List<String> orgIds) {
		this.orgIds = orgIds;
	}

	public List<String> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

	public String[] getUserArray() {
		return userArray;
	}

	public void setUserArray(String[] userArray) {
		this.userArray = userArray;
	}

	public String[] getOrgArray() {
		return orgArray;
	}

	public void setOrgArray(String[] orgArray) {
		this.orgArray = orgArray;
	}

	public String[] getRoleArray() {
		return roleArray;
	}

	public void setRoleArray(String[] roleArray) {
		this.roleArray = roleArray;
	}

	public List<SubscribeInfo> getUserList() {
		return userList;
	}

	public void setUserList(List<SubscribeInfo> userList) {
		this.userList = userList;
	}

	public List<Organization> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<Organization> orgList) {
		this.orgList = orgList;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

}