package tw.idv.tibame.events.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.idv.tibame.events.entity.EventApplicableProducts;
import tw.idv.tibame.events.entity.EventSingleThreshold;
import tw.idv.tibame.events.service.EventApplicableProductsService;
import tw.idv.tibame.events.service.EventSingleThresholdService;
import tw.idv.tibame.events.service.impl.EventSingleThresholdServiceImpl;

@RestController
@RequestMapping("/AddEvent")
@CrossOrigin(origins = "*")
public class AddEvent {

	@Autowired
	EventSingleThresholdService service;
	
	@Autowired
	EventSingleThresholdServiceImpl ESservice;
	
	@Autowired
	EventApplicableProductsService EAservice;
	
	@PostMapping("/addEvent")
	public ResponseEntity<String> addEvent(@RequestBody EventSingleThreshold newEvent) {

		String message;
		try {
			message = service.addEvent(newEvent);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("系統繁忙中...請稍後再試");
		}

		if (Objects.equals(message, "新增成功")) {

			return ResponseEntity.status(HttpStatus.OK).body(message);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
	}
	
	@GetMapping("/generateOrderId")
	public ResponseEntity<String> generateOrderId() {
	    String eventIdNew;
	    try {
	    	eventIdNew = ESservice.generateOrderId();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("系統繁忙中...請稍後再試");
	    }
	    
	    return ResponseEntity.status(HttpStatus.OK).body(eventIdNew);
	}
	
	@PostMapping("/addProduct")
	public ResponseEntity<String> addProduct(@RequestBody EventApplicableProducts addProducts) {

		String message;
		try {
			message = EAservice.addProduct(addProducts);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("系統繁忙中...請稍後再試");
		}

		if (Objects.equals(message, "新增成功")) {

			return ResponseEntity.status(HttpStatus.OK).body(message);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
	}
}
