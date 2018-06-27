package com.message.ctrl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.message.entity.MsgList;
import com.message.service.IMsgService;
import com.message.util.DataGrid;
import com.message.util.DataGridResult;
import com.message.util.ResponseJSON;
import com.message.util.TransField2Dic;

/**
 * 主题推送明细管理器
 * 
 * @author hasee
 *
 */
@Controller
@RequestMapping("/msg/msgList")
@SuppressWarnings("unchecked")
public class MsgListCtrl {
	private Logger logger = Logger.getLogger(MsgListCtrl.class);
	@Autowired
	private IMsgService msgService;

	/**
	 * 分页查询：主题推送明细
	 * 
	 * @param list
	 * @param dataGrid
	 * @return
	 */
	@RequestMapping("/search")
	@ResponseBody
	public DataGridResult search(MsgList list, DataGrid dataGrid) {
		try {
			msgService.searchMsgList(list, dataGrid);
			TransField2Dic.transField2Dic(dataGrid.getDataGridResult().getRows());
		} catch (Exception e) {
			logger.error("获取推送明细列表失败", e);
		}
		return dataGrid.getDataGridResult();
	}

	@RequestMapping("/resend")
	@ResponseBody
	public ResponseJSON resend(String [] ids) {
		ResponseJSON rj = new ResponseJSON();
		try {
			msgService.resendMsg(ids);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("消息重发失败");
			logger.error("消息重发失败", e);
		}
		return rj;
	}
}
