package tw.idv.tibame.members.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;

import jakarta.persistence.PersistenceContext;
import tw.idv.tibame.members.entity.Members;
import tw.idv.tibame.suppliers.entity.Suppliers;

@Repository
public class MemberDAOImpl implements tw.idv.tibame.members.dao.MemberDAO {

	@PersistenceContext
	private Session session;
	@Autowired
	private Gson gson;

	@Override
	public Boolean insert(Members entity) throws Exception {

		session.persist(entity);

		return true;
	}

	@Override
	public Members selectById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Members> getAll() throws Exception {
		List<Members> result = session.createQuery("FROM Members", Members.class).getResultList();
		return result;
	}

	@Override
	public Members update(Members newMember) {
		if(newMember!=null) {
			newMember=session.merge(newMember);
			return newMember;
		}
		return null;
	}

	@Override
	public Members selectByLogin(String memberAcct, String password) {
		final String sql = "select * from Members where memberAcct =:memberAcct and password =:password";
		return session.createNativeQuery(sql, Members.class).setParameter("memberAcct", memberAcct)
				.setParameter("password", password).uniqueResult();
	}

	@Override
	public Members selectOneByMemberId(String memberId) {
		String hql = "FROM Members WHERE memberId = :memberId";
		return session.createQuery(hql, Members.class).setParameter("memberId", memberId).uniqueResult();
	}

	@Override
	public Members selectOneByMemberAcct(String memberAcct) {
		String hql = "FROM Members WHERE memberAcct = :memberAcct";

		return session.createQuery(hql, Members.class).setParameter("memberAcct", memberAcct).uniqueResult();

	}

	@Override
	public Members selectManyByMemberId(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Members selectManyByMemberAcct(String memberAcct) {
		return null;
	}

	@Override
	public Members selectManyByMemberIdDate(String memberId, Timestamp regTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Members selectManyByMemberIdRegStatusOpen(String memberId, String regStatusOpen) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Members selectByRecientPhoneNumDeliveryAddress(String lastRecipient, String lastPhoneNum,
			String lastDeliveryAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String selectLastMember() {

		String sql = "SELECT memberId FROM Members ORDER BY memberId DESC";

		NativeQuery<String> nativeQuery = session.createNativeQuery(sql, String.class).setFirstResult(0)
				.setMaxResults(1);

		return nativeQuery.uniqueResult();
	}

	@Override
	public String selectPasswordByMemberAcct(String memberAcct) {
		String sql = "SELECT `password` FROM Members WHERE memberAcct ='" + memberAcct + "'";

		return session.createNativeQuery(sql, String.class).uniqueResult();
	}

	@Override
	public String getAllBySearch(String searchCase, String searchSelect, Timestamp startDate, Timestamp closeDate,
			String dateSelect) {
		String hql = "FROM Members WHERE " + searchSelect + " LIKE '%" + searchCase + "%' AND " + dateSelect
				+ " BETWEEN '" + startDate + "' AND '" + closeDate + "'";
		return gson.toJson(session.createQuery(hql, Members.class).getResultList());
	}

}
