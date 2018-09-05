/**
 * 
 */
package com.cnbn.apollo.charging.sncmcc.zx.dao;

import com.cnbn.apollo.charging.sncmcc.zx.model.ProductPackage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author guochunyuan
 *
 */
@Component
public interface ProductPackageDao {

	/**
	 * @param productPackage
	 * @return int
	 * */
	int insert(ProductPackage productPackage);
	
	/**
	 * @param params
	 * @return int
	 * */
	int updateById(Map<String, Object> params);
	
	/**	
	 * @param params
	 * @return int
	 * */
	int updateByProductId(Map<String, Object> params);
	
	/**
	 * @param params
	 * @return List<ProductPackage>
	 * */
	List<ProductPackage> query(Map<String, Object> params);
	
	/**
	 * @param params
	 * @return ProductPackage
	 * */
	ProductPackage queryByProductId(Map<String, Object> params);
}
