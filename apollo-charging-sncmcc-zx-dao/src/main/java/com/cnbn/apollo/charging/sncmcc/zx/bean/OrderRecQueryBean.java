/**
 * 
 */
package com.cnbn.apollo.charging.sncmcc.zx.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author DuQiyu
 *
 */
public class OrderRecQueryBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 第几页
	 * */
	private int pageNo;
	
	/**
	 * 分页大小
	 * */
	private int pageSize;

	/**
	 * 总记录条数
	 * */
	private int total;

	/**
	 * 
	 * */
	private List<OrderQueryBean> list;

	public OrderRecQueryBean(int pageNo, int pageSize, int total, List<OrderQueryBean> list) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.total = total;
		this.list = list;
	}

	public OrderRecQueryBean() {
		super();
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<OrderQueryBean> getList() {
		return list;
	}

	public void setList(List<OrderQueryBean> list) {
		this.list = list;
	}

}
