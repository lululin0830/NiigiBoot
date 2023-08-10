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
		System.out.println(supplierId);
		Query<?> query = session.createQuery("FROM SubOrder as So,Members As Mb ,SubOrderDetail AS sod, Product as pd "
				+ "where supplierId = :supplierId AND So.memberId = Mb.memberId And So.subOrderId = sod.subOrderId And sod.productId = pd.productId ");

		query.setParameter("supplierId", supplierId);

		System.out.println(gson.toJson(query.getResultList()));
		return gson.toJson(query.getResultList());

	}

	@Override
	public String getSupplierSubOrderBySearch(String searchcase, String SearchSelect, Timestamp startDate,
			Timestamp closeDate, String supplierId) {

		String Table = "sod.";
		if (SearchSelect.equals("memberAcct")) {
			Table = "Mb.";
		}

		Query<?> query = session.createQuery("FROM SubOrder as So, Members As Mb ,SubOrderDetail AS sod, Product as pd "
				+ "where So.supplierId = :supplierId AND So.memberId = Mb.memberId And So.subOrderId = sod.subOrderId And sod.productId = pd.productId"
				+ " AND " + Table + SearchSelect + " LIKE '%" + searchcase + "%' AND orderCreateTime BETWEEN '"
				+ startDate + "' AND '" + closeDate + "'");

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

	@Override
	public String memberCheckOrder(String memberId) {
		String hql = "select mo.orderStatus,so.subOrderStatus,mo.orderCreateTime,mo.orderId,mo.totalAmount,sp.shopName,"
				+ "so.subOrderId,so.subPaidAmount,sod.productSpecId,pd.productName,pd.productPrice,sod.eventPrice,"
				+ "sod.itemCouponDiscount,so.recipient,so.deliveryAddress,so.phoneNum "
				+ "from MainOrder as mo,SubOrder as so,SubOrderDetail as sod,Suppliers as sp,Product as pd "
				+ "where mo.orderId = so.orderId and so.subOrderId = sod.subOrderId and so.supplierId = sp.supplierId and sod.productId = pd.productId "
				+ "and mo.memberId = :memberId ";
		Query<?> query = session.createQuery(hql);
		query.setParameter("memberId", memberId);
		return gson.toJson(query.getResultList());
	}


//	@Override
//	public String memberCheckOrder(String memberId) {
//		
//		String hql = "select mo.orderStatus,so.subOrderStatus,mo.orderCreateTime,mo.orderId,mo.totalAmount,sp.shopName,"
//				+ "so.subOrderId,so.subPaidAmount,sod.productSpecId,pd.productName,pd.productPrice,sod.eventPrice,"
//				+ "sod.itemCouponDiscount,so.recipient,so.deliveryAddress,so.phoneNum "
//				+ "from MainOrder as mo,SubOrder as so,SubOrderDetail as sod,Suppliers as sp,Product as pd "
//				+ "where mo.orderId = so.orderId and so.subOrderId = sod.subOrderId and so.supplierId = sp.supplierId and sod.productId = pd.productId "		
//				+ "and mo.memberId = :memberId ";				
//				Query <Object[]> query = session.createQuery(hql,Object[].class);
//				query.setParameter("memberId", memberId);
//			
//		return gson.toJson(query.getResultList());
//	}
//	
	public List<Object[]> memberCheckOrder2(String memberId) {
		System.out.println(memberId);
		String hql = "select mo.orderStatus,mo.paymentStatus,so.subOrderStatus,mo.orderCreateTime,mo.orderId,mo.totalAmount,sp.shopName,"
				+ "so.subOrderId,so.subPaidAmount,pd.picture1,so.commentStatus "				
				+ "from MainOrder as mo,SubOrder as so,SubOrderDetail as sod,Suppliers as sp,Product as pd "
				+ "where mo.orderId = so.orderId and so.subOrderId = sod.subOrderId and so.supplierId = sp.supplierId and sod.productId = pd.productId "		
				+ "and mo.memberId = :memberId ";				
				Query <Object[]> query = session.createQuery(hql,Object[].class);
				query.setParameter("memberId", memberId);
			
		return query.getResultList();
	}

	@Override
	public String ConfirmReceipt(String subOrderId) {
		
		Query query = session.createQuery("update SubOrder SET subOrderStatus = '3' where subOrderId = :subOrderId");
		query.setParameter("subOrderId", subOrderId);
		query.executeUpdate();		
		
		return "確認收貨成功";
	}

	@Override
	public String cancelSubOrder(String subOrderId) {
		
		Query query = session.createQuery("update SubOrder SET subOrderStatus = '5' where subOrderId = :subOrderId");
		query.setParameter("subOrderId", subOrderId);
		query.executeUpdate();
		
		return "取消訂單成功";
	}

	@Override
	public String subOrderDetailcomment(String subOrderId) {
		String hql = "SELECT so.subOrderId,sod.orderDetailId,pd.productName,pd.picture1 "
				+ "FROM SubOrderDetail as sod,Product as pd,SubOrder as so "
				+ "where so.subOrderId = sod.subOrderId and sod.productId = pd.productId and so.subOrderId = :subOrderId";
		
		Query<?> query = session.createQuery(hql);
		query.setParameter("subOrderId", subOrderId);
		
		return gson.toJson(query.getResultList());	
	}

	@Override
	public String orderRefundUpdate(String refundSubOrderId, String refundReason, String refundMark) {

		String subHql = "UPDATE SubOrder SET subOrderStatus = '4' where subOrderId = :subOrderId";
		Query<SubOrder>query = session.createQuery(subHql);
		query.setParameter("subOrderId", refundSubOrderId);
		query.executeUpdate();
		return "子訂單更新狀態成功";
	}
	
	
	
}
