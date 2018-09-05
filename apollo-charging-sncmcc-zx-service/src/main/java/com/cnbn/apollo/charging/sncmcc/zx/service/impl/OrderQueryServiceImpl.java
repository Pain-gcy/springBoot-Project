/**
 * 
 */
package com.cnbn.apollo.charging.sncmcc.zx.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cnbn.apollo.charging.sncmcc.zx.bean.OrderQueryBean;
import com.cnbn.apollo.charging.sncmcc.zx.bean.OrderRecQueryBean;

import com.cnbn.apollo.charging.sncmcc.zx.common.SnCmccConstants;
import com.cnbn.apollo.charging.sncmcc.zx.dao.ProductUserOrderDao;
import com.cnbn.apollo.charging.sncmcc.zx.model.ProductUserOrder;
import com.cnbn.apollo.charging.sncmcc.zx.service.OrderQueryService;
import com.cnbn.gitv.common.Constants;
import com.cnbn.gitv.common.DateUtils;
import com.cnbn.gitv.common.JsonUtils;
import com.cnbn.gitv.common.RedisPoolUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.*;


/**
 * @author guochunyuan
 *
 */
@Service
public class OrderQueryServiceImpl extends AbstractService implements OrderQueryService {

	private final static Logger LOG = LoggerFactory.getLogger(OrderQueryServiceImpl.class);

	/**
	 * 订购记录Key，{0}:userId, {1}:isEffective, {2}:pageNo, {3}:pageSize
	 * */
	private final static String KEY_PRODUCT_USER_ORDER = "rec/{0}/{1}/{2}/{3}";

	@Resource
	private ProductUserOrderDao productUserOrderDao;

	@Override
	public String orderRecordQuery(String mac, String userId, String userToken, String epgServer, int isEffective,
                                   int pageNo, int pageSize) {
		String result;
		try {
			if (SnCmccConstants.IS_EFFECTIVE_FALSE == isEffective) {
				// 暂不提供失效订单数据
				return JsonUtils.STATUS_OK();
			}
			String key = MessageFormat.format(KEY_PRODUCT_USER_ORDER, userId, isEffective, pageNo, pageSize);
			result = RedisPoolUtils.get(jedisSentinelPool,key);
			if (StringUtils.isEmpty(result)) {
				OrderRecQueryBean orderRecQueryBean = new OrderRecQueryBean();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("userId", userId);
				params.put("expiredTime", new Date());
				// 未续订(0)、续订中(1)、已退订(2)
				params.put("status", new int[] {SnCmccConstants.ORDER_STATUS_SUCCESS, SnCmccConstants.ORDER_STATUS_RE_ORDER, SnCmccConstants.IS_REORDER_CANCELLED});
				int total = productUserOrderDao.total(params);
				if (total > 0) {
					params.put("offset", (pageNo - 1) * pageSize);
					params.put("limit", pageSize);
					List<ProductUserOrder> productUserOrderList = productUserOrderDao.query(params);
					if (null != productUserOrderList && productUserOrderList.size() > 0) {
						List<OrderQueryBean> orderQueryBeanList = new ArrayList<>();
						for (ProductUserOrder productUserOrder : productUserOrderList) {
							OrderQueryBean bean = new OrderQueryBean();
							bean.setOrderId(productUserOrder.getOrderId());
							bean.setProductId(productUserOrder.getProductId());
							// 单点产品展示专辑名称，其他展示产品包名称
							bean.setProductName((SnCmccConstants.PRODUCT_TYPE_ONCE == productUserOrder.getOrderType()) ? productUserOrder.getAlbumName() : productUserOrder.getProductName());
							bean.setProductType(productUserOrder.getOrderType());
							bean.setPayType(productUserOrder.getPayMode());
							bean.setPayName(getPayName(productUserOrder.getPayMode()));
							bean.setTvId(productUserOrder.getTvId());
							bean.setAlbumId(productUserOrder.getAlbumId());
							bean.setChnId(productUserOrder.getChnId());
							bean.setFee(productUserOrder.getPrice());
							bean.setPhoneNumber(productUserOrder.getPhoneNumber());
							bean.setEffectiveTime(DateUtils.dateToString(productUserOrder.getEffectiveTime(), DateUtils.DATETIME_FORMAT2));
							bean.setExpiredTime((null == productUserOrder.getExpiredTime()) ? "" : DateUtils.dateToString(productUserOrder.getExpiredTime(), DateUtils.DATETIME_FORMAT2));
							bean.setReOrder(productUserOrder.getStatus());
							bean.setOrderTime(DateUtils.dateToString(productUserOrder.getCreateTime(), DateUtils.DATETIME_FORMAT2));
							orderQueryBeanList.add(bean);
						}
						orderRecQueryBean.setList(orderQueryBeanList);
					}
				}
				orderRecQueryBean.setTotal(total);
				orderRecQueryBean.setPageNo(pageNo);
				orderRecQueryBean.setPageSize(pageSize);
				result = JsonUtils.STATUS(Constants.RESPONSE_SUCCESS, orderRecQueryBean, "");
				RedisPoolUtils.set(jedisSentinelPool,key, result, 10 * 60);
			}
		} catch (Exception e) {
			result = JsonUtils.STATUS_ERROR(Constants.UNKNOWN_EXCEPTION, "订购记录查询未知异常。");
			LOG.error("orderRecordQuery error.", e);
		}
		return result;
	}

	@Override
	public String payResultQuery(String mac, String userName, String userId, String orderId, String epgServer) {
		return null;
	}
	
	private String getRecordList(NodeList elements, int pageNo, int pageSize) throws Exception {
		OrderRecQueryBean orderRecQueryBean = new OrderRecQueryBean();
		orderRecQueryBean.setPageNo(pageNo);
		orderRecQueryBean.setPageSize(pageSize);
		orderRecQueryBean.setTotal(0);
		List<OrderQueryBean> list = new ArrayList<>();
		if (null != elements && elements.getLength() > 0) {
			int total = 0;
			for (int i = 0; i < elements.getLength(); ++i) {
				Element element = (Element)elements.item(i);
				// 产品Id
				NodeList nPid = element.getElementsByTagName("itv:pid");
				String productId = (null == nPid) ? null : nPid.item(0).getTextContent();
				if (StringUtils.isEmpty(productId)) {
					continue;
				}
				String jLocalProductStr = super.getProductPackage(productId);
				if (StringUtils.isEmpty(jLocalProductStr)) {
					continue;
				}
				OrderQueryBean bean = new OrderQueryBean();
				bean.setProductId(productId);
				bean.setOrderId("");
				bean.setPhoneNumber("");
				bean.setAlbumId("0");
				bean.setTvId("0");
				bean.setChnId("0");
				// 支付方式目前默认为话费
				bean.setPayType(0);
				bean.setPayName("话费");
				JSONObject jLocalProduct = JSON.parseObject(jLocalProductStr);
				// 产品名称
				NodeList nTitle = element.getElementsByTagName("title");
				String productName = (null == nTitle) ? jLocalProduct.getString("productName") : nTitle.item(0).getTextContent().trim();
				// 其他itv:attribute名称节点
				NodeList nodeList = element.getElementsByTagName("itv:attribute");
				int productType = 0;
				int price = 0;
				String contentId = "";
				String startTime = "";
				String endTime = "";
				for (int j = 0; j < nodeList.getLength(); ++j) {
					Element attr = (Element)nodeList.item(j);
					String name = attr.getAttribute("name");
					String value = attr.getTextContent().trim();
					if ("type".equals(name)) {
						productType = StringUtils.isEmpty(value) ? 0 : Integer.parseInt(value);
					} else if ("price".equals(name)) {
						price = StringUtils.isEmpty(value) ? 0 : Integer.parseInt(value);
					} else if ("contentid".equals(name)) {
						contentId = value;
					} else if ("starttime".equals(name)) {
						startTime = value;
					} else if ("endtime".equals(name)) {
						endTime = value;
					} else {
						;
					}
				}
				// 产品类型
				if (SnCmccConstants.PRODUCT_TYPE_ONCE == productType) {
					if (StringUtils.isEmpty(contentId)) {
						// 单点内容，返回的contentId为空，判断为异常订单，过滤
						continue;
					}
					String jLocalVideoStr = super.getProductVideo(contentId);
					if (StringUtils.isEmpty(jLocalVideoStr)) {
						// 本地不存在相应contentId对应专辑信息，过滤
						continue;
					}
					JSONObject jLocalVideo = JSON.parseObject(jLocalVideoStr);
					// 单点内容产品包名称设置为专辑名称
					productName = jLocalVideo.getString("albumName");
					// 同时设置专辑信息
					bean.setAlbumId(jLocalVideo.getString("albumId"));
					bean.setTvId(jLocalVideo.getString("tvId"));
					bean.setChnId(jLocalVideo.getString("chnId"));
				}
				bean.setProductName(productName);
				bean.setProductType(productType);
				bean.setFee(price);
				bean.setEffectiveTime(startTime);
				bean.setOrderTime(bean.getEffectiveTime());
				bean.setExpiredTime(endTime);
				if (endTime.compareTo("20991231235959") >= 0) {
					bean.setReOrder(SnCmccConstants.IS_REORDER_TRUE);
				}
				total++;
				list.add(bean);
			}
			orderRecQueryBean.setTotal(total);
		}
		orderRecQueryBean.setList(list);
		return JsonUtils.STATUS_OK(orderRecQueryBean, "");
	}
	
}
