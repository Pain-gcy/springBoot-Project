/**
 * 
 */
package com.cnbn.apollo.charging.sncmcc.zx.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author DuQiyu
 *
 */
public class ProductPackage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 * */
	private int id;

	/**
	 * 产品包Id
	 * */
	private String productId;
	
	/**
	 * 服务编号Id
	 * */
	private String serviceId;

	/**
	 * 产品包名称
	 * */
	private String productName;

	/**
	 * 产品包类型（0:包月 1:单点）
	 * */
	private int productType;

	/**
	 * 产品包描述
	 * */
	private String productDesc;

	/**
	 * 产品有效期(单位:分钟)
	 * */
	private int periodValidity;

	/**
	 * 产品优先级
	 * */
	private int priority;
	
	/**
	 * 终端是否可见
	 * */
	private int isVisible;
	
	/**
	 * 是否有效(1:有效 0:无效)
	 * */
	private int isEffective;

	/**
	 * 产品实际价格(单位:分)
	 * */
	private int price;
	
	/**
	 * 是否自动续订
	 * */
	private int isReOrder;
	
	/**
	 * 更新时间
	 * */
	private Date updateTime;

	/**
	 * 创建时间
	 * */
	private Date createTime;

	public ProductPackage(int id, String productId, String serviceId, String productName, int productType,
			String productDesc, int periodValidity, int priority, int isVisible, int isEffective, int price,
			int isReOrder, Date updateTime, Date createTime) {
		super();
		this.id = id;
		this.productId = productId;
		this.serviceId = serviceId;
		this.productName = productName;
		this.productType = productType;
		this.productDesc = productDesc;
		this.periodValidity = periodValidity;
		this.priority = priority;
		this.isVisible = isVisible;
		this.isEffective = isEffective;
		this.price = price;
		this.isReOrder = isReOrder;
		this.updateTime = updateTime;
		this.createTime = createTime;
	}

	public ProductPackage() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public int getPeriodValidity() {
		return periodValidity;
	}

	public void setPeriodValidity(int periodValidity) {
		this.periodValidity = periodValidity;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(int isVisible) {
		this.isVisible = isVisible;
	}

	public int getIsEffective() {
		return isEffective;
	}

	public void setIsEffective(int isEffective) {
		this.isEffective = isEffective;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getIsReOrder() {
		return isReOrder;
	}

	public void setIsReOrder(int isReOrder) {
		this.isReOrder = isReOrder;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
}
