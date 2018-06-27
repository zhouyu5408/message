package com.message.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 机构中间类，只用于获取指定的信息
 * 
 * @Date: 2017年12月28日 下午5:22:52 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
@JsonInclude(Include.NON_NULL)
public class Organization {
	/**
	 * 机构编号
	 */
	private String orgId;
	/**
	 * 机构名称
	 */
	private String orgName;
	private String id;// 便于前端取数据之用
	private String text;// 便于前端取数据之用
	private List<Organization> children;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
		this.id = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
		this.text = orgName;
	}

	public List<Organization> getChildren() {
		if (null == children) {
			return new ArrayList<Organization>();
		}
		return children;
	}

	public void setChildren(List<Organization> children) {
		this.children = children;
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
