/**
 * 
 */
package com.cnbn.apollo.charging.sncmcc.zx.service.impl;


import com.alibaba.fastjson.JSON;
import com.cnbn.apollo.charging.sncmcc.zx.common.SnCmccConstants;
import com.cnbn.apollo.charging.sncmcc.zx.dao.ProductPackageDao;
import com.cnbn.apollo.charging.sncmcc.zx.dao.ProductVideoDao;
import com.cnbn.apollo.charging.sncmcc.zx.model.ProductPackage;
import com.cnbn.apollo.charging.sncmcc.zx.model.ProductVideo;
import com.cnbn.gitv.common.RedisPoolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisSentinelPool;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DuQiyu
 * 
 *         付费相关通用业务抽象类
 */
public abstract class AbstractService {

	private final static Logger LOG = LoggerFactory.getLogger(AbstractService.class);

	/**
	 * 产品包缓存key
	 */
	protected final static String KEY_PRODUCT_PACKAGE = "package/{0}";
	
	/**
	 * 内容Id缓存key
	 * */
	protected final static String KEY_PRODUCT_VIDEO = "video/{0}";

	/**
	 * 用户黑名单缓存信息；
	 */
	protected final static String KEY_USERID = "userId/{0}";

	@Resource
	protected ProductPackageDao productPackageDao;
	
	@Resource
	protected ProductVideoDao productVideoDao;

	@Resource
	protected JedisSentinelPool jedisSentinelPool;

	/**
	 * 获取产品包信息（json格式）
	 * @param productId - 产品包Id
	 * @return String
	 * */
	protected String getProductPackage(String productId) {
		String key = MessageFormat.format(KEY_PRODUCT_PACKAGE, productId);
		String value = null;
		try {
			value = RedisPoolUtils.get(jedisSentinelPool, key);
			if (null != value) {
				return value;
			}
		} catch (Exception e) {
			LOG.error("get key error.", e);
		}
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("productId", productId);
			// 只查询有效的产品包
			params.put("isEffective", SnCmccConstants.IS_EFFECTIVE_TRUE);
			ProductPackage productPackage = productPackageDao.queryByProductId(params);
			if (null != productPackage) {
				value = JSON.toJSONString(productPackage);
			} else { // 查询不到的产品缓存值设置为空数据
				value = "";
			}
			try {
				RedisPoolUtils.set(jedisSentinelPool, key, value, 24 * 3600);
			} catch(Exception e) {
				LOG.error("set key error.", e);
			}
		} catch (Exception e) {
			LOG.error("getProductPackage error.", e);
		}
		return value;
	}
	
	/**
	 * 订购内容Id相关信息持久化
	 * @param contentId
	 * @param albumId
	 * @param albumName
	 * @param tvId
	 * @param tvName
	 * @return 
	 * */
	protected synchronized void setProductVideo(String contentId, String chnId, String albumId, String albumName, String tvId, String tvName) {
		String key = MessageFormat.format(KEY_PRODUCT_VIDEO, contentId);
		try {
			String value = RedisPoolUtils.get(jedisSentinelPool, key);
			if (null != value) {
				return ;
			}
		} catch (Exception e) {
			LOG.error("get key error.", e);
		}
		try {
			ProductVideo productVideo = null;
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("contentId", contentId);
			List<ProductVideo> productVideoList = productVideoDao.query(params);
			if (null != productVideoList && productVideoList.size() > 0) {
				productVideo = productVideoList.get(0);
			} else {
				productVideo = new ProductVideo();
				productVideo.setContentId(contentId);
				productVideo.setChnId(chnId);
				productVideo.setAlbumId(albumId);
				productVideo.setAlbumName(albumName);
				productVideo.setTvId(tvId);
				productVideo.setTvName(tvName);
				Date date = new Date();
				productVideo.setCreateTime(date);
				productVideo.setUpdateTime(date);
				productVideoDao.insert(productVideo);
			}
			RedisPoolUtils.set(jedisSentinelPool, key, JSON.toJSONString(productVideo));
		} catch (Exception e) {
			LOG.error("setProductVideo error.", e);
		}
	}
	
	/**
	 * 获取订购内容相关信息
	 * @param contentId
	 * @return String
	 * */
	protected String getProductVideo(String contentId) {
		String key = MessageFormat.format(KEY_PRODUCT_VIDEO, contentId);
		String value = null;
		try {
			value = RedisPoolUtils.get(jedisSentinelPool, key);
			if (null != value) {
				return value;
			}
		} catch (Exception e) {
			LOG.error("get key error.", e);
		}
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("contentId", contentId);
			List<ProductVideo> productVideoList = productVideoDao.query(params);
			if (null != productVideoList && productVideoList.size() > 0) {
				value = JSON.toJSONString(productVideoList.get(0));
			}
		} catch (Exception e) {
			LOG.error("getProductVideo error.", e);
		}
		return value;
	}
	
	/**
	 * 根据支付类型获取详细支付名称
	 * @param payType
	 * @return String
	 * */
	protected static String getPayName(int payType) {
		switch(payType) {
		case 0:
			return "话费";
		case 1:
			return "支付宝";
		case 2:
			return "微信";
		default:
			return "其他";
		}
	}
	/**
	 * 用于检查黑名单缓存里面是否含有这个用户
	 * @param useriId
	 * @return
	 */
	protected String checkUserid(String useriId){
		String key = MessageFormat.format(KEY_USERID, useriId);
		String value = null;
		try {
			value = RedisPoolUtils.get(jedisSentinelPool,key);
		} catch (Exception e) {
			LOG.error("get key error.", e);
		}
		return value;
	}
}
