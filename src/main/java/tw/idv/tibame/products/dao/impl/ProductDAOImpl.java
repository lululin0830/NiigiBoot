package tw.idv.tibame.products.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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
	    StringBuilder sql = new StringBuilder().append("SELECT p.*, s.shopName ").append("FROM Product p ")
	            .append("LEFT JOIN Suppliers s ON p.registerSupplier = s.supplierId ").append("WHERE 1=1 ")
	            .append("AND p.productStatus = '0' ").append("AND ("); // 新增商品狀態條件

	    Map<String, Object> parameters = new HashMap<>();

	    // 動態添加搜尋條件
	    for (int i = 0; i < keywords.length; i++) {
	        String paramName = "keyword" + i;
	        if (i > 0) {
	            sql.append(" OR ");
	        }
	        sql.append("(p.productName LIKE :").append(paramName).append(" OR p.productInfo LIKE :").append(paramName).append(")");

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

}
