package tw.idv.tibame.shoppingcart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import tw.idv.tibame.shoppingcart.service.ShoppingCartService;

@RestController
@RequestMapping("shoppingCart")
@CrossOrigin(origins = "*")
public class ShoppingCartController {

	@Autowired
	ShoppingCartService service;
	@Autowired
	Gson gson;
	
	@PutMapping("add")
	public ResponseEntity<String> addToCart (@RequestBody String data){
		

		if(service.addToCart(gson.fromJson(data, JsonObject.class) )) {
			return ResponseEntity.status(HttpStatus.OK).body("已加入購物車");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("系統繁忙中...請稍後再試");
		
	}
	
	@PostMapping()
	public ResponseEntity<String> initShoppingCart (@RequestBody String memberId) {
		
		System.out.println(memberId);
		
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.init(memberId));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("系統繁忙中...請稍後再試");
		}
	}
	
}
