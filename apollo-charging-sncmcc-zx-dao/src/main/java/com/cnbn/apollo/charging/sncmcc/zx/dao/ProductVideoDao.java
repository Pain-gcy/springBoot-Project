/**
 * 
 */
package com.cnbn.apollo.charging.sncmcc.zx.dao;

import com.cnbn.apollo.charging.sncmcc.zx.model.ProductVideo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author guochunyuan
 *
 */
@Component
public interface ProductVideoDao {
	
	/**
	 * @param productVideo
	 * @return int
	 * */
	int insert(ProductVideo productVideo);
	
	/**
	 * @param params
	 * @return int
	 * */
	int updateById(Map<String, Object> params);
	
	/**
	 * @param params
	 * @return List<ProductVideo>
	 * */
	List<ProductVideo> query(Map<String, Object> params);
	
}
