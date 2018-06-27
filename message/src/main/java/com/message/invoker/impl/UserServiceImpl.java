package com.message.invoker.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.message.entity.Organization;
import com.message.entity.Role;
import com.message.entity.SubscribeInfo;
import com.message.invoker.IUserRemoteService;
import com.message.invoker.IUserService;
import com.message.spring.SpringManager;

@Service
public class UserServiceImpl implements IUserService {
	public IUserRemoteService getUserService() {
		return SpringManager.getBeanByType(IUserRemoteService.class);
	}

	public SubscribeInfo getSubscribeInfo(String userId) {
		return getUserService().getUserInfo(userId);
	}

	public List<SubscribeInfo> getUserListByOrg(String orgId) {
		return getUserService().getUserListByOrg(orgId);
	}

	public List<SubscribeInfo> getUserListByRole(String roleId) {
		return getUserService().getUserListByRole(roleId);
	}

	@Override
	public List<SubscribeInfo> getSystemUsers() {
		return getUserService().getSystemUsers();
	}

	@Override
	public List<Organization> getSystemOrganization() {
		return getUserService().getSystemOrganization();
	}

	@Override
	public List<Role> getSystemRole() {
		return getUserService().getSystemRole();
	}

	@Override
	public Organization getOrganization(String orgId) {
		return getUserService().getOrganization(orgId);
	}

	@Override
	public Role getRole(String roleId) {
		return getUserService().getRole(roleId);
	}

}
