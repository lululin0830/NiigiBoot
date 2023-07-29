package tw.idv.tibame.orders.dao.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	private Gson gson;
	
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

		String Result = gson.toJson(list);
		return Result;

	}

	@Override
	public String getAllInit() {

		Query<?> query = session
				.createQuery("FROM SubOrder as so,SubOrderDetail as sod where so.subOrderId = sod.subOrderId");
		List<?> list = query.getResultList();

		String result = gson.toJson(list);

		return result;
	}

	@Override
	public String getSupplierSubOrderInit(String supplierId) {
		
		Query<?> query = session.createQuery("FROM SubOrder as So,Members As Mb ,SubOrderDetail AS sod, Product as pd "
											+ "where supplierId = :supplierId AND So.memberId = Mb.memberId And So.subOrderId = sod.subOrderId And sod.productId = pd.productId");
		
		query.setParameter("supplierId", supplierId);
		
		System.out.println(gson.toJson(query.getResultList()));
		return gson.toJson(query.getResultList());
		
	}

	@Override
	public String getSupplierSubOrderBySearch(String searchcase, String SearchSelect, Timestamp startDate,
			Timestamp closeDate, String supplierId) {
		
		String Table = "sod.";
		if(SearchSelect.equals("memberAcct") ) {
			Table = "Mb.";
		}
		
		Query<?>query = session.createQuery("FROM SubOrder as So, Members As Mb ,SubOrderDetail AS sod, Product as pd " + 
				"where So.supplierId = :supplierId AND So.memberId = Mb.memberId And So.subOrderId = sod.subOrderId And sod.productId = pd.productId" +
				" AND "+ Table + SearchSelect + " LIKE '%" + searchcase + "%' AND orderCreateTime BETWEEN '" + startDate + "' AND '" + closeDate +"'");
		
			query.setParameter("supplierId", supplierId);
		return gson.toJson(query.getResultList());
	}

	@Override
	public String supplierSubOrderCancel(String subOrderId) {
				
			Query query = session.createQuery("update SubOrder SET subOrderStatus = '5' where subOrderId = :subOrderId");
			query.setParameter("subOrderId", subOrderId);
			query.executeUpdate();
			System.out.println(subOrderId);
		return "取消訂單成功";		
	}

	

}
