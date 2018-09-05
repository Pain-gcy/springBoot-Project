package com.cnbn.apollo.charging.sncmcc.zx.controller;


import com.alibaba.fastjson.JSONObject;
import com.cnbn.apollo.charging.sncmcc.zx.service.OrderService;
import com.cnbn.gitv.common.Constants;
import com.cnbn.gitv.common.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author guochunyuan
 * 2018年9月3日17:26:09
 */

@RestController
public class OrderController  {

	private final static Logger LOG = LoggerFactory.getLogger(OrderController.class);

	@Resource
	private OrderService orderService;
	
	@PostMapping(value = "/v1/account/order/")
	public String order(@RequestBody String msgCtx) {
		String result = StringUtils.EMPTY;
		try {
			if (StringUtils.isEmpty(msgCtx)) {
				return JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID, "invalid params.");
			}
			JSONObject jObj = JSONObject.parseObject(msgCtx);
			LOG.info(jObj.toString());
			String mac = jObj.getString("mac").toLowerCase();
			String userId = jObj.getString("userId");
			String productId = jObj.getString("productId");
			String contentId = jObj.getString("contentId");
			int payType = (jObj.getInteger("payType") == null) ? 0 : jObj.getInteger("payType");
			String userToken = jObj.getString("userToken");
			String chnId = jObj.getString("chnId");
			String albumId = jObj.getString("albumId");
			String albumName = jObj.getString("albumName");
			String tvId = jObj.getString("tvId");
			String tvName = jObj.getString("tvName");
			String epgServer = jObj.getString("epgServer");
			//String superId = jObj.getString("superid");
			if (epgServer.contains("http://")) { // 过滤“http://”前缀
				epgServer = epgServer.substring(7);
			}
			if (StringUtils.isEmpty(mac) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(productId) || StringUtils.isEmpty(contentId)
				|| StringUtils.isEmpty(userToken) || StringUtils.isEmpty(albumId) || StringUtils.isEmpty(epgServer)) {
				return JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID, "invalid params.");
			}
			return orderService.order(mac, userId, null, null, productId, contentId, payType, userToken, chnId, albumId, albumName, tvId, tvName, epgServer);
		} catch (Exception e) {
			LOG.error("auth error!", e);
		}
		return result;
	}

	@PostMapping(value = "/v1/account/unifiedorder")
	public String unifiedorder(String msgCtx) {
		String result = StringUtils.EMPTY;
		try {
			if (StringUtils.isEmpty(msgCtx)) {
				return JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID, "invalid params.");
			}
			JSONObject jObj = JSONObject.parseObject(msgCtx);
			LOG.info(jObj.toString());
			String mac = jObj.getString("mac").toLowerCase();
			String userId = jObj.getString("userId");
			String contentId = jObj.getString("contentId");
			String userToken = jObj.getString("userToken");
			String epgServer = jObj.getString("epgServer");
			String productId = jObj.getString("productId");
			String payType = jObj.getString("payType");
			String contentName = jObj.getString("contentName");
			if (epgServer.contains("http://")) { // 过滤“http://”前缀
				epgServer = epgServer.substring(7);
			}
			if (StringUtils.isEmpty(mac) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(contentId)
					|| StringUtils.isEmpty(userToken) || StringUtils.isEmpty(epgServer)) {
				return JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID, "invalid params.");
			}
			return orderService.order(mac, userId, null, null, productId, contentId, Integer.parseInt(payType), userToken, null, null, contentName, null, null, epgServer);
		} catch (Exception e) {
			LOG.error("auth error!", e);
		}
		return result;
	}

	@PostMapping(value = "/v1/account/sendSms/")
	public String sendSms(String msgCtx) {
		String result = StringUtils.EMPTY;
		try {
			if (StringUtils.isEmpty(msgCtx)) {
				return JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID, "invalid params.");
			}
			JSONObject jObj = JSONObject.parseObject(msgCtx);
			LOG.info(jObj.toString());
			return "";
		} catch (Exception e) {
			LOG.error("sendSms error!", e);
		}
		return result;
	}
	
	@PostMapping(value = "/v1/account/cancelOrder/")
	public String cancelOrder(@RequestBody String msgCtx) {
		String result = StringUtils.EMPTY;
		try {
			if (StringUtils.isEmpty(msgCtx)) {
				return JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID, "invalid params.");
			}
			JSONObject jObj = JSONObject.parseObject(msgCtx);
			LOG.info(jObj.toString());
			String mac = jObj.getString("mac").toLowerCase();
			String userId = jObj.getString("userId");
			String productId = jObj.getString("productId");
			String effectiveTime = jObj.getString("effectiveTime");
			String userToken = jObj.getString("userToken");
			String epgServer = jObj.getString("epgServer");
			if (epgServer.contains("http://")) { // 过滤“http://”前缀
				epgServer = epgServer.substring(7);
			}
			if (StringUtils.isEmpty(mac) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(productId) ||
					StringUtils.isEmpty(effectiveTime) || StringUtils.isEmpty(userToken) || StringUtils.isEmpty(epgServer)) {
				return JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID, "invalid params.");
			}
			return orderService.cancelOrder(mac, userId, productId, effectiveTime, userToken, epgServer);
		} catch (Exception e) {
			LOG.error("cancelOrder error!", e);
		}
		return result;
	}
}
