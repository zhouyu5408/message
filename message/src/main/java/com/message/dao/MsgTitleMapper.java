package com.message.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.message.entity.MsgTitle;

/**
 * 订阅主题
 * 
 * @Date: 2017年12月29日 上午9:23:11 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public interface MsgTitleMapper {
	int deleteByPrimaryKey(String id);

	int insert(MsgTitle record);

	int insertSelective(MsgTitle record);

	MsgTitle selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(MsgTitle record);

	int updateByPrimaryKey(MsgTitle record);

	/**
	 * 通过主题编号获取对应的主题信息
	 * 
	 * @author zhouyu
	 * @param titleId
	 *            主题编号
	 * @return
	 */
	MsgTitle getTitleByTitleId(String titleId);

	/**
	 * 分页查询
	 * 
	 * @param dataGrid
	 * @return
	 */
	List<MsgTitle> search(MsgTitle title);

	/**
	 * 获取所有主题列表
	 * 
	 * @return
	 */
	List<MsgTitle> getAllTitle();

	/**
	 * 删除指定id集合的主题
	 * 
	 * @param ids
	 */
	void batchDeleteTitle(String[] ids);

	/**
	 * 获取ids对应的主题信息列表
	 * 
	 * @param ids
	 * @return
	 */
	List<MsgTitle> getTitleList(String[] ids);

	/**
	 * 获取主题类型下的主题列表
	 * 
	 * @param typeId
	 * @return
	 */
	List<MsgTitle> getTitleByType(@Param("titleType") String titleType);

	/**
	 * 查询出已经存在有效订阅关系的主题
	 * 
	 * @param title
	 * @return
	 */
	List<MsgTitle> getSubscribeTitle(MsgTitle title);
}