package com.message.enums;

/**
 * 消息模块缓存枚举类 . <br/>
 * 创建: 2017年11月24日 下午3:42:12 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (深圳市雁联计算系统有限公司) , All Rights Reserved.
 * @since JDK 1.7
 */
public enum MessageCacheEnum implements BaseEnum {
	SystemParam("SystemParam", "系统参数"), RespCode("RespCode", "系统响应码");

	private MessageCacheEnum(String code, String desc) {
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
		for (MessageCacheEnum jobType : MessageCacheEnum.values()) {
			if (jobType.desc.equals(desc)) {
				return jobType.code;
			}
		}
		return "";
	}

	public static String getDescByCode(String code) {
		for (MessageCacheEnum jobType : MessageCacheEnum.values()) {
			if (jobType.code.equals(code)) {
				return jobType.desc;
			}
		}
		return "";
	}

	/**
	 * 枚举转换
	 */
	public static MessageCacheEnum parseOf(String value) {
		for (MessageCacheEnum item : values())
			if (item.getCode().equals(value))
				return item;
		throw new IllegalArgumentException("枚举值[" + value + "]不匹配!");
	}
}
