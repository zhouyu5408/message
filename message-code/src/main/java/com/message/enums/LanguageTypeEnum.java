package com.message.enums;

/**
 * 语言类型枚举类 . <br/>
 * 
 * @Date: 2017年11月27日 上午9:26:57 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public enum LanguageTypeEnum implements BaseEnum {
	zh_CN("zh-CN", "中文(简体)"), en("en", "English");

	private LanguageTypeEnum(String code, String desc) {
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
		for (LanguageTypeEnum jobType : LanguageTypeEnum.values()) {
			if (jobType.desc.equals(desc)) {
				return jobType.code;
			}
		}
		return "";
	}

	public static String getDescByCode(String code) {
		for (LanguageTypeEnum jobType : LanguageTypeEnum.values()) {
			if (jobType.code.equals(code)) {
				return jobType.desc;
			}
		}
		return "";
	}

	/**
	 * 枚举转换
	 */
	public static LanguageTypeEnum parseOf(String value) {
		for (LanguageTypeEnum item : values())
			if (item.getCode().equals(value))
				return item;
		throw new IllegalArgumentException("枚举值[" + value + "]不匹配!");
	}
}