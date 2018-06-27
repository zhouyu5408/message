package com.message.dao;

import java.util.List;

import com.message.entity.MsgSubGroupMember;

/**
 * 订阅组成员
 * 
 * @Date: 2017年12月29日 上午9:22:32 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public interface MsgSubGroupMemberMapper {
	int deleteByPrimaryKey(String id);

	int insert(MsgSubGroupMember record);

	int insertSelective(MsgSubGroupMember record);

	MsgSubGroupMember selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(MsgSubGroupMember record);

	int updateByPrimaryKey(MsgSubGroupMember record);

	/**
	 * 获取组中所有的成员
	 * 
	 * @author zhouyu
	 * @param subGroupId 订阅组编号
	 * @return
	 */
	List<MsgSubGroupMember> getGroupMemberByGroupId(String subGroupId);

	/**
	 * 删除组对应的所有成员
	 * 
	 * @author zhouyu
	 * @param subGroupId
	 * @return
	 */
	int deleteByGroupId(String subGroupId);
}