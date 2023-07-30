package tw.idv.tibame.products.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import tw.idv.tibame.products.dao.ProductDAO;
import tw.idv.tibame.products.dao.ProductSpecDAO;
import tw.idv.tibame.products.entity.Product;
import tw.idv.tibame.products.entity.ProductSpec;
import tw.idv.tibame.products.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private ProductSpecDAO specDAO;
	@Autowired
	private Gson gson;
	
	@Override
	public String productPageInit(Integer productId) throws Exception {

		List<ProductSpec> spec = specDAO.selectByProductId(productId);
		
		
		return gson.toJson(spec);
	}
	
}
