package com.message.enums;

/**
 * 订阅类型枚举类 . <br/>
 * 
 * @Date: 2017年11月24日 下午5:38:05 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public enum DeliveryWayEnum implements BaseEnum {
	PHONE("phone", "手机短信"), EMAIL("email", "电子邮件"), POPUP("popup", "桌面弹窗");

	private DeliveryWayEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	private String code;
	private String desc;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static String getCodeByDesc(String desc) {
		for (DeliveryWayEnum jobType : DeliveryWayEnum.values()) {
			if (jobType.desc.equals(desc)) {
				return jobType.code;
			}
		}
		return "";
	}

	public static String getDescByCode(String code) {
		for (DeliveryWayEnum jobType : DeliveryWayEnum.values()) {
			if (jobType.code.equals(code)) {
				return jobType.desc;
			}
		}
		return "";
	}

	/**
	 * 枚举转换
	 */
	public static DeliveryWayEnum parseOf(String value) {
		for (DeliveryWayEnum item : values())
			if (item.getCode().equals(value))
				return item;
		throw new IllegalArgumentException("枚举值[" + value + "]不匹配!");
	}
}
