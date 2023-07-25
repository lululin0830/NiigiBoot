package tw.idv.tibame.products.controller;


import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tw.idv.tibame.core.util.CommonUtils;
import tw.idv.tibame.products.dao.ProductDAO;
import tw.idv.tibame.products.dao.ProductSpecDAO;
import tw.idv.tibame.products.entity.Product;
import tw.idv.tibame.products.entity.ProductSpec;

@WebServlet("/Test01")
public class Test extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8199356609082890875L;

	ProductDAO dao1;
	ProductSpecDAO dao2;
	
	@Override	
	public void init() throws ServletException {
		dao1 = CommonUtils.getBean(getServletContext(), ProductDAO.class);
		dao2 = CommonUtils.getBean(getServletContext(), ProductSpecDAO.class);
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		

		
		try {
			//新增
//			Product products = new Product();
//			products.setRegisterSupplier("S000000001");
//			products.setCategorieId("C000000007");
//			products.setProductName("USB隨身碟粉色系");
//			products.setProductPrice(240);
//			products.setProductInfo("1GB儲存容量");
//			products.setProductStatus("0");
//			Boolean b = dao1.insert(products);
//			System.out.println(b);
			
			
			//selectById
//			Product product = dao1.selectById(10000002);
//			ProductSpec productSpec = dao2.selectById("10000001001");
//			System.out.println(product);
//			System.out.println(productSpec);
			
			
			//getall
//			List<Product> product = dao1.getAll();
//		    for (Product p : product) {
//		        System.out.println("Product ID: " + p.getProductId());
//		        System.out.println("Product Name: " + p.getProductName());
//		        System.out.println("Product Price: " + p.getProductPrice());
//		        // 顯示其他屬性
//		        // ...
//		        System.out.println(); // 換行分隔每個 Product 的資訊
//		    }
			
			//update
//			Product products = new Product();
//			products.setProductId(10000116);
//			products.setCategorieId("C000000007");
//			products.setProductName("USB隨身碟金色系");
//			products.setProductPrice(20);
//			products.setProductInfo("10000GB儲存容量");
//			dao1.update(products);
			
			//進店逛逛
//			List<Product> product = dao1.selectBySupplier("S000000001");
//		    for (Product p : product) {
//		        System.out.println("Product ID: " + p.getProductId());
//		        System.out.println("Product Name: " + p.getProductName());
//		        System.out.println("Product Price: " + p.getProductPrice());
//		        // 顯示其他屬性
//		        // ...
//		        System.out.println(); // 換行分隔每個 Product 的資訊
//		    }
			
			
			//// 關鍵字搜尋
//			String[] keywords = {"冰箱","Usb"};
//			List<Product> product = dao1.selectByKeywords(keywords);
//		    for (Product p : product) {
//	        System.out.println("Product ID: " + p.getProductId());
//	        System.out.println("Product Name: " + p.getProductName());
//	        System.out.println("Product Price: " + p.getProductPrice());
//	        // 顯示其他屬性
//	        // ...
//	        System.out.println(); // 換行分隔每個 Product 的資訊
//	    }
			
			
			//分類頁查詢
			List<Product> product = dao1.selectByCategorie("3C周邊");
		    for (Product p : product) {
	        System.out.println("Product ID: " + p.getProductId());
	        System.out.println("Product Name: " + p.getProductName());
	        System.out.println("Product Price: " + p.getProductPrice());
	        // 顯示其他屬性
	        // ...
	        System.out.println(); // 換行分隔每個 Product 的資訊
	    }
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
