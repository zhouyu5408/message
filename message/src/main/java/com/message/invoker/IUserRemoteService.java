package com.message.invoker;

import java.util.List;

import com.message.entity.Organization;
import com.message.entity.Role;
import com.message.entity.SubscribeInfo;

/**
 * 获取指定的用户信息（该接口由调用者实现，通过Spring注入）
 * 
 * @Date: 2017年12月29日 上午11:13:01 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public interface IUserRemoteService {
	/**
	 * 根据用户编号获取用户信息（邮件/电话/用户状态/...）
	 * 
	 * @author zhouyu
	 * @param userId
	 * @return
	 */
	SubscribeInfo getUserInfo(String userId);

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
	 * 获取系统所有角色
	 * 
	 * @return
	 */
	List<Role> getSystemRole();

	/**
	 * 获取系统指定机构信息
	 * 
	 * @param orgId
	 * @return
	 */
	Organization getOrganization(String orgId);

	/**
	 * 获取系统指定角色信息
	 * 
	 * @param roleId
	 * @return
	 */
	Role getRole(String roleId);
}
