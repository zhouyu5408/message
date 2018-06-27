package com.message.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.message.BasicMsg;
import com.message.constants.MessageConstants;
import com.message.dao.MsgDeliveryWayMapper;
import com.message.dao.MsgListMapper;
import com.message.dao.MsgParamMapper;
import com.message.dao.MsgSubGroupMapper;
import com.message.dao.MsgSubGroupMemberMapper;
import com.message.dao.MsgSubscribeMapper;
import com.message.dao.MsgTitleMapper;
import com.message.dao.MsgTitleModelAttachMapper;
import com.message.dao.MsgTitleModelMapper;
import com.message.dao.MsgTitleTypeMapper;
import com.message.entity.MsgDeliveryWay;
import com.message.entity.MsgList;
import com.message.entity.MsgParam;
import com.message.entity.MsgSubGroup;
import com.message.entity.MsgSubGroupMember;
import com.message.entity.MsgSubscribe;
import com.message.entity.MsgTitle;
import com.message.entity.MsgTitleModel;
import com.message.entity.MsgTitleModelAttach;
import com.message.entity.MsgTitleType;
import com.message.entity.Organization;
import com.message.entity.Role;
import com.message.entity.SubscribeInfo;
import com.message.enums.DeliveryWayEnum;
import com.message.enums.SendEnum;
import com.message.enums.SubTypeEnum;
import com.message.enums.YesNoEnum;
import com.message.exception.BaseException;
import com.message.invoker.IFileService;
import com.message.invoker.IUserService;
import com.message.service.IMsgService;
import com.message.util.DataGrid;
import com.message.util.DataGridResult;
import com.message.util.DefaultCreator;
import com.message.util.Tools;

/**
 * @Date: 2018年1月2日 下午5:28:42 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
@Service
@Transactional
public class MsgServiceImpl implements IMsgService {
	private static final Logger logger = Logger.getLogger(MsgServiceImpl.class);
	@Autowired
	private MsgDeliveryWayMapper deliveryWayMap;
	@Autowired
	private MsgListMapper msgListMap;
	@Autowired
	private MsgParamMapper paramMap;
	@Autowired
	private MsgSubGroupMapper subGroupMap;
	@Autowired
	private MsgSubGroupMemberMapper groupMemberMap;
	@Autowired
	private MsgSubscribeMapper msgSubMap;
	@Autowired
	private MsgTitleMapper titleMap;
	@Autowired
	private MsgTitleModelAttachMapper modelAttachMap;
	@Autowired
	private MsgTitleModelMapper modelMap;
	@Autowired
	private MsgTitleTypeMapper titleTypeMap;
	@Autowired
	private IUserService userService;
	@Autowired
	private IFileService fileService;

	@Override
	public MsgParam getParamByName(String paramName) {
		return paramMap.getParamByName(paramName);
	}

	/**
	 * 1、通过主题编号获取所有的订阅关系； 2、循环遍历订阅关系分为用户订阅和非用户订阅类型两种方式处理；
	 * 3、将用户订阅类型获对应的数据，直接添加到数据集中； 4、非用户 订阅类型再次处理以后添加到数据集中。
	 */
	@Override
	public Set<SubscribeInfo> getSubscriberSet(MsgTitle title) {
		Set<SubscribeInfo> subscribers = new HashSet<SubscribeInfo>();
		List<MsgSubscribe> msgSubscribe = msgSubMap.getListByTitleId(title.getTitleId());
		for (MsgSubscribe subscribe : msgSubscribe) {
			if (SubTypeEnum.USER.getCode().equals(subscribe.getSubType())) {
				SubscribeInfo subscribeInfo = getSubscriberInfo(subscribe);
				if (subscribeInfo != null) {
					subscribers.add(subscribeInfo);
				}
			} else {
				List<SubscribeInfo> subscribeInfos = getSubscriberInfoByType(subscribe);
				for (SubscribeInfo subscribeInfo : subscribeInfos) {
					subscribers.add(subscribeInfo);
				}
			}
		}
		return subscribers;
	}

	/**
	 * 封装订阅用户信息
	 * 
	 * @author zhouyu
	 * @param subscribe
	 * @return
	 */
	private SubscribeInfo getSubscriberInfo(MsgSubscribe subscribe) {
		SubscribeInfo user = null;
		MsgDeliveryWay deliveryWay = null;
		try {
			user = userService.getSubscribeInfo(subscribe.getUserId());
		} catch (Exception e) {
			logger.error("订阅人[" + subscribe.getUserName() + "]不存在", e);
		}
		try {
			deliveryWay = deliveryWayMap.getDeliveryWay(subscribe.getDeliveryWay());
		} catch (Exception e) {
			logger.error("订阅方式[" + subscribe.getDeliveryWay() + "]未定义", e);
		}
		user.setDeliveryWay(subscribe.getDeliveryWay());
		user.setMsgObject(deliveryWay.getMsgObject());
		return user;
	}

	/**
	 * 根据订阅者类型获取订阅用户信息
	 * 
	 * @author zhouyu
	 * @param subscribe
	 * @return
	 */
	private List<SubscribeInfo> getSubscriberInfoByType(MsgSubscribe subscribe) {
		List<SubscribeInfo> subscribeInfos = new ArrayList<SubscribeInfo>();
		// 获取订阅方式
		MsgDeliveryWay deliveryWay = null;
		String delWay = subscribe.getDeliveryWay();
		try {
			deliveryWay = deliveryWayMap.getDeliveryWay(subscribe.getDeliveryWay());
		} catch (Exception e) {
			logger.error("订阅方式[" + subscribe.getDeliveryWay() + "]未定义", e);
		}
		// 获取订阅者类型
		String subType = subscribe.getSubType();
		// 根据订阅者类型获取对应的用户群体
		if (SubTypeEnum.GROUP.getCode().equals(subType)) {
			// 1、获取组中所有的成员
			String groupId = subscribe.getGroupId();
			List<MsgSubGroupMember> members = groupMemberMap.getGroupMemberByGroupId(groupId);
			// 2、对所有的成员类型进行判断
			for (MsgSubGroupMember member : members) {
				String memberType = member.getMemberType();
				if (SubTypeEnum.ROLE.getCode().equals(memberType)) {
					String roleId = member.getMemberId();
					List<SubscribeInfo> roleUsers = null;
					try {
						roleUsers = userService.getUserListByRole(roleId);
					} catch (Exception e) {
						logger.error("获取订阅组中用户失败，角色编号[" + roleId + "]", e);
					}
					for (SubscribeInfo user : roleUsers) {
						user.setDeliveryWay(delWay);
						user.setMsgObject(deliveryWay.getMsgObject());
						subscribeInfos.add(user);
					}
				} else if (SubTypeEnum.ORG.getCode().equals(memberType)) {
					String orgId = member.getMemberId();
					List<SubscribeInfo> orgUsers = null;
					try {
						orgUsers = userService.getUserListByOrg(orgId);
					} catch (Exception e) {
						logger.error("获取订阅组中用户失败，机构编号[" + orgId + "]", e);
					}
					for (SubscribeInfo user : orgUsers) {
						user.setDeliveryWay(delWay);
						user.setMsgObject(deliveryWay.getMsgObject());
						subscribeInfos.add(user);
					}
				} else if (SubTypeEnum.USER.getCode().equals(memberType)) {
					String userId = member.getMemberId();
					SubscribeInfo user = null;
					try {
						user = userService.getSubscribeInfo(userId);
						user.setDeliveryWay(delWay);
						user.setMsgObject(deliveryWay.getMsgObject());
					} catch (Exception e) {
						logger.error("获取订阅组中用户失败，用户编号[" + userId + "]", e);
					}
					subscribeInfos.add(user);
				}
			}
		} else if (SubTypeEnum.ROLE.getCode().equals(subType)) {
			// 获取所有指定角色的用户
			String roleId = subscribe.getGroupId();
			List<SubscribeInfo> roleUsers = null;
			try {
				roleUsers = userService.getUserListByRole(roleId);
			} catch (Exception e) {
				logger.error("获取订阅组中用户失败，角色编号[" + roleId + "]", e);
			}
			for (SubscribeInfo user : roleUsers) {
				user.setDeliveryWay(delWay);
				user.setMsgObject(deliveryWay.getMsgObject());
				subscribeInfos.add(user);
			}
		} else if (SubTypeEnum.ORG.getCode().equals(subType)) {
			// 获取指定机构下的所有用户
			String orgId = subscribe.getGroupId();
			List<SubscribeInfo> orgUsers = null;
			try {
				orgUsers = userService.getUserListByOrg(orgId);
			} catch (Exception e) {
				logger.error("获取订阅组中用户失败，机构编号[" + orgId + "]", e);
			}
			for (SubscribeInfo user : orgUsers) {
				user.setDeliveryWay(delWay);
				user.setMsgObject(deliveryWay.getMsgObject());
				subscribeInfos.add(user);
			}
		}

		return subscribeInfos;
	}

	@Override
	public MsgDeliveryWay getDeliverWay(String deliveryWay) {
		MsgDeliveryWay way = deliveryWayMap.getDeliveryWay(deliveryWay);
		return way;
	}

	@Override
	public MsgTitleModel getTitleModel(String titleId, String deliveryWay) {
		return modelMap.getModelByIdAndWay(titleId, deliveryWay);
	}

	@Transactional
	@Override
	public void saveTitleSendRecord(MsgTitleModel titleModel) {
		MsgList record = new MsgList();
		record.setId(UUID.randomUUID().toString());
		record.setTitleId(titleModel.getTitleId());
		record.setSendTime(new Date());
		record.setSendStatus(SendEnum.SUCCESS.getCode());
		msgListMap.insert(record);
	}

	@Transactional
	@Override
	public void saveTitleSendRecord(MsgTitle title) {
		MsgList msgList = new MsgList();
		msgList.setId(UUID.randomUUID().toString());
		msgList.setTitleId(title.getTitleId());
		msgList.setSendTime(new Date());
		msgListMap.insert(msgList);
	}

	@Transactional
	@Override
	public void saveMsgList(MsgTitle title, SubscribeInfo subscribeInfo, String sendStatus, String deliveryWay) {
		MsgList record = new MsgList();
		record.setUserId(subscribeInfo.getUserId());
		record.setId(UUID.randomUUID().toString());
		record.setTitleId(title.getTitleId());
		record.setSendStatus(sendStatus);
		record.setDeliveryWay(deliveryWay);
		record.setSendTime(new Date());
		if (DeliveryWayEnum.POPUP.getCode().equals(deliveryWay)) {
			record.setRecvStatus(YesNoEnum.NO.getCode());
		}
		msgListMap.insert(record);
	}

	@Override
	public List<MsgTitleModelAttach> getAttachList(String attachGroupId) {
		List<MsgTitleModelAttach> list = null;
		list = modelAttachMap.getAttachListByGroupId(attachGroupId);
		return list;
	}

	@Transactional
	@Override
	public void updateAndSaveTitleModel(String titleId, String deliveryWay, String titleModel) {
		MsgTitleModel model = new MsgTitleModel();
		model.setTitleId(titleId);
		model.setDeliveryWay(deliveryWay);
		model.setTitleModel(titleModel);
		modelMap.updateModelByTitleAndDeliveryWay(model);
	}

	@Override
	public List<MsgList> getMsgList(String titleId) {
		return msgListMap.getMsgListByTitleId(titleId);
	}

	@Override
	public void resendMsg(String[] ids) {
		List<MsgList> list = msgListMap.selectResendList(ids);
		ExecutorService pool = Executors.newCachedThreadPool();
		for (final MsgList msgList : list) {
			pool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						resendOneMsg(msgList);
					} catch (Exception e) {
						logger.error("修改发送明细表中id[" + msgList.getId() + "]的记录失败", e);
					}
				}
			});
		}
	}

	// 发送指定的信息
	@Transactional
	@Override
	public void resendOneMsg(MsgList msgList) {
		String titleId = msgList.getTitleId();
		String userId = msgList.getUserId();
		String way = msgList.getDeliveryWay();
		Integer resendCount = (null == msgList.getResendCount()) ? 0 : msgList.getResendCount();
		MsgDeliveryWay deliveryWay = deliveryWayMap.getDeliveryWay(way);
		SubscribeInfo user = userService.getSubscribeInfo(userId);
		user.setDeliveryWay(way);
		user.setMsgObject(deliveryWay.getMsgObject());
		MsgTitle title = titleMap.getTitleByTitleId(titleId);
		String sendStatus = null;
		try {
			BasicMsg basicMsg = DefaultCreator.getComponent(user.getMsgObject());
			MsgTitleModel msgTitleMode = getTitleModel(title.getTitleId(), way);
			basicMsg.setTitleModel(msgTitleMode);
			sendStatus = basicMsg.publish(user);
		} catch (Exception e) {
			logger.error("重发id=[" + msgList.getId() + "的记录失败", e);
			sendStatus = SendEnum.FAIL.getCode();
		}
		msgList.setSendStatus(sendStatus);
		msgList.setResendCount(resendCount + 1);
		msgList.setSendTime(new Date());
		msgListMap.updateMsgListRecord(msgList);
	}

	@Transactional
	@Override
	public void insertSubGroup(MsgSubGroup subGroup) {
		MsgSubGroup existGroup = subGroupMap.getByGroupId(subGroup.getSubGroupId());
		if (null != existGroup) {
			throw new BaseException("用户组编号已经被占用");
		}
		// 新增订阅组
		subGroup.setId(UUID.randomUUID().toString());
		subGroupMap.insert(subGroup);
		insertMemberToGroup(subGroup);
	}

	@Transactional
	@Override
	public void updateSubGroup(MsgSubGroup subGroup) {
		subGroupMap.updateByPrimaryKeySelective(subGroup);
		// 先删除原来的组
		String subGroupId = subGroup.getSubGroupId();
		groupMemberMap.deleteByGroupId(subGroupId);
		// 再次新增组
		insertMemberToGroup(subGroup);
	}

	private void insertMemberToGroup(MsgSubGroup subGroup) {
		// 新增订阅组成员(用户/机构/角色/...)
		String subGroupId = subGroup.getSubGroupId();
		String[] userArray = subGroup.getUserArray();
		if (null != userArray) {
			for (int i = 0; i < userArray.length; i++) {
				MsgSubGroupMember member = new MsgSubGroupMember();
				member.setId(UUID.randomUUID().toString());
				member.setSubGroupId(subGroupId);
				member.setMemberId(userArray[i]);
				SubscribeInfo subscribeInfo = userService.getSubscribeInfo(userArray[i]);
				if (null != subscribeInfo) {
					member.setMemberName(subscribeInfo.getUserName());
				}
				member.setMemberType(SubTypeEnum.USER.getCode());
				groupMemberMap.insert(member);
			}
		}
		String[] orgArray = subGroup.getOrgArray();
		if (null != orgArray) {
			for (int i = 0; i < orgArray.length; i++) {
				MsgSubGroupMember member = new MsgSubGroupMember();
				member.setId(UUID.randomUUID().toString());
				member.setSubGroupId(subGroupId);
				member.setMemberId(orgArray[i]);
				Organization org = userService.getOrganization(orgArray[i]);
				if (null != org) {
					member.setMemberName(org.getOrgName());
				}
				member.setMemberType(SubTypeEnum.ORG.getCode());
				groupMemberMap.insert(member);
			}
		}
		String[] roleArray = subGroup.getRoleArray();
		if (null != roleArray) {
			for (int i = 0; i < roleArray.length; i++) {
				MsgSubGroupMember member = new MsgSubGroupMember();
				member.setId(UUID.randomUUID().toString());
				member.setSubGroupId(subGroupId);
				member.setMemberId(roleArray[i]);
				Role role = userService.getRole(roleArray[i]);
				if (null != role) {
					member.setMemberName(role.getRoleName());
				}
				member.setMemberType(SubTypeEnum.ROLE.getCode());
				groupMemberMap.insert(member);
			}
		}
	}

	@Transactional
	@Override
	public void deleteSubGroup(String subGroupId) {
		subGroupMap.deleteByGroupId(subGroupId);
		groupMemberMap.deleteByGroupId(subGroupId);
	}

	@Override
	public MsgSubGroup getGroupInfoByGroupId(String subGroupId) {
		MsgSubGroup subGroup = subGroupMap.getByGroupId(subGroupId);
		List<SubscribeInfo> userList = new ArrayList<SubscribeInfo>();
		List<Organization> orgList = new ArrayList<Organization>();
		List<Role> roleList = new ArrayList<Role>();
		List<MsgSubGroupMember> members = groupMemberMap.getGroupMemberByGroupId(subGroupId);
		for (MsgSubGroupMember member : members) {
			if (SubTypeEnum.USER.getCode().equals(member.getMemberType())) {
				SubscribeInfo user = new SubscribeInfo();
				user.setUserId(member.getMemberId());
				user.setUserName(member.getMemberName());
				userList.add(user);
			} else if (SubTypeEnum.ORG.getCode().equals(member.getMemberType())) {
				Organization org = new Organization();
				org.setOrgId(member.getMemberId());
				org.setOrgName(member.getMemberName());
				orgList.add(org);
			} else if (SubTypeEnum.ROLE.getCode().equals(member.getMemberType())) {
				Role role = new Role();
				role.setRoleId(member.getMemberId());
				role.setRoleName(member.getMemberName());
				roleList.add(role);
			}
		}
		subGroup.setUserList(userList);
		subGroup.setOrgList(orgList);
		subGroup.setRoleList(roleList);
		return subGroup;
	}

	public MsgSubGroup getGroupInfoByGroupId2(String subGroupId) {
		MsgSubGroup subGroup = subGroupMap.getByGroupId(subGroupId);
		List<String> users = new ArrayList<String>();
		List<String> orgs = new ArrayList<String>();
		List<String> roles = new ArrayList<String>();
		List<MsgSubGroupMember> members = groupMemberMap.getGroupMemberByGroupId(subGroupId);
		for (MsgSubGroupMember member : members) {
			if (SubTypeEnum.USER.getCode().equals(member.getMemberType())) {
				users.add(member.getMemberId());
			} else if (SubTypeEnum.ORG.getCode().equals(member.getMemberType())) {
				orgs.add(member.getMemberId());
			} else if (SubTypeEnum.ROLE.getCode().equals(member.getMemberType())) {
				roles.add(member.getMemberId());
			}
		}
		subGroup.setUserIds(users);
		subGroup.setOrgIds(orgs);
		subGroup.setRoleIds(roles);
		return subGroup;
	}

	@Transactional
	@Override
	public void saveTitle(MsgTitle title) {
		MsgTitle existTitle = titleMap.getTitleByTitleId(title.getTitleId());
		if (null != existTitle) {
			throw new BaseException("主题编号已被占用");
		}
		title.setId(UUID.randomUUID().toString());
		titleMap.insert(title);
		// 新增主题的时候要添加默认邮箱模版
		MsgTitleModel emailModel = new MsgTitleModel();
		emailModel.setId(UUID.randomUUID().toString());
		emailModel.setTitleId(title.getTitleId());
		emailModel.setDeliveryWay(DeliveryWayEnum.EMAIL.getCode());
		emailModel.setTitleModel(title.getEmailModel());
		emailModel.setAttachGroupId(UUID.randomUUID().toString());
		modelMap.insert(emailModel);
		// 新增主题的时候要添加默认短信模版
		MsgTitleModel phoneModel = new MsgTitleModel();
		phoneModel.setId(UUID.randomUUID().toString());
		phoneModel.setTitleId(title.getTitleId());
		phoneModel.setDeliveryWay(DeliveryWayEnum.PHONE.getCode());
		phoneModel.setTitleModel(title.getPhoneModel());
		phoneModel.setAttachGroupId(UUID.randomUUID().toString());
		modelMap.insert(phoneModel);
		// 新增主题的时候要添加默认弹窗模版
		MsgTitleModel popupModel = new MsgTitleModel();
		popupModel.setId(UUID.randomUUID().toString());
		popupModel.setTitleId(title.getTitleId());
		popupModel.setDeliveryWay(DeliveryWayEnum.POPUP.getCode());
		popupModel.setTitleModel(title.getPopupModel());
		popupModel.setAttachGroupId(UUID.randomUUID().toString());
		modelMap.insert(popupModel);
		// 新增附件组
		String[] addKeys = title.getAddKeys();
		if (null != addKeys && addKeys.length > 0) {
			for (int i = 0; i < addKeys.length; i++) {
				MsgTitleModelAttach attach = new MsgTitleModelAttach();
				attach.setId(UUID.randomUUID().toString());
				attach.setAttachGroupId(emailModel.getAttachGroupId());
				attach.setFlag(YesNoEnum.YES.getCode());
				attach.setFileKey(addKeys[i]);
				modelAttachMap.insertSelective(attach);
			}
		}
	}

	// 备份用的
	public void saveTitle2(MsgTitle title) {
		title.setId(UUID.randomUUID().toString());
		titleMap.insert(title);
		// 新增主题的时候要添加默认邮箱模版
		MsgTitleModel emailModel = new MsgTitleModel();
		emailModel.setId(UUID.randomUUID().toString());
		emailModel.setTitleId(title.getTitleId());
		emailModel.setDeliveryWay(DeliveryWayEnum.EMAIL.getCode());
		emailModel.setTitleModel(title.getTitleContent());
		emailModel.setAttachGroupId(UUID.randomUUID().toString());
		modelMap.insert(emailModel);
		// 新增主题的时候要添加默认短信模版
		MsgTitleModel phoneModel = new MsgTitleModel();
		phoneModel.setId(UUID.randomUUID().toString());
		phoneModel.setTitleId(title.getTitleId());
		phoneModel.setDeliveryWay(DeliveryWayEnum.PHONE.getCode());
		phoneModel.setTitleModel(title.getTitleContent());
		phoneModel.setAttachGroupId(UUID.randomUUID().toString());
		modelMap.insert(phoneModel);
		// 新增主题的时候要添加默认弹窗模版
		MsgTitleModel popupModel = new MsgTitleModel();
		popupModel.setId(UUID.randomUUID().toString());
		popupModel.setTitleId(title.getTitleId());
		popupModel.setDeliveryWay(DeliveryWayEnum.POPUP.getCode());
		popupModel.setTitleModel(title.getTitleContent());
		popupModel.setAttachGroupId(UUID.randomUUID().toString());
		modelMap.insert(popupModel);
	}

	@Transactional
	@Override
	public void insertMsgSubscribe(MsgSubscribe subscribe) {
		subscribe.setId(UUID.randomUUID().toString());
		msgSubMap.insert(subscribe);
	}

	@Transactional
	@Override
	public void deleteMsgSubscribe(String id) {
		msgSubMap.updateMsgSubscribeToFalse(id);
	}

	@Transactional
	@Override
	public void deleteMsgSubscribe(String[] ids) {
		if (null == ids || ids.length == 0) {
			return;
		}
		msgSubMap.batchDelete(ids);
	}

	@Override
	public MsgTitle getTitleByTitleId(String titleId) {
		return titleMap.getTitleByTitleId(titleId);
	}

	@Transactional
	@Override
	public void saveGroupMsgSubscribe(MsgSubscribe subscribe) {
		String[] deliveryWays = subscribe.getDeliveryWays();
		if (null == deliveryWays) {
			throw new BaseException("请选择订阅方式");
		}
		for (int i = 0; i < deliveryWays.length; i++) {
			subscribe.setId(UUID.randomUUID().toString());
			subscribe.setDeliveryWay(deliveryWays[i]);
			subscribe.setSubType(SubTypeEnum.GROUP.getCode());
			subscribe.setFlag(YesNoEnum.YES.getCode());
			msgSubMap.insertGroupMsgSubIfNotExists(subscribe);
		}
	}

	@Transactional
	@Override
	public void saveRoleMsgSubscribe(MsgSubscribe subscribe) {
		String[] deliveryWays = subscribe.getDeliveryWays();
		if (null == deliveryWays) {
			throw new BaseException("请选择订阅方式");
		}
		for (int i = 0; i < deliveryWays.length; i++) {
			subscribe.setId(UUID.randomUUID().toString());
			subscribe.setDeliveryWay(deliveryWays[i]);
			subscribe.setSubType(SubTypeEnum.ROLE.getCode());
			subscribe.setFlag(YesNoEnum.YES.getCode());
			msgSubMap.insertRoleMsgSubIfNotExists(subscribe);
		}
	}

	@Transactional
	@Override
	public void saveOrgMsgSubscribe(MsgSubscribe subscribe) {
		String[] deliveryWays = subscribe.getDeliveryWays();
		if (null == deliveryWays) {
			throw new BaseException("请选择订阅方式");
		}
		for (int i = 0; i < deliveryWays.length; i++) {
			subscribe.setId(UUID.randomUUID().toString());
			subscribe.setDeliveryWay(deliveryWays[i]);
			subscribe.setSubType(SubTypeEnum.ORG.getCode());
			subscribe.setFlag(YesNoEnum.YES.getCode());
			msgSubMap.insertOrgMsgSubIfNotExists(subscribe);
		}
	}

	@Transactional
	@Override
	public void saveUserMsgSubscribe(MsgSubscribe subscribe) {
		String[] deliveryWays = subscribe.getDeliveryWays();
		if (null == deliveryWays) {
			throw new BaseException("请选择订阅方式");
		}
		for (int i = 0; i < deliveryWays.length; i++) {
			subscribe.setId(UUID.randomUUID().toString());
			subscribe.setDeliveryWay(deliveryWays[i]);
			subscribe.setSubType(SubTypeEnum.USER.getCode());
			subscribe.setFlag(YesNoEnum.YES.getCode());
			msgSubMap.insertUserMsgSubIfNotExists(subscribe);
		}
	}

	@Transactional
	@Override
	public void saveMsgSubscribeByTitle(MsgTitle title, String[] deliveryWays, List<MsgSubGroup> groups,
			List<Role> roles, List<Organization> orgs, List<SubscribeInfo> users) {
		for (int i = 0; i < deliveryWays.length; i++) {
			for (MsgSubGroup group : groups) {
				MsgSubscribe subscribe = new MsgSubscribe();
				subscribe.setId(UUID.randomUUID().toString());
				subscribe.setTitleId(title.getTitleId());
				subscribe.setTitleName(title.getTitleName());
				subscribe.setDeliveryWay(deliveryWays[i]);
				subscribe.setGroupId(group.getSubGroupId());
				subscribe.setGroupName(group.getSubGroupName());
				subscribe.setSubType(SubTypeEnum.GROUP.getCode());
				subscribe.setFlag(YesNoEnum.YES.getCode());
				msgSubMap.insertGroupMsgSubIfNotExists(subscribe);
			}
			for (Role role : roles) {
				MsgSubscribe subscribe = new MsgSubscribe();
				subscribe.setId(UUID.randomUUID().toString());
				subscribe.setTitleId(title.getTitleId());
				subscribe.setTitleName(title.getTitleName());
				subscribe.setDeliveryWay(deliveryWays[i]);
				subscribe.setGroupId(role.getRoleId());
				subscribe.setGroupName(role.getRoleName());
				subscribe.setSubType(SubTypeEnum.ROLE.getCode());
				subscribe.setFlag(YesNoEnum.YES.getCode());
				msgSubMap.insertRoleMsgSubIfNotExists(subscribe);
			}
			for (Organization org : orgs) {
				MsgSubscribe subscribe = new MsgSubscribe();
				subscribe.setId(UUID.randomUUID().toString());
				subscribe.setTitleId(title.getTitleId());
				subscribe.setTitleName(title.getTitleName());
				subscribe.setDeliveryWay(deliveryWays[i]);
				subscribe.setGroupId(org.getOrgId());
				subscribe.setGroupName(org.getOrgName());
				subscribe.setSubType(SubTypeEnum.ORG.getCode());
				subscribe.setFlag(YesNoEnum.YES.getCode());
				msgSubMap.insertOrgMsgSubIfNotExists(subscribe);
			}
			for (SubscribeInfo user : users) {
				MsgSubscribe subscribe = new MsgSubscribe();
				subscribe.setId(UUID.randomUUID().toString());
				subscribe.setTitleId(title.getTitleId());
				subscribe.setTitleName(title.getTitleName());
				subscribe.setDeliveryWay(deliveryWays[i]);
				subscribe.setUserId(user.getUserId());
				subscribe.setUserName(user.getUserName());
				subscribe.setSubType(SubTypeEnum.USER.getCode());
				subscribe.setFlag(YesNoEnum.YES.getCode());
				msgSubMap.insertUserMsgSubIfNotExists(subscribe);
			}
		}
	}

	@Override
	public List<MsgSubscribe> getUserMsgSubscribe(MsgSubscribe subscribe) {
		subscribe.setSubType(SubTypeEnum.USER.getCode());
		subscribe.setFlag(YesNoEnum.YES.getCode());
		return msgSubMap.getMsgSubscribeBySubType(subscribe);
	}

	@Override
	public List<MsgSubscribe> getOrgMsgSubscribe(MsgSubscribe subscribe) {
		subscribe.setSubType(SubTypeEnum.ORG.getCode());
		subscribe.setFlag(YesNoEnum.YES.getCode());
		return msgSubMap.getMsgSubscribeBySubType(subscribe);
	}

	@Override
	public List<MsgSubscribe> getRoleMsgSubscribe(MsgSubscribe subscribe) {
		subscribe.setSubType(SubTypeEnum.ROLE.getCode());
		subscribe.setFlag(YesNoEnum.YES.getCode());
		return msgSubMap.getMsgSubscribeBySubType(subscribe);
	}

	@Override
	public List<MsgSubscribe> getGroupMsgSubscribe(MsgSubscribe subscribe) {
		subscribe.setSubType(SubTypeEnum.GROUP.getCode());
		subscribe.setFlag(YesNoEnum.YES.getCode());
		return msgSubMap.getMsgSubscribeBySubType(subscribe);
	}

	@Override
	public List<MsgTitleModelAttach> getAttachByTitleAndDeliveryWay(String titleId, String deliveryWay) {
		MsgTitleModel model = modelMap.getModelByIdAndWay(titleId, deliveryWay);
		return modelAttachMap.getAttachListByGroupId(model.getAttachGroupId());
	}

	@Transactional
	@Override
	public void deleteAttachFile(String fileKey) {

	}

	@Override
	public List<MsgList> getMsgList(MsgList msgList) {
		return msgListMap.searchMsgList(msgList);
	}

	@Override
	public List<MsgTitle> getNotReadMsgListByUserId(String userId) {
		// 只需要获取用户没有读取的主题编号
		List<MsgList> titleIds = msgListMap.searchNotReadMsgListByUserId(userId);
		List<MsgTitle> titles = new ArrayList<MsgTitle>();
		for (MsgList msgList : titleIds) {
			MsgTitle title = titleMap.getTitleByTitleId(msgList.getTitleId());
			titles.add(title);
		}
		return titles;
	}

	@Override
	public int checkUserHasUnsendMsg(String userId) {
		return msgListMap.existUnsendMsg(userId);
	}

	@Override
	@Transactional
	public void changeMsgStatus(String userId, String titleId) {
		if (null != userId && !"".equals(userId) && null != titleId && !"".equals(titleId)) {
			msgListMap.changeMsgStatus(titleId, userId);
		} else {
			logger.error("修改消息列表推送状态失败", new BaseException("用户编号和主题编号不能为空"));
		}
	}

	@Override
	public File getFileByKey(String key) {
		return fileService.getFileByKey(key);
	}

	@Override
	public MsgTitleModel getModelByTitleidAndDeliveryWay(String titleId, String deliveryWay) {
		return modelMap.getModelByIdAndWay(titleId, deliveryWay);
	}

	@Override
	@Transactional
	public void saveMsgTitleModelAttach(String titleId, String deliveryWay, String fileKey) {
		MsgTitleModel model = modelMap.getModelByIdAndWay(titleId, deliveryWay);
		if (null == model) {
			logger.error("数据库中没有[" + titleId + "]和[" + deliveryWay + "]方式对应的附件组");
			throw new BaseException("没有对应的附件组");
		}
		MsgTitleModelAttach attach = new MsgTitleModelAttach();
		attach.setId(UUID.randomUUID().toString());
		attach.setAttachGroupId(model.getAttachGroupId());
		attach.setFileKey(fileKey);
		modelAttachMap.insert(attach);
	}

	@Override
	@Transactional
	public void removeFileKey(String fileKey) {
		modelAttachMap.failureFileKey(fileKey);
	}

	@Override
	public void searchSubGroup(MsgSubGroup subGroup, DataGrid dataGrid) {
		// 排序
		if (!Tools.isEmpty(dataGrid.getSort())) {
			subGroup.setSort(Tools.fieldName2ColName(dataGrid.getSort()));
			subGroup.setOrder(dataGrid.getOrder());
		}
		// 分页
		PageHelper.startPage(dataGrid.getPage(), dataGrid.getRows());
		Page<MsgSubGroup> page = (Page<MsgSubGroup>) subGroupMap.search(subGroup);
		// 设置结果
		DataGridResult dgr = new DataGridResult();
		dgr.setRows(page.getResult());
		dgr.setTotal(page.getTotal());
		dataGrid.setDataGridResult(dgr);
	}

	@Override
	public void searchTitle(MsgTitle title, DataGrid dataGrid) {
		// 排序
		if (!Tools.isEmpty(dataGrid.getSort())) {
			title.setSort(Tools.fieldName2ColName(dataGrid.getSort()));
			title.setOrder(dataGrid.getOrder());
		}
		// 分页
		PageHelper.startPage(dataGrid.getPage(), dataGrid.getRows());
		Page<MsgTitle> page = (Page<MsgTitle>) titleMap.search(title);
		// 设置结果
		DataGridResult dgr = new DataGridResult();
		dgr.setRows(page.getResult());
		dgr.setTotal(page.getTotal());
		dataGrid.setDataGridResult(dgr);
	}

	@Override
	public Map<String, Object> getSystemMembers() {
		Map<String, Object> members = new HashMap<>();
		members.put(MessageConstants.SYSTEM_USER, userService.getSystemUsers());
		members.put(MessageConstants.SYSTEM_ORG, userService.getSystemOrganization());
		members.put(MessageConstants.SYSTEM_ROLE, userService.getSystemRole());
		return members;
	}

	@Override
	public void searchGroupSubscribe(MsgSubscribe msgSubscribe, DataGrid dataGrid) {
		// 排序
		if (!Tools.isEmpty(dataGrid.getSort())) {
			msgSubscribe.setSort(Tools.fieldName2ColName(dataGrid.getSort()));
			msgSubscribe.setOrder(dataGrid.getOrder());
		}
		// 分页
		PageHelper.startPage(dataGrid.getPage(), dataGrid.getRows());
		Page<MsgSubscribe> page = (Page<MsgSubscribe>) msgSubMap.getMsgSubscribeBySubType(msgSubscribe);
		// 设置结果
		DataGridResult dgr = new DataGridResult();
		dgr.setRows(page.getResult());
		dgr.setTotal(page.getTotal());
		dataGrid.setDataGridResult(dgr);
	}

	@Override
	public List<MsgSubGroup> getAllSubGroup() {
		return subGroupMap.getAllSubGroup();
	}

	@Override
	public List<MsgTitle> getAllTitle() {
		return titleMap.getAllTitle();
	}

	private MsgSubscribe getMemberSubscribeInfo(MsgSubscribe subscribe) {
		if (null == subscribe.getTitleId() || "".equals(subscribe.getTitleId().trim())) {
			throw new BaseException("订阅主题不能为空");
		}
		if (null == subscribe.getGroupId() || "".equals(subscribe.getGroupId().trim())) {
			throw new BaseException("订阅对象不能为空");
		}
		List<MsgSubscribe> list = msgSubMap.getListByTitleIdAndGroupId(subscribe);
		if (null == list || list.size() == 0) {
			throw new BaseException("找不到对应的订阅信息");
		}
		String[] deliveryWays = new String[list.size()];
		for (int i = 0; i < deliveryWays.length; i++) {
			deliveryWays[i] = list.get(i).getDeliveryWay();
		}
		subscribe.setDeliveryWays(deliveryWays);
		return subscribe;
	}

	@Override
	public MsgSubscribe getGroupSubscribeInfo(MsgSubscribe subscribe) {
		subscribe.setSubType(SubTypeEnum.GROUP.getCode());
		return getMemberSubscribeInfo(subscribe);

	}

	@Override
	public MsgSubscribe getUserSubscribeInfo(MsgSubscribe subscribe) {
		if (null == subscribe.getTitleId() || "".equals(subscribe.getTitleId().trim())) {
			throw new BaseException("订阅主题不能为空");
		}
		if (null == subscribe.getUserId() || "".equals(subscribe.getUserId().trim())) {
			throw new BaseException("订阅对象不能为空");
		}
		subscribe.setSubType(SubTypeEnum.USER.getCode());
		List<MsgSubscribe> list = msgSubMap.getListByTitleAndUser(subscribe);
		if (null == list || list.size() == 0) {
			throw new BaseException("找不到对应的订阅信息");
		}
		String[] deliveryWays = new String[list.size()];
		for (int i = 0; i < deliveryWays.length; i++) {
			deliveryWays[i] = list.get(i).getDeliveryWay();
		}
		subscribe.setDeliveryWays(deliveryWays);
		return subscribe;
	}

	@Override
	public MsgSubscribe getOrgSubscribeInfo(MsgSubscribe subscribe) {
		subscribe.setSubType(SubTypeEnum.ORG.getCode());
		return getMemberSubscribeInfo(subscribe);
	}

	private void validateSubcribe(MsgSubscribe subscribe) {
		if (null == subscribe.getTitleId() || "".equals(subscribe.getTitleId().trim())) {
			throw new BaseException("订阅主题不能为空");
		}
		if (null == subscribe.getGroupId() || "".equals(subscribe.getGroupId().trim())) {
			throw new BaseException("订阅对象不能为空");
		}
	}

	@Override
	@Transactional
	public void updateGroupSubscribe(MsgSubscribe subscribe) {
		validateSubcribe(subscribe);
		msgSubMap.deleteByTitleAndGroup(subscribe);
		String[] deliveryWays = subscribe.getDeliveryWays();
		if (null == deliveryWays || deliveryWays.length == 0) {
			return;
		}
		for (int i = 0; i < deliveryWays.length; i++) {
			MsgSubscribe msgSubscribe = new MsgSubscribe();
			msgSubscribe.setId(UUID.randomUUID().toString());
			msgSubscribe.setGroupId(subscribe.getGroupId());
			msgSubscribe.setGroupName(subscribe.getGroupName());
			msgSubscribe.setTitleId(subscribe.getTitleId());
			msgSubscribe.setTitleName(subscribe.getTitleName());
			msgSubscribe.setSubType(SubTypeEnum.GROUP.getCode());
			msgSubscribe.setFlag(YesNoEnum.YES.getCode());
			msgSubscribe.setDeliveryWay(deliveryWays[i]);
			msgSubMap.insert(msgSubscribe);
		}
	}

	@Override
	@Transactional
	public void updateOrgSubscribe(MsgSubscribe subscribe) {
		validateSubcribe(subscribe);
		msgSubMap.deleteByTitleAndGroup(subscribe);
		String[] deliveryWays = subscribe.getDeliveryWays();
		if (null == deliveryWays || deliveryWays.length == 0) {
			return;
		}
		for (int i = 0; i < deliveryWays.length; i++) {
			MsgSubscribe msgSubscribe = new MsgSubscribe();
			msgSubscribe.setId(UUID.randomUUID().toString());
			msgSubscribe.setGroupId(subscribe.getGroupId());
			msgSubscribe.setGroupName(subscribe.getGroupName());
			msgSubscribe.setTitleId(subscribe.getTitleId());
			msgSubscribe.setTitleName(subscribe.getTitleName());
			msgSubscribe.setSubType(SubTypeEnum.ORG.getCode());
			msgSubscribe.setFlag(YesNoEnum.YES.getCode());
			msgSubscribe.setDeliveryWay(deliveryWays[i]);
			msgSubMap.insert(msgSubscribe);
		}
	}

	@Override
	@Transactional
	public void updateUserSubscribe(MsgSubscribe subscribe) {
		validateSubcribe(subscribe);
		msgSubMap.deleteByTitleAndUser(subscribe);
		String[] deliveryWays = subscribe.getDeliveryWays();
		if (null == deliveryWays || deliveryWays.length == 0) {
			return;
		}
		for (int i = 0; i < deliveryWays.length; i++) {
			MsgSubscribe msgSubscribe = new MsgSubscribe();
			msgSubscribe.setId(UUID.randomUUID().toString());
			msgSubscribe.setUserId(subscribe.getUserId());
			msgSubscribe.setUserName(subscribe.getUserName());
			msgSubscribe.setTitleId(subscribe.getTitleId());
			msgSubscribe.setTitleName(subscribe.getTitleName());
			msgSubscribe.setSubType(SubTypeEnum.USER.getCode());
			msgSubscribe.setFlag(YesNoEnum.YES.getCode());
			msgSubscribe.setDeliveryWay(deliveryWays[i]);
			msgSubMap.insert(msgSubscribe);
		}
	}

	@Override
	public void searchMsgList(MsgList list, DataGrid dataGrid) {
		// 排序
		if (!Tools.isEmpty(dataGrid.getSort())) {
			list.setSort(Tools.fieldName2ColName(dataGrid.getSort()));
			list.setOrder(dataGrid.getOrder());
		}
		// 分页
		PageHelper.startPage(dataGrid.getPage(), dataGrid.getRows());
		List<MsgList> msgList = msgListMap.searchMsgList(list);
		for (MsgList msg : msgList) {
			String titleId = msg.getTitleId();
			MsgTitle title = titleMap.getTitleByTitleId(titleId);
			if (null != title) {
				msg.setTitleName(title.getTitleName());
			}
			String userId = msg.getUserId();
			SubscribeInfo user = userService.getSubscribeInfo(userId);
			if (null != user) {
				msg.setUserName(user.getUserName());
			}
			Integer resendCount = msg.getResendCount();
			if (null == resendCount) {
				msg.setResendCount(0);
			}
		}
		Page<MsgList> page = (Page<MsgList>) msgList;
		// 设置结果
		DataGridResult dgr = new DataGridResult();
		dgr.setRows(page.getResult());
		dgr.setTotal(page.getTotal());
		dataGrid.setDataGridResult(dgr);
	}

	@Override
	@Transactional
	public void batchDeleteTitle(String[] ids) {
		if (null == ids || ids.length == 0) {
			return;
		}
		List<MsgTitle> titleList = titleMap.getTitleList(ids);
		if (null != titleList && titleList.size() > 0) {
			for (MsgTitle title : titleList) {
				String titleId = title.getTitleId();
				String attachGroupId = modelMap.getModelByIdAndWay(titleId, DeliveryWayEnum.EMAIL.getCode())
						.getAttachGroupId();
				modelAttachMap.deleteByAttachGroupId(attachGroupId);
				modelMap.deleteByTitleId(titleId);
				msgSubMap.deleteTitleAllSubscribe(titleId);
			}
		}
		titleMap.batchDeleteTitle(ids);
	}

	@Override
	public List<Organization> getSystemOrg() {
		return userService.getSystemOrganization();
	}

	@Override
	public List<SubscribeInfo> getSystemUser() {
		return userService.getSystemUsers();
	}

	@Override
	public void searchMsgTitle(MsgTitle title, DataGrid dataGrid) {
		// 排序
		if (!Tools.isEmpty(dataGrid.getSort())) {
			title.setSort(Tools.fieldName2ColName(dataGrid.getSort()));
			title.setOrder(dataGrid.getOrder());
		}
		// 分页
		PageHelper.startPage(dataGrid.getPage(), dataGrid.getRows());
		Page<MsgTitle> page = (Page<MsgTitle>) titleMap.search(title);
		// 设置结果
		DataGridResult dgr = new DataGridResult();
		dgr.setRows(page.getResult());
		dgr.setTotal(page.getTotal());
		dataGrid.setDataGridResult(dgr);
	}

	@Override
	public List<MsgTitleType> getTitleTypeList() {
		return titleTypeMap.getAll();
	}

	@Override
	public MsgTitle getAppointedTitle(String id) {
		MsgTitle title = titleMap.selectByPrimaryKey(id);
		if (null == title) {
			throw new BaseException("找不到对应的主题信息");
		}
		// 1、获取主题对应的三个模版信息
		String titleId = title.getTitleId();
		MsgTitleModel emailModel = modelMap.getModelByIdAndWay(titleId, DeliveryWayEnum.EMAIL.getCode());
		title.setEmailModel(emailModel.getTitleModel());
		MsgTitleModel phoneModel = modelMap.getModelByIdAndWay(titleId, DeliveryWayEnum.PHONE.getCode());
		title.setPhoneModel(phoneModel.getTitleModel());
		MsgTitleModel popupModel = modelMap.getModelByIdAndWay(titleId, DeliveryWayEnum.POPUP.getCode());
		title.setPopupModel(popupModel.getTitleModel());
		// 2、获取模版中含有的附件信息（此应用中只有邮件包含附件信息）
		String attachGroupId = emailModel.getAttachGroupId();
		List<MsgTitleModelAttach> attachList = modelAttachMap.getAttachListByGroupId(attachGroupId);
		List<String> allKeys = new ArrayList<String>();
		if (null != attachList && attachList.size() > 0) {
			for (MsgTitleModelAttach attach : attachList) {
				allKeys.add(attach.getFileKey());
			}
		}
		return title;
	}

	@Override
	@Transactional
	public void updateTilteAndModel(MsgTitle title) {
		MsgTitleModel model = new MsgTitleModel();
		model.setTitleId(title.getTitleId());
		// 修改邮件模版
		String emailModel = title.getEmailModel();
		model.setTitleModel(emailModel);
		modelMap.updateModelByTitleAndDeliveryWay(model);
		// 修改短信模版
		String phoneModel = title.getPhoneModel();
		model.setTitleModel(phoneModel);
		modelMap.updateModelByTitleAndDeliveryWay(model);
		// 修改弹窗模版
		String popupModel = title.getPopupModel();
		model.setTitleModel(popupModel);
		modelMap.updateModelByTitleAndDeliveryWay(model);
		// 删除部分附件
		String[] delKeys = title.getDelKeys();
		if (null != delKeys && delKeys.length > 0) {
			modelAttachMap.deleteByFileKeys(delKeys);
		}
		// 添加新的附件
		String[] addKeys = title.getAddKeys();
		if (null != addKeys && addKeys.length > 0) {
			String attachGroupId = modelMap.getModelByIdAndWay(title.getTitleId(), DeliveryWayEnum.EMAIL.getCode())
					.getAttachGroupId();
			for (int i = 0; i < addKeys.length; i++) {
				MsgTitleModelAttach attach = new MsgTitleModelAttach();
				attach.setId(UUID.randomUUID().toString());
				attach.setAttachGroupId(attachGroupId);
				attach.setFlag(YesNoEnum.YES.getCode());
				attach.setFileKey(addKeys[i]);
				modelAttachMap.insertSelective(attach);
			}
		}
	}

	@Override
	public List<MsgTitle> getTitleByType(String typeId) {
		return titleMap.getTitleByType(typeId);
	}

	@Override
	public void searchTitleSubscribe(MsgTitle title, DataGrid dataGrid) {
		// 排序
		if (!Tools.isEmpty(dataGrid.getSort())) {
			title.setSort(Tools.fieldName2ColName(dataGrid.getSort()));
			title.setOrder(dataGrid.getOrder());
		}
		// 分页
		PageHelper.startPage(dataGrid.getPage(), dataGrid.getRows());
		List<MsgTitle> titleList = titleMap.getSubscribeTitle(title);
		for (MsgTitle msgTitle : titleList) {
			MsgTitleType type = titleTypeMap.getByTypeId(msgTitle.getTitleType());
			if (null != type) {
				title.setTypeName(type.getTypeName());
			}
		}
		Page<MsgTitle> page = (Page<MsgTitle>) titleList;
		// 设置结果
		DataGridResult dgr = new DataGridResult();
		dgr.setRows(page.getResult());
		dgr.setTotal(page.getTotal());
		dataGrid.setDataGridResult(dgr);
	}

	@Override
	@Transactional
	public void saveTitleSubscribe(MsgTitle title) {
		String[] deliveryWays = title.getDeliveryWays();
		if (null == deliveryWays || deliveryWays.length == 0) {
			throw new BaseException("请选择订阅方式");
		}
		String[] userIds = title.getUserIds();
		String[] orgIds = title.getOrgIds();
		String[] roleIds = title.getRoleIds();
		if (null == userIds && null == orgIds && null == roleIds) {
			throw new BaseException("请选择订阅对象");
		}
		for (int i = 0; i < deliveryWays.length; i++) {
			for (int j = 0; j < userIds.length; j++) {
				String userId = userIds[j];
				MsgSubscribe subscribe = new MsgSubscribe();
				subscribe.setId(UUID.randomUUID().toString());
				subscribe.setTitleId(title.getTitleId());
				subscribe.setTitleName(title.getTitleName());
				subscribe.setDeliveryWay(deliveryWays[i]);
				subscribe.setUserId(userId);
				subscribe.setUserName(userService.getSubscribeInfo(userId).getUserName());
				subscribe.setFlag(YesNoEnum.YES.getCode());
				subscribe.setSubType(SubTypeEnum.USER.getCode());
				msgSubMap.insertUserMsgSubIfNotExists(subscribe);
			}
			for (int j = 0; j < orgIds.length; j++) {
				String orgId = orgIds[j];
				MsgSubscribe subscribe = new MsgSubscribe();
				subscribe.setId(UUID.randomUUID().toString());
				subscribe.setTitleId(title.getTitleId());
				subscribe.setTitleName(title.getTitleName());
				subscribe.setDeliveryWay(deliveryWays[i]);
				subscribe.setGroupId(orgId);
				subscribe.setGroupName(userService.getOrganization(orgId).getOrgName());
				subscribe.setFlag(YesNoEnum.YES.getCode());
				subscribe.setSubType(SubTypeEnum.ORG.getCode());
				msgSubMap.insertOrgMsgSubIfNotExists(subscribe);
			}
			for (int j = 0; j < roleIds.length; j++) {
				String roleId = roleIds[j];
				MsgSubscribe subscribe = new MsgSubscribe();
				subscribe.setId(UUID.randomUUID().toString());
				subscribe.setTitleId(title.getTitleId());
				subscribe.setTitleName(title.getTitleName());
				subscribe.setDeliveryWay(deliveryWays[i]);
				subscribe.setGroupId(roleId);
				subscribe.setGroupName(userService.getRole(roleId).getRoleName());
				subscribe.setFlag(YesNoEnum.YES.getCode());
				subscribe.setSubType(SubTypeEnum.ROLE.getCode());
				msgSubMap.insertRoleMsgSubIfNotExists(subscribe);
			}
		}
	}

	@Override
	public MsgTitle getTitleSubscribeByTitleId(String titleId) {
		if (null == titleId || "".equals(titleId.trim())) {
			throw new BaseException("请选择主题编号");
		}
		// 找到主题信息
		MsgTitle title = titleMap.getTitleByTitleId(titleId);
		if (null == title) {
			throw new BaseException("找不到对应的主题信息");
		}
		// 找到主题类型信息
		MsgTitleType type = titleTypeMap.getByTypeId(title.getTitleType());
		if (null == type) {
			throw new BaseException("没有对应的主题类型");
		}
		List<MsgSubscribe> list = msgSubMap.getListByTitleId(titleId);
		List<SubscribeInfo> users = new ArrayList<SubscribeInfo>();
		List<Organization> orgs = new ArrayList<Organization>();
		List<Role> roles = new ArrayList<Role>();
		for (MsgSubscribe subscribe : list) {
			String subType = subscribe.getSubType();
			if (subType.equals(SubTypeEnum.USER.getCode())) {
				String userId = subscribe.getUserId();
				SubscribeInfo user = userService.getSubscribeInfo(userId);
				users.add(user);
			}
			String groupId = subscribe.getGroupId();
			if (subType.equals(SubTypeEnum.ORG.getCode())) {
				Organization org = userService.getOrganization(groupId);
				orgs.add(org);
			}
			if (subType.equals(SubTypeEnum.ROLE.getCode())) {
				Role role = userService.getRole(groupId);
				roles.add(role);
			}
		}
		List<MsgSubscribe> subscribes = msgSubMap.getDeliveryWaysByTitleId(titleId);
		if (null != subscribes && subscribes.size() != 0) {
			String[] deliveryWays = new String[subscribes.size()];
			for (int j = 0; j < deliveryWays.length; j++) {
				deliveryWays[j] = subscribes.get(j).getDeliveryWay();
			}
			title.setDeliveryWays(deliveryWays);
		}
		title.setUsers(users);
		title.setOrgs(orgs);
		title.setRoles(roles);
		return title;
	}

	@Override
	@Transactional
	public void updateTitleSubscribe(MsgTitle title) {
		String[] deliveryWays = title.getDeliveryWays();
		if (null == deliveryWays || deliveryWays.length == 0) {
			throw new BaseException("请选择订阅方式");
		}
		String[] userIds = title.getUserIds();
		String[] orgIds = title.getOrgIds();
		String[] roleIds = title.getRoleIds();
		if (null == userIds && null == orgIds && null == roleIds) {
			throw new BaseException("请选择订阅对象");
		}
		msgSubMap.deleteTitleAllSubscribe(title.getTitleId());
		for (int i = 0; i < deliveryWays.length; i++) {
			for (int j = 0; j < userIds.length; j++) {
				String userId = userIds[j];
				MsgSubscribe subscribe = new MsgSubscribe();
				subscribe.setId(UUID.randomUUID().toString());
				subscribe.setTitleId(title.getTitleId());
				subscribe.setTitleName(title.getTitleName());
				subscribe.setDeliveryWay(deliveryWays[i]);
				subscribe.setUserId(userId);
				subscribe.setUserName(userService.getSubscribeInfo(userId).getUserName());
				subscribe.setFlag(YesNoEnum.YES.getCode());
				subscribe.setSubType(SubTypeEnum.USER.getCode());
				msgSubMap.insertUserMsgSubIfNotExists(subscribe);
			}
			for (int j = 0; j < orgIds.length; j++) {
				String orgId = orgIds[j];
				MsgSubscribe subscribe = new MsgSubscribe();
				subscribe.setId(UUID.randomUUID().toString());
				subscribe.setTitleId(title.getTitleId());
				subscribe.setTitleName(title.getTitleName());
				subscribe.setDeliveryWay(deliveryWays[i]);
				subscribe.setGroupId(orgId);
				subscribe.setGroupName(userService.getOrganization(orgId).getOrgName());
				subscribe.setFlag(YesNoEnum.YES.getCode());
				subscribe.setSubType(SubTypeEnum.ORG.getCode());
				msgSubMap.insertOrgMsgSubIfNotExists(subscribe);
			}
			for (int j = 0; j < roleIds.length; j++) {
				String roleId = roleIds[j];
				MsgSubscribe subscribe = new MsgSubscribe();
				subscribe.setId(UUID.randomUUID().toString());
				subscribe.setTitleId(title.getTitleId());
				subscribe.setTitleName(title.getTitleName());
				subscribe.setDeliveryWay(deliveryWays[i]);
				subscribe.setGroupId(roleId);
				subscribe.setGroupName(userService.getRole(roleId).getRoleName());
				subscribe.setFlag(YesNoEnum.YES.getCode());
				subscribe.setSubType(SubTypeEnum.ROLE.getCode());
				msgSubMap.insertRoleMsgSubIfNotExists(subscribe);
			}
		}
	}

	@Override
	@Transactional
	public void deleteTitleSubscribe(String titleId) {
		msgSubMap.deleteTitleAllSubscribe(titleId);
	}

}
