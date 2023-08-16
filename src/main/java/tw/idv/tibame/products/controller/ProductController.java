package tw.idv.tibame.products.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tw.idv.tibame.products.entity.Product;
import tw.idv.tibame.products.service.ProductService;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*")
public class ProductController {

	@Autowired
	private ProductService service;

	@GetMapping("/{productId}")
	public ResponseEntity<String> productPageInit(@PathVariable Integer productId) {

		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.productPageInit(productId));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(String.format("error:%s , errMsg: %s", "系統繁忙中...請稍後再試", e.getMessage()));

		}

	}

	@GetMapping("/products")
	public List<Product> getAllFindLatestProducts() throws Exception {
		System.out.println("into controller");
		return service.getAllFindLatestProducts();
	}

	@GetMapping("/exproducts")
	public List<Product> getAllExpensiveProducts() throws Exception {
		System.out.println("into controller");
		return service.getAllExpensiveProducts();
	}

	@GetMapping("/search")
	public ResponseEntity<List<Product>> searchProducts(@RequestParam("search") String keyword) throws Exception {
		// 在這裡進行搜尋和處理搜尋結果，我們假設你有一個Service來處理搜尋邏輯
		List<Product> searchResults = service.getKeywordProducts(keyword);
		return ResponseEntity.ok(searchResults);
	}

	@GetMapping("/searchCategorie")
	public ResponseEntity<List<Product>> getCategorieProducts(@RequestParam("text") String categorie) throws Exception {
		// 在這裡處理接收前端傳來的文字，並調用Service方法來處理資料
		List<Product> categorieProducts = service.getCategorieProducts(categorie);
		// 回傳處理後的資料給前端
		return ResponseEntity.ok(categorieProducts);
	}

	@PostMapping("/upload")
	public ResponseEntity<String> uploadImages(@RequestParam Integer productId, @RequestParam MultipartFile[] images) {
		try {
			service.saveProductImages(productId, images);

			return ResponseEntity.status(HttpStatus.OK).body("上傳成功");

		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(String.format("error:%s , errMsg: %s", "系統繁忙中...請稍後再試", e.getMessage()));
		}
	}

	@PostMapping("/insertProduct")
	public Integer createProduct(@RequestBody Map<String, String> requestData) throws Exception {
		String registerSupplier = requestData.get("registerSupplier");
		String categorieId = requestData.get("categorieId");
		String productName = requestData.get("productName");
		String productPrice = requestData.get("productPrice");
		String productInfo = requestData.get("productInfo");
		String productStatus = requestData.get("productStatus");
		return service.addProduct(registerSupplier, categorieId, productName, productPrice, productInfo, productStatus);
	}

	@PostMapping("/product-details")
	public Integer getProductDetails(@RequestBody Map<String, String> requestData) throws Exception {
		Integer productId = Integer.parseInt(requestData.get("productId"));
		String registerSupplier = requestData.get("registerSupplier");
		return service.getProductById(productId, registerSupplier);
	}

	@GetMapping("/retrieveProductText")
	public Product retrieveProductById(@RequestParam String productId) throws Exception {
		return service.getById(Integer.parseInt(productId));
	}
	
	// 修改規格資料(不含圖)
	@PostMapping("/updateProductText")
	public Boolean updateProductText(@RequestBody Map<String, String> requestData) throws Exception {
		Integer productId = Integer.parseInt(requestData.get("productId"));
		String productName = requestData.get("productName");
		String productInfo = requestData.get("productInfo");
		String categorieId = requestData.get("categorieId");
		Integer productPrice = Integer.parseInt(requestData.get("productPrice"));
		return service.updateProduct(productId,productName,productInfo,categorieId,productPrice);
	}

}
