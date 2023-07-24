package tw.idv.tibame.orders.dao.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;

import jakarta.persistence.PersistenceContext;
import tw.idv.tibame.core.util.CommonUtils;
import tw.idv.tibame.orders.dao.SubOrderDAO;
import tw.idv.tibame.orders.entity.SubOrder;

@Repository
public class SubOrderDAOImpl implements SubOrderDAO {

	@PersistenceContext
	private Session session;

	@Override
	public Boolean insert(SubOrder entity) throws Exception {
		session.persist(entity);
		return true;
	}

	@Override
	public SubOrder selectById(String id) throws Exception {

//		
//		SubOrder subOrder = session.get(SubOrder.class, id);
		return session.get(SubOrder.class, id);
	}

	@Override
	public List<SubOrder> getAll() throws Exception {

		Query<SubOrder> query = session.createQuery("FROM SubOrder", SubOrder.class);
		return query.getResultList();
	}

	@Override
	public SubOrder update(SubOrder newSubOrder) throws Exception {

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

		String hql = "FROM SubOrder as so,SubOrderDetail as sod where so.subOrderId = sod.subOrderId AND so."
				+ SearchSelect + " LIKE '%" + searchcase + "%' AND " + dateSelect + " between '" + startDate + "' AND '"
				+ closeDate + "'";

		System.out.println(searchcase);
		System.out.println(SearchSelect);
		System.out.println(startDate);
		System.out.println(closeDate);
		System.out.println(dateSelect);

		Query<?> query = session.createQuery(hql);
		List<?> list = query.getResultList();
		System.out.println("我走到查詢結果了");

		Gson gson = new Gson();
		String Result = gson.toJson(list);
//		System.out.println(Result);
		return Result;

	}

	@Override
	public String getAllInit() {

//		Query<?> query = session
//				.createQuery("FROM SubOrder so JOIN SubOrderDetail sod ON so.subOrderId = sod.subOrderId");
		Query<?> query = session
				.createQuery("FROM SubOrder as so,SubOrderDetail as sod where so.subOrderId = sod.subOrderId");
		List<?> list = query.getResultList();

		Gson gson = new Gson();
		String result = gson.toJson(list);

		return result;
	}

	@Override
	public String getSupplierSubOrderInit(String supplierId) {

		Gson gson = new Gson();

//		Query<?> query = session.createQuery("FROM SubOrder as So JOIN Members As Mb ON So.memberId = Mb.memberId WHERE supplierId = :supplierId");
		Query<?> query = session.createQuery("FROM SubOrder as So,Members As Mb where So.memberId = Mb.memberId");
//		query.setParameter("supplierId", supplierId);
		
		System.out.println(gson.toJson(query.getResultList()));
		return gson.toJson(query.getResultList());
		
	}

	@Override
	public String getSupplierSubOrderSearch() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String supplierSubOrderFront() {
		
        return CommonUtils.toJson((session.createQuery("FROM SubOrder so JOIN Members mb ON so.memberId = mb.memberId").getResultList()));
	}
	
	

}
