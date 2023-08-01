package tw.idv.tibame.events.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import jakarta.persistence.PersistenceContext;
import tw.idv.tibame.events.entity.EventApplicableProducts;

@Repository
public class EventApplicableProductsDAOImpl {

	@PersistenceContext
	Session session;

	public List<Object[]> selectAllByProductId (Integer productId){
		
		String hql = "FROM EventApplicableProducts AS eap , EventSingleThreshold AS est "
				+ "WHERE eap.eventId = est.eventId AND productId = :productId "
				+ "AND est.eventEnd >= CURDATE() AND est.eventType in ('1','3','4')";

		return session.createQuery(hql, Object[].class).setParameter("productId", productId)
				.getResultList();
	}
	
	
	public List<EventApplicableProducts> selectCoupontByProductId(Integer[] productId) {

		String hql = "FROM EventApplicableProducts AS eap , EventSingleThreshold AS est "
				+ "WHERE eap.eventId = est.eventId AND productId = :productId "
				+ "AND est.eventEnd >= CURDATE() AND est.eventType ='1'";

		return session.createQuery(hql, EventApplicableProducts.class).setParameter("productId", productId)
				.getResultList();

	}

	public List<EventApplicableProducts> selectDiscountByProductId(Integer productId) {

		String hql = "FROM EventApplicableProducts AS eap , EventSingleThreshold AS est "
				+ "WHERE eap.eventId = est.eventId AND productId = :productId "
				+ "AND est.eventEnd >= CURDATE() AND est.eventType ='3'";

		return session.createQuery(hql, EventApplicableProducts.class).setParameter("productId", productId)
				.getResultList();

	}

	public List<EventApplicableProducts> selectGiftByProductId(Integer productId) {

		String hql = "FROM EventApplicableProducts AS eap , EventSingleThreshold AS est "
				+ "WHERE eap.eventId = est.eventId AND productId = :productId "
				+ "AND est.eventEnd >= CURDATE() AND est.eventType ='4'";

		return session.createQuery(hql, EventApplicableProducts.class).setParameter("productId", productId)
				.getResultList();

	}

	public List<EventApplicableProducts> selectDiscountRateByCartList(Integer[] productId) {

		StringBuilder temp = new StringBuilder();
		for (Integer id : productId) {
			temp.append("'" + id + "',");
		}
		String queryString = temp.deleteCharAt(temp.length() - 1).toString();

		String hql = "FROM EventApplicableProducts WHERE productId in (" + queryString + ") "
				+ "AND est.eventEnd >= CURDATE() AND est.eventType = '3' "
				+ "AND discountRate IS NOT NULL";

		return session.createQuery(hql, EventApplicableProducts.class).getResultList();

	}

	public List<EventApplicableProducts> selectDiscountAmountByCartList(Integer[] productId) {

		StringBuilder temp = new StringBuilder();
		for (Integer id : productId) {
			temp.append("'" + id + "',");
		}
		String queryString = temp.deleteCharAt(temp.length() - 1).toString();

		String hql = "FROM EventApplicableProducts WHERE productId in (" + queryString + ") "
				+ "AND est.eventEnd >= CURDATE() AND est.eventType = '3' "
				+ "AND discountAmount IS NOT NULL";

		return session.createQuery(hql, EventApplicableProducts.class).getResultList();

	}

	public List<EventApplicableProducts> selectGiftByCartList(Integer[] productId) {

		StringBuilder temp = new StringBuilder();
		for (Integer id : productId) {
			temp.append("'" + id + "',");
		}
		String queryString = temp.deleteCharAt(temp.length() - 1).toString();

		String hql = "FROM EventApplicableProducts AS eap , EventSingleThreshold AS est "
				+ "WHERE eap.eventId = est.eventId AND productId in (" + queryString + ") "
				+ "AND est.eventEnd >= CURDATE() AND est.eventType = '4' ";

		return session.createQuery(hql, EventApplicableProducts.class).getResultList();

	}

}
