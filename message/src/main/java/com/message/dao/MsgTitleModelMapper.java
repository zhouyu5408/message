package com.message.dao;

import org.apache.ibatis.annotations.Param;

import com.message.entity.MsgTitleModel;

/**
 * 主题模版
 * 
 * @Date: 2017年12月29日 上午9:23:53 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public interface MsgTitleModelMapper {
	int deleteByPrimaryKey(String id);

	int insert(MsgTitleModel record);

	int insertSelective(MsgTitleModel record);

	MsgTitleModel selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(MsgTitleModel record);

	int updateByPrimaryKey(MsgTitleModel record);

	/**
	 * 通过主题和订阅方式获取对应的消息模版
	 * 
	 * @author zhouyu
	 * @param titleId
	 * @param deliveryWay
	 * @return
	 */
	MsgTitleModel getModelByIdAndWay(@Param("titleId") String titleId, @Param("deliveryWay") String deliveryWay);

	/**
	 * 通过主题编号和订阅方式修改对应的模版
	 * 
	 * @author zhouyu
	 * @param model
	 * @return
	 */
	MsgTitleModel updateModelByTitleAndDeliveryWay(MsgTitleModel model);

	/**
	 * 删除指定主题所有的相关的模版信息
	 * 
	 * @param titleId
	 */
	void deleteByTitleId(String titleId);
}