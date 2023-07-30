package tw.idv.tibame.products.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    
	
}
