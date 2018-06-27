package com.message.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 角色中间类，用于获取指定角色信息
 * 
 * @Date: 2017年12月28日 下午5:23:52 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
@JsonInclude(Include.NON_NULL)
public class Role {
	/**
	 * 角色编号
	 */
	private String roleId;
	/**
	 * 角色名
	 */
	private String roleName;
	private String id;// 便于前端取数据之用
	private String text;// 便于前端取数据之用

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
		this.id = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
		this.text = roleName;
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

}
