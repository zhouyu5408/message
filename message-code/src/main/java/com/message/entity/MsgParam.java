package com.message.entity;

import java.io.Serializable;
import java.util.Date;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 消息所需参数表
 * 
 * @Date: 2017年12月28日 下午5:15:52 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
@JsonInclude(Include.NON_NULL)
public class MsgParam extends MsgBaseEntity implements Serializable {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 参数编号
	 */
	@NotBlank(message = "参数编号不能为空")
	private String paramNo;
	/**
	 * 参数名称
	 */
	@NotBlank(message = "参数名称不能为空")
	private String paramName;
	/**
	 * 参数值
	 */
	private String paramValue;
	/**
	 * 参数类型
	 */
	private String paramType;
	/**
	 * 参数说明
	 */
	private String remark;
	/**
	 * 是否可以修改
	 */
	private String isModify;
	/**
	 * 修改的时间
	 */
	private Date modifyTime;

	private static final long serialVersionUID = 1L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getParamNo() {
		return paramNo;
	}

	public void setParamNo(String paramNo) {
		this.paramNo = paramNo == null ? null : paramNo.trim();
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName == null ? null : paramName.trim();
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue == null ? null : paramValue.trim();
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType == null ? null : paramType.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getIsModify() {
		return isModify;
	}

	public void setIsModify(String isModify) {
		this.isModify = isModify == null ? null : isModify.trim();
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}