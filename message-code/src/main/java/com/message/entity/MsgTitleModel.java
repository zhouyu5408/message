package com.message.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 主题模版表
 * 
 * @Date: 2017年12月28日 下午5:18:59 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
@JsonInclude(Include.NON_NULL)
public class MsgTitleModel extends MsgBaseEntity implements Serializable {
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
	 * 订阅方式
	 */
	@NotBlank(message = "订阅方式不能为空")
	private String deliveryWay;
	/**
	 * 主题模版
	 */
	private String titleModel;
	/**
	 * 附件组编号
	 */
	private String attachGroupId;

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

	public String getDeliveryWay() {
		return deliveryWay;
	}

	public void setDeliveryWay(String deliveryWay) {
		this.deliveryWay = deliveryWay == null ? null : deliveryWay.trim();
	}

	public String getTitleModel() {
		return titleModel;
	}

	public void setTitleModel(String titleModel) {
		this.titleModel = titleModel == null ? null : titleModel.trim();
	}

	public String getAttachGroupId() {
		return attachGroupId;
	}

	public void setAttachGroupId(String attachGroupId) {
		this.attachGroupId = attachGroupId == null ? null : attachGroupId.trim();
	}
}