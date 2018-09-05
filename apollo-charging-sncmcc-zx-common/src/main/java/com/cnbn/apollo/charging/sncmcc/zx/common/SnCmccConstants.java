/**
 * 
 */
package com.cnbn.apollo.charging.sncmcc.zx.common;

/**
 * @author DuQiyu
 *
 */
public class SnCmccConstants {
	
	/***************************银河鉴权相关code********************************/
	
	/**
	 * 鉴权成功
	 * */
	public static final int AUTH_RESULT_SUCCESS = 0;
	
	/**
	 * 鉴权失败
	 * */
	public static final int AUTH_RESULT_FAILED = 1;
	
	/***************************银河订单状态相关code****************************/
	/**
	 * 支付成功未续订
	 * */
	public static final int ORDER_STATUS_SUCCESS = 0;
	
	/*
	 * 成功且续订中
	 * */
	public static final int ORDER_STATUS_RE_ORDER= 1;
	
	/**
	 * 已退订
	 * */
	public static final int ORDER_STATUS_CANCEL_ORDER = 2;
	
	/**
	 * 未支付
	 * */
	public static final int ORDER_STATUS_NOT_PAY = 3;
	
	/**
	 * 失败
	 * */
	public static final int ORDER_STATUS_FAILED = 4;
	
	/**
	 * 暂停
	 * */
	public static final int ORDER_STATUS_SUSPENSION = 5;
	
	/**
	 * 支付异常（如访问订购接口超时，接口返回非HTTP 200等）
	 * */
	public static final int ORDER_STATUS_ERROR = -1;
	
	
	/**************************产品包有效性*******************************/
	/**
	 * 无效
	 * */
	public static final int IS_EFFECTIVE_FALSE = 0;
	
	/**
	 * 有效
	 * */
	public static final int IS_EFFECTIVE_TRUE = 1;
	
	/**************************产品包可见性********************************/
	/**
	 * 不可见
	 * */
	public static final int IS_VISIBLE_FALSE = 0;
	
	/**
	 * 可见
	 * */
	public static final int IS_VISIBLE_TRUE = 1;
	
	/**************************产品包续订属性*********************************/
	/**
	 * 不续订
	 * */
	public static final int IS_REORDER_FALSE = 0;
	
	/**
	 * 自动续订
	 * */
	public static final int IS_REORDER_TRUE = 1;
	
	/**
	 * 已退订
	 * */
	public static final int IS_REORDER_CANCELLED = 2;
	
	/****************************产品包类型******************************/
	/**
	 * 自然月(连续)包月
	 * */
	public final static int PRODUCT_TYPE_MONTH = 0;
	
	/**
	 * 单点
	 * */
	public final static int PRODUCT_TYPE_ONCE = 1;
	
	/**
	 * 包时段
	 * */
	public final static int PRODUCT_TYPE_PERIOD = 2;
	
	
	/****************************支付方式********************************/
	/**
	 * 手机话费
	 * */
	public final static int PAY_TYPE_PHONE_BILL = 0;
	
	/**
	 * 支付宝
	 * */
	public final static int PAY_TYPE_ALIPAY = 1;
	
	/**
	 * 微信
	 * */
	public final static int PAY_TYPE_WEIXIN = 2;
	
	/*************************第三方鉴权相关Code********************************/
	/**
	 * 成功
	 * */
	public static final String SUCCESS = "0";
	
	
	/*************************第三方其他相关code*********************************/
	
}
