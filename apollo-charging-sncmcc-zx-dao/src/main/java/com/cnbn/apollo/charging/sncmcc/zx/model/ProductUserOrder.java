package com.cnbn.apollo.charging.sncmcc.zx.model;

import java.io.Serializable;
import java.util.Date;

public class ProductUserOrder implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 * */
	private int id;

	/**
	 * (宽带)业务账号
	 * */
	private String userId;

	/**
	 * 订单号
	 * */
	private String orderId;

	/**
	 * 业务流水号
	 * */
	private String transId;

	/**
	 * 产品Id
	 * */
	private String productId;

	/**
	 * 产品名称
	 * */
	private String productName;

	/**
	 * 活动Id
	 * */
	private String promotionId;

	/**
	 * 活动名称
	 * */
	private String promotionName;

	/**
	 * 活动类型
	 * */
	private String promotionType;

	/**
	 * 内容id
	 * */
	private String contentId;

	/**
	 * 订购手机号
	 * */
	private String phoneNumber;

	/**
	 * 订购价格
	 * */
	private Integer price;

	/**
	 * 订购数（暂不用）
	 * */
	private Integer productCount;

	/**
	 * 支付方式
	 * */
	private Integer payMode;

	/**
	 * 订购类型（0:包月 1:单点）
	 * */
	private Integer orderType;

	/**
	 * 机顶盒MAC
	 * */
	private String mac;

	/**
	 * 频道Id
	 * */
	private String chnId;
	
	/**
	 * 专辑id
	 * */
	private String albumId;

	/**
	 * 专辑名称
	 * */
	private String albumName;

	/**
	 * 剧集Id
	 * */
	private String tvId;

	/**
	 * 剧集名称
	 * */
	private String tvName;

	/**
	 * 第几集
	 * */
	private Integer tvSet;

	/**
	 * 生效时间
	 * */
	private Date effectiveTime;

	/**
	 * 失效时间
	 * */
	private Date expiredTime;

	/**
	 * 支付时间
	 * */
	private Date payTime;

	/**
	 * 0:支付成功未续订 1:成功且续订中 2:已退订 3:未支付 4:失败 5:暂停
	 * */
	private Integer status;

	/**
	 * 第三方支付二维码
	 * */
	private String qrCode;

	private Date createTime;

	private Date updateTime;

	public ProductUserOrder(int id, String userId, String orderId, String transId, String productId, String productName,
			String promotionId, String promotionName, String promotionType, String contentId, String phoneNumber,
			Integer price, Integer productCount, Integer payMode, Integer orderType, String mac, String chnId,
			String albumId, String albumName, String tvId, String tvName, Integer tvSet, Date effectiveTime,
			Date expiredTime, Date payTime, Integer status, String qrCode, Date createTime, Date updateTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.orderId = orderId;
		this.transId = transId;
		this.productId = productId;
		this.productName = productName;
		this.promotionId = promotionId;
		this.promotionName = promotionName;
		this.promotionType = promotionType;
		this.contentId = contentId;
		this.phoneNumber = phoneNumber;
		this.price = price;
		this.productCount = productCount;
		this.payMode = payMode;
		this.orderType = orderType;
		this.mac = mac;
		this.chnId = chnId;
		this.albumId = albumId;
		this.albumName = albumName;
		this.tvId = tvId;
		this.tvName = tvName;
		this.tvSet = tvSet;
		this.effectiveTime = effectiveTime;
		this.expiredTime = expiredTime;
		this.payTime = payTime;
		this.status = status;
		this.qrCode = qrCode;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public ProductUserOrder() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
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

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public Integer getPayMode() {
		return payMode;
	}

	public void setPayMode(Integer payMode) {
		this.payMode = payMode;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public String getTvId() {
		return tvId;
	}

	public void setTvId(String tvId) {
		this.tvId = tvId;
	}

	public String getTvName() {
		return tvName;
	}

	public void setTvName(String tvName) {
		this.tvName = tvName;
	}

	public Integer getTvSet() {
		return tvSet;
	}

	public void setTvSet(Integer tvSet) {
		this.tvSet = tvSet;
	}

	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getChnId() {
		return chnId;
	}

	public void setChnId(String chnId) {
		this.chnId = chnId;
	}
	
}
