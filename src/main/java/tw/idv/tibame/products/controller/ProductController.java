package tw.idv.tibame.products.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.idv.tibame.products.entity.Product;
import tw.idv.tibame.products.service.ProductService;

@RestController
//@RequestMapping
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/products")
	public List<Product> getAllFindLatestProducts() throws Exception {
		System.out.println("into controller");
		return productService.getAllFindLatestProducts();
	}

	@GetMapping("/exproducts")
	public List<Product> getAllExpensiveProducts() throws Exception {
		System.out.println("into controller");
		return productService.getAllExpensiveProducts();
	}

	@GetMapping("/search")
	public ResponseEntity<List<Product>> searchProducts(@RequestParam("search") String keyword) throws Exception {
		// 在這裡進行搜尋和處理搜尋結果，我們假設你有一個Service來處理搜尋邏輯
		List<Product> searchResults = productService.getKeywordProducts(keyword);
		return ResponseEntity.ok(searchResults);
	}

    @GetMapping("/searchCategorie")
	public ResponseEntity<List<Product>> getCategorieProducts(@RequestParam("text") String categorie) throws Exception {
        // 在這裡處理接收前端傳來的文字，並調用Service方法來處理資料
        List<Product> categorieProducts = productService.getCategorieProducts(categorie);
        // 回傳處理後的資料給前端
        return ResponseEntity.ok(categorieProducts);
	}

}
