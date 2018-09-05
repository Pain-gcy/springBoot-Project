/**
 * 
 */
package com.cnbn.apollo.charging.sncmcc.zx.service;

/**
 * @author DuQiyu
 * 
 *         付费鉴权接口
 */
public interface AuthenticationService {

	/**
	 * 付费内容鉴权接口
	 * 
	 * @param mac
	 * @param userId
	 * @param contentId
	 * @param userToken
	 * 
	 * @return String
	 */
	String auth(String mac, String userId, String contentId, String userToken, String epgServer);
	
	/**
	 * 获取指定缓存数据
	 * @param key 缓存key
	 * @return String
	 * */
	String getKey(String key);
	
	/**
	 * 删除指定缓存数据
	 * @param key 缓存key
	 * @return String
	 * */
	long delKey(String key);

}
