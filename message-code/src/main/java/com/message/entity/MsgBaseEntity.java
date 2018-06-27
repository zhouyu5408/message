package com.message.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class MsgBaseEntity implements Serializable {
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String LOG_DATE = "yyyy-MM-dd HH:mm:ss";

	private static final long serialVersionUID = -1836123103315772606L;

	private String dataStatus;

	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	// 排序字段，不能为列
	private String sort;
	// 排序字段，不能为列
	private String order;

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
