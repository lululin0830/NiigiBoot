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
	public Boolean updateSpecStock(String productSpecId, Integer specStock);

	// 列出全部規格，已下架或上架中+BY商家編號
	public List<ProductSpec> findActiveSpecificationsBySupplierId(String shelvesStatus, String registerSupplier);

	// 列出全部規格，all+BY商家編號
	public List<ProductSpec> findInactiveSpecificationsBySupplierId(String registerSupplier);

	// 算出總數量，已下架或上架中+BY商家編號
	public Integer getTotalCountOfProductsByStatusAndSupplierId(String shelvesStatus, String registerSupplier);

	// 算出總數量，all+BY商家編號
	public Integer getTotalCountOfActiveProductsBySupplierId(String registerSupplier);

	// 以規格編號算出已售出多少數量+BY商品狀態(需0)(這個是借放的，等等要移位)
	public Integer getSoldQuantityBySpecIdAndStatus(String productSpecId);

	// 列出全部規格，已售完(要去除強制下架)+BY商家編號
	public List<ProductSpec> getAllSpecsForSoldOutProductsBySupplierId(String registerSupplier);
	
	//算出總數量，已售完(要去除強制下架)+BY商家編號
	public Integer getCountForSoldOutProductsBySupplierId (String registerSupplier);
	
}
