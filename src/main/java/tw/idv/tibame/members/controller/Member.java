package tw.idv.tibame.members.controller;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tw.idv.tibame.core.util.CommonUtils;
import tw.idv.tibame.members.entity.Members;
import tw.idv.tibame.members.service.MemberService;

@WebServlet("/MemberSelect")
public class Member extends HttpServlet {
	/**
	 * 顯示後台的會員畫面跟搜尋
	 */
	private static final long serialVersionUID = 4241331332431629387L;

	private MemberService memberService;
	private Gson gson;

	@Override
	public void init() throws ServletException {
		memberService = CommonUtils.getBean(getServletContext(), MemberService.class);
		gson = CommonUtils.getBean(getServletContext(), Gson.class);
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

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
		resp.setHeader("Access-Control-Allow-Credentials", "true");
		resp.setContentType("application/json; charset=utf-8");

		List<Members> members = memberService.getAll();

		String jsonmember = gson.toJson(members);

		resp.getWriter().print(jsonmember);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
		resp.setHeader("Access-Control-Allow-Credentials", "true");
		resp.setContentType("application/json; charset=utf-8");

		JsonElement reqs = gson.fromJson(req.getReader(), JsonElement.class);
		JsonObject searchCondition = reqs.getAsJsonObject();
		
		resp.getWriter().print(memberService.getBySearch(searchCondition));
		System.out.println((memberService.getBySearch(searchCondition)));
	}

}
