package com.message.ctrl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.message.entity.MsgSubscribe;
import com.message.enums.SubTypeEnum;
import com.message.exception.BaseException;
import com.message.service.IMsgService;
import com.message.util.DataGrid;
import com.message.util.DataGridResult;
import com.message.util.ResponseJSON;
import com.message.util.TransField2Dic;

/**
 * 组的订阅关系控制器
 * 
 * @author hasee
 *
 */
@Controller
@RequestMapping("/msg/groupSubscribe")
@SuppressWarnings("unchecked")
public class GroupSubscribeCtrl {
	private Logger logger = Logger.getLogger(GroupSubscribeCtrl.class);
	@Autowired
	private IMsgService msgService;

	/**
	 * 分页查询：组订阅信息列表
	 * 
	 * @param group
	 * @param dataGrid
	 * @return
	 */
	@RequestMapping("/search")
	@ResponseBody
	public DataGridResult search(MsgSubscribe msgSubscribe, DataGrid dataGrid) {
		try {
			msgSubscribe.setSubType(SubTypeEnum.GROUP.getCode());
			msgService.searchGroupSubscribe(msgSubscribe, dataGrid);
			TransField2Dic.transField2Dic(dataGrid.getDataGridResult().getRows());
		} catch (Exception e) {
			logger.error("获取组订阅信息列表失败", e);
		}
		return dataGrid.getDataGridResult();
	}

	/**
	 * 新增组订阅关系
	 * 
	 * @param subscribe
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public ResponseJSON addGroupSubscribe(MsgSubscribe subscribe) {
		ResponseJSON rj = new ResponseJSON();
		try {
			msgService.saveGroupMsgSubscribe(subscribe);
		} catch (BaseException e) {
			rj.setSuccess(false);
			rj.setErrMsg(e.getErrMsg());
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("新增组订阅关系失败");
			logger.error("新增组订阅关系失败", e);
		}
		return rj;
	}

	/**
	 * 根据主题编号和组编号获取对应的订阅信息
	 * 
	 * @param subscribe
	 * @return
	 */
	@RequestMapping("/getGroupSubscribeInfo")
	@ResponseBody
	public ResponseJSON getGroupSubscribeInfo(MsgSubscribe subscribe) {
		ResponseJSON rj = new ResponseJSON();
		try {
			MsgSubscribe msgSubcribe = msgService.getGroupSubscribeInfo(subscribe);
			rj.setData(msgSubcribe);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("获取组订阅关系失败");
			logger.error("获取组订阅关系失败", e);
		}
		return rj;
	}

	/**
	 * 删除选择的订阅关系
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ResponseJSON deleteMsgSubscribeByIds(String[] ids) {
		ResponseJSON rj = new ResponseJSON();
		try {
			msgService.deleteMsgSubscribe(ids);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("删除订阅关系失败");
			logger.error("删除订阅关系失败", e);
		}
		return rj;
	}

	/**
	 * 修改组订阅关系
	 * 
	 * @param subscribe
	 * @return
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public ResponseJSON upateGroupSubscribe(MsgSubscribe subscribe) {
		ResponseJSON rj = new ResponseJSON();
		try {
			msgService.updateGroupSubscribe(subscribe);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("修改订阅关系失败");
			logger.error("修改订阅关系失败", e);
		}
		return rj;
	}
}
