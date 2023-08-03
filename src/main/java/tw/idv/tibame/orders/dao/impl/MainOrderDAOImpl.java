package tw.idv.tibame.orders.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import jakarta.persistence.PersistenceContext;
import tw.idv.tibame.orders.dao.MainOrderDAO;
import tw.idv.tibame.orders.entity.MainOrder;

@Repository
public class MainOrderDAOImpl implements MainOrderDAO {

	@PersistenceContext
	private Session session;

	@Override
	public Boolean insert(MainOrder entity) {
		session.persist(entity);
		return true;
	}

	@Override
	public MainOrder selectById(String id) {
		return session.get(MainOrder.class, id);
	}

	// 尚未改成Hibernate
	@Override
	public List<MainOrder> getAll() {

		Query<MainOrder> query = session.createQuery("FROM MainOrder", MainOrder.class);
		return query.getResultList();
	}

	@Override
	public MainOrder update(MainOrder newMainOrder) {

		MainOrder mainOrder = null;

		mainOrder = session.get(MainOrder.class, newMainOrder.getOrderId());

		final String orderStatus = newMainOrder.getOrderStatus();
		final String paymentStatus = newMainOrder.getPaymentStatus();
		final Timestamp paymentTime = newMainOrder.getPaymentTime();
		final String billStatus = newMainOrder.getBillStatus();
		final Date billDate = newMainOrder.getBillDate();

		if (orderStatus != null && !orderStatus.isBlank()) {
			mainOrder.setOrderStatus(orderStatus);
		}
		if (paymentStatus != null && !paymentStatus.isBlank()) {
			mainOrder.setPaymentStatus(paymentStatus);
		}
		if (paymentTime != null) {
			mainOrder.setPaymentTime(paymentTime);
		}
		if (billStatus != null && !billStatus.isBlank()) {
			mainOrder.setBillStatus(billStatus);
		}
		if (billDate != null) {
			mainOrder.setBillDate(billDate);
		}

		return mainOrder;

	}

	// 尚未改成Hibernate
	@Override
	public List<MainOrder> selectByMemberID(String memberId) {

		final String url = "jdbc:mysql://localhost:3306/GP?serverTimezone=Asia/Taipei";
		final String user = "root";
		final String password = "password";
		final String GET_ALL_STMT = "select * from MainOrder where memberId = ? order by orderId desc ;";

		List<MainOrder> queryResultList = new ArrayList<MainOrder>();
		MainOrder queryResult = null;
		try (Connection connection = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = connection.prepareStatement(GET_ALL_STMT)) {

			pstmt.setString(1, memberId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				queryResult = new MainOrder();

				queryResult.setOrderId(rs.getString(1));
				queryResult.setMemberId(rs.getString(2));
				queryResult.setOrderCreateTime(rs.getTimestamp(3));
				queryResult.setOrderStatus(rs.getString(4));
				queryResult.setTotalAmount(rs.getInt(5));
				queryResult.setTotalGrossProfit(rs.getInt(6));
				queryResult.setPointsDiscount(rs.getInt(7));
				queryResult.setCouponDiscount(rs.getInt(8));
				queryResult.setPaidAmount(rs.getInt(9));
				queryResult.setPaymentType(rs.getString(10));
				queryResult.setPaymentStatus(rs.getString(11));
				queryResult.setPaymentTime(rs.getTimestamp(12));
				queryResult.setBillStatus(rs.getString(13));
				queryResult.setBillDate(rs.getDate(14));
				queryResult.setShipmentType(rs.getString(15));
				queryResult.setPhoneNum(rs.getString(16));
				queryResult.setPhoneNum(rs.getString(17));
				queryResult.setDeliveryAddress(rs.getString(18));

				queryResultList.add(queryResult);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return queryResultList;
	}

	@Override
	public String selectLastOrder() {
		String sql = "SELECT orderId FROM MainOrder ORDER BY orderId DESC";

		NativeQuery<String> nativeQuery = session.createNativeQuery(sql, String.class).setFirstResult(0)
				.setMaxResults(1);

		return nativeQuery.uniqueResult();
	}

	@Override
	public String cancelMainOrder(String mainOrderId) {
		
		Query query = session.createQuery("update MainOrder SET OrderStatus = '1' where OrderId = :OrderId");
		query.setParameter("OrderId", mainOrderId);
		query.executeUpdate();		
		
		return "取消訂單成功";
		
	}
	
	
}
