package com.message.dao;

import java.util.List;

import com.message.entity.MsgSubGroup;

/**
 * 订阅组
 * 
 * @Date: 2017年12月29日 上午9:22:05 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public interface MsgSubGroupMapper {
	int deleteByPrimaryKey(String id);

	int insert(MsgSubGroup record);

	int insertSelective(MsgSubGroup record);

	MsgSubGroup selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(MsgSubGroup record);

	int updateByPrimaryKey(MsgSubGroup record);

	/**
	 * 通过订阅组编号删除对应的订阅组信息
	 * 
	 * @author zhouyu
	 * @param subGroupId
	 *            订阅组编号
	 * @return
	 */
	int deleteByGroupId(String subGroupId);

	/**
	 * 通过订阅组编号获取订阅组信息
	 * 
	 * @author zhouyu
	 * @param subGroupId
	 * @return
	 */
	MsgSubGroup getByGroupId(String subGroupId);

	/**
	 * 分页查询
	 * 
	 * @param subGroup
	 * @return
	 */
	List<MsgSubGroup> search(MsgSubGroup subGroup);

	/**
	 * 获取所有的订阅组信息
	 * 
	 * @return
	 */
	List<MsgSubGroup> getAllSubGroup();
}