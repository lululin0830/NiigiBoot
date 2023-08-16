package tw.idv.tibame.products.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import tw.idv.tibame.products.entity.Product;

public interface ProductService {

	public List<Product> getAllFindLatestProducts() throws Exception;

	public List<Product> getAllExpensiveProducts() throws Exception;

	public List<Product> getKeywordProducts(String keyword) throws Exception;

	public List<Product> getCategorieProducts(String categorie) throws Exception;

	public void saveProductImages(Integer productId, MultipartFile[] images) throws IOException;

	public Integer addProduct(String registerSupplier, String categorieId, String productName, String productPrice,
			String productInfo, String productStatus) throws Exception;

	public Integer getProductById(Integer productId, String registerSupplier) throws Exception;

	public Product getById(Integer productId) throws Exception;

	// 修改商品資料(不含圖)
	public Boolean updateProduct(Integer productId, String productName, String productInfo, String categorieId,
			Integer productPrice) throws Exception;


	public String productPageInit(Integer productId) throws Exception;
}
