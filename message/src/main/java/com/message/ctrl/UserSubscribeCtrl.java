package com.message.ctrl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.message.entity.MsgSubscribe;
import com.message.entity.SubscribeInfo;
import com.message.enums.SubTypeEnum;
import com.message.exception.BaseException;
import com.message.service.IMsgService;
import com.message.util.DataGrid;
import com.message.util.DataGridResult;
import com.message.util.ResponseJSON;
import com.message.util.TransField2Dic;

/**
 * 用户订阅关系控制器
 * 
 * @author hasee
 *
 */
@Controller
@RequestMapping("/msg/userSubscribe")
@SuppressWarnings("unchecked")
public class UserSubscribeCtrl {
	private Logger logger = Logger.getLogger(UserSubscribeCtrl.class);
	@Autowired
	private IMsgService msgService;

	/**
	 * 分页查询：用户订阅信息列表
	 * 
	 * @param group
	 * @param dataGrid
	 * @return
	 */
	@RequestMapping("/search")
	@ResponseBody
	public DataGridResult search(MsgSubscribe msgSubscribe, DataGrid dataGrid) {
		try {
			msgSubscribe.setSubType(SubTypeEnum.USER.getCode());
			msgService.searchGroupSubscribe(msgSubscribe, dataGrid);
			TransField2Dic.transField2Dic(dataGrid.getDataGridResult().getRows());
		} catch (Exception e) {
			logger.error("获取组订阅信息列表失败", e);
		}
		return dataGrid.getDataGridResult();
	}

	/**
	 * 新增用户订阅关系时，获取系统用户列表
	 * 
	 * @return
	 */
	@RequestMapping("/getSystemUser")
	@ResponseBody
	public ResponseJSON getSystemUser() {
		ResponseJSON rj = new ResponseJSON();
		try {
			List<SubscribeInfo> list = msgService.getSystemUser();
			rj.setData(list);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("获取系统用户列表失败");
			logger.error("获取系统用户列表失败", e);
		}
		return rj;
	}

	/**
	 * 新增用户订阅关系
	 * 
	 * @param subscribe
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public ResponseJSON addUserSubscribe(MsgSubscribe subscribe) {
		ResponseJSON rj = new ResponseJSON();
		try {
			msgService.saveUserMsgSubscribe(subscribe);
		} catch (BaseException e) {
			rj.setSuccess(false);
			rj.setErrMsg(e.getErrMsg());
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("新增用户订阅关系失败");
			logger.error("新增用户订阅关系失败", e);
		}
		return rj;
	}

	/**
	 * 根据主题编号和用户编号获取对应的订阅信息
	 * 
	 * @param subscribe
	 * @return
	 */
	@RequestMapping("/getUserSubscribeInfo")
	@ResponseBody
	public ResponseJSON getUserSubscribeInfo(MsgSubscribe subscribe) {
		ResponseJSON rj = new ResponseJSON();
		try {
			MsgSubscribe msgSubcribe = msgService.getUserSubscribeInfo(subscribe);
			rj.setData(msgSubcribe);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("获取用户订阅关系失败");
			logger.error("获取用户订阅关系失败", e);
		}
		return rj;
	}

	/**
	 * 修改用户订阅关系
	 * 
	 * @param subscribe
	 * @return
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public ResponseJSON upateUserSubscribe(MsgSubscribe subscribe) {
		ResponseJSON rj = new ResponseJSON();
		try {
			msgService.updateUserSubscribe(subscribe);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("修改订阅关系失败");
			logger.error("修改订阅关系失败", e);
		}
		return rj;
	}
}
