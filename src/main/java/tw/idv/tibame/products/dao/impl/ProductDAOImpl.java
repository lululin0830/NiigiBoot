package tw.idv.tibame.products.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	// 獲取全部的商品
	@Override
	public List<Product> getAll() throws Exception {
		return session.createQuery("from Product", Product.class).list();
	}

	// 修改
	@Override
	public Product update(Product newProduct) {
		Product product = null;

		try {
			product = session.get(Product.class, newProduct.getProductId());

			final String categorieId = newProduct.getCategorieId();
			final String productName = newProduct.getProductName();
			final Integer productPrice = newProduct.getProductPrice();
			final String productInfo = newProduct.getProductInfo();

			if (categorieId != null && !categorieId.isBlank()) {
				product.setCategorieId(categorieId);
			}
			if (productName != null && !productName.isBlank()) {
				product.setProductName(productName);
			}
			if (productPrice != null) {
				product.setProductPrice(productPrice);
			}
			if (productInfo != null && !productInfo.isBlank()) {
				product.setProductInfo(productInfo);
			}
			return product;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 進店逛逛
	@Override
	public List<Product> selectBySupplier(String supplierIds) {
		String sql = "SELECT * FROM Product WHERE registerSupplier = :supplierIds AND productStatus = '0';";
		NativeQuery<Product> nativeQuery = session.createNativeQuery(sql, Product.class);
		nativeQuery.setParameter("supplierIds", supplierIds);
		return nativeQuery.getResultList();
	}

	// 關鍵字搜尋
	@Override
	public List<Product> selectByKeywords(String[] keywords) {
		StringBuilder sql = new StringBuilder().append("SELECT * ").append("FROM Product ")
//				.append("LEFT JOIN Suppliers s ON p.registerSupplier = s.supplierId ")
				.append("WHERE 1=1 ").append("AND productStatus = '0' ").append("AND ("); // 新增商品狀態條件

		Map<String, Object> parameters = new HashMap<>();

		// 動態添加搜尋條件
		for (int i = 0; i < keywords.length; i++) {
			String paramName = "keyword" + i;
			if (i > 0) {
				sql.append(" OR ");
			}
			sql.append("(productName LIKE :").append(paramName).append(" OR productInfo LIKE :").append(paramName)
					.append(")");

			parameters.put(paramName, "%" + keywords[i] + "%");
		}
		sql.append(") ");

		NativeQuery<Product> nativeQuery = session.createNativeQuery(sql.toString(), Product.class);

		// 設置參數值
		for (String paramName : parameters.keySet()) {
			nativeQuery.setParameter(paramName, parameters.get(paramName));
		}

		return nativeQuery.getResultList();

	}

	// 分類頁查詢
	@Override
	public List<Product> selectByCategorie(String categorie) {
		String sql = "SELECT p.*, c.categorieName AS subCategoryName, mc.categorieName AS mainCategoryName, s.shopName "
				+ "FROM Product p " + "INNER JOIN Categorie c ON p.categorieId = c.categorieId "
				+ "LEFT JOIN Categorie mc ON c.mainCategorie = mc.categorieId "
				+ "LEFT JOIN Suppliers s ON p.registerSupplier = s.supplierId "
				+ "WHERE ( c.categorieName = :categorie OR mc.categorieName = :categorie ) "
				+ "AND p.productStatus = '0';";

		NativeQuery<Product> nativeQuery = session.createNativeQuery(sql, Product.class);
		nativeQuery.setParameter("categorie", categorie);
		return nativeQuery.getResultList();
	}

	@Override
	public List<String> getSupplierIdList(String productIds) {

		String sql = "select registerSupplier from Product where ProductId in (" + productIds
				+ ") group by registerSupplier;";
		NativeQuery<String> nativeQuery = session.createNativeQuery(sql, String.class);

		return nativeQuery.getResultList();
	}

	@Override
	public List<Product> findLatestProducts() throws Exception {
		return session.createQuery("FROM Product ORDER BY firstOnShelvesDate", Product.class).getResultList();
	}

	@Override
	public List<Product> findMostExpensiveProduct() throws Exception {
		return session.createQuery("FROM Product ORDER BY productPrice DESC", Product.class).getResultList();
	}

	@Override
	public List<Object> selectSameShopProductByProductId(Integer productId) {
		String sql = "SELECT productId,productName,productPrice,picture1 FROM Product "
				+ "WHERE registerSupplier = ( SELECT registerSupplier FROM Product WHERE productId = :productId ) "
				+ "AND productStatus = '0' AND productId != :productId ORDER BY firstOnShelvesDate DESC ";
		return session.createNativeQuery(sql, Object.class).setParameter("productId", productId).setFirstResult(0)
				.setMaxResults(3).getResultList();
	}

	// 修改上下架狀態
	@Override
	public Boolean updateStatus(Integer productId, String productStatus) throws Exception {
		String sql = "UPDATE Product SET productStatus = :productStatus WHERE productId = :productId";
		NativeQuery<Boolean> nativeQuery = session.createNativeQuery(sql, Boolean.class);
		nativeQuery.setParameter("productStatus", productStatus);
		nativeQuery.setParameter("productId", productId);
		int updatedCount = nativeQuery.executeUpdate();
		return updatedCount > 0;
	}

	// 以商品編號查詢輸入者是否正確(編輯商品用)
	@Override
	public Integer selectByProductId(Integer productId, String registerSupplier) {
		String sql = "select count(*) from Product where productId = :productId and registerSupplier = :registerSupplier";
		NativeQuery<Integer> nativeQuery = session.createNativeQuery(sql, Integer.class);
		nativeQuery.setParameter("productId", productId);
		nativeQuery.setParameter("registerSupplier", registerSupplier);
		return nativeQuery.uniqueResult();
	}

}
