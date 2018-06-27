package com.message.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.message.entity.MsgSubscribe;

/**
 * 订订阅关系
 * 
 * @Date: 2017年12月29日 上午9:22:46 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public interface MsgSubscribeMapper {
	int deleteByPrimaryKey(String id);

	int insert(MsgSubscribe record);

	int insertSelective(MsgSubscribe record);

	MsgSubscribe selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(MsgSubscribe record);

	int updateByPrimaryKey(MsgSubscribe record);

	/**
	 * 删除订阅关系意味着将订阅关系从有效变成无效（true-->false）
	 */
	int updateMsgSubscribeToFalse(@Param("id") String id);

	/**
	 * 根据主题ID获取订阅关系
	 */
	List<MsgSubscribe> getListByTitleId(String titleId);

	/**
	 * 如果存在组订阅信息，则跳过
	 */
	int insertGroupMsgSubIfNotExists(MsgSubscribe record);

	/**
	 * 如果存在角色订阅信息，则跳过
	 */
	int insertRoleMsgSubIfNotExists(MsgSubscribe record);

	/**
	 * 如果存在机构订阅信息，则跳过
	 */
	int insertOrgMsgSubIfNotExists(MsgSubscribe record);

	/**
	 * 如果存在用户订阅信息，则跳过
	 */
	int insertUserMsgSubIfNotExists(MsgSubscribe record);

	/**
	 * 根据订阅类型获取对应的有效订阅列表
	 */
	List<MsgSubscribe> getMsgSubscribeBySubType(MsgSubscribe subscribe);

	/**
	 * 根据主题编号和组编号获取对应的订阅关系
	 * 
	 * @param subscribe
	 * @return
	 */
	List<MsgSubscribe> getListByTitleIdAndGroupId(MsgSubscribe subscribe);

	/**
	 * 根据主题编号和用户编号获取对应的订阅关系
	 * 
	 * @param subscribe
	 * @return
	 */
	List<MsgSubscribe> getListByTitleAndUser(MsgSubscribe subscribe);

	/**
	 * 批量删除选中的订阅关系
	 * 
	 * @param ids
	 */
	void batchDelete(String[] ids);

	/**
	 * 根据主题编号和主题编号删除
	 * 
	 * @param subscribe
	 */
	void deleteByTitleAndGroup(MsgSubscribe subscribe);

	/**
	 * 根据主题编号和用户编号删除
	 * 
	 * @param subscribe
	 */
	void deleteByTitleAndUser(MsgSubscribe subscribe);

	/**
	 * 查看是否已经存在该记录了
	 * 
	 * @param subscribe
	 */
	MsgSubscribe searchByTitleGroupDeliveryway(MsgSubscribe subscribe);

	/**
	 * 查看用户订阅记录是否已经存在
	 * 
	 * @param subscribe
	 * @return
	 */
	MsgSubscribe searchByTitleUserDeliveryway(MsgSubscribe subscribe);

	/**
	 * 删除主题订阅者(用户、机构、角色(组除外))
	 * 
	 * @param titleId
	 */
	void deleteTitleSubscribe(String titleId);

	/**
	 * 获取主题订阅的所有方式
	 * 
	 * @param titleId
	 * @return
	 */
	List<MsgSubscribe> getDeliveryWaysByTitleId(String titleId);

	/**
	 * 删除主题订阅的所有对象
	 * 
	 * @param titleId
	 */
	void deleteTitleAllSubscribe(String titleId);
}