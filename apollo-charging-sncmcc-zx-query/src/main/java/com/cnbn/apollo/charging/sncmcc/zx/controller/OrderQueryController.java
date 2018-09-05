package com.cnbn.apollo.charging.sncmcc.zx.controller;


import com.alibaba.fastjson.JSONObject;

import com.cnbn.apollo.charging.sncmcc.zx.service.OrderQueryService;
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
 * @date 2018年9月3日10:03:24
 */
@RestController
public class OrderQueryController  {

	private final static Logger LOG = LoggerFactory.getLogger(OrderQueryController.class);

	
	@Resource
	private OrderQueryService orderQueryService;

	@PostMapping(value = "/v1/account/orderRecQuery/")
	public String orderRecQuery(@RequestBody  String msgCtx) {
		String result = StringUtils.EMPTY;
		try {
			if (StringUtils.isEmpty(msgCtx)) {
				return JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID, "invalid params.");
			}
			JSONObject jObj = JSONObject.parseObject(msgCtx);
			LOG.info(jObj.toString());
			String mac = jObj.getString("mac").toLowerCase();
			String userId = jObj.getString("userId");
			String userToken = jObj.getString("userToken");
			String epgServer = jObj.getString("epgServer");
			if (epgServer.contains("http://")) { // 过滤“http://”前缀
				epgServer = epgServer.substring(7);
			}
			int isEffective = (jObj.getIntValue("isEffective") < 0) ? 1 : jObj.getIntValue("isEffective");
			int pageNo = (jObj.getIntValue("pageNo") <= 0) ? 1 : jObj.getIntValue("pageNo");
			int pageSize = (jObj.getIntValue("pageSize") <= 0) ? 10 : jObj.getIntValue("pageSize");
			if (StringUtils.isEmpty(mac) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(userToken)) {
				return JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID, "invalid params.");
			}
			return orderQueryService.orderRecordQuery(mac, userId, userToken, epgServer, isEffective, pageNo, pageSize);
		} catch (Exception e) {
			LOG.error("auth error!", e);
		}
		return result;
	}
	
	/**
	 * 暂无订单状态查询功能
	 * */
	public String payResultQuery(String msgCtx) {
		
		return StringUtils.EMPTY;
	}
}
