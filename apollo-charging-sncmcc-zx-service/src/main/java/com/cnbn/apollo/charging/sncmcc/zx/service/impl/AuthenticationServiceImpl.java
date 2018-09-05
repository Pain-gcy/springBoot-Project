/**
 * 
 */
package com.cnbn.apollo.charging.sncmcc.zx.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cnbn.apollo.charging.sncmcc.zx.bean.AuthBean;
import com.cnbn.apollo.charging.sncmcc.zx.bean.AuthProductBean;
import com.cnbn.apollo.charging.sncmcc.zx.common.HuaWeiMessage;
import com.cnbn.apollo.charging.sncmcc.zx.common.SnCmccConstants;
import com.cnbn.apollo.charging.sncmcc.zx.service.AuthenticationService;
import com.cnbn.gitv.common.Constants;
import com.cnbn.gitv.common.JsonUtils;
import com.cnbn.gitv.common.RedisPoolUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author guochunyuan
 *
 */
@Service
public class AuthenticationServiceImpl extends AbstractService implements AuthenticationService {

	private final static  Logger LOG = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
	
	@Override
	public String auth(String mac, String userId, String contentId, String userToken, String epgServer) {
		String result = StringUtils.EMPTY;
		try {
			// 调用华为鉴权接口
			String resp = HuaWeiMessage.authorize(userId, contentId, userToken, epgServer);
			LOG.info("mac:{}, userId:{}, resp:{}", mac, userId, resp);
			if (StringUtils.isEmpty(resp)) {
				return JsonUtils.STATUS_ERROR(Constants.REMOTE_API_EXCEPTION, "请求华为鉴权接口异常。");
			}
			JSONObject jObj = JSON.parseObject(resp);
			String returnCode = jObj.getString("returncode");
			if (SnCmccConstants.SUCCESS.equals(returnCode)) { 
				// 鉴权成功
				result = this.authSuccess(returnCode, jObj);
			} else {
				//签权失败 需要购买；
				//判断该用户是否是黑名单；2018年5月23日17:00:27
				String blackUser = isBlackUser(mac);
				if (null != blackUser){
					return blackUser;
				}
				// 鉴权失败
				result = this.authFailed(returnCode, userId, jObj);
			}
		} catch (Exception e) {
			result = JsonUtils.STATUS_ERROR(Constants.UNKNOWN_EXCEPTION, "鉴权未知异常。");
			LOG.error("auth error.", e);
		}
		return result;
	}

	@Override
	public String getKey(String key) {
		String result = StringUtils.EMPTY;
		try {
			result = RedisPoolUtils.get(jedisSentinelPool,key);
		} catch (Exception e) {
			LOG.error("get key error.", e);
		}
		return result;
	}

	@Override
	public long delKey(String key) {
		long result = 0;
		try {
			result = RedisPoolUtils.evict(jedisSentinelPool,key);
		} catch (Exception e) {
			LOG.error("del key error.", e);
		}
		return result;
	}
	/**
	 * 0表示用户是酒店或者政企
	 * 1表示用户是家庭普通用户
	 * @param mac
	 * @return
	 */
	private String isBlackUser(String mac){
		String result = super.checkUserid(mac.replace(":","").toUpperCase());
	    if (StringUtils.equals("0",result)){
			return JsonUtils.STATUS_ERROR(Constants.USER_NOT_EFFECTIVE, "用户是酒店或政企用户。");
		}else if(StringUtils.equals("1",result)){
			return JsonUtils.STATUS_ERROR(Constants.USER_NOT_EFFECTIVE, "用户是黑名单用户。");
		}
		return null;
	}
	/**
	 * 鉴权成功
	 * 
	 * */
	private String authSuccess(String returnCode, JSONObject jObj) {
		String result = StringUtils.EMPTY;
		try { 
			AuthBean authBean = new AuthBean();
			authBean.setResult(SnCmccConstants.AUTH_RESULT_SUCCESS);
			authBean.setDesc(jObj.getString("description") + " returncode:" + returnCode);
			JSONArray jUrls = jObj.getJSONArray("urls");
			if (null == jUrls || jUrls.size() == 0) { 
				// 鉴权成功，无播放地址，判断为异常报文，返回错误结果
				return JsonUtils.STATUS(Constants.REMOTE_API_EXCEPTION, authBean, "playurl is empty.");
			}
			JSONObject jUrl = (JSONObject)jUrls.get(0);
			String playUrl = jUrl.getString("playurl");
			if (StringUtils.isEmpty(playUrl)) {
				// 鉴权成功，无播放地址，判断为异常报文，返回错误结果
				return JsonUtils.STATUS(Constants.REMOTE_API_EXCEPTION, authBean, "playurl is empty.");
			}
			authBean.setPlayUrl(playUrl);
			result = JsonUtils.STATUS_OK(authBean, "");
		} catch (Exception e) {
			result = JsonUtils.STATUS_ERROR(Constants.UNKNOWN_EXCEPTION, "鉴权未知异常。");
			LOG.error("authSuccess error.", e);
		}
		return result;
	}
	
	/**
	 * 鉴权失败
	 * */
	private String authFailed(String returnCode, String userId, JSONObject jObj) {
		String result = StringUtils.EMPTY;
		try {
			AuthBean authBean = new AuthBean();
			authBean.setResult(SnCmccConstants.AUTH_RESULT_FAILED);
			authBean.setDesc(jObj.getString("description") + " returncode:" + returnCode);
//			JSONArray jUrls = jObj.getJSONArray("urls");
//			if (null == jUrls || jUrls.size() == 0) { 
				// 鉴权失败且无播放地址，判断为异常报文，返回错误结果
//				return JsonUtils.STATUS(Constants.REMOTE_API_EXCEPTION, authBean, "previewurl is empty.");
//			}
//			JSONObject jUrl = (JSONObject)jUrls.get(0);
//			String playUrl = jUrl.getString("previewurl");
//			if (StringUtils.isEmpty(playUrl)) {
				// 鉴权失败且无播放地址，判断为异常报文，返回错误结果
//				return JsonUtils.STATUS(Constants.REMOTE_API_EXCEPTION, authBean, "previewurl is empty.");
//			}
			// 试看播放地址设置
			authBean.setPlayUrl("");
			// 手机号设置
			authBean.setPhoneNumber(userId);
			// 产品列表组装
			List<AuthProductBean> productList = new ArrayList<AuthProductBean>();
			JSONArray jProductList = jObj.getJSONArray("productlist");
			if (null != jProductList && jProductList.size() > 0) {
				// 存在可订购的产品列表
				for (Object obj : jProductList) {
					JSONObject jProduct = (JSONObject)obj;
					String productId = jProduct.getString("productid");
					if (StringUtils.isEmpty(productId)) { 
						// 如果产品Id为空，自动过滤掉该产品
						continue;
					}
					String jLocalProductStr = super.getProductPackage(productId);
					if (StringUtils.isEmpty(jLocalProductStr)) {
						// 如果为查询到对应的本地产品信息，自动过滤掉该产品，避免出现数据不同导致订购异常
						continue;
					}
					JSONObject jLocalProduct = JSON.parseObject(jLocalProductStr);
					// Edit in 2017.09.28 过滤终端不可见属性的产品
					if (SnCmccConstants.IS_VISIBLE_FALSE == jLocalProduct.getIntValue("isVisible")) {
						continue;
					}
					// End Edit
					AuthProductBean bean = new AuthProductBean();
					bean.setProductId(productId);
					bean.setProductName(StringUtils.isEmpty(jProduct.getString("name")) ? jLocalProduct.getString("productName") : jProduct.getString("name"));
					bean.setProductType(jProduct.getIntValue("producttype"));
					bean.setFee(StringUtils.isEmpty(jProduct.getString("price")) ? jLocalProduct.getIntValue("price") : jProduct.getIntValue("price"));
					bean.setProductDesc(jLocalProduct.getString("productDesc"));
					bean.setRentalTerm(jLocalProduct.getIntValue("periodValidity"));
					bean.setPriority(jLocalProduct.getIntValue("priority"));
					bean.setIsBuy(0);
					productList.add(bean);
				}
			}
			// 按优先级高低进行排序
			Collections.sort(productList);
			authBean.setProductList(productList);
			result = JsonUtils.STATUS_OK(authBean, "");
		} catch (Exception e) {
			result = JsonUtils.STATUS_ERROR(Constants.UNKNOWN_EXCEPTION, "鉴权未知异常。");
			LOG.error("authFailed error.", e);
		}
		return result;
	}

}
