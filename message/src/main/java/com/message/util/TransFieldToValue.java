package com.message.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.message.annotation.EnumCodeToDic;

public abstract class TransFieldToValue {
	private static Logger logger = Logger.getLogger(TransFieldToValue.class);

	protected <T> void transField(T t) {
		LinkedList<Field> fields = new LinkedList<Field>();
		Class<?> curClass = t.getClass();

		do {
			fields.addAll(Arrays.asList(curClass.getDeclaredFields()));
		} while ((curClass = curClass.getSuperclass()) != null);

		for (Field field : fields) {
			if (!field.isAnnotationPresent(EnumCodeToDic.class)) {
				continue;
			}

			field.setAccessible(true);
			if (field.getType() != String.class) {
				StringBuilder builder = new StringBuilder();
				builder.append("转义失败,因为不是 String 类型!").append(t.getClass().getName()).append("-")
						.append(field.getName());
				logger.error(builder.toString());
				continue;
			}

			/**
			 * 如果字段上存在 EnumCodeToDic 注解,则开始 解析
			 * 
			 * 首先取出 值 然后 找到 枚举中 对应的 字典 然后 序列化 成 json 放入 字段中
			 */
			try {
				// 获取 code 值
				Object code = field.get(t);
				EnumCodeToDic annotation = field.getAnnotation(EnumCodeToDic.class);

				String value = transValue(code, annotation);

				field.set(t, value);
			} catch (Exception e) {
				StringBuilder builder = new StringBuilder();
				builder.append("转义失败").append(t.getClass().getName()).append("-").append(field.getName()).append("--")
						.append(e.getMessage());
				logger.error(builder.toString());
			}

		}
	}

	protected abstract String transValue(Object code, EnumCodeToDic annotation)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;
}
