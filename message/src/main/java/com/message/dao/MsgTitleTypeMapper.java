package com.message.dao;

import java.util.List;

import com.message.entity.MsgTitleType;

/**
 * 主题类型
 * 
 * @Date: 2017年12月29日 上午9:24:13 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public interface MsgTitleTypeMapper {
	int deleteByPrimaryKey(String id);

	int insert(MsgTitleType record);

	int insertSelective(MsgTitleType record);

	MsgTitleType selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(MsgTitleType record);

	int updateByPrimaryKey(MsgTitleType record);

	/**
	 * 获取主题类型集合
	 * 
	 * @return
	 */
	List<MsgTitleType> getAll();

	/**
	 * 通过主题编号获取主题类型信息
	 * 
	 * @param typeId
	 * @return
	 */
	MsgTitleType getByTypeId(String typeId);
}