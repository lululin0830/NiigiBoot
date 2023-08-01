package tw.idv.tibame.orders.dao.impl;

import java.sql.Date;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import jakarta.persistence.PersistenceContext;
import tw.idv.tibame.orders.dao.SubOrderDetailDAO;
import tw.idv.tibame.orders.entity.SubOrderDetail;

@Repository
public class SubOrderDetailDAOImpl implements SubOrderDetailDAO {

	@PersistenceContext
	private Session session;

	@Override
	public Boolean insert(SubOrderDetail entity) {
		session.persist(entity);
		return true;
	}

	@Override
	public SubOrderDetail selectById(String id) {
		return session.get(SubOrderDetail.class, id);
	}

	@Override
	public List<SubOrderDetail> getAll() {
		return null;
	}

	@Override
	public void upadte_rate(SubOrderDetail SubOrderDetail) {

	}

	@Override
	public void upadte_refundline(Date refundDeadline) {

	}

	@Override
	public void update_refund(SubOrderDetail SubOrderDetail) {

	}

	@Override
	public void item_status(String itemStatus) {

	}

	@Override
	public void delete(SubOrderDetail SubOrderDetail) {

	}

	@Override
	public List<Object[]> selectCommentByProductId(Integer productId) {

		String hql = "SELECT sod.ratingStar,sod.comment,sod.commentDate,m.memberAcct,m.memPhoto "
				+ "FROM SubOrderDetail AS sod , SubOrder AS so , Members AS m "
				+ "WHERE sod.subOrderId = so.subOrderId "
				+ "AND so.memberId = m.memberId "
				+ "AND sod.productId = :productId AND comment IS NOT NULL";

		return session.createQuery(hql, Object[].class).setParameter("productId", productId).getResultList();
	}

	@Override
	public double selectAvgRatingByProductId(Integer productId) {
		
		String sql = "SELECT AVG(ratingStar) FROM SubOrderDetail AS sod ,SubOrder AS so "
				+ "WHERE sod.subOrderId = so.subOrderId "
				+ "AND so.supplierId = ( SELECT supplierId FROM Product WHERE productId = :productId )";
		
		Double result = 
				session.createQuery(sql, double.class).setParameter("productId", productId)
				.uniqueResult();
		
		if (result != null) {
			
			return result;
		}
		
		return 0;
	}

}
