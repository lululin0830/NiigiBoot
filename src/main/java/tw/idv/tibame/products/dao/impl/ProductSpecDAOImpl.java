package tw.idv.tibame.products.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import jakarta.persistence.PersistenceContext;
import tw.idv.tibame.products.dao.ProductSpecDAO;
import tw.idv.tibame.products.entity.ProductSpec;

@Repository
public class ProductSpecDAOImpl implements ProductSpecDAO {

	@PersistenceContext
	private Session session;
	
	@Override
	public Boolean insert(ProductSpec entity) throws Exception {
		return null;
	}

	@Override
	public ProductSpec selectById(String id) throws Exception {
		return session.get(ProductSpec.class, id);
	}

	@Override
	public List<ProductSpec> getAll() throws Exception {
		return null;
	}

	@Override
	public ProductSpec update(ProductSpec newProductSpec) {
		return null;
	}

}
