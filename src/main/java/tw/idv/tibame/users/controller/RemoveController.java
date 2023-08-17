package tw.idv.tibame.users.controller;

import java.io.IOException;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tw.idv.tibame.core.util.CommonUtils;
import tw.idv.tibame.users.entity.Users;
import tw.idv.tibame.users.service.UserService;
@WebServlet("/Remove")
public class RemoveController extends HttpServlet{

	/**
	 * 移除
	 */
	private static final long serialVersionUID = -5105297640809688078L;
	
	private UserService userService;
	private Gson gson;
	@Override
	public void init() throws ServletException {
		userService = CommonUtils.getBean(getServletContext(), UserService.class);
		gson = CommonUtils.getBean(getServletContext(), Gson.class);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Access-Control-Allow-Origin", "*"); // 允許來自所有網域的請求
		resp.setHeader("Access-Control-Allow-Methods", "GET"); // 允許的 HTTP 方法
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type"); // 允許的請求Header
		resp.setHeader("Access-Control-Allow-Credentials", "true"); // 是否允許帶有憑證的請求
		resp.setContentType("application/json; charset=utf-8");
		
		Integer users = gson.fromJson(req.getReader(), Users.class).getUserId();
		
		boolean remove = userService.remove(users);
		
		String jsonRespone = gson.toJson(remove);
		
		resp.getWriter().write(jsonRespone);
	}
}
