package tw.idv.tibame.users.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tw.idv.tibame.core.util.CommonUtils;
import tw.idv.tibame.users.dao.UserDAO;
import tw.idv.tibame.users.entity.Users;

@WebServlet("/Test02")
public class Test extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7194442875656370356L;
	
	UserDAO dao;

	@Override
	public void init() throws ServletException {
		dao = CommonUtils.getBean(getServletContext(), UserDAO.class);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			Users users = new Users();
			users.setUserName("12345");
			users.setUserAcct("12345");
			users.setPassword("12345");
			Boolean b = dao.insert(users);
			System.out.println(b);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
