/**
 * 
 */
package com.cnbn.apollo.charging.sncmcc.zx.dao;

import com.cnbn.apollo.charging.sncmcc.zx.model.ProductUserOrder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author guochunyuan
 *
 */
@Component
public interface ProductUserOrderDao {

	/**
	 * @param productUserOrder
	 * @return int
	 * */
	int insert(ProductUserOrder productUserOrder);
	
	/**
	 * @param params
	 * @return int
	 * */
	int updateById(Map<String, Object> params);

	/**
	 * 退订接口专用
	 * @param params
	 * @return int
	 * */
	int updateCancelOrder(Map<String, Object> params);


	/**
	 * @param params
	 * @return List<ProductUserOrder>
	 * */
	List<ProductUserOrder> query(Map<String, Object> params);
	/**
	 * @param params
	 * @return List<ProductUserOrder>
	 * */
	ProductUserOrder queryOne(Map<String, Object> params);

	/**
	 * @param params
	 * @return int
	 * */
	int total(Map<String, Object> params);
	
	/**
	 * @param id
	 * @return ProductUserOrder
	 * */
	ProductUserOrder queryById(int id);
	
}
