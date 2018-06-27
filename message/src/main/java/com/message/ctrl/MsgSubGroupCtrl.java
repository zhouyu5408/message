package com.message.ctrl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.message.entity.MsgSubGroup;
import com.message.exception.BaseException;
import com.message.service.IMsgService;
import com.message.util.DataGrid;
import com.message.util.DataGridResult;
import com.message.util.ResponseJSON;

/**
 * 订阅组管理控制器
 * 
 * @author zhouyu
 *
 */
@Controller
@RequestMapping("/msg/subGroup")
public class MsgSubGroupCtrl {
	private Logger logger = Logger.getLogger(MsgSubGroupCtrl.class);

	@Autowired
	private IMsgService msgService;

	/**
	 * 分页查询
	 * 
	 * @param subGroup
	 * @param dataGrid
	 * @return
	 */
	@RequestMapping("/search")
	@ResponseBody
	public DataGridResult search(MsgSubGroup subGroup, DataGrid dataGrid) {
		try {
			msgService.searchSubGroup(subGroup, dataGrid);
		} catch (Exception e) {
			logger.error("查询订阅组时异常", e);
		}
		return dataGrid.getDataGridResult();
	}

	/**
	 * 添加订阅组
	 * 
	 * @param subGroup
	 * @param userIds
	 * @param orgIds
	 * @param roleIds
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public ResponseJSON addSubGroup(@Valid MsgSubGroup subGroup, BindingResult result) {
		ResponseJSON rj = new ResponseJSON();
		StringBuilder sb = new StringBuilder();
		if (result.hasErrors()) {
			List<ObjectError> errorList = result.getAllErrors();
			for (ObjectError error : errorList) {
				sb.append(error.getDefaultMessage() + ";  ");
			}
			rj.setSuccess(false);
			rj.setErrMsg(sb.toString());
			return rj;
		}
		try {
			List<String> userList, orgList, roleList = null;
			if (null != subGroup.getUserArray() && subGroup.getUserArray().length > 0) {
				userList = Arrays.asList(subGroup.getUserArray());
				subGroup.setUserIds(userList);
			}
			if (null != subGroup.getOrgArray() && subGroup.getOrgArray().length > 0) {
				orgList = Arrays.asList(subGroup.getOrgArray());
				subGroup.setOrgIds(orgList);
			}
			if (null != subGroup.getRoleArray() && subGroup.getRoleArray().length > 0) {
				roleList = Arrays.asList(subGroup.getRoleArray());
				subGroup.setRoleIds(roleList);
			}
			msgService.insertSubGroup(subGroup);
		} catch (BaseException e) {
			rj.setSuccess(false);
			rj.setErrMsg(e.getErrMsg());
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("添加订阅组失败");
			logger.error("添加订阅组失败", e);
		}
		return rj;
	}

	/**
	 * 根据组编号删除订阅组已经订阅组的所有成员
	 * 
	 * @param subGroupId
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ResponseJSON delete(String subGroupId) {
		ResponseJSON rj = new ResponseJSON();
		try {
			msgService.deleteSubGroup(subGroupId);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("删除订阅组失败");
			logger.error("删除订阅组失败", e);
		}
		return rj;
	}

	/**
	 * 修改订阅组成员变量
	 * 
	 * @return
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public ResponseJSON modify(MsgSubGroup subGroup) {
		ResponseJSON rj = new ResponseJSON();
		try {
			msgService.updateSubGroup(subGroup);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("删除订阅组失败");
			logger.error("删除订阅组失败", e);
		}
		return rj;
	}

	/**
	 * 根据订阅组编号获取对应的订阅组信息
	 * 
	 * @param subGroupId
	 * @return
	 */
	@RequestMapping("/searchGroupInfo")
	@ResponseBody
	public ResponseJSON getGroupMember(String subGroupId) {
		ResponseJSON rj = new ResponseJSON();
		try {
			MsgSubGroup group = msgService.getGroupInfoByGroupId(subGroupId);
			rj.setData(group);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("获取订阅组信息失败");
			logger.error("获取订阅组信息失败", e);
		}
		return rj;
	}

	/**
	 * 获取系统成员信息
	 * 
	 * @return
	 */
	@RequestMapping("/searchSystemMembers")
	@ResponseBody
	public ResponseJSON getSystemMembers() {
		ResponseJSON rj = new ResponseJSON();
		try {
			Map<String, Object> members = msgService.getSystemMembers();
			rj.setData(members);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("获取系统成员信息失败");
			logger.error("获取系统成员信息失败", e);
		}
		return rj;
	}

	/**
	 * 获取所有的订阅组信息
	 * 
	 * @return
	 */
	@RequestMapping("/getAllGroup")
	@ResponseBody
	public ResponseJSON getAllSubGroup() {
		ResponseJSON rj = new ResponseJSON();
		try {
			List<MsgSubGroup> subGoups = msgService.getAllSubGroup();
			rj.setData(subGoups);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("获取订阅组信息列表失败");
			logger.error("获取订阅组信息列表失败", e);
		}
		return rj;
	}

}
