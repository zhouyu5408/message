package com.message.util;

public class ResponseJSON {
	/**
	 * 是否成功
	 */
	private boolean success = true;
	/**
	 * 返回内容
	 */
	private Object data;
	/**
	 * 错误代码
	 */
	private String errCode;
	/**
	 * 错误信息
	 */
	private String errMsg;
	/**
	 * 重定向地址
	 */
	private String redirectAction;
	/**
	 * 日志记录
	 */
	private String logMsg;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getRedirectAction() {
		return redirectAction;
	}

	public void setRedirectAction(String redirectAction) {
		this.redirectAction = redirectAction;
	}

	public String getLogMsg() {
		return logMsg;
	}

	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
	}
}
