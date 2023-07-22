package tw.idv.tibame.orders.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tw.idv.tibame.orders.service.OrderService;
import tw.idv.tibame.orders.service.impl.OrderServiceImpl;
/**
 * Servlet implementation class SubOrder
 */
@WebServlet("/SubOrder")
public class SupplierGetSubOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setHeader("Access-Control-Allow-Origin", "*"); // 允許來自所有網域的請求
		response.setHeader("Access-Control-Allow-Methods", "GET"); // 允許的 HTTP 方法
		response.setHeader("Access-Control-Allow-Headers", "Content-Type"); // 允許的請求Header
		response.setHeader("Access-Control-Allow-Credentials", "true"); // 是否允許帶有憑證的請求
		response.setContentType("application/json; charset=utf-8");		
		
		OrderService orderService = new OrderServiceImpl();
		response.getWriter().print(orderService.getSupplierSubOrderInit(""));
		
	}

}
