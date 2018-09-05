/**
 * 
 */
package com.cnbn.apollo.charging.sncmcc.zx.service;

/**
 * @author DuQiyu
 * 
 *         订购记录相关接口
 */
public interface OrderQueryService {

	/**
	 * 订购记录查询接口
	 * 
	 * @param mac 机顶盒mac
	 * @param userId 业务子账号（如果没有，请填写宽带账号）
	 * @param userToken token
	 * @param epgServer 调度服务器地址
	 * @param isEffective 有效(1)/无效(0)订单查询
	 * @param pageNo 第几页数据
	 * @param pageSize 单页大小
	 * @return String
	 */
	String orderRecordQuery(String mac, String userId, String userToken, String epgServer, int isEffective, int pageNo, int pageSize);

	/**
	 * 订单状态查询
	 * 
	 * @param mac 机顶盒mac
	 * @param userName 宽带账号
	 * @param userId 业务子账号（如果没有，请填写宽带账号）
	 * @param orderId 订单id
	 * @return String
	 */
	String payResultQuery(String mac, String userName, String userId, String orderId, String epgServer);
}
