package tw.idv.tibame.orders.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import tw.idv.tibame.core.LoginRequired;
import tw.idv.tibame.orders.dao.MainOrderDAO;
import tw.idv.tibame.orders.service.OrderService;

@RestController
@RequestMapping("/MemberCheckOrder")
@CrossOrigin(origins = "*")
public class MemberCheckOrder{
	
	@Autowired
	OrderService orderService;
	
	@LoginRequired
	@PostMapping("/orderAll")
	public String memberOrderAllInitString(@RequestBody String memberId, @RequestHeader("Authorization") String jwtToken){

		return orderService.memberCheckOrder(memberId);		
	}

	@LoginRequired
	@PostMapping("/subOrderDetail")
	public String checkOrderDetail(@RequestBody String subOrderId, @RequestHeader("Authorization") String jwtToken) {
		
		return orderService.checkOrderDetail(subOrderId);	
	}
	
	@LoginRequired
	@PatchMapping("/subOrderConfirmReceipt")
	public String ConfirmReceipt(@RequestBody String subOrderId, @RequestHeader("Authorization") String jwtToken) {

		return orderService.subOrderReceipt(subOrderId);
	}
	
	@LoginRequired
	@PostMapping("/cancelMainOrder")
	public String cancelMainOrder(@RequestBody String mainOrderId, @RequestHeader("Authorization") String jwtToken) {
		
		return orderService.cancelMainOrder(mainOrderId);
		
	}
	
	@LoginRequired
	@PostMapping("/cancelSubOrder")
	public String cancelSubOrder(@RequestBody String subOrderId, @RequestHeader("Authorization") String jwtToken) {
		return orderService.cancelSubOrder(subOrderId);
	}
	
	@LoginRequired
	@PostMapping("/subOrderDetailcomment")
	public String subOrderDetailcomment(@RequestBody String subOrderId, @RequestHeader("Authorization") String jwtToken) {
		return orderService.subOrderDetailcomment(subOrderId);
	}
	
	@LoginRequired
	@PostMapping("/updateSubOrderDetailComment")
	public String insertSubOrderDetailComment(@RequestBody String json, @RequestHeader("Authorization") String jwtToken) {
		System.out.println("Json"+json);
		return orderService.updateSubOrderDetailComment(json);
	}
	
	@LoginRequired
	@PostMapping("/updateRefund")
	public String orderRefundUpdate(@RequestBody String json, @RequestHeader("Authorization") String jwtToken) {
		System.out.println(json);
		orderService.orderRefundUpdate(json);
		return null;
	}
}
