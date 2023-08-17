package tw.idv.tibame.orders.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/SupplierGetSubOrderBySearch")
public class SupplierGetSubOrderBySearch{
	
	
	private Gson gson;
	private OrderService orderService;
			
	@Autowired
	public SupplierGetSubOrderBySearch(Gson gson,OrderService orderService) {
		this.gson = gson;
		this.orderService = orderService;
	}
	
	@LoginRequired
	@PostMapping
	private String SupplierGetSubOrderBySearch(@RequestBody String data,@RequestHeader("Authorization") String jwtToken){
		
		JsonElement request = gson.fromJson(data, JsonElement.class);
		JsonObject searchCondition = request.getAsJsonObject();
		
		return(orderService.getSupplierSubOrderBySearch(searchCondition));
	}	
}
