package tw.idv.tibame.orders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.idv.tibame.orders.service.OrderService;

@RestController
@RequestMapping("/MemberCheckOrder")
@CrossOrigin(origins = "*")
public class MemberCheckOrder{
	
	@Autowired
	OrderService orderService;
	
	@PostMapping("/orderAll")
	public String memberOrderAllInitString(@RequestBody String memberId){

		return orderService.memberCheckOrder(memberId);		
	}

	@PostMapping("/subOrderDetail")
	public String checkOrderDetail(@RequestBody String subOrderId) {
		
		return orderService.checkOrderDetail(subOrderId);	
	}
	
	@PutMapping("/subOrderConfirmReceipt")
	public String ConfirmReceipt(@RequestBody String subOrderId) {

		return orderService.subOrderReceipt(subOrderId);
	}

}
