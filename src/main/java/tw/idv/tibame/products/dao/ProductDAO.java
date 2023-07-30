package tw.idv.tibame.products.dao;

import java.util.List;

import tw.idv.tibame.core.dao.CoreDAO;
import tw.idv.tibame.products.dto.ProductInfoDTO;
import tw.idv.tibame.products.entity.Product;

public interface ProductDAO extends CoreDAO<Product, Integer> {

	// 萬用更新
	public Product update(Product newProduct)throws Exception;
	
	// 商家查詢商品(全部)
	public List<Product> selectBySupplier (String supplierId);
	
	// 前台查詢商品(關鍵字)
	public List<Product> selectByKeywords (String[] keywords);

	// 前台查詢商品(商品分類)
	public List<Product> selectByCategorie (String categorie);
	
	public List<String> getSupplierIdList (String productIds);
	
	//最新的上市4筆
	public List<ProductInfoDTO> findLatestProducts() throws Exception;
	
	//最貴的商品4筆
	public List<ProductInfoDTO> findMostExpensiveProduct() throws Exception;
	
}
