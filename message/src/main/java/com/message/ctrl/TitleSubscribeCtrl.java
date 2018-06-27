package com.message.ctrl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.message.entity.MsgTitle;
import com.message.exception.BaseException;
import com.message.service.IMsgService;
import com.message.util.DataGrid;
import com.message.util.DataGridResult;
import com.message.util.ResponseJSON;
import com.message.util.TransField2Dic;

/**
 * 主题订阅（从主题的方向去配置订阅关系）
 * 
 * @author hasee
 *
 */
@Controller
@RequestMapping("/msg/titleSubscribe")
@SuppressWarnings("unchecked")
public class TitleSubscribeCtrl {
	private Logger logger = Logger.getLogger(TitleSubscribeCtrl.class);
	@Autowired
	private IMsgService msgService;

	/**
	 * 分页查询：被订阅的主题
	 * 
	 * @param group
	 * @param dataGrid
	 * @return
	 */
	@RequestMapping("/search")
	@ResponseBody
	public DataGridResult search(MsgTitle title, DataGrid dataGrid) {
		try {
			msgService.searchTitleSubscribe(title, dataGrid);
			TransField2Dic.transField2Dic(dataGrid.getDataGridResult().getRows());
		} catch (Exception e) {
			logger.error("获取主题订阅信息列表失败", e);
		}
		return dataGrid.getDataGridResult();
	}

	/**
	 * 根据主题新增订阅关系
	 * 
	 * @param title
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public ResponseJSON addTitleSubscribe(MsgTitle title) {
		ResponseJSON rj = new ResponseJSON();
		try {
			msgService.saveTitleSubscribe(title);
		} catch (BaseException e) {
			rj.setSuccess(false);
			rj.setErrMsg(e.getErrMsg());
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("新增主题订阅关系失败");
			logger.error("新增主题编号[" + title.getTitleId() + "]订阅关系失败", e);
		}
		return rj;
	}

	/**
	 * 删除主题订阅关系
	 * 
	 * @param titleId
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ResponseJSON deleteTitleSubscribe(String titleId) {
		ResponseJSON rj = new ResponseJSON();
		try {
			msgService.deleteTitleSubscribe(titleId);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("删除主题订阅关系失败");
			logger.error("删除主题编号[" + titleId + "]订阅关系失败", e);
		}
		return rj;
	}

	@RequestMapping("/getTitleMember")
	@ResponseBody
	public ResponseJSON getTitleSubscribeMemberByTitleId(String titleId) {
		ResponseJSON rj = new ResponseJSON();
		try {
			MsgTitle title = msgService.getTitleSubscribeByTitleId(titleId);
			rj.setData(title);
		} catch (BaseException e) {
			rj.setSuccess(false);
			rj.setErrMsg(e.getErrMsg());
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("获取主题信息失败");
			logger.error("获取主题编号[" + titleId + "]信息失败", e);
		}
		return rj;
	}

	/**
	 * 修改主题的订阅关系
	 * 
	 * @param titleId
	 * @return
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public ResponseJSON modifyTitleSubscribe(MsgTitle title) {
		ResponseJSON rj = new ResponseJSON();
		try {
			msgService.updateTitleSubscribe(title);
		} catch (BaseException e) {
			rj.setSuccess(false);
			rj.setErrMsg(e.getErrMsg());
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("修改主题订阅关系失败");
			logger.error("修改主题编号[" + title.getTitleId() + "]订阅关系失败", e);
		}
		return rj;
	}
}
