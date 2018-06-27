package com.message.dao;

import java.util.List;

import com.message.entity.MsgTitleModelAttach;

/**
 * 模版附件所属
 *
 * @Date: 2017年12月29日 上午9:23:24 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public interface MsgTitleModelAttachMapper {
	int deleteByPrimaryKey(String id);

	int insert(MsgTitleModelAttach record);

	int insertSelective(MsgTitleModelAttach record);

	MsgTitleModelAttach selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(MsgTitleModelAttach record);

	int updateByPrimaryKey(MsgTitleModelAttach record);

	/**
	 * 获取附件组中的所有附件信息
	 * 
	 * @author zhouyu
	 * @param attachGroupId
	 * @return
	 */
	List<MsgTitleModelAttach> getAttachListByGroupId(String attachGroupId);

	/**
	 * 将指定的fileKey失效
	 * 
	 * @param fileKey
	 */
	void failureFileKey(String fileKey);

	/**
	 * 删除delKeys对应的附件信息
	 * 
	 * @param delKeys
	 */
	void deleteByFileKeys(String[] delKeys);

	/**
	 * 根据附件组编号删除附件信息
	 * 
	 * @param attachGroupId
	 */
	void deleteByAttachGroupId(String attachGroupId);
}