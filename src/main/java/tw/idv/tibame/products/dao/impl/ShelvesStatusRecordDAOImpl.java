package tw.idv.tibame.products.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import jakarta.persistence.PersistenceContext;
import tw.idv.tibame.products.dao.ShelvesStatusRecordDAO;
import tw.idv.tibame.products.entity.ShelvesStatusRecord;

@Repository
public class ShelvesStatusRecordDAOImpl implements ShelvesStatusRecordDAO {

	@PersistenceContext
	private Session session;

	@Override
	public Boolean insert(ShelvesStatusRecord entity) throws Exception {
		session.persist(entity);
		return true;
	}

	@Override
	public ShelvesStatusRecord selectById(String id) throws Exception {
		return session.get(ShelvesStatusRecord.class, id);
	}

	@Override
	public List<ShelvesStatusRecord> getAll() throws Exception {
		return session.createQuery("from ShelvesStatusRecord", ShelvesStatusRecord.class).list();
	}

	// 用於計算pk單號(新增時用的)
	@Override
	public Integer selectByShelvesStatusDate(String restockDate) throws Exception {
		String sql = "select count(*) from ShelvesStatusRecord where statusModifyTime like '%" + restockDate + "%';";
		NativeQuery<Integer> nativeQuery = session.createNativeQuery(sql, Integer.class);
		return nativeQuery.uniqueResult();
	}

	// 綜合查詢中的日期查詢
	@Override
	public List<ShelvesStatusRecord> selectByDate(String startDate, String endDate) throws Exception {
		String sql = "";
		if (startDate.isBlank() || startDate == null) {
			sql = "SELECT * FROM ShelvesStatusRecord WHERE DATE(statusModifyTime) <= '" + endDate + "';";
		} else if (endDate.isBlank() || endDate == null) {
			sql = "SELECT * FROM ShelvesStatusRecord WHERE DATE(statusModifyTime) >= '" + startDate + "';";
		} else {
			sql = "SELECT * FROM ShelvesStatusRecord WHERE DATE(statusModifyTime) BETWEEN '" + startDate + "' AND '"
					+ endDate + "';";
		}
		NativeQuery<ShelvesStatusRecord> nativeQuery = session.createNativeQuery(sql, ShelvesStatusRecord.class);
		return nativeQuery.getResultList();
	}

	// 綜合查詢中的select的條件查詢
	@Override
	public List<ShelvesStatusRecord> selectByOptionValue(String searchValue, String selectValue) throws Exception {
		String sql = "";
		if (selectValue.equals("shelvesStatusId")) {
			sql = "select * from ShelvesStatusRecord where shelvesStatusId LIKE '%" + searchValue + "%';";
			NativeQuery<ShelvesStatusRecord> nativeQuery = session.createNativeQuery(sql, ShelvesStatusRecord.class);
			return nativeQuery.getResultList();
		} else if (selectValue.equals("productId")) {
			sql = "select * from ShelvesStatusRecord where productId =" + searchValue + ";";
			NativeQuery<ShelvesStatusRecord> nativeQuery = session.createNativeQuery(sql, ShelvesStatusRecord.class);
			return nativeQuery.getResultList();
		} else {
			sql = "select s.*,p.productName from ShelvesStatusRecord s inner join Product p on s.productId = p.productId where productName LIKE '%"
					+ searchValue + "%';";
			NativeQuery<ShelvesStatusRecord> nativeQuery = session.createNativeQuery(sql, ShelvesStatusRecord.class);
			return nativeQuery.getResultList();
		}

	}

	// 綜合查詢(日期+select)
	@Override
	public List<ShelvesStatusRecord> selectByOptionValueDate(String searchValue, String selectValue, String startDate,
			String endDate) throws Exception {
		String sql = "";
		if (selectValue.equals("shelvesStatusId")) {
			if (startDate.isBlank() || startDate == null) {
				sql = "select * from ShelvesStatusRecord where shelvesStatusId LIKE '%" + searchValue + "%' and DATE(statusModifyTime) <= '" + endDate + "';";
			} else if (endDate.isBlank() || endDate == null) {
				sql = "select * from ShelvesStatusRecord where shelvesStatusId LIKE '%" + searchValue + "%' and DATE(statusModifyTime) >= '" + startDate + "';";
			} else {
				sql = "select * from ShelvesStatusRecord where shelvesStatusId LIKE '%" + searchValue + "%' and DATE(statusModifyTime) BETWEEN '" + startDate + "' AND '"
						+ endDate + "';";
			}
		} else if (selectValue.equals("productId")) {
			if (startDate.isBlank() || startDate == null) {
				sql = "select * from ShelvesStatusRecord where productId =" + searchValue + " and DATE(statusModifyTime) <= '" + endDate + "';";
			} else if (endDate.isBlank() || endDate == null) {
				sql = "select * from ShelvesStatusRecord where productId =" + searchValue + " and DATE(statusModifyTime) >= '" + startDate + "';";
			} else {
				sql = "select * from ShelvesStatusRecord where productId =" + searchValue + " and DATE(statusModifyTime) BETWEEN '" + startDate + "' AND '"
						+ endDate + "';";
			}
		} else {
			if (startDate.isBlank() || startDate == null) {
				sql = "select s.*,p.productName from ShelvesStatusRecord s inner join Product p on s.productId = p.productId where productName LIKE '%"
						+ searchValue + "%' and DATE(s.statusModifyTime) <= '" + endDate + "';";
			} else if (endDate.isBlank() || endDate == null) {
				sql = "select s.*,p.productName from ShelvesStatusRecord s inner join Product p on s.productId = p.productId where productName LIKE '%"
						+ searchValue + "%' and DATE(s.statusModifyTime) >= '" + startDate + "';";
			} else {
				sql = "select s.*,p.productName from ShelvesStatusRecord s inner join Product p on s.productId = p.productId where productName LIKE '%"
						+ searchValue + "%' and DATE(s.statusModifyTime) BETWEEN '" + startDate + "' AND '"
						+ endDate + "';";
			}
		}
		NativeQuery<ShelvesStatusRecord> nativeQuery = session.createNativeQuery(sql, ShelvesStatusRecord.class);
		return nativeQuery.getResultList();
	}

}
