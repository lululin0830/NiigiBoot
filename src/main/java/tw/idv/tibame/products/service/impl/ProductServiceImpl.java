package tw.idv.tibame.products.service.impl;

import java.io.IOException;
import java.sql.Blob;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import tw.idv.tibame.events.dao.EventApplicableProductsDAOImpl;
import tw.idv.tibame.orders.dao.SubOrderDetailDAO;
import tw.idv.tibame.products.dao.ProductDAO;
import tw.idv.tibame.products.dao.ProductRepository;
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
	private EventApplicableProductsDAOImpl eventDAO;
	@Autowired
	private SubOrderDetailDAO detailDAO;
	@Autowired
	private Gson gson;

	private final ProductRepository productRepository;

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public String productPageInit(Integer productId) throws Exception {

		List<Object> result = new LinkedList<Object>();
		List<ProductSpec> spec = specDAO.selectByProductId(productId);
		List<Object[]> events = eventDAO.selectAllByProductId(productId);
		List<Object[]> comments = detailDAO.selectCommentByProductId(productId);
		List<Object> sameShopProduct = productDAO.selectSameShopProductByProductId(productId);

		result.add(spec.stream().filter(p -> p.getShelvesStatus().equals("0")).collect(Collectors.toList()));
		if (events.size() > 0) {
			result.add(events);
		} else {
			result.add("noEvent");
		}

		if (comments.size() > 0) {
			result.add(comments);
		} else {
			result.add("noComment");
		}

		Map<String, Object> shopAvgRating = new HashMap<>();
		shopAvgRating.put("shopAvgRating", detailDAO.selectAvgRatingByProductId(productId));
		result.add(shopAvgRating);

		result.add(sameShopProduct);

		return gson.toJson(result);
	}

	 public void saveProductImages(Integer productId, MultipartFile[] images) throws IOException {
		 
	            Product product = productRepository.findById(productId).orElse(null);
	            if (product == null) {
	                throw new IllegalArgumentException("Product with ID " + productId + " not found.");
	            }

	            for (int i = 0; i < images.length; i++) {
	                MultipartFile image = images[i];
	                byte[] imageData = image.getBytes();
	                switch (i) {
	                    case 0:
	                        product.setPicture1(imageData);
	                        break;
	                    case 1:
	                        product.setPicture2(imageData);
	                        break;
	                    case 2:
	                        product.setPicture3(imageData);
	                        break;
	                    case 3:
	                        product.setPicture4(imageData);
	                        break;
	                    case 4:
	                        product.setPicture5(imageData);
	                        break;
	                }
	            }

	            productRepository.save(product);
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

	public List<Product> getCategorieProducts(String categorie) throws Exception {
		return productDAO.selectByCategorie(categorie);
	}

}
