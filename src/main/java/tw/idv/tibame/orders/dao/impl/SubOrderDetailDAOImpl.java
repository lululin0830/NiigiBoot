package tw.idv.tibame.orders.dao.impl;

import java.sql.Date;
import java.util.List;

import tw.idv.tibame.orders.dao.SubOrderDetailDAO;
import tw.idv.tibame.orders.entity.SubOrderDetail;

public class SubOrderDetailDAOImpl implements SubOrderDetailDAO{

	@Override
	public Boolean insert(SubOrderDetail entity) {
		// TODO Auto-generated method stub		
		getSession().persist(entity);
		return true;
	}

	@Override
	public SubOrderDetail selectById(String id) {
		// TODO Auto-generated method stub
		return getSession().get(SubOrderDetail.class, id);
	}

	@Override
	public List<SubOrderDetail> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void upadte_rate(SubOrderDetail SubOrderDetail) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void upadte_refundline(Date refundDeadline) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update_refund(SubOrderDetail SubOrderDetail) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void item_status(String itemStatus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(SubOrderDetail SubOrderDetail) {
		// TODO Auto-generated method stub
		
	}

}
