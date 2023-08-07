package tw.idv.tibame.products.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
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
		session.persist(entity);
		return true;
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

	// 修改規格的上下架狀態
	@Override
	public Boolean updateStatus(String[] productSpecIds, String shelvesStatus) {
		StringBuilder sql = new StringBuilder().append("UPDATE ProductSpec SET ")
				.append("shelvesStatus = :shelvesStatus ").append("WHERE ");

		Map<String, Object> parameters = new HashMap<>();

		// 動態添加搜尋條件
		for (int i = 0; i < productSpecIds.length; i++) {
			String paramName = "productSpecId" + i;
			if (i > 0) {
				sql.append(" OR ");
			}
			sql.append("productSpecId = :").append(paramName);

			parameters.put(paramName, productSpecIds[i]);
		}
		NativeQuery<Boolean> nativeQuery = session.createNativeQuery(sql.toString(), Boolean.class);
		nativeQuery.setParameter("shelvesStatus", shelvesStatus);
		for (String paramName : parameters.keySet()) {
			nativeQuery.setParameter(paramName, parameters.get(paramName));
		}
		int updatedCount = nativeQuery.executeUpdate();
		return updatedCount > 0;

	}

	// 以商品編號查全部規格(不包含強制下架)
	@Override
	public List<ProductSpec> selectByProductId(Integer productIds) {
		String sql = "SELECT * FROM ProductSpec WHERE productId = :productIds and shelvesStatus != '2';";
		NativeQuery<ProductSpec> nativeQuery = session.createNativeQuery(sql, ProductSpec.class);
		nativeQuery.setParameter("productIds", productIds);
		return nativeQuery.getResultList();
	}

	// 以商品編號計算有幾種規格
	@Override
	public Integer selectByProductSpecId(Integer productId) {
		String sql = "select count(*) from ProductSpec where productId = :productIds";
		NativeQuery<Integer> nativeQuery = session.createNativeQuery(sql, Integer.class);
		nativeQuery.setParameter("productIds", productId);
		return nativeQuery.uniqueResult();
	}

	// 更新庫存
	@Override
	public Boolean updateSpecStock(String productSpecId, Integer specStock) {
		String sql = "update ProductSpec set specStock = :specStock where productSpecId = :productSpecId";
		NativeQuery<Boolean> nativeQuery = session.createNativeQuery(sql, Boolean.class);
		nativeQuery.setParameter("productSpecId", productSpecId);
		nativeQuery.setParameter("specStock", specStock);
		int updatedCount = nativeQuery.executeUpdate();
		return updatedCount > 0;
	}

	// 列出全部規格，all+BY商家編號
	@Override
	public List<ProductSpec> findInactiveSpecificationsBySupplierId(String registerSupplier) {
		String sql = "select ps.*, p.registerSupplier, p.productName, p.productPrice from ProductSpec ps inner join Product p on ps.productId = p.productId where shelvesStatus != '2' and registerSupplier = :registerSupplier";
		NativeQuery<ProductSpec> nativeQuery = session.createNativeQuery(sql, ProductSpec.class);
		nativeQuery.setParameter("registerSupplier", registerSupplier);
		return nativeQuery.getResultList();

	}

	// 列出全部規格，已下架或上架中+BY商家編號
	@Override
	public List<ProductSpec> findActiveSpecificationsBySupplierId(String shelvesStatus, String registerSupplier) {
		String sql = "select ps.*, p.registerSupplier, p.productName, p.productPrice from ProductSpec ps inner join Product p on ps.productId = p.productId where shelvesStatus = :shelvesStatus and registerSupplier = :registerSupplier";
		NativeQuery<ProductSpec> nativeQuery = session.createNativeQuery(sql, ProductSpec.class);
		nativeQuery.setParameter("shelvesStatus", shelvesStatus);
		nativeQuery.setParameter("registerSupplier", registerSupplier);
		return nativeQuery.getResultList();

	}

	// 列出全部規格，已售完(要去除強制下架)+BY商家編號
	@Override
	public List<ProductSpec> getAllSpecsForSoldOutProductsBySupplierId(String registerSupplier) {
		String sql = "select ps.*, p.registerSupplier, p.productName, p.productPrice from ProductSpec ps inner join Product p on ps.productId = p.productId where specStock = 0 and shelvesStatus != '2' and registerSupplier = :registerSupplier";
		NativeQuery<ProductSpec> nativeQuery = session.createNativeQuery(sql, ProductSpec.class);
		nativeQuery.setParameter("registerSupplier", registerSupplier);
		return nativeQuery.getResultList();
	}

	// 算出總數量，all+BY商家編號
	@Override
	public Integer getTotalCountOfActiveProductsBySupplierId(String registerSupplier) {
		String sql = "select count(*) from ProductSpec ps inner join Product p on ps.productId = p.productId where shelvesStatus != '2' and registerSupplier = :registerSupplier";
		NativeQuery<Integer> nativeQuery = session.createNativeQuery(sql, Integer.class);
		nativeQuery.setParameter("registerSupplier", registerSupplier);
		return nativeQuery.uniqueResult();
	}

	// 算出總數量，已下架或上架中+BY商家編號
	@Override
	public Integer getTotalCountOfProductsByStatusAndSupplierId(String shelvesStatus, String registerSupplier) {
		String sql = "select count(*) from ProductSpec ps inner join Product p on ps.productId = p.productId where shelvesStatus = :shelvesStatus and registerSupplier = :registerSupplier";
		NativeQuery<Integer> nativeQuery = session.createNativeQuery(sql, Integer.class);
		nativeQuery.setParameter("shelvesStatus", shelvesStatus);
		nativeQuery.setParameter("registerSupplier", registerSupplier);
		return nativeQuery.uniqueResult();

	}

	// 算出總數量，已售完(要去除強制下架)+BY商家編號
	@Override
	public Integer getCountForSoldOutProductsBySupplierId(String registerSupplier) {
		String sql = "select count(*) from ProductSpec ps inner join Product p on ps.productId = p.productId where specStock = 0 and shelvesStatus != '2' and registerSupplier = :registerSupplier";
		NativeQuery<Integer> nativeQuery = session.createNativeQuery(sql, Integer.class);
		nativeQuery.setParameter("registerSupplier", registerSupplier);
		return nativeQuery.uniqueResult();

	}

	// 以規格編號算出已售出多少數量+BY商品狀態(需0)(這個是借放的，等等要移位)
	@Override
	public Integer getSoldQuantityBySpecIdAndStatus(String productSpecId) {
		String sql = "select count(*) from SubOrderDetail where productSpecId = :productSpecId and itemStatus = '0'";
		NativeQuery<Integer> nativeQuery = session.createNativeQuery(sql, Integer.class);
		nativeQuery.setParameter("productSpecId", productSpecId);
		return nativeQuery.uniqueResult();
	}

	// 搜尋規格編號/商品名稱，all+BY商家編號
	@Override
	public List<ProductSpec> findInactiveSpecificationsBySupplierId(String optionName,String searchText,String registerSupplier) {
		String sql = "select ps.*, p.registerSupplier, p.productName, p.productPrice from ProductSpec ps inner join Product p on ps.productId = p.productId where " + optionName + " LIKE '%" + searchText + "%' and shelvesStatus != '2' and registerSupplier = '" + registerSupplier + "';";
		NativeQuery<ProductSpec> nativeQuery = session.createNativeQuery(sql, ProductSpec.class);
		return nativeQuery.getResultList();
	}

	// 搜尋規格編號/商品名稱，已下架或上架中+BY商家編號
	@Override
	public List<ProductSpec> findActiveSpecificationsBySupplierId(String optionName,String searchText,String shelvesStatus, String registerSupplier) {
		String sql = "select ps.*, p.registerSupplier, p.productName, p.productPrice from ProductSpec ps inner join Product p on ps.productId = p.productId where " + optionName + " LIKE '%" + searchText + "%' and shelvesStatus = :shelvesStatus and registerSupplier = :registerSupplier";
		NativeQuery<ProductSpec> nativeQuery = session.createNativeQuery(sql, ProductSpec.class);
		nativeQuery.setParameter("shelvesStatus", shelvesStatus);
		nativeQuery.setParameter("registerSupplier", registerSupplier);
		return nativeQuery.getResultList();

	}

	// 搜尋規格編號/商品名稱，已售完(要去除強制下架)+BY商家編號
	@Override
	public List<ProductSpec> getAllSpecsForSoldOutProductsBySupplierId(String optionName,String searchText,String registerSupplier) {
		String sql = "select ps.*, p.registerSupplier, p.productName, p.productPrice from ProductSpec ps inner join Product p on ps.productId = p.productId where " + optionName + " LIKE '%" + searchText + "%' and specStock = 0 and shelvesStatus != '2' and registerSupplier = :registerSupplier";
		NativeQuery<ProductSpec> nativeQuery = session.createNativeQuery(sql, ProductSpec.class);
		nativeQuery.setParameter("registerSupplier", registerSupplier);
		return nativeQuery.getResultList();
	}

	// 搜尋規格編號/商品名稱的總數量，all+BY商家編號
	@Override
	public Integer getTotalCountOfActiveProductsBySupplierId(String optionName,String searchText,String registerSupplier) {
		String sql = "select count(*) from ProductSpec ps inner join Product p on ps.productId = p.productId where " + optionName + " LIKE '%" + searchText + "%' and shelvesStatus != '2' and registerSupplier = :registerSupplier";
		NativeQuery<Integer> nativeQuery = session.createNativeQuery(sql, Integer.class);
		nativeQuery.setParameter("registerSupplier", registerSupplier);
		return nativeQuery.uniqueResult();
	}

	// 搜尋規格編號/商品名稱的總數量，已下架或上架中+BY商家編號
	@Override
	public Integer getTotalCountOfProductsByStatusAndSupplierId(String optionName,String searchText,String shelvesStatus, String registerSupplier) {
		String sql = "select count(*) from ProductSpec ps inner join Product p on ps.productId = p.productId where " + optionName + " LIKE '%" + searchText + "%' and shelvesStatus = :shelvesStatus and registerSupplier = :registerSupplier";
		NativeQuery<Integer> nativeQuery = session.createNativeQuery(sql, Integer.class);
		nativeQuery.setParameter("shelvesStatus", shelvesStatus);
		nativeQuery.setParameter("registerSupplier", registerSupplier);
		return nativeQuery.uniqueResult();

	}

	// 搜尋規格編號/商品名稱的總數量，已售完(要去除強制下架)+BY商家編號
	@Override
	public Integer getCountForSoldOutProductsBySupplierId(String optionName,String searchText,String registerSupplier) {
		String sql = "select count(*) from ProductSpec ps inner join Product p on ps.productId = p.productId where " + optionName + " LIKE '%" + searchText + "%' and specStock = 0 and shelvesStatus != '2' and registerSupplier = :registerSupplier";
		NativeQuery<Integer> nativeQuery = session.createNativeQuery(sql, Integer.class);
		nativeQuery.setParameter("registerSupplier", registerSupplier);
		return nativeQuery.uniqueResult();

	}

}
