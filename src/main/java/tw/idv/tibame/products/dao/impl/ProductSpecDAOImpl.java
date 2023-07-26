package tw.idv.tibame.products.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import tw.idv.tibame.products.dao.ProductSpecDAO;
import tw.idv.tibame.products.entity.ProductSpec;

@Repository
@Transactional
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
	
	
	//修改規格的上下架狀態
	@Override
	public Boolean updateStatus(String[] productSpecIds,String shelvesStatus) {
		StringBuilder sql = new StringBuilder()
				.append("UPDATE ProductSpec SET ")
				.append("shelvesStatus = :shelvesStatus ")
				.append("WHERE ");
		
		Map<String, Object> parameters = new HashMap<>();
		
		// 動態添加搜尋條件
	    for (int i = 0; i < productSpecIds.length; i++) {
	        String paramName = "productSpecId" + i;
	        if (i > 0) {
	            sql.append(" OR ");
	        }
	        sql.append("productSpecId = :").append(paramName);

	        parameters.put(paramName,productSpecIds[i]);
	    }
		NativeQuery<Boolean> nativeQuery = session.createNativeQuery(sql.toString(),Boolean.class);
		nativeQuery.setParameter("shelvesStatus", shelvesStatus);
	    for (String paramName : parameters.keySet()) {
	        nativeQuery.setParameter(paramName, parameters.get(paramName));
	    }
        int updatedCount = nativeQuery.executeUpdate();
        return updatedCount > 0;
		
	}
	
	//以商品編號查全部規格(不包含強制下架)
	@Override
	public List<ProductSpec> selectByProductId(Integer productIds){
		String sql = "SELECT * FROM ProductSpec WHERE productId = :productIds and shelvesStatus != '2';";
		NativeQuery<ProductSpec> nativeQuery = session.createNativeQuery(sql, ProductSpec.class);
		nativeQuery.setParameter("productIds", productIds);
		return nativeQuery.getResultList();
	}
	
	
	//以商品編號計算有幾種規格
	public Integer selectByProductSpecId (Integer productId) {
		String sql = "select count(*) from ProductSpec where productId = :productIds";
		NativeQuery<Integer> nativeQuery = session.createNativeQuery(sql, Integer.class);
		nativeQuery.setParameter("productIds", productId);
		return nativeQuery.uniqueResult();
		
		
		
	}

}
