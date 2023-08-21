package tw.idv.tibame.orders.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tw.idv.tibame.core.LoginRequired;
import tw.idv.tibame.core.util.CommonUtils;
import tw.idv.tibame.orders.service.OrderService;
import tw.idv.tibame.orders.service.impl.OrderServiceImpl;

@RestController
@RequestMapping("/SupplierSubOrder")
public class SupplierGetSubOrder{

	private OrderService orderService;
	private Gson gson;
		
	public SupplierGetSubOrder(OrderService orderService, Gson gson) {
		super();
		this.orderService = orderService;
		this.gson = gson;
	}

	@LoginRequired
	@PostMapping("/init")
	public String SupplierGetSubOrderInit(@RequestBody String data, @RequestHeader("Authorization") String jwtToken){
		
		JsonElement req = gson.fromJson(data, JsonElement.class);
		JsonObject reqjson = req.getAsJsonObject();
		String supplierId = reqjson.get("supplierId").getAsString();
		
		return(orderService.getSupplierSubOrderInit(supplierId));

	}	
	
	@LoginRequired
	@PostMapping("/cnacelSubOrder")
	public String cnacelSubOrder(@RequestBody String data, @RequestHeader("Authorization") String jwtToken){
		
//		String subOrderId = dta.getParameter("subOrderId");
		String subOrderId = data;
		
		System.out.println(subOrderId);
		return orderService.supplierSubOrderCancel(subOrderId);
		
		
	}
	
	@LoginRequired
	@PostMapping("orderStatusDeliver")
	public String orderStatusDeliver(@RequestBody String data,@RequestHeader("Authorization") String jwtToken){
		return orderService.orderStatusDeliver(data);
	}
	
	
	@LoginRequired
	@PostMapping("orderStatusComplete")
	public String orderStatusComplete(@RequestBody String data,@RequestHeader("Authorization") String jwtToken) {
		return orderService.orderStatusComplete(data);
	}
	
	@LoginRequired
	@PostMapping("orderStatusCancel")
	public String orderStatusCancel(@RequestBody String data,@RequestHeader("Authorization") String jwtToken) {
		return orderService.orderStatusCancel(data);
	}

}
