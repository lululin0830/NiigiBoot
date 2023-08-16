package tw.idv.tibame.orders.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PostMapping("/init")
	public String SupplierGetSubOrderInit(@RequestBody String data){
		
		JsonElement req = gson.fromJson(data, JsonElement.class);
		JsonObject reqjson = req.getAsJsonObject();
		String supplierId = reqjson.get("supplierId").getAsString();
		
		return(orderService.getSupplierSubOrderInit(supplierId));

	}	
	@PostMapping("/cnacelSubOrder")
	public String cnacelSubOrder(@RequestBody String data){
		
//		String subOrderId = dta.getParameter("subOrderId");
		String subOrderId = data;
		
		System.out.println(subOrderId);
		return(orderService.supplierSubOrderCancel(subOrderId));
		
		
	}

}
