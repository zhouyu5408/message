package com.message.enums;

public enum MsgTypeEnum implements BaseEnum {
	NORMAL("0", "一般消息"), 
	ANNOUNCEMENT("1", "公告");

	private MsgTypeEnum(String code, String desc) {
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
		for (MsgTypeEnum jobType : MsgTypeEnum.values()) {
			if (jobType.desc.equals(desc)) {
				return jobType.code;
			}
		}
		return "";
	}

	public static String getDescByCode(String code) {
		for (MsgTypeEnum jobType : MsgTypeEnum.values()) {
			if (jobType.code.equals(code)) {
				return jobType.desc;
			}
		}
		return "";
	}
	
	/**
	 * 枚举转换
	 */
	public static MsgTypeEnum parseOf(String value) {
		for (MsgTypeEnum item : values())
			if (item.getCode().equals(value))
				return item;
		throw new IllegalArgumentException("枚举值[" + value + "]不匹配!");
	}
}
