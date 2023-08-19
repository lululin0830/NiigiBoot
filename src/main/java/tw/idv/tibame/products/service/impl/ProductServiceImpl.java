package tw.idv.tibame.products.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import tw.idv.tibame.events.dao.EventApplicableProductsDAOImpl;
import tw.idv.tibame.events.dao.EventSingleThresholdDAOImpl;
import tw.idv.tibame.events.entity.EventSingleThreshold;
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
	private EventSingleThresholdDAOImpl eventInfoDAO;
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

		// 查詢商品&商品規格
		List<ProductSpec> spec = specDAO.selectByProductId(productId);
		Product product = spec.get(0).getProduct();
		final int price = product.getProductPrice();
		result.add(spec.stream().filter(p -> p.getShelvesStatus().equals("0")).collect(Collectors.toList()));

		// 查詢活動
		List<Object[]> events = eventDAO.selectAllByProductId(productId);
		List<EventSingleThreshold> coupon = eventInfoDAO.selectAllCouponByProductId(productId);
		List<EventSingleThreshold> discountRate = eventInfoDAO.selectAllDiscountRateByProductId(productId);
		List<EventSingleThreshold> discountAmount = eventInfoDAO.selectAllDiscountAmountByProductId(productId);

		// 查詢評價
		List<Object[]> comments = detailDAO.selectCommentByProductId(productId);

		// 推薦商品
		List<Object> sameShopProduct = productDAO.selectSameShopProductByProductId(productId);

		// result加入活動資訊
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

		// 計算商家平均評分
		Map<String, Object> shopAvgRating = new HashMap<>();
		shopAvgRating.put("shopAvgRating", detailDAO.selectAvgRatingByProductId(productId));
		result.add(shopAvgRating);

		// result 加入推薦商品
		result.add(sameShopProduct);

		// 計算活動價
		OptionalInt couponPrice = coupon.stream()
				.filter(c -> (c.getMinPurchaseAmount() != null && c.getMinPurchaseAmount() <= price)
						|| (c.getMinPurchaseQuantity() != null && c.getMinPurchaseQuantity() <= 1))
				.mapToInt(c -> (int) (c.getDiscountAmount() != null ? price - c.getDiscountAmount()
						: price * c.getDiscountRate()))
				.min();

		int eventPrice = couponPrice.orElse(price);

		List<EventSingleThreshold> allDiscounts = Stream.concat(discountRate.stream(), discountAmount.stream())
				.collect(Collectors.toList());

		for (EventSingleThreshold e : allDiscounts) {

			if (eventPrice == 0) {

				if ((e.getMinPurchaseQuantity() != null && e.getMinPurchaseQuantity() <= 1)
						|| (e.getMinPurchaseAmount() != null && e.getMinPurchaseAmount() <= eventPrice)) {

					eventPrice = e.getDiscountAmount() != null ? price - e.getDiscountAmount()
							: (int) (price * e.getDiscountRate());
				}

			} else {

				if ((e.getMinPurchaseQuantity() != null && e.getMinPurchaseQuantity() <= 1)
						|| (e.getMinPurchaseAmount() != null && e.getMinPurchaseAmount() <= price)) {

					eventPrice = e.getDiscountAmount() != null ? eventPrice - e.getDiscountAmount()
							: (int) (eventPrice * e.getDiscountRate());
				}
			}
		}

		result.add(eventPrice);

		return gson.toJson(result);
	}

	public void saveProductImages(Integer productId, MultipartFile[] images, List<Integer> imgName) throws IOException {

		Product product = productRepository.findById(productId).orElse(null);
		if (product == null) {
			throw new IllegalArgumentException("Product with ID " + productId + " not found.");
		}
		
		for (int i = 0; i < images.length; i++) {
			MultipartFile image = images[i];
			byte[] imageData = image.getBytes();
			switch (imgName.get(i)) {
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

	@Override
	public List<Product> getAllFindLatestProducts(Integer num) throws Exception {
		return productDAO.findLatestProducts(num);

	}

	@Override
	public List<Product> getAllExpensiveProducts(Integer num) throws Exception {
		return productDAO.findMostExpensiveProduct(num);
	}

	@Override
	public List<Product> getKeywordProducts(String keyword) throws Exception {
		String[] keywords = keyword.split("\\s+");
		return productDAO.selectByKeywords(keywords);
	}

	@Override
	public List<Product> getCategorieProducts(String categorie) throws Exception {
		return productDAO.selectByCategorie(categorie);
	}

	@Override
	public Integer addProduct(String registerSupplier, String categorieId, String productName, String productPrice,
			String productInfo, String productStatus) throws Exception {
		Product products = new Product();
		products.setRegisterSupplier(registerSupplier);
		products.setCategorieId(categorieId);
		products.setProductName(productName);
		products.setProductPrice(Integer.parseInt(productPrice));
		products.setProductInfo(productInfo);
		products.setProductStatus(productStatus);
		productDAO.insert(products);
		return products.getProductId();
	}

	@Override
	public Integer getProductById(Integer productId, String registerSupplier) throws Exception {
		return productDAO.selectByProductId(productId, registerSupplier);
	}
	

	@Override
	public Product getById(Integer productId) throws Exception {
		return productDAO.selectById(productId);
	}
	
	
	// 修改商品資料(不含圖)
	@Override
	public Boolean updateProduct(Integer productId, String productName, String productInfo,
			Integer productPrice) throws Exception {
		Product product = new Product();
		product.setProductId(productId);
		product.setProductName(productName);
		product.setProductInfo(productInfo);
		product.setProductPrice(productPrice);
		return productDAO.update(product);
	}

}
