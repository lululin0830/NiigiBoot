package tw.idv.tibame.products.dao;

import tw.idv.tibame.core.dao.CoreDAO;
import tw.idv.tibame.products.entity.ProductSpec;

public interface ProductSpecDAO extends CoreDAO<ProductSpec, String> {

	// 萬用更新
	public ProductSpec update(ProductSpec newProductSpec);

}
