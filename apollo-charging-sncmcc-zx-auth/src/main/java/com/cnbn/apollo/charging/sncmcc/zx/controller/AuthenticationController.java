/**
 * 
 */
package com.cnbn.apollo.charging.sncmcc.zx.controller;

import com.alibaba.fastjson.JSONObject;
import com.cnbn.apollo.charging.sncmcc.zx.service.AuthenticationService;
import com.cnbn.gitv.common.Constants;
import com.cnbn.gitv.common.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author guochunyuan
 *
 */
@RestController
public class AuthenticationController {

	private final static  Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

	@Resource
	private AuthenticationService authenticationService;

	@PostMapping(value = "/v1/account/auth/")
	public String auth(@RequestBody String msgCtx) {
		String result = StringUtils.EMPTY;
		try {
			if (StringUtils.isEmpty(msgCtx)) {
				return JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID, "invalid params.");
			}
			JSONObject obj =JSONObject.parseObject(msgCtx);
			LOG.info(obj.toString());
			String mac = obj.getString("mac").toLowerCase();
			String userId = obj.getString("userId");
			String contentId = obj.getString("contentId");
			String userToken = obj.getString("userToken");
			String epgServer = obj.getString("epgServer");
			if (epgServer.contains("http://")) { // 过滤“http://”前缀
				epgServer = epgServer.substring(7);
			}
			if (StringUtils.isEmpty(mac) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(contentId)
				|| StringUtils.isEmpty(userToken) || StringUtils.isEmpty(epgServer)) {
				return JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID, "invalid params.");
			}
			String auth = authenticationService.auth(mac, userId, contentId, userToken, epgServer);
			return auth;
		} catch (Exception e) {
			LOG.error("auth error!", e);
		}
		return result;
	}

	@PostMapping(value = "/v1/account/products", produces = "application/json;charset=UTF-8")
	public String products(@RequestBody String msgCtx) {
		String result = StringUtils.EMPTY;
		try {
			if (StringUtils.isEmpty(msgCtx) ) {
				return JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID, "invalid params.");
			}
			JSONObject obj = JSONObject.parseObject(msgCtx);
			LOG.info(obj.toString());
			String userId = obj.getString("userId");
			String contentId = obj.getString("contentId");
			String userToken = obj.getString("userToken");
			String epgServer = obj.getString("epgServer");
			String mac = obj.getString("mac").toLowerCase();
			if (epgServer.contains("http://")) { // 过滤“http://”前缀
				epgServer = epgServer.substring(7);
			}
			if (StringUtils.isEmpty(mac) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(contentId)
					|| StringUtils.isEmpty(userToken) || StringUtils.isEmpty(epgServer)) {
				return JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID, "invalid params.");
			}
			return authenticationService.auth(mac, userId, contentId, userToken, epgServer);
		} catch (Exception e) {
			LOG.error("auth error !", e);
		}
		return result;
	}
	@GetMapping(value = "/get/{key}/" ,produces = "application/json;charset=UTF-8")
	public String getKey(@PathVariable("key") String key) {
		String result ;
		try {
			if (StringUtils.isEmpty(key)) {
				return JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID, "invalid params");
			}
			result = authenticationService.getKey(key);
			if (null == result) {
				result = "null";
			}
		} catch (Exception e) {
			result = "error";
			LOG.error("getKey error!", e);
		}
		return result;
	}
	@GetMapping(value = "/del/{key}/" ,produces = "application/json;charset=UTF-8")
	public String delKey(@PathVariable("key") String key) {
		String result = "success";
		try {
			if (StringUtils.isEmpty(key)) {
				return "invalid key";
			}
			authenticationService.delKey(key);
		} catch (Exception e) {
			result = "error";
			LOG.error("delKey error!", e);
		}
		return result;
	}
}
