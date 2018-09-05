/**
 * 
 */
package com.cnbn.apollo.charging.sncmcc.zx.service;

/**
 * @author DuQiyu
 * 
 *         订购业务相关接口
 */
public interface OrderService {

	/**
	 * 订购接口
	 * 
	 * @param mac
	 * @param userId
	 * @param phoneNumber
	 * @param verifyCode
	 * @param productId
	 * @param contentId
	 * @param payType
	 * @param userToken
	 * @param albumId
	 * @param albumName
	 * @param tvId
	 * @param tvName
	 * @param epgServer
	 * @return String
	 */
	String order(String mac, String userId, String phoneNumber, String verifyCode, String productId, String contentId, int payType, String userToken, String chnId, String albumId, String albumName, String tvId, String tvName, String epgServer);

	/**
	 * 验证码发送接口
	 * 
	 * @param mac
	 * @param phoneNumber
	 * @param content
	 * @param userToken
	 * @return String
	 */
	String sendSms(String mac, String userId, String phoneNumber, String content, String userToken);

	/**
	 * 计费标识验证接口
	 * 
	 * @param mac
	 * @param userId
	 * @param phoneNumber
	 * @param verifyCode
	 * @param userToken
	 * @return String
	 */
	String phoneConfirm(String mac, String userId, String phoneNumber, String verifyCode, String userToken);

	/**
	 * 退订接口（仅连续包月使用）
	 *
	 * @param mac
	 * @param userId
	 * @param productId
	 * @param effectiveTime
	 * @param userToken
	 * @param epgServer
	 * @return String
	 */
	String cancelOrder(String mac, String userId, String productId, String effectiveTime, String userToken, String epgServer);

}
