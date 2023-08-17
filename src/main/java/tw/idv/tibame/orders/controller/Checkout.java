package tw.idv.tibame.orders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import tw.idv.tibame.core.LoginRequired;
import tw.idv.tibame.orders.service.OrderService;
import tw.idv.tibame.shoppingcart.service.ShoppingCartService;

@RestController
@RequestMapping
@CrossOrigin(origins = "*")
public class Checkout {

	@Autowired
	OrderService service;
	@Autowired
	ShoppingCartService cartService;

	@Autowired
	Gson gson;

	@LoginRequired
	@PostMapping("checkout")
	public ResponseEntity<String> checkout(@RequestBody String memberId,
			@RequestHeader("Authorization") String jwtToken) {

		return ResponseEntity.status(HttpStatus.OK).body(service.checkoutInit(memberId));

	}

	@LoginRequired
	@PostMapping("createOrder")
	public ResponseEntity<String> createOrder(@RequestBody String orderData,
			@RequestHeader("Authorization") String jwtToken) throws JsonSyntaxException, Exception {

		if (service.createOrder(gson.fromJson(orderData, JsonObject.class))) {
			return ResponseEntity.status(HttpStatus.OK).body(service.checkoutInit("訂單成立！！"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(service.checkoutInit("系統忙碌中"));
	}

	@PostMapping("clearCart")
	public void clearCart(@RequestBody String memberId) {

		cartService.deleteCart(memberId);

	}

}
