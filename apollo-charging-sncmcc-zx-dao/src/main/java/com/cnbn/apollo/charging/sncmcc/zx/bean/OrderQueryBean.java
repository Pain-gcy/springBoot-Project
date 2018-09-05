/**
 * 
 */
package com.cnbn.apollo.charging.sncmcc.zx.bean;

import java.io.Serializable;

/**
 * @author DuQiyu
 *
 */
public class OrderQueryBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单号
	 * */
	private String orderId;
	
	/**
	 * 业务账号
	 * */
	private String userId;
	
	/**
	 * 产品Id
	 * */
	private String productId;

	/**
	 * 产品名称
	 * */
	private String productName;
	
	/**
	 * 产品类型（0:包自然月 1:单点 2:包时段）
	 * */
	private int productType;

	/**
	 * 支付方式
	 * */
	private int payType;

	/**
	 * 支付名称
	 * */
	private String payName;

	/**
	 * 频道id
	 * */
	private String chnId;
	
	/**
	 * 专辑Id，包月、包时段为0
	 * */
	private String albumId;

	/**
	 * tvId，包月、包时段为0
	 * */
	private String tvId;

	/**
	 * 产品价格
	 * */
	private int fee;

	/**
	 * 话费支付时使用的手机号
	 * */
	private String phoneNumber;

	/**
	 * 订单生效时间
	 * */
	private String effectiveTime;

	/**
	 * 订单失效时间
	 * */
	private String expiredTime;

	/**
	 * 是否续订（0:未续订 1:续订中 2:已退订）
	 * */
	private int reOrder;

	/**
	 * 订购时间
	 * */
	private String orderTime;

	public OrderQueryBean(String orderId, String userId, String productId, String productName, int productType,
			int payType, String payName, String chnId, String albumId, String tvId, int fee, String phoneNumber,
			String effectiveTime, String expiredTime, int reOrder, String orderTime) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.productId = productId;
		this.productName = productName;
		this.productType = productType;
		this.payType = payType;
		this.payName = payName;
		this.chnId = chnId;
		this.albumId = albumId;
		this.tvId = tvId;
		this.fee = fee;
		this.phoneNumber = phoneNumber;
		this.effectiveTime = effectiveTime;
		this.expiredTime = expiredTime;
		this.reOrder = reOrder;
		this.orderTime = orderTime;
	}

	public OrderQueryBean() {
		super();
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public int getProductType() {
		return productType;
	}

	public void setProductType(int productType) {
		this.productType = productType;
	}

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	public String getTvId() {
		return tvId;
	}

	public void setTvId(String tvId) {
		this.tvId = tvId;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(String expiredTime) {
		this.expiredTime = expiredTime;
	}

	public int getReOrder() {
		return reOrder;
	}

	public void setReOrder(int reOrder) {
		this.reOrder = reOrder;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getChnId() {
		return chnId;
	}

	public void setChnId(String chnId) {
		this.chnId = chnId;
	}
	
}
