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
public class AuthBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String playUrl;
	
	private String phoneNumber;
	
	private List<AuthProductBean> productList;
	
	private String desc;
	
	private int result;

	public AuthBean(String playUrl, String phoneNumber, List<AuthProductBean> productList, String desc, int result) {
		super();
		this.playUrl = playUrl;
		this.phoneNumber = phoneNumber;
		this.productList = productList;
		this.desc = desc;
		this.result = result;
	}

	public AuthBean() {
		super();
	}

	public String getPlayUrl() {
		return playUrl;
	}

	public void setPlayUrl(String playUrl) {
		this.playUrl = playUrl;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<AuthProductBean> getProductList() {
		return productList;
	}

	public void setProductList(List<AuthProductBean> productList) {
		this.productList = productList;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
	
}
