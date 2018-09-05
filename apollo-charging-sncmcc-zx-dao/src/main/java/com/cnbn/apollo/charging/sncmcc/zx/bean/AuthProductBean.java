/**
 * 
 */
package com.cnbn.apollo.charging.sncmcc.zx.bean;

import java.io.Serializable;

/**
 * @author DuQiyu
 *
 */
public class AuthProductBean implements Serializable, Comparable<AuthProductBean> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 价格（单位：分）
	 * */
	private int fee;

	/**
	 * 是否已购买（0：未购买 1：已购买）
	 * */
	private int isBuy;

	/**
	 * 产品信息描述
	 * */
	private String productDesc;

	/**
	 * 产品Id
	 * */
	private String productId;

	/**
	 * 产品名称
	 * */
	private String productName;

	/**
	 * 产品类型（0:包自然月 1:单点）
	 * */
	private int productType;

	/**
	 * 有效时长（单位：天，包自然月单位请用“月”代替）
	 * */
	private int rentalTerm;
	
	/**
	 * 产品优先级
	 * */
	private int priority;

	public AuthProductBean(int fee, int isBuy, String productDesc, String productId, String productName,
			int productType, int rentalTerm, int priority) {
		super();
		this.fee = fee;
		this.isBuy = isBuy;
		this.productDesc = productDesc;
		this.productId = productId;
		this.productName = productName;
		this.productType = productType;
		this.rentalTerm = rentalTerm;
		this.priority = priority;
	}

	public AuthProductBean() {
		super();
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public int getIsBuy() {
		return isBuy;
	}

	public void setIsBuy(int isBuy) {
		this.isBuy = isBuy;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductType() {
		return productType;
	}

	public void setProductType(int productType) {
		this.productType = productType;
	}

	public int getRentalTerm() {
		return rentalTerm;
	}

	public void setRentalTerm(int rentalTerm) {
		this.rentalTerm = rentalTerm;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	// 自定义排序方法：优先级一级排序，价格二级排序
	@Override
	public int compareTo(AuthProductBean o) {
		int result = this.priority - o.priority;
		if (0 == result) {
			result = this.fee - o.fee;
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AuthProductBean) {
			AuthProductBean o = (AuthProductBean)obj;
			if (this.priority == o.getPriority()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
