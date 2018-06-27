package com.message.util;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.message.annotation.EnumCodeToDic;

public class TransField2Dic extends TransFieldToValue {
	private static TransField2Dic transField2Dic = new TransField2Dic();

	/**
	 * 将 数组中 需要转义的字段 转义
	 * 
	 * @param list
	 */
	public static <T> void transField2Dic(List<T> list) {
		for (T t : list) {
			transField2Dic.transField(t);
		}
	}

	@Override
	protected String transValue(Object code, EnumCodeToDic annotation)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// if (code==null||code.equals("")) {
		// return null;
		// }
		// 得到枚举类之后,直接通过 反射 执行 静态方法 getDescByCode
		String desc = (String) annotation.value().getMethod("getDescByCode", String.class).invoke(null, code);

		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("desc", desc);
		return json.toJSONString();
	}
}
