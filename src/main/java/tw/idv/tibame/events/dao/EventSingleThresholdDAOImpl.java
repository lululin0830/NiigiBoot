package tw.idv.tibame.events.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import jakarta.persistence.PersistenceContext;
import tw.idv.tibame.core.dao.CoreDAO;
import tw.idv.tibame.events.entity.EventSingleThreshold;

@Repository
public class EventSingleThresholdDAOImpl implements CoreDAO<EventSingleThreshold, String> {

	@PersistenceContext
	Session session;

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

}
