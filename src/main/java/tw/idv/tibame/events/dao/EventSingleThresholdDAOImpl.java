package tw.idv.tibame.events.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;

import jakarta.persistence.PersistenceContext;
import tw.idv.tibame.core.dao.CoreDAO;
import tw.idv.tibame.events.entity.EventSingleThreshold;


@Repository
public class EventSingleThresholdDAOImpl implements CoreDAO<EventSingleThreshold,String> {

	@PersistenceContext
	Session session;	
	@Autowired
	private Gson gson;

	@Override
	public Boolean insert(EventSingleThreshold entity) throws Exception {
		return null;
	}

	@Override
	public EventSingleThreshold selectById(String id) throws Exception {
		return session.get(EventSingleThreshold.class, id);
	}

	@Override
	public List<EventSingleThreshold> getAll() throws Exception {
		return null;
	}

	public EventSingleThreshold selectEventInfoByCouponCode(String couponCode) throws Exception {

		String hql = "SELECT new tw.idv.tibame.events.entity.EventSingleThreshold(eventName,eventInfo) "
				+ "FROM EventSingleThreshold WHERE couponCode = :couponCode";

		return session.createQuery(hql, EventSingleThreshold.class).setParameter("couponCode", couponCode)
				.uniqueResult();

	}

	public List<EventSingleThreshold> selectAllCouponByProductId(Integer productId) {

		String hql = "FROM EventSingleThreshold WHERE eventId IN ( "
				+ "SELECT eventId FROM EventApplicableProducts WHERE productId = :productId "
				+ "AND eventEnd >= CURDATE() AND eventType = '1' )";

		return session.createQuery(hql, EventSingleThreshold.class).setParameter("productId", productId)
				.getResultList();

	}
	
	public List<EventSingleThreshold> selectAllDiscountRateByProductId(Integer productId) {

		String hql = "FROM EventSingleThreshold WHERE eventId IN ( "
				+ "SELECT eventId FROM EventApplicableProducts WHERE productId = :productId "
				+ "AND eventEnd >= CURDATE() AND eventType = '3' "
				+ "AND discountRate IS NOT NULL )";

		return session.createQuery(hql, EventSingleThreshold.class).setParameter("productId", productId)
				.getResultList();

	}
	
	public List<EventSingleThreshold> selectAllDiscountAmountByProductId(Integer productId) {

		String hql = "FROM EventSingleThreshold WHERE eventId IN ( "
				+ "SELECT eventId FROM EventApplicableProducts WHERE productId = :productId "
				+ "AND eventEnd >= CURDATE() AND eventType = '3' "
				+ "AND discountAmount IS NOT NULL )";

		return session.createQuery(hql, EventSingleThreshold.class).setParameter("productId", productId)
				.getResultList();

	}

	public String getAllInit() {
		String result = gson.toJson(session.createQuery("FROM EventSingleThreshold", EventSingleThreshold.class).getResultList());
		return result;
	}


	public String getAllBySearch(String searchCase, String searchSelect, Timestamp startDate, Timestamp closeDate) {

		String hql = "FROM EventSingleThreshold " +
	             "WHERE " + searchSelect + " LIKE '%" + searchCase + "%' " +
	             "AND (eventStart BETWEEN '" + startDate + "' AND '" + closeDate + "') " +
	             "AND (eventEnd BETWEEN '" + startDate + "' AND '" + closeDate + "')";
		return gson.toJson(session.createQuery(hql, EventSingleThreshold.class).getResultList());
	}

}
