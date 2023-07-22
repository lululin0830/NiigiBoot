package tw.idv.tibame.orders.dao.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.google.gson.Gson;

import tw.idv.tibame.orders.dao.SubOrderDAO;
import tw.idv.tibame.orders.entity.SubOrder;

public class SubOrderDAOImpl implements SubOrderDAO {

	@Override
	public Boolean insert(SubOrder entity) throws Exception {
		getSession().persist(entity);
		return true;
	}

	@Override
	public SubOrder selectById(String id) throws Exception {

//		Session session = getSession();
//		SubOrder subOrder = session.get(SubOrder.class, id);
		return getSession().get(SubOrder.class, id);
	}

	@Override
	public List<SubOrder> getAll() throws Exception {

		Session session = getSession();
		Query<SubOrder> query = session.createQuery("FROM SubOrder", SubOrder.class);
		return query.getResultList();
	}

	@Override
	public SubOrder update(SubOrder newSubOrder) throws Exception {
		Session session = getSession();
		SubOrder subOrder = session.get(SubOrder.class, newSubOrder.getSubOrderId());

		final Timestamp orderCloseTime = newSubOrder.getOrderCloseTime();
		final String subOrderStatus = newSubOrder.getSubOrderStatus();
		final String grantStatus = newSubOrder.getGrantStatus();
		final Date grantDate = newSubOrder.getGrantDate();

		if (orderCloseTime != null && !(orderCloseTime.toString().isBlank())) {
			subOrder.setOrderCloseTime(orderCloseTime);
		}
		if (subOrderStatus != null && !(subOrderStatus.isBlank())) {
			subOrder.setSubOrderStatus(subOrderStatus);
		}
		if (grantStatus != null && !(grantStatus.isBlank())) {
			subOrder.setGrantStatus(grantStatus);
		}
		if (grantDate != null && !(grantDate.toString().isBlank())) {
			subOrder.setGrantDate(grantDate);
		}

		return subOrder;
	}

	@Override
	public String getAllByOrderId(String searchcase, String SearchSelect, Timestamp startDate, Timestamp closeDate,
			String dateSelect) throws Exception {
		
		String hql = "FROM SubOrder so JOIN SubOrderDetail sod ON so.subOrderId = sod.subOrderId WHERE so." + SearchSelect + " LIKE '%" + searchcase + "%' AND " + dateSelect + " between '" + startDate + "' AND '" + closeDate+"'" ;
		Session session = getSession();
		
		System.out.println(searchcase);
		System.out.println(SearchSelect);
		System.out.println(startDate);
		System.out.println(closeDate);
		System.out.println(dateSelect);
		
		Query<?> query = session.createQuery(hql);
		List<?> list= query.getResultList();	
		System.out.println("我走到查詢結果了");
		
		Gson gson = new Gson();
		String Result = gson.toJson(list);
//		System.out.println(Result);
		return Result;
				
	}

	@Override
	public String getAllInit() {
		
		Session session = getSession();
		
		Query<?> query = session.createQuery("FROM SubOrder so JOIN SubOrderDetail sod ON so.subOrderId = sod.subOrderId");
		List<?> list = query.getResultList();
		
		Gson gson = new Gson();
		String result = gson.toJson(list);
		
		return result;
	}

	@Override
	public String getSupplierSubOrderInit(String supplierId) {
		
		Gson gson = new Gson();
		Session session = getSession();
		Query<?>query = session.createQuery("FROM SubOrder WHERE supplierId = :supplierId");
		query.setParameter("supplierId", supplierId);
				
		return gson.toJson(query.getResultList());
		
		 
	}

	@Override
	public String getSupplierSubOrderSearch() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
