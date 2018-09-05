/**
 * 
 */
package com.cnbn.apollo.charging.sncmcc.zx.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cnbn.apollo.charging.sncmcc.zx.bean.CancelOrderBean;
import com.cnbn.apollo.charging.sncmcc.zx.bean.OrderBean;
import com.cnbn.apollo.charging.sncmcc.zx.common.HuaWeiMessage;
import com.cnbn.apollo.charging.sncmcc.zx.common.SnCmccConstants;
import com.cnbn.apollo.charging.sncmcc.zx.dao.ProductUserOrderDao;
import com.cnbn.apollo.charging.sncmcc.zx.model.ProductUserOrder;
import com.cnbn.apollo.charging.sncmcc.zx.service.OrderService;
import com.cnbn.gitv.common.Constants;
import com.cnbn.gitv.common.DateUtils;
import com.cnbn.gitv.common.JsonUtils;
import com.cnbn.gitv.common.RedisPoolUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author guochunyuan
 *
 */
@Service
public class OrderServiceImpl extends AbstractService implements OrderService {

	private final static Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

	/**
	 * 订购记录通配Key，{0}:userId, {1}:isEffective
	 * */
	private final static String KEY_PRODUCT_USER_ORDER = "rec/{0}/{1}/*";

	@Resource
	private ProductUserOrderDao productUserOrderDao;
	
	@Override
	public String order(String mac, String userId, String phoneNumber, String verifyCode, String productId,
                        String contentId, int payType, String userToken, String chnId, String albumId, String albumName,
                        String tvId, String tvName, String epgServer) {
		String result = StringUtils.EMPTY;
		try {
			// 订购内容信息入库（用于订购记录查询关联）
			super.setProductVideo(contentId, chnId, albumId, albumName, tvId, tvName);
			// 查询本地产品包信息
			String jLocalProductStr = super.getProductPackage(productId);
			if (StringUtils.isEmpty(jLocalProductStr)) {
				return JsonUtils.STATUS_ERROR(Constants.PRODUCT_NOT_EFFECTIVE, "当前产品暂不可用。productId:" + productId);
			}
			JSONObject jLocalProduct = JSON.parseObject(jLocalProductStr);
			// 本地订单入库
			Date date = new Date();
			ProductUserOrder productUserOrder = new ProductUserOrder();
			productUserOrder.setUserId(userId);
			productUserOrder.setProductId(productId);
			productUserOrder.setProductName(jLocalProduct.getString("productName"));
			productUserOrder.setContentId(contentId);
			productUserOrder.setPhoneNumber(phoneNumber);
			productUserOrder.setPrice(jLocalProduct.getIntValue("price"));
			productUserOrder.setPayMode(payType);
			productUserOrder.setOrderType(jLocalProduct.getIntValue("productType"));
			productUserOrder.setMac(mac);
			productUserOrder.setChnId(chnId);
			productUserOrder.setAlbumId(albumId);
			productUserOrder.setAlbumName(albumName);
			productUserOrder.setTvId(tvId);
			productUserOrder.setTvName(tvName);
			productUserOrder.setPayTime(date);
			productUserOrder.setCreateTime(date);
			productUserOrderDao.insert(productUserOrder);
			
			String sid = jLocalProduct.getString("serviceId");
			String continueType = jLocalProduct.getString("isReOrder");
			// 注：这里的产品价格参数只是为了遵循接口规范，产品实际扣费情况以华为系统为主
			String orderPrice = jLocalProduct.getString("price");
			// 调用华为订购接口
			String resp = HuaWeiMessage.subscribe(userId, productId, sid, contentId, continueType, orderPrice, userToken, epgServer, DateUtils.dateToString(date , DateUtils.DATETIME_FORMAT2));
			LOG.info("mac:{}, userId:{}, resp:{}", mac, userId, resp);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", productUserOrder.getId());
			params.put("updateTime", new Date());
			if (StringUtils.isEmpty(resp)) {
				params.put("status", SnCmccConstants.ORDER_STATUS_ERROR);
				productUserOrderDao.updateById(params);
				return JsonUtils.STATUS_ERROR(Constants.REMOTE_API_EXCEPTION, "请求订购接口异常。");
			}
			JSONObject jObj = JSON.parseObject(resp);
			String returnCode = jObj.getString("returncode");
			String description = jObj.getString("description");
			if (SnCmccConstants.SUCCESS.equals(returnCode)) {
				// 订购成功订单状态变更
				int isReOrder = jLocalProduct.getIntValue("isReOrder");
				if (SnCmccConstants.IS_REORDER_TRUE == isReOrder) {//续订
					params.put("status", SnCmccConstants.ORDER_STATUS_RE_ORDER);
				} else {//第一次订购
					params.put("status", SnCmccConstants.ORDER_STATUS_SUCCESS);
					params.put("expiredTime", DateUtils.addMinutes(date, jLocalProduct.getIntValue("periodValidity")));
				}
				params.put("effectiveTime", date);
				// 鉴权成功报文组装
				OrderBean orderBean = new OrderBean();
				JSONArray jUrls = jObj.getJSONArray("urls");
				if (null != jUrls && jUrls.size() > 0) {
					JSONObject jUrl = (JSONObject)jUrls.get(0);
					orderBean.setPlayUrl(jUrl.getString("playurl"));
				}
				result = JsonUtils.STATUS_OK(orderBean, description);
				// Add in 2018年5月23日17:25:37 订购成功后，删除订购记录缓存
				Set<String> keys = RedisPoolUtils.keys(jedisSentinelPool, MessageFormat.format(KEY_PRODUCT_USER_ORDER, userId, 1));
				RedisPoolUtils.evict(jedisSentinelPool,keys);
			} else {
				params.put("status", SnCmccConstants.ORDER_STATUS_FAILED);
				result = JsonUtils.STATUS_FAILED(Constants.REMOTE_ORDER_SERVICE_FAILED, description + " returnCode:" + returnCode);
			}
			// 更新订单信息
			productUserOrderDao.updateById(params);
		} catch (Exception e) {
			result = JsonUtils.STATUS_ERROR(Constants.UNKNOWN_EXCEPTION, "订购未知异常。");
			LOG.error("order error.", e);
		}
		return result;
	}

	@Override
	public String sendSms(String mac, String userId, String phoneNumber, String content, String userToken) {
		
		return null;
	}

	@Override
	public String phoneConfirm(String mac, String userId, String phoneNumber, String verifyCode, String userToken) {
		
		return null;
	}

	@Override
	public String cancelOrder(String mac, String userId, String productId, String effectiveTime, String userToken, String epgServer) {
		String result = StringUtils.EMPTY;
		try {
			// 2018年8月17日18:20:48
			Map<String,Object> map =  new HashMap<>();
			map.put("userId",userId);
			map.put("productId",productId);
			ProductUserOrder productUserOrder = productUserOrderDao.queryOne(map);
			if (null != productUserOrder) {
				Date effectiveTimesql = productUserOrder.getEffectiveTime();
				effectiveTime = DateUtils.dateToString(effectiveTimesql, DateUtils.DATETIME_FORMAT2);
			}
			// 订购首月不允许退订
			if (DateUtils.dateToString(new Date(), DateUtils.YM).equals(effectiveTime.substring(0, 6))) {
				return JsonUtils.STATUS_ERROR(Constants.RE_CNCL_ORDER_ERROR, "订购首月免费，首月不可退订。");
			}
			String expiredTime = DateUtils.getLastDay(null, DateUtils.DATETIME_FORMAT2);
			// 调用华为退订接口
			String resp = HuaWeiMessage.cancelsubscribe(userId, productId, "", "", effectiveTime, expiredTime, userToken, epgServer);
			LOG.debug("mac:{}, userId:{}, resp:{}", mac, userId, resp);
			JSONObject jObj = JSON.parseObject(resp);
			String returnCode = jObj.getString("returncode");
			String description = jObj.getString("description");
			if (SnCmccConstants.SUCCESS.equals(returnCode)) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("userId", userId);
				params.put("productId", productId);
				params.put("status", SnCmccConstants.ORDER_STATUS_RE_ORDER);
				params.put("cancelStatus", SnCmccConstants.ORDER_STATUS_CANCEL_ORDER);
				params.put("expiredTime", DateUtils.stringToDate(expiredTime, DateUtils.DATETIME_FORMAT2));
				params.put("updateTime", new Date());
				// 更新用户续订中的订单状态
				productUserOrderDao.updateCancelOrder(params);
				// 退订成功报文组装
				CancelOrderBean cancelOrderBean = new CancelOrderBean();
				cancelOrderBean.setExpiredTime(expiredTime);
				result = JsonUtils.STATUS_OK(cancelOrderBean, description);
				// 退订成功后，删除订购记录缓存
				RedisPoolUtils.evict(jedisSentinelPool,RedisPoolUtils.keys(jedisSentinelPool,MessageFormat.format(KEY_PRODUCT_USER_ORDER, userId, 1)));
			} else {
				result = JsonUtils.STATUS_FAILED(Constants.REMOTE_CNCLORDER_SERVICE_FAILED, description + " returnCode:" + returnCode);
			}
		} catch (Exception e) {
			LOG.error("cancelOrder error.", e);
		}
		return result;
	}
}
