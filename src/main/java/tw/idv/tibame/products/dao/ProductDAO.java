package tw.idv.tibame.products.dao;

import java.util.List;

import tw.idv.tibame.core.dao.CoreDAO;
import tw.idv.tibame.products.entity.Product;

public interface ProductDAO extends CoreDAO<Product, Integer> {

	// 萬用更新
	public Boolean update(Product newProduct) throws Exception;

	// 商家查詢商品(全部)
	public List<Product> selectBySupplier(String supplierId);

	// 前台查詢商品(關鍵字)
	public List<Product> selectByKeywords(String[] keywords);

	// 前台查詢商品(商品分類)
	public List<Product> selectByCategorie(String categorie);

	public List<String> getSupplierIdList(String productIds);

	// 最新的上市4筆
	public List<Product> findLatestProducts(Integer num) throws Exception;

	// 最貴的商品4筆
	public List<Product> findMostExpensiveProduct(Integer num) throws Exception;

	List<Object> selectSameShopProductByProductId(Integer productId);
	
	
	//修改上下架狀態
	public Boolean updateStatus(Integer productId, String productStatus)throws Exception;
	
	
	// 以商品編號查詢輸入者是否正確(編輯商品用)
	public Integer selectByProductId(Integer productId, String registerSupplier);
}
