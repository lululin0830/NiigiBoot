package tw.idv.tibame.orders.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tw.idv.tibame.orders.dao.MainOrderDAO;
import tw.idv.tibame.orders.dao.impl.MainOrderDAOImpl;
import tw.idv.tibame.orders.entity.MainOrder;
import tw.idv.tibame.orders.service.OrderService;
import tw.idv.tibame.orders.service.impl.OrderServiceImpl;

@WebServlet("/Test")
public class Test extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		MainOrderDAO dao = new MainOrderDAOImpl();
		OrderService service = new OrderServiceImpl();
		
		try {
			
			service.beginTransaction();
			
			MainOrder test = dao.selectById("20230715000000001");
			System.out.println(test);
			
			service.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
