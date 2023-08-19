package tw.idv.tibame.events.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.idv.tibame.events.dao.EventApplicableProductsDAOImpl;
import tw.idv.tibame.events.entity.EventApplicableProducts;
import tw.idv.tibame.events.service.EventApplicableProductsService;

@Service
@Transactional
public class EventApplicableProductsServiceImpl implements EventApplicableProductsService{

	@Autowired
	EventApplicableProductsDAOImpl dao;
	
	@Override
	public String addProduct(EventApplicableProducts addProducts) throws Exception{

		dao.insert(addProducts);
		return "新增成功";
	}
}
