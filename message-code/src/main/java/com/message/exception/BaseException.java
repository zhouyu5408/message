package com.message.exception;

public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BaseException(String errMsg, String errCode) {
		this.errMsg = errMsg;
		this.errCode = errCode;
	}

	public BaseException(String errMsg) {
		this.errMsg = errMsg;
	}

	private String errMsg;
	private String errCode;

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
}
