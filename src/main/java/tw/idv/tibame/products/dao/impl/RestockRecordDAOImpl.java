package tw.idv.tibame.products.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import tw.idv.tibame.products.dao.RestockRecordDAO;
import tw.idv.tibame.products.entity.RestockRecord;

@Repository
public class RestockRecordDAOImpl implements RestockRecordDAO {

	@PersistenceContext
	private Session session;

	@Override
	public Boolean insert(RestockRecord entity) throws Exception {
		session.persist(entity);
		return true;
	}

	@Override
	public RestockRecord selectById(String id) throws Exception {
		return session.get(RestockRecord.class, id);
	}

	@Override
	public List<RestockRecord> getAll() throws Exception {
		return session.createQuery("from RestockRecord", RestockRecord.class).list();
	}

	// 用於計算補貨單號(新增時用的)
	@Override
	public Integer selectByRestockDate(String restockDate) {
		String sql = "select count(*) from RestockRecord where restockDate = :restockDate";
		NativeQuery<Integer> nativeQuery = session.createNativeQuery(sql, Integer.class);
		nativeQuery.setParameter("restockDate", restockDate);
		return nativeQuery.uniqueResult();
	}

	// 以商品編號模糊查詢
	@Override
	public List<RestockRecord> selectByProductId(Integer productId) throws Exception {
		String sql = "select * from RestockRecord where productId LIKE '%" + productId + "%';";
		NativeQuery<RestockRecord> nativeQuery = session.createNativeQuery(sql, RestockRecord.class);
		return nativeQuery.getResultList();
	}

	// 以規格編號模糊查詢
	@Override
	public List<RestockRecord> selectBySpecId(String productSpecId) throws Exception {
		String sql = "select * from RestockRecord where productSpecId LIKE '%" + productSpecId + "%';";
		NativeQuery<RestockRecord> nativeQuery = session.createNativeQuery(sql, RestockRecord.class);
		return nativeQuery.getResultList();
	}

	// 以補貨日期查詢
	@Override
	public List<RestockRecord> selectByDate(String beforeDate, String afterDate) throws Exception {
		String sql = "";
		if (beforeDate.isBlank()) {
			sql = "SELECT * FROM RestockRecord WHERE restockDate <= '" + afterDate + "';";
		} else if (afterDate.isBlank()) {
			sql = "SELECT * FROM RestockRecord WHERE restockDate >= '" + beforeDate + "';";
		} else {
			sql = "SELECT * FROM RestockRecord WHERE restockDate BETWEEN '" + beforeDate + "' AND '" + afterDate + "';";
		}
		NativeQuery<RestockRecord> nativeQuery = session.createNativeQuery(sql, RestockRecord.class);
		return nativeQuery.getResultList();
	}
	
	//綜合查詢
	public List<RestockRecord> selectAll(String optionName,String inputText,String beforeDate,String afterDate) throws Exception{
		String sql="";
		if(optionName == "商品編號") {
			int productId = Integer.parseInt(inputText);
			if (beforeDate.isBlank()) {
				sql = "SELECT * FROM RestockRecord WHERE (productId LIKE '%" + productId + "%') and (restockDate <= '" + afterDate + "');";
			} else if (afterDate.isBlank()) {
				sql = "SELECT * FROM RestockRecord WHERE (productId LIKE '%" + productId + "%') and (restockDate >= '" + beforeDate + "');";
			} else {
				sql = "SELECT * FROM RestockRecord WHERE (productId LIKE '%" + productId + "%') and (restockDate BETWEEN '" + beforeDate + "' AND '" + afterDate + "');";
			}
			
		}else if(optionName == "規格編號") {
			
			if (beforeDate.isBlank()) {
				sql = "SELECT * FROM RestockRecord WHERE (productSpecId LIKE '%" + inputText + "%') and (restockDate <= '" + afterDate + "');";
			} else if (afterDate.isBlank()) {
				sql = "SELECT * FROM RestockRecord WHERE (productSpecId LIKE '%" + inputText + "%') and (restockDate >= '" + beforeDate + "');";
			} else {
				sql = "SELECT * FROM RestockRecord WHERE (productSpecId LIKE '%" + inputText + "%') and (restockDate BETWEEN '" + beforeDate + "' AND '" + afterDate + "');";
			}
			
		}
		NativeQuery<RestockRecord> nativeQuery = session.createNativeQuery(sql, RestockRecord.class);
		return nativeQuery.getResultList();
	}
	
	
	

}
