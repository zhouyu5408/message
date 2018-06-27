package com.message.dao;

import com.message.entity.MsgDeliveryWay;

/**
 * 订阅方式表
 * 
 * @Date: 2017年12月29日 上午9:21:09 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public interface MsgDeliveryWayMapper {
    int deleteByPrimaryKey(String id);

    int insert(MsgDeliveryWay record);

    int insertSelective(MsgDeliveryWay record);

    MsgDeliveryWay selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MsgDeliveryWay record);

    int updateByPrimaryKey(MsgDeliveryWay record);
    
    MsgDeliveryWay getDeliveryWay(String deliveryWay);
}