package com.message.ctrl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.message.entity.MsgSubscribe;
import com.message.entity.Organization;
import com.message.enums.SubTypeEnum;
import com.message.exception.BaseException;
import com.message.service.IMsgService;
import com.message.util.DataGrid;
import com.message.util.DataGridResult;
import com.message.util.ResponseJSON;
import com.message.util.TransField2Dic;

/**
 * 机构的订阅关系控制器
 * 
 * @author hasee
 *
 */
@Controller
@RequestMapping("/msg/orgSubscribe")
@SuppressWarnings("unchecked")
public class OrgSubscribeCtrl {
	private Logger logger = Logger.getLogger(OrgSubscribeCtrl.class);
	@Autowired
	private IMsgService msgService;

	/**
	 * 分页查询：机构订阅信息列表
	 * 
	 * @param group
	 * @param dataGrid
	 * @return
	 */
	@RequestMapping("/search")
	@ResponseBody
	public DataGridResult search(MsgSubscribe msgSubscribe, DataGrid dataGrid) {
		try {
			msgSubscribe.setSubType(SubTypeEnum.ORG.getCode());
			msgService.searchGroupSubscribe(msgSubscribe, dataGrid);
			TransField2Dic.transField2Dic(dataGrid.getDataGridResult().getRows());
		} catch (Exception e) {
			logger.error("获取机构订阅信息列表失败", e);
		}
		return dataGrid.getDataGridResult();
	}

	/**
	 * 添加机构订阅关系时，获取系统的机构列表
	 * 
	 * @return
	 */
	@RequestMapping("/getSystemOrg")
	@ResponseBody
	public ResponseJSON getSystemOrg() {
		ResponseJSON rj = new ResponseJSON();
		try {
			List<Organization> list = msgService.getSystemOrg();
			rj.setData(list);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("获取系统机构列表失败");
			logger.error("获取系统机构列表失败", e);
		}
		return rj;
	}

	/**
	 * 新增机构订阅关系
	 * 
	 * @param subscribe
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public ResponseJSON addOrgSubscribe(MsgSubscribe subscribe) {
		ResponseJSON rj = new ResponseJSON();
		try {
			msgService.saveOrgMsgSubscribe(subscribe);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("新增机构订阅关系失败");
			logger.error("新增机构订阅关系失败", e);
		}
		return rj;
	}

	/**
	 * 根据主题编号和机构编号获取对应的订阅信息
	 * 
	 * @param subscribe
	 * @return
	 */
	@RequestMapping("/getOrgSubscribeInfo")
	@ResponseBody
	public ResponseJSON getOrgSubscribeInfo(MsgSubscribe subscribe) {
		ResponseJSON rj = new ResponseJSON();
		try {
			MsgSubscribe msgSubcribe = msgService.getOrgSubscribeInfo(subscribe);
			rj.setData(msgSubcribe);
		} catch (BaseException e) {
			rj.setSuccess(false);
			rj.setErrMsg(e.getErrMsg());
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("获取机构订阅关系失败");
			logger.error("获取机构订阅关系失败", e);
		}
		return rj;
	}

	/**
	 * 修改机构订阅信息
	 * 
	 * @param subscribe
	 * @return
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public ResponseJSON upateOrgSubscribe(MsgSubscribe subscribe) {
		ResponseJSON rj = new ResponseJSON();
		try {
			msgService.updateOrgSubscribe(subscribe);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("修改订阅关系失败");
			logger.error("修改订阅关系失败", e);
		}
		return rj;
	}
}
