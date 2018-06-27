package com.message.ctrl;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.message.entity.MsgTitle;
import com.message.entity.MsgTitleModel;
import com.message.entity.MsgTitleType;
import com.message.exception.BaseException;
import com.message.service.IMsgService;
import com.message.util.DataGrid;
import com.message.util.DataGridResult;
import com.message.util.ResponseJSON;

/**
 * 主题、主题订阅以及主题模版控制器
 * 
 * @author zhouyu
 *
 */
@Controller
@RequestMapping("/msg/title")
public class MsgTitleCtrl {
	private Logger logger = Logger.getLogger(MsgTitleCtrl.class);

	@Autowired
	private IMsgService msgService;

	/**
	 * 分页查询:主题信息
	 * 
	 * @param title
	 * @param dataGrid
	 * @return
	 */
	@RequestMapping("/search")
	@ResponseBody
	public DataGridResult search(MsgTitle title, DataGrid dataGrid) {
		try {
			msgService.searchMsgTitle(title, dataGrid);
		} catch (Exception e) {
			logger.error("分页查询失败", e);
		}
		return dataGrid.getDataGridResult();
	}

	/**
	 * 获取主题类型
	 * 
	 * @return
	 */
	@RequestMapping("/titleType")
	@ResponseBody
	public ResponseJSON getTiltleType() {
		ResponseJSON rj = new ResponseJSON();
		try {
			List<MsgTitleType> list = msgService.getTitleTypeList();
			rj.setData(list);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("获取主题类型失败");
			logger.error("获取主题类型失败", e);
		}
		return rj;
	}

	/**
	 * 获取主题类型下的主题列表
	 * 
	 * @param typeId
	 * @return
	 */
	@RequestMapping("/getTitleByType")
	@ResponseBody
	public ResponseJSON getTitleByType(String typeId) {
		ResponseJSON rj = new ResponseJSON();
		try {
			List<MsgTitle> titleList = msgService.getTitleByType(typeId);
			rj.setData(titleList);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("获取主题列表失败");
			logger.error("获取主题类型[" + typeId + "]下的主题列表失败", e);
		}
		return rj;
	}

	/**
	 * 新增主题，添加对应的模版
	 * 
	 * @param title
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public ResponseJSON addTitle(@Valid MsgTitle title, BindingResult result) {
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
			msgService.saveTitle(title);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("新增主题失败");
			logger.error("新增主题【 " + title.getId() + "--" + title.getTitleName() + " 】", e);
		}
		return rj;
	}

	/**
	 * 获取指定的主题信息
	 * 
	 * @return
	 */
	@RequestMapping("/getAppointedTitle")
	@ResponseBody
	public ResponseJSON getAppointedTitle(String id) {
		ResponseJSON rj = new ResponseJSON();
		try {
			MsgTitle title = msgService.getAppointedTitle(id);
			rj.setData(title);
		} catch (BaseException e) {
			rj.setSuccess(false);
			rj.setErrMsg(e.getErrMsg());
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("获取主题信息失败");
			logger.error("获取指定编号为[" + id + "]主题信息失败", e);
		}
		return rj;
	}

	/**
	 * 修改主题和主题模版
	 * 
	 * @param title
	 * @return
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public ResponseJSON modifyTitle(MsgTitle title) {
		ResponseJSON rj = new ResponseJSON();
		try {
			msgService.updateTilteAndModel(title);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("修改主题信息失败");
			logger.error("修改主题信息失败", e);
		}
		return rj;
	}

	/**
	 * 获取所有主题信息
	 * 
	 * @return
	 */
	@RequestMapping("/getAllTitle")
	@ResponseBody
	public ResponseJSON getAllTitle() {
		ResponseJSON rj = new ResponseJSON();
		try {
			List<MsgTitle> titles = msgService.getAllTitle();
			rj.setData(titles);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("获取主题列表失败");
			logger.error("获取主题列表失败", e);
		}
		return rj;
	}

	/**
	 * 删除主题以及主题对应的模版和附件
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ResponseJSON deleteTitleById(String[] ids) {
		ResponseJSON rj = new ResponseJSON();
		try {
			msgService.batchDeleteTitle(ids);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("删除主题失败");
			logger.error("删除主题失败", e);
		}
		return rj;
	}

	/**
	 * 根据主题新增订阅关系
	 * 
	 * @return
	 */
	@RequestMapping("/addSubscribeTitle")
	@ResponseBody
	public ResponseJSON addSubscribeTitle() {
		ResponseJSON rj = new ResponseJSON();
		try {

		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("新增主题订阅关系失败");
			logger.error("新增主题订阅关系失败", e);
		}
		return rj;
	}

	/**
	 * 根据订阅主题编号和订阅方式获取指定的模版
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/getModel")
	@ResponseBody
	public ResponseJSON getModelByTitleAndDeliveyway(MsgTitleModel model) {
		ResponseJSON rj = new ResponseJSON();
		try {
			MsgTitleModel titleModel = msgService.getTitleModel(model.getTitleId(), model.getDeliveryWay());
			rj.setData(titleModel);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("获取指定模版失败");
			logger.error("获取主题编号[" + model.getTitleId() + "]，订阅方式[" + model.getDeliveryWay() + "]失败", e);
		}
		return rj;
	}

	/**
	 * 保存上传后的文件key
	 * 
	 * @param fileKey
	 * @return
	 */
	@RequestMapping("/saveFileKey")
	@ResponseBody
	public ResponseJSON saveFileKey(String titleId, String deliveryWay, String fileKey) {
		ResponseJSON rj = new ResponseJSON();
		try {
			msgService.saveMsgTitleModelAttach(titleId, deliveryWay, fileKey);
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setErrMsg("保存附件失败");
			logger.error("保存主题编号[" + titleId + "]订阅方式[" + deliveryWay + "]文件key[" + fileKey + "]失败", e);
		}
		return rj;
	}
}
