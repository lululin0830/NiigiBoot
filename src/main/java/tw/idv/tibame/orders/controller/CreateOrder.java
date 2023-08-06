package tw.idv.tibame.orders.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tw.idv.tibame.core.util.CommonUtils;
import tw.idv.tibame.orders.service.OrderService;

@WebServlet("/CreateOrder")
public class CreateOrder extends HttpServlet {

	/**
	 * 成立訂單(2023-07-15 v1)
	 */
	private static final long serialVersionUID = 1L;

	private OrderService orderService;
	private Gson gson;

	@Override
	public void init() throws ServletException {
		orderService = CommonUtils.getBean(getServletContext(), OrderService.class);
		gson = CommonUtils.getBean(getServletContext(), Gson.class);
	};

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization");
		resp.setHeader("Access-Control-Allow-Credentials", "true");
		resp.setContentType("application/json; charset=utf-8");
		resp.setStatus(HttpServletResponse.SC_OK);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "POST");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization");
		resp.setHeader("Access-Control-Allow-Credentials", "true");
		resp.setContentType("application/json; charset=utf-8");

		JsonObject orderData = gson.fromJson(req.getReader(), JsonObject.class);

		PrintWriter out = resp.getWriter();

		try {
			out.print(orderService.createOrder(orderData) ? "訂單成立！！" : "系統忙碌中");
		} catch (Exception e) {
			e.printStackTrace();
			out.print("系統忙碌中");
		}
	}

}
