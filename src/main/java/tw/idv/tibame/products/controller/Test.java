package tw.idv.tibame.products.controller;


import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tw.idv.tibame.products.dao.ProductDAO;
import tw.idv.tibame.products.dao.ProductSpecDAO;
import tw.idv.tibame.products.dao.impl.ProductDAOImpl;
import tw.idv.tibame.products.dao.impl.ProductSpecDAOImpl;
import tw.idv.tibame.products.entity.Product;
import tw.idv.tibame.products.entity.ProductSpec;
import tw.idv.tibame.products.service.ProductService;
import tw.idv.tibame.products.service.impl.ProductServiceImpl;

@WebServlet("/Test01")
public class Test extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8199356609082890875L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		ProductService service = new ProductServiceImpl();
		
		ProductDAO dao1 = new ProductDAOImpl();
		ProductSpecDAO dao2 = new ProductSpecDAOImpl();
		
		try {
			
			service.beginTransaction();
			
			Product product = dao1.selectById(10000001);
			ProductSpec productSpec = dao2.selectById("10000001001");
			
			System.out.println(product);
			System.out.println(productSpec);
			
			
			service.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
