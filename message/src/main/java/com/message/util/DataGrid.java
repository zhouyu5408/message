package com.message.util;

/**
 * 页面查询数据集合
 * 
 */
public class DataGrid {

	private int page = 1;// 当前页
	private int rows = 10;// 每页显示记录数
	private String sort = null;// 排序字段名
	private String order = "ASC"; // 排序方式

	private DataGridResult dataGridResult;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSort() {
		return sort;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public DataGridResult getDataGridResult() {
		return dataGridResult;
	}

	public void setDataGridResult(DataGridResult dataGridResult) {
		this.dataGridResult = dataGridResult;
	}

	public int getRows() {
		return rows;
	}
}
