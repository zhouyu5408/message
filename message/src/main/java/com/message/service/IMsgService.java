package com.message.service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.message.entity.MsgDeliveryWay;
import com.message.entity.MsgList;
import com.message.entity.MsgParam;
import com.message.entity.MsgSubGroup;
import com.message.entity.MsgSubscribe;
import com.message.entity.MsgTitle;
import com.message.entity.MsgTitleModel;
import com.message.entity.MsgTitleModelAttach;
import com.message.entity.MsgTitleType;
import com.message.entity.Organization;
import com.message.entity.Role;
import com.message.entity.SubscribeInfo;
import com.message.util.DataGrid;

/**
 * 消息处理逻辑接口，用于定义用于消息处理逻辑的各种接口 . <br/>
 * 
 * @Date: 2017年11月24日 下午5:32:32 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
/**
 * @Date: 2017年12月26日 下午2:10:34 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public interface IMsgService {
	/**
	 * dentification of bean
	 */
	public static String BEAN = IMsgService.class.getName();

	/**
	 * get System Param by paramName
	 * 
	 * @author zhouyu
	 * @param paramName
	 * @return
	 */
	MsgParam getParamByName(String paramName);

	/**
	 * 通过主题获取对应的订阅人列表. <br/>
	 * 包含订阅人的userId,userName，deliveryWay，需要去重。
	 * 
	 * @author zhouyu
	 * @param title
	 *            主题
	 * @return
	 */
	Set<SubscribeInfo> getSubscriberSet(MsgTitle title);

	/**
	 * 获取消息推送方式<br/>
	 * 
	 * @author zhouyu
	 * @param deliveryWay
	 *            订阅方式
	 * @return
	 */
	MsgDeliveryWay getDeliverWay(String deliveryWay);

	/**
	 * 根据主题编号和订阅方式获取对应模版类型<br/>
	 * 
	 * @author zhouyu
	 * @param titleId
	 *            主题编号
	 * @param deliveryWay
	 *            订阅方式
	 */
	MsgTitleModel getTitleModel(String titleId, String deliveryWay);

	/**
	 * 主题推送后入库，记录那些主题已经发送过 （需要记录时间）
	 * 
	 * @author zhouyu
	 * @param titleModel
	 *            主题模版
	 */
	void saveTitleSendRecord(MsgTitleModel titleModel);

	/**
	 * 主题推送后入库，记录那些主题已经发送过 （需要记录时间）
	 * 
	 * @author zhouyu
	 * @param title
	 *            主题
	 */
	void saveTitleSendRecord(MsgTitle title);

	/**
	 * 将推送主题的明细记录下来
	 * 
	 * @author zhouyu
	 * @param title
	 *            主题
	 * @param subscribeInfo
	 *            订阅人（用户）
	 * @param sendStatus
	 *            推送状态（成功/失败/未推送）
	 * @param deliveryWay
	 *            订阅方式
	 */
	void saveMsgList(MsgTitle title, SubscribeInfo subscribeInfo, String sendStatus, String deliveryWay);

	/**
	 * 获取邮件发送的附件组
	 * 
	 * @author zhouyu
	 * @param attachGropuId
	 *            附件组编号
	 * @return
	 */
	List<MsgTitleModelAttach> getAttachList(String attachGroupId);

	/**
	 * 保存主题对应的模版
	 * 
	 * @author zhouyu
	 * @param titleId
	 *            主题编号
	 * @param deliveryWay
	 *            订阅方式
	 * @param model
	 *            模版
	 */
	void updateAndSaveTitleModel(String titleId, String deliveryWay, String titleModel);

	/**
	 * 根据主题编码获取对应的主题发送记录
	 * 
	 * @author zhouyu
	 * @param titleId
	 *            主题编码
	 * @return
	 */
	List<MsgList> getMsgList(String titleId);

	/**
	 * 重新发送指定的信息
	 * 
	 * @author zhouyu
	 * @param ids
	 */
	void resendMsg(String[] ids);

	/**
	 * 重发某一条记录
	 * 
	 * @param msgList
	 */
	void resendOneMsg(MsgList msgList);

	/**
	 * 新增订阅组，需要组在subGroup中添加用户组，机构组，角色组
	 * 
	 * @author zhouyu
	 * @param subGroup
	 *            订阅组
	 */
	void insertSubGroup(MsgSubGroup subGroup);

	/**
	 * 修改订阅组成员
	 * 
	 * @author zhouyu
	 * @param subGroup
	 *            订阅组
	 */
	void updateSubGroup(MsgSubGroup subGroup);

	/**
	 * 删除指定组（包括组对应的成员）
	 * 
	 * @author zhouyu
	 * @param subGroupId
	 *            订阅组编号
	 */
	void deleteSubGroup(String subGroupId);

	/**
	 * 获取指定订阅组信息
	 * 
	 * @author zhouyu
	 * @param subGroupId
	 *            订阅组编号
	 * @return
	 */
	MsgSubGroup getGroupInfoByGroupId(String subGroupId);

	/**
	 * 新增主题,并添加邮件、弹窗、短信对应的默认模版
	 * 
	 * @author zhouyu
	 * @param title
	 *            主题
	 */
	void saveTitle(MsgTitle title);

	/**
	 * 新增订阅关系
	 * 
	 * @author zhouyu
	 * @param subscribe
	 *            订阅关系
	 */
	void insertMsgSubscribe(MsgSubscribe subscribe);

	/**
	 * 删除订阅关系(将订阅事件改为无效)
	 * 
	 * @author zhouyu
	 * @param id
	 *            订阅关系主键
	 */
	void deleteMsgSubscribe(String id);

	/**
	 * 删除订阅关系(将订阅事件改为无效)
	 * 
	 * @author zhouyu
	 * @param id
	 *            订阅关系主键
	 */
	void deleteMsgSubscribe(String[] ids);

	/**
	 * 通过主题编号获取主题信息
	 * 
	 * @author zhouyu
	 * @param string
	 *            主题编号
	 * @return
	 */
	MsgTitle getTitleByTitleId(String titleId);

	/**
	 * 新增组订阅关系
	 * 
	 * @author zhouyu
	 * @param subscribe
	 *            半封装的订阅关系
	 */
	void saveGroupMsgSubscribe(MsgSubscribe subscribe);

	/**
	 * 新增角色订阅关系
	 * 
	 * @author zhouyu
	 * @param subscribe
	 *            半封装的订阅关系
	 */
	void saveRoleMsgSubscribe(MsgSubscribe subscribe);

	/**
	 * 新增机构订阅关系
	 * 
	 * @author zhouyu
	 * @param subscribe
	 *            半封装的订阅关系
	 */
	void saveOrgMsgSubscribe(MsgSubscribe subscribe);

	/**
	 * 新增用户订阅关系
	 * 
	 * @author zhouyu
	 * @param subscribe
	 *            半封装的订阅关系
	 */
	void saveUserMsgSubscribe(MsgSubscribe subscribe);

	/**
	 * 根据主题选择订阅对象和订阅方式
	 * 
	 * @author zhouyu
	 * @param title
	 *            主题
	 * @param deliveryWays
	 *            订阅方式（多种）
	 * @param groupIds
	 *            多订阅组
	 * @param roleIds
	 *            多角色
	 * @param orgIds
	 *            多机构
	 * @param userIds
	 *            多用户
	 */
	void saveMsgSubscribeByTitle(MsgTitle title, String[] deliveryWays, List<MsgSubGroup> groups, List<Role> roles,
			List<Organization> orgs, List<SubscribeInfo> users);

	/**
	 * 获取用户的有效订阅列表
	 * 
	 * @author zhouyu
	 * @param subscribe
	 *            查询条件
	 * @return
	 */
	List<MsgSubscribe> getUserMsgSubscribe(MsgSubscribe subscribe);

	/**
	 * 获取机构的有效订阅列表
	 * 
	 * @author zhouyu
	 * @param subscribe
	 *            查询条件
	 * @return
	 */
	List<MsgSubscribe> getOrgMsgSubscribe(MsgSubscribe subscribe);

	/**
	 * 获取角色的有效订阅列表
	 * 
	 * @author zhouyu
	 * @param subscribe
	 *            查询条件
	 * @return
	 */
	List<MsgSubscribe> getRoleMsgSubscribe(MsgSubscribe subscribe);

	/**
	 * 获取组的有效订阅列表
	 * 
	 * @author zhouyu
	 * @param subscribe
	 *            查询条件
	 * @return
	 */
	List<MsgSubscribe> getGroupMsgSubscribe(MsgSubscribe subscribe);

	/**
	 * 通过主题编号和订阅方式获取对应的附件组信息
	 * 
	 * @author zhouyu
	 * @param titleId
	 *            主题编号
	 * @param deliveryWay
	 *            订阅方式
	 * @return
	 */
	List<MsgTitleModelAttach> getAttachByTitleAndDeliveryWay(String titleId, String deliveryWay);

	/**
	 * 删除指定的附件(未实现)
	 * 
	 * @author zhouyu
	 * @param fileKey
	 */
	void deleteAttachFile(String fileKey);

	/**
	 * 根据查询条件获取推送明细列表
	 * 
	 * @author zhouyu
	 * @param msgList
	 *            查询条件
	 * @return
	 */
	List<MsgList> getMsgList(MsgList msgList);

	/**
	 * 获取指定用户未读取的主题(无论是否成功发出)
	 * 
	 * @author zhouyu
	 * @param userId
	 *            用户编号
	 * @return
	 */
	List<MsgTitle> getNotReadMsgListByUserId(String userId);

	/**
	 * 用户登陆时，检查是否有未发送的弹窗消息
	 * 
	 * @author zhouyu
	 * @param userId
	 *            用户编号
	 * @return
	 */
	int checkUserHasUnsendMsg(String userId);

	/**
	 * 如果用户已经浏览的弹窗消息，则视为已经接受<br/>
	 * 改变所有发送状态为"success",并将接受状态变成"yes"
	 * 
	 * @author zhouyu
	 * @param userId
	 * @param titleId
	 */
	void changeMsgStatus(String userId, String titleId);

	/**
	 * 通过key值从文件服务器获取对应的file
	 * 
	 * @param key
	 * @return
	 */
	File getFileByKey(String key);

	/**
	 * 通过主题编号和订阅模版获取唯一数据
	 * 
	 * @param titleId
	 * @param deliveryWay
	 * @return
	 */
	MsgTitleModel getModelByTitleidAndDeliveryWay(String titleId, String deliveryWay);

	/**
	 * 新增附件信息
	 * 
	 * @param attachGroupId
	 * @param fileKey
	 */
	void saveMsgTitleModelAttach(String titleId, String deliveryWay, String fileKey);

	/**
	 * 将对应的fileKey指定为失效
	 * 
	 * @param fileKey
	 */
	void removeFileKey(String fileKey);

	/**
	 * 分页查询：订阅组信息
	 * 
	 * @param subGroup
	 * @param dataGrid
	 */
	void searchSubGroup(MsgSubGroup subGroup, DataGrid dataGrid);

	/**
	 * 分页查询：主题信息
	 * 
	 * @param title
	 * @param dataGrid
	 */
	void searchTitle(MsgTitle title, DataGrid dataGrid);

	/**
	 * 获取系统所有成员：用户、机构、角色
	 * 
	 * @return
	 */
	Map<String, Object> getSystemMembers();

	/**
	 * 获取组的订阅关系
	 * 
	 * @param msgSubscribe
	 * @param dataGrid
	 */
	void searchGroupSubscribe(MsgSubscribe msgSubscribe, DataGrid dataGrid);

	/**
	 * 获取所有的订阅组
	 * 
	 * @return
	 */
	List<MsgSubGroup> getAllSubGroup();

	/**
	 * 获取所有主题列表
	 * 
	 * @return
	 */
	List<MsgTitle> getAllTitle();

	/**
	 * 根据主题和订阅组编号获取对应的订阅关系
	 * 
	 * @param subscribe
	 * @return
	 */
	MsgSubscribe getGroupSubscribeInfo(MsgSubscribe subscribe);

	/**
	 * 根据主题和用户编号获取对应的订阅关系
	 * 
	 * @param subscribe
	 * @return
	 */
	MsgSubscribe getUserSubscribeInfo(MsgSubscribe subscribe);

	/**
	 * 根据主题和机构编号获取对应的订阅关系
	 * 
	 * @param subscribe
	 * @return
	 */
	MsgSubscribe getOrgSubscribeInfo(MsgSubscribe subscribe);

	/**
	 * 修改组订阅关系
	 * 
	 * @param subscribe
	 */
	void updateGroupSubscribe(MsgSubscribe subscribe);

	/**
	 * 修改机构订阅关系
	 * 
	 * @param subscribe
	 */
	void updateOrgSubscribe(MsgSubscribe subscribe);

	/**
	 * 修改用户订阅关系
	 * 
	 * @param subscribe
	 */
	void updateUserSubscribe(MsgSubscribe subscribe);

	/**
	 * 分页查询：消息推送明细
	 * 
	 * @param list
	 * @param dataGrid
	 */
	void searchMsgList(MsgList list, DataGrid dataGrid);

	/**
	 * 批量删除指定id集合的主题
	 * 
	 * @param ids
	 *            指定id的集合
	 */
	void batchDeleteTitle(String[] ids);

	/**
	 * 获取系统中的机构列表
	 * 
	 * @return
	 */
	List<Organization> getSystemOrg();

	/**
	 * 获取系统中的用户列表
	 * 
	 * @return
	 */
	List<SubscribeInfo> getSystemUser();

	/**
	 * 分页查询：订阅主题
	 * 
	 * @param title
	 * @param dataGrid
	 */
	void searchMsgTitle(MsgTitle title, DataGrid dataGrid);

	/**
	 * 获取所有主题类型集合
	 * 
	 * @return
	 */
	List<MsgTitleType> getTitleTypeList();

	/**
	 * 修改主题模版
	 * 
	 * @param title
	 */
	void updateTilteAndModel(MsgTitle title);

	/**
	 * 根据主题主键获取指定的主题信息
	 * 
	 * @param id
	 * @return
	 */
	MsgTitle getAppointedTitle(String id);

	/**
	 * 获取主题类型下的主题列表
	 * 
	 * @param typeId
	 * @return
	 */
	List<MsgTitle> getTitleByType(String typeId);

	/**
	 * 分页查询：被订阅的主题
	 * 
	 * @param title
	 * @param dataGrid
	 */
	void searchTitleSubscribe(MsgTitle title, DataGrid dataGrid);

	/**
	 * 根据主题来定义订阅关系
	 * 
	 * @param title
	 */
	void saveTitleSubscribe(MsgTitle title);

	/**
	 * 根据主题编号获取主题的订阅关系
	 * 
	 * @param titleId
	 * @return
	 */
	MsgTitle getTitleSubscribeByTitleId(String titleId);

	/**
	 * 修改主题订阅关系
	 * 
	 * @param title
	 */
	void updateTitleSubscribe(MsgTitle title);

	/**
	 * 根据主题编号删除主题订阅关系
	 * 
	 * @param titleId
	 */
	void deleteTitleSubscribe(String titleId);

}
