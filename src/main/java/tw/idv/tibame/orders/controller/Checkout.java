package tw.idv.tibame.orders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.idv.tibame.orders.service.OrderService;

@RestController
@RequestMapping("checkout")
@CrossOrigin(origins = "*")
public class Checkout {
	
	@Autowired
	OrderService service;

	@PostMapping
	public ResponseEntity<String> checkout (@RequestBody String memberId){
		
		
		return ResponseEntity.status(HttpStatus.OK).body(service.checkoutInit(memberId));
		
	}
}
