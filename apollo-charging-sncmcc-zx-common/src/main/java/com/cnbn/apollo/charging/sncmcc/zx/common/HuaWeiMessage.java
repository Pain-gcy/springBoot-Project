/**
 * 
 */
package com.cnbn.apollo.charging.sncmcc.zx.common;


import com.alibaba.fastjson.JSON;
import com.cnbn.gitv.common.HttpClientUtil;
import org.apache.http.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author DuQiyu
 *	
 *	 华为平台增值业务接口调用
 */
public abstract class HuaWeiMessage {

	private static final Logger logger = LoggerFactory.getLogger(HuaWeiMessage.class);
	/**
	 * 鉴权
	 * */
	private final static String AUTHORIZE = "http://{0}/EPG/interEpg/user/default/authorize";
	
	/**
	 * 订购
	 * */
	private final static String SUBSCRIBE = "http://{0}/EPG/interEpg/user/default/subscribe";
	
	/**
	 * 退订
	 * */
	private final static String CANCELSUBSCRIBE = "http://{0}/EPG/interEpg/user/default/cancelsubscribe";
	
	/**
	 * 订购记录查询
	 * */
	private final static String PRODUCTS = "http://{0}/EPG/interEpg/user/default/products";
	
	/**
	 * 鉴权请求
	 * @param userId - 用户登录账号（非必填项，不属于OPEN API接口文档标准参数，传递该值只是为了标识用户账号信息，下同）
	 * @param cid - 内容Id
	 * @param userToken - 登录token
	 * @param epgServer - 客户端开机调度的服务器地址
	 * @return String
	 * */
	public static String authorize(String userId, String cid, String userToken, String epgServer) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("userid", userId);
		params.put("cid", cid);
		params.put("supercid", "-2");
		params.put("businessType", "1");
		params.put("tid", "-1");
		params.put("playType", "1");
		params.put("contentType", "0");
		params.put("idflag", "1");
		String url = MessageFormat.format(AUTHORIZE, epgServer);
		logger.debug("req:{}", JSON.toJSONString(params));
		return HttpClientUtil.POST.applicationJson(url, headers(userToken), JSON.toJSONString(params), Consts.UTF_8).getContent();
	}
	
	/**
	 * 订购请求
	 * @param userId - 用户登录账号
	 * @param pid - 产品包Id
	 * @param sid - 服务Id
	 * @param cid - 内容Id
	 * @param continueType - 是否续订（0:不续订 1:续订，注意：连续包月产品这里一定要传1，否则会被判断为30天有效期）
	 * @param orderPrice - 只是为了标识价格，实际扣费已华为系统配置为主
	 * @param userToken - 登录token
	 * @param epgServer - 客户端开机调度的服务器地址
	 * @return String
	 * */
	public static String subscribe(String userId, String pid, String sid, String cid, String continueType, String orderPrice, String userToken, String epgServer, String serStarttime) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("userid", userId);
		params.put("pid", pid);
		params.put("sid", sid);
		params.put("cid", cid);
		params.put("orderPrice", orderPrice);
		params.put("continueType", continueType);
		params.put("supercid", "-2");
		params.put("playType", "1");
		params.put("orderMode", "1");
		params.put("contentType", "0");
		params.put("serStarttime", serStarttime);
		params.put("serEndtime", "null");
		params.put("idflag", "1");
		params.put("businessType", "1");
		String url = MessageFormat.format(SUBSCRIBE, epgServer);
		logger.debug("req:{}", JSON.toJSONString(params));
		return HttpClientUtil.POST.applicationJson(url, headers(userToken), JSON.toJSONString(params), Consts.UTF_8).getContent();
	}

	/**
	 * 退订请求
	 * @param userId - 用户登录账号
	 * @param pid - 产品包Id
	 * @param sid - 服务Id
	 * @param cid - 内容Id
	 * @param serStarttime - 生效时间，必填参数（注意：必须与订购记录查询中的生效时间一致，否则会造成退订失败）
	 * @param serEndtime - 失效时间
	 * @param userToken - 登录token
	 * @param epgServer - 客户端开机调度的服务器地址
	 * @return String
	 * */
	public static String cancelsubscribe(String userId, String pid, String sid, String cid, String serStarttime, String serEndtime, String userToken, String epgServer) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("userid", userId);
		params.put("pid", pid);
		params.put("sid", sid);
		params.put("cid", cid);
		params.put("serStarttime", serStarttime);
		params.put("serEndtime", serEndtime);
		params.put("contentType", "0");
		params.put("continueType", "1");
		params.put("idflag", "1");
		params.put("businessType", "1");
		String url = MessageFormat.format(CANCELSUBSCRIBE, epgServer);
		logger.debug("req:{}", JSON.toJSONString(params));
		return HttpClientUtil.POST.applicationJson(url, headers(userToken), JSON.toJSONString(params), Consts.UTF_8).getContent();
	}
	
	/**
	 * 订购记录查询(GET请求)
	 * @param userToken - 登录token
	 * @param epgServer - 客户端开机调度的服务器地址
	 * @return String - XML数据
	 * */
	public static String products(String userToken, String epgServer) throws Exception {
		String url = MessageFormat.format(PRODUCTS, epgServer);
		return  HttpClientUtil.GET.invoke(url,headers(userToken), Consts.UTF_8).getContent();
	}
	
	private static Map<String, String> headers(String userToken) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", userToken);
		return headers;
	}
}
