package com.message.util;

import java.util.List;

@SuppressWarnings("rawtypes")
public class DataGridResult {
	private List rows;
	private long total;// 总记录数

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
}
