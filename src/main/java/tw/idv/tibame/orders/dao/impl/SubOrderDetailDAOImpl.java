package tw.idv.tibame.orders.dao.impl;

import java.sql.Date;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import jakarta.persistence.PersistenceContext;
import tw.idv.tibame.orders.dao.SubOrderDetailDAO;
import tw.idv.tibame.orders.entity.SubOrderDetail;

@Repository
public class SubOrderDetailDAOImpl implements SubOrderDetailDAO{

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

}
