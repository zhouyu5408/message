package com.message.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.message.entity.MsgList;

/**
 * 消息推送明细
 * 
 * @Date: 2017年12月29日 上午9:21:20 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public interface MsgListMapper {
	int deleteByPrimaryKey(String id);

	int insert(MsgList record);

	int insertSelective(MsgList record);

	MsgList selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(MsgList record);

	int updateByPrimaryKey(MsgList record);

	/**
	 * 根据主题编号获取对应的发送列表
	 * 
	 * @author zhouyu
	 * @param titleId
	 *            主题编号
	 * @return
	 */
	List<MsgList> getMsgListByTitleId(@Param("titleId") String titleId);

	/**
	 * 根据查询条件获取对应的信息
	 * 
	 * @author zhouyu
	 * @param msgList
	 *            查询条件
	 * @return
	 */
	List<MsgList> searchMsgList(MsgList msgList);

	/**
	 * 获取指定用户未读取的弹窗消息
	 * 
	 * @author zhouyu
	 * @param userId
	 *            指定用户编号
	 * @return
	 */
	List<MsgList> searchNotReadMsgListByUserId(@Param("userId") String userId);

	/**
	 * 用户是否有未发送的弹窗消息
	 * 
	 * @author zhouyu
	 * @param userId
	 * @return
	 */
	int existUnsendMsg(@Param("userId") String userId);

	/**
	 * 改变用户和主题对应的发送状态和接受状态
	 * 
	 * @author zhouyu
	 * @param titleId
	 *            主题编号
	 * @param userId
	 *            用户编号
	 */
	int changeMsgStatus(@Param("titleId") String titleId, @Param("userId") String userId);

	/**
	 * 修改重发记录
	 * 
	 * @author zhouyu
	 * @param msgList
	 */
	void updateMsgListRecord(MsgList msgList);

	/**
	 * 获取需要重发的集合
	 * 
	 * @param ids
	 * @return
	 */
	List<MsgList> selectResendList(String[] ids);
}