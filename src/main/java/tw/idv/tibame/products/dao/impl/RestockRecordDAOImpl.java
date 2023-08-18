package tw.idv.tibame.products.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import tw.idv.tibame.products.dao.RestockRecordDAO;
import tw.idv.tibame.products.entity.RestockRecord;
import tw.idv.tibame.products.entity.ShelvesStatusRecord;

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

	// 綜合查詢中的日期查詢
	@Override
	public List<RestockRecord> selectByDate(String startDate, String endDate) throws Exception {
		String sql = "";
		if (startDate.isBlank()) {
			sql = "SELECT * FROM RestockRecord WHERE restockDate <= '" + endDate + "';";
		} else if (endDate.isBlank()) {
			sql = "SELECT * FROM RestockRecord WHERE restockDate >= '" + startDate + "';";
		} else {
			sql = "SELECT * FROM RestockRecord WHERE restockDate BETWEEN '" + startDate + "' AND '" + endDate + "';";
		}
		NativeQuery<RestockRecord> nativeQuery = session.createNativeQuery(sql, RestockRecord.class);
		return nativeQuery.getResultList();
	}

	// 綜合查詢中的select的條件查詢
	@Override
	public List<RestockRecord> selectByOptionValue(String searchValue, String selectValue) throws Exception {
		String sql = "";
		if (selectValue.equals("restockId")) {
			sql = "select * from RestockRecord where restockId LIKE '%" + searchValue + "%';";
			NativeQuery<RestockRecord> nativeQuery = session.createNativeQuery(sql, RestockRecord.class);
			return nativeQuery.getResultList();
		} else if (selectValue.equals("productId")) {
			sql = "select * from RestockRecord where productId =" + searchValue + ";";
			NativeQuery<RestockRecord> nativeQuery = session.createNativeQuery(sql, RestockRecord.class);
			return nativeQuery.getResultList();
		} else {
			sql = "select * from RestockRecord where restockMemberId = :searchValue";
			NativeQuery<RestockRecord> nativeQuery = session.createNativeQuery(sql, RestockRecord.class);
			nativeQuery.setParameter("searchValue", searchValue);
			return nativeQuery.getResultList();
		}

	}

	// 綜合查詢(日期+select)
	@Override
	public List<RestockRecord> selectByOptionValueDate(String searchValue, String selectValue, String startDate,
			String endDate) throws Exception {
		String sql = "";
		if (selectValue.equals("restockId")) {
			if (startDate.isBlank()) {
				sql = "select * from RestockRecord where restockId LIKE '%" + searchValue + "%' and restockDate <= '" + endDate + "';";
			} else if (endDate.isBlank()) {
				sql = "select * from RestockRecord where restockId LIKE '%" + searchValue + "%' and restockDate >= '" + startDate + "';";
			} else {
				sql = "select * from RestockRecord where restockId LIKE '%" + searchValue + "%' and restockDate BETWEEN '" + startDate + "' AND '" + endDate + "';";
			}
			NativeQuery<RestockRecord> nativeQuery = session.createNativeQuery(sql, RestockRecord.class);
			return nativeQuery.getResultList();
		} else if (selectValue.equals("productId")) {
			if (startDate.isBlank()) {
				sql = "select * from RestockRecord where productId =" + searchValue + " and restockDate <= '" + endDate + "';";
			} else if (endDate.isBlank()) {
				sql = "select * from RestockRecord where productId =" + searchValue + " and restockDate >= '" + startDate + "';";
			} else {
				sql = "select * from RestockRecord where productId =" + searchValue + " and restockDate BETWEEN '" + startDate + "' AND '" + endDate + "';";
			}
			NativeQuery<RestockRecord> nativeQuery = session.createNativeQuery(sql, RestockRecord.class);
			return nativeQuery.getResultList();
		} else {
			if (startDate.isBlank()) {
				sql = "SELECT * FROM RestockRecord WHERE restockMemberId = :searchValue and restockDate <= '" + endDate + "';";
				NativeQuery<RestockRecord> nativeQuery = session.createNativeQuery(sql, RestockRecord.class);
				nativeQuery.setParameter("searchValue", searchValue);
				return nativeQuery.getResultList();
			} else if (endDate.isBlank()) {
				sql = "SELECT * FROM RestockRecord WHERE restockMemberId = :searchValue and restockDate >= '" + startDate + "';";
				NativeQuery<RestockRecord> nativeQuery = session.createNativeQuery(sql, RestockRecord.class);
				nativeQuery.setParameter("searchValue", searchValue);
				return nativeQuery.getResultList();
			} else {
				sql = "SELECT * FROM RestockRecord WHERE restockMemberId = :searchValue and restockDate BETWEEN '" + startDate + "' AND '" + endDate + "';";
				NativeQuery<RestockRecord> nativeQuery = session.createNativeQuery(sql, RestockRecord.class);
				nativeQuery.setParameter("searchValue", searchValue);
				return nativeQuery.getResultList();
			}

		}
	}

}
