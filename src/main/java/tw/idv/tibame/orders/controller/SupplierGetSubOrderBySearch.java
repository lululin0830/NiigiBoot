package tw.idv.tibame.orders.controller;

import java.io.IOException;

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
@WebServlet("/SupplierGetSubOrderBySearch")
public class SupplierGetSubOrderBySearch extends HttpServlet{

	private OrderService orderService;
	private Gson gson;
	
	@Override
	public void init() throws ServletException {
		orderService = CommonUtils.getBean(getServletContext(), OrderService.class);
		gson = CommonUtils.getBean(getServletContext(), Gson.class);
	};
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1426976558004822266L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Access-Control-Allow-Origin", "*"); // 允許來自所有網域的請求
		resp.setHeader("Access-Control-Allow-Methods", "GET"); // 允許的 HTTP 方法
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type"); // 允許的請求Header
		resp.setHeader("Access-Control-Allow-Credentials", "true"); // 是否允許帶有憑證的請求
		resp.setContentType("application/json; charset=utf-8");
		
		JsonElement request = gson.fromJson(req.getReader(), JsonElement.class);
		JsonObject searchCondition = request.getAsJsonObject();
		
		resp.getWriter().print(orderService.getSupplierSubOrderBySearch(searchCondition));
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
		resp.setHeader("Access-Control-Allow-Credentials", "true");
		resp.setContentType("application/json; charset=utf-8");
		resp.setStatus(HttpServletResponse.SC_OK);
	}	
}
