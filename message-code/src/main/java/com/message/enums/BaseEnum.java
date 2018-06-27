package com.message.enums;

/**
 * 枚举类型需要实现的接口 . <br/>
 * 创建: 2017年11月24日 下午3:41:24 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (深圳市雁联计算系统有限公司) , All Rights Reserved.
 * @since JDK 1.7
 */
public interface BaseEnum {
	/**
	 * 
	 * 获取枚举代码. <br/>
	 *
	 * @return
	 */
	public abstract String getCode();

	/**
	 * 
	 * 设置枚举代码. <br/>
	 *
	 * @param s
	 */
	public abstract void setCode(String s);

	/**
	 * 
	 * 获取枚举描述. <br/>
	 *
	 * @return
	 */
	public abstract String getDesc();

	/**
	 * 
	 * 设置枚举描述. <br/>
	 *
	 * @param s
	 */
	public abstract void setDesc(String s);

}
