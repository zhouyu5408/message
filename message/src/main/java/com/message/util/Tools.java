package com.message.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.HtmlUtils;

public class Tools {
	public static boolean isEmpty(String string) {
		return string == null ? true : string.equals("") ? true : false;
	}

	public static HttpHeaders getDownloadHeader(String fileName) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return headers;
	}

	public static <E> List<E> pageList(int pageNum, int pageSize, List<E> list) {
		int from = (pageNum - 1) * 4;
		int to = from + pageSize;

		if (to > list.size()) {
			to = list.size();
		}
		return list.subList(from, to);

	}

	/**
	 * bean属性名称转数据库列属性名称
	 * 
	 * @fieldName fieldName
	 * @return
	 */
	public static String fieldName2ColName(String fieldName) {
		StringBuilder result = new StringBuilder();
		if (fieldName != null && fieldName.length() > 0) {
			// 将第一个字符处理成大写
			result.append(fieldName.substring(0, 1).toUpperCase());
			// 循环处理其余字符
			for (int i = 1; i < fieldName.length(); i++) {
				String s = fieldName.substring(i, i + 1);
				// 在大写字母前添加下划线
				if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
					result.append("_");
				}
				// 其他字符直接转成大写
				result.append(s.toUpperCase());
			}
		}
		return result.toString();
	}

	/**
	 * 将 对象中 String 类型的 值 去两端空格
	 * 
	 * @param bean
	 */
	public static void trimStringPropAndEsCape(final Object bean) {
		/**
		 * 1、得到属性描述 2、筛选 String 属性 3、去空格,并且转义
		 */
		List<PropertyDescriptor> propertyDescriptors = new ArrayList<>(
				Arrays.asList(PropertyUtils.getPropertyDescriptors(bean)));

		CollectionUtils.filter(propertyDescriptors, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				PropertyDescriptor propertyDescriptor = (PropertyDescriptor) object;
				return propertyDescriptor.getPropertyType() == String.class;
			}
		});

		CollectionUtils.forAllDo(propertyDescriptors, new Closure() {
			@Override
			public void execute(Object input) {
				PropertyDescriptor propertyDescriptor = (PropertyDescriptor) input;
				String name = propertyDescriptor.getName();
				try {
					String value = (String) PropertyUtils.getProperty(bean, name);
					if (value != null) {
						PropertyUtils.setProperty(bean, name, HtmlUtils.htmlEscape(value.trim()));
					}
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void trimAndEsCape(String string) {
		if (string == null) {
			return;
		}
		string = HtmlUtils.htmlEscape(string.trim());
	}

	public static void trimAndEsCape(String[] strings) {
		if (strings == null) {
			return;
		}
		for (String string : strings) {
			trimAndEsCape(string);
		}
	}

}
