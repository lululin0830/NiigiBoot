package tw.idv.tibame.products.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import tw.idv.tibame.products.dao.ProductDAO;
import tw.idv.tibame.products.dao.ProductSpecDAO;
import tw.idv.tibame.products.entity.Product;
import tw.idv.tibame.products.entity.ProductSpec;
import tw.idv.tibame.products.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private ProductSpecDAO specDAO;
	@Autowired
	private Gson gson;
	
	@Override
	public String productPageInit(Integer productId) throws Exception {

		List<ProductSpec> spec = specDAO.selectByProductId(productId);
		
		
		return gson.toJson(spec);
	}
	

	public List<Product> getAllFindLatestProducts() throws Exception {
		return productDAO.findLatestProducts();

	}

	public List<Product> getAllExpensiveProducts() throws Exception {
		return productDAO.findMostExpensiveProduct();
	}

	public List<Product> getKeywordProducts(String keyword) throws Exception {
		String[] keywords = keyword.split("\\s+");
		return productDAO.selectByKeywords(keywords);
	}
	
	public List<Product> getCategorieProducts(String categorie) throws Exception{
		return productDAO.selectByCategorie(categorie);
	}

}
