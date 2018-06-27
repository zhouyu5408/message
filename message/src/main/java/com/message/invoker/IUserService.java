package com.message.invoker;

import java.util.List;

import com.message.entity.Organization;
import com.message.entity.Role;
import com.message.entity.SubscribeInfo;

/**
 * 获取平台中指定用户
 * 
 * @Date: 2017年12月29日 上午11:09:14 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public interface IUserService {
	/**
	 * 根据用户编号获取订阅用户所需要的信息
	 * 
	 * @author zhouyu
	 * @param userId
	 * @return
	 */
	SubscribeInfo getSubscribeInfo(String userId);

	/**
	 * 获取指定机构下的用户
	 * 
	 * @author zhouyu
	 * @param orgId
	 * @return
	 */
	List<SubscribeInfo> getUserListByOrg(String orgId);

	/**
	 * 获取指定角色的用户
	 * 
	 * @author zhouyu
	 * @param roleId
	 * @return
	 */
	List<SubscribeInfo> getUserListByRole(String roleId);

	/**
	 * 获取系统所有用户
	 * 
	 * @return
	 */
	List<SubscribeInfo> getSystemUsers();

	/**
	 * 获取系统机构以及机构树
	 * 
	 * @return
	 */
	List<Organization> getSystemOrganization();

	/**
	 * 获取系统角色
	 * 
	 * @return
	 */
	List<Role> getSystemRole();

	/**
	 * 获取系统机构信息
	 * 
	 * @param string
	 * @return
	 */
	Organization getOrganization(String orgId);

	/**
	 * 获取系统角色信息
	 * 
	 * @param roleId
	 * @return
	 */
	Role getRole(String roleId);
}
