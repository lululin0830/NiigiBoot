package tw.idv.tibame.products.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import jakarta.persistence.PersistenceContext;
import tw.idv.tibame.products.dao.ProductDAO;
import tw.idv.tibame.products.entity.Product;

@Repository
public class ProductDAOImpl implements ProductDAO {

	@PersistenceContext
	private Session session;

	@Override
	public Boolean insert(Product entity) throws Exception {
		session.persist(entity);
		return true;
	}

	@Override
	public Product selectById(Integer id) throws Exception {
		return session.get(Product.class, id);
	}

	@Override
	public List<Product> getAll() throws Exception {
		return null;
	}

	@Override
	public Product update(Product newProduct) {
		return null;
	}

	@Override
	public String selectBySupplier(String supplierId) {
		return null;
	}

	@Override
	public String selectByKeywords(String[] keywords) {
		return null;
	}

	@Override
	public String selectByCategorie(String categorie) {
		return null;
	}

	@Override
	public List<String> getSupplierIdList(String productIds) {

		String sql = "select registerSupplier from Product where ProductId in (" + productIds
				+ ") group by registerSupplier;";
		NativeQuery<String> nativeQuery = session.createNativeQuery(sql, String.class);

		return nativeQuery.getResultList();
	}

}
