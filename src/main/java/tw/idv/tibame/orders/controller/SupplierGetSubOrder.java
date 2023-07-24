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
import tw.idv.tibame.orders.service.impl.OrderServiceImpl;


@WebServlet("/SupplierSubOrder")
public class SupplierGetSubOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private OrderService orderService;

	@Override
	public void init() throws ServletException {
		orderService = CommonUtils.getBean(getServletContext(), OrderService.class);
	};

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setHeader("Access-Control-Allow-Origin", "*"); // 允許來自所有網域的請求
		response.setHeader("Access-Control-Allow-Methods", "GET"); // 允許的 HTTP 方法
		response.setHeader("Access-Control-Allow-Headers", "Content-Type"); // 允許的請求Header
		response.setHeader("Access-Control-Allow-Credentials", "true"); // 是否允許帶有憑證的請求
		response.setContentType("application/json; charset=utf-8");
		
		Gson gson = new Gson();
		JsonElement req = gson.fromJson(request.getReader(), JsonElement.class);
		JsonObject reqjson = req.getAsJsonObject();
		String supplierId = reqjson.get("supplierId").getAsString();
				
		response.getWriter().print(orderService.getSupplierSubOrderInit(supplierId));

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
