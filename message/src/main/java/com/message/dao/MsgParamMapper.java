package com.message.dao;

import com.message.entity.MsgParam;

/**
 * 参数
 * 
 * @Date: 2017年12月29日 上午9:21:47 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public interface MsgParamMapper {
    int deleteByPrimaryKey(String id);

    int insert(MsgParam record);

    int insertSelective(MsgParam record);

    MsgParam selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MsgParam record);

    int updateByPrimaryKey(MsgParam record);
    
    /**
     * 根据参数名获取参数信息
     * 
     * @author zhouyu
     * @param paramName
     * @return
     */
    MsgParam getParamByName(String paramName);
}