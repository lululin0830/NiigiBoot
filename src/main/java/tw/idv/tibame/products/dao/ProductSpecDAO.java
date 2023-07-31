package tw.idv.tibame.products.dao;

import java.util.List;

import tw.idv.tibame.core.dao.CoreDAO;
import tw.idv.tibame.products.entity.ProductSpec;

public interface ProductSpecDAO extends CoreDAO<ProductSpec, String> {

	// 萬用更新
	public ProductSpec update(ProductSpec newProductSpec);

	// 以商品編號查全部規格
	public List<ProductSpec> selectByProductId(Integer productIds);

	// 修改規格的上下架狀態
	public Boolean updateStatus(String[] productSpecIds, String shelvesStatus);

	// 以商品編號計算有幾種規格
	public Integer selectByProductSpecId(Integer productIds);

	// 更新庫存
	public Boolean updateSpecStock(String productSpecId,Integer specStock);

}
