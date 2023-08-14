package tw.idv.tibame.users.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import tw.idv.tibame.users.dao.UserDAO;
import tw.idv.tibame.users.entity.Users;

@Repository
public class UserDAOImpl implements UserDAO {

	@PersistenceContext
	private Session session;
	@Autowired
	private Gson gson;

	@Override
	public Boolean insert(Users entity) throws Exception {
		session.persist(entity);
		return true;
	}

	@Override
	public Users selectById(String id) throws Exception {
		return session.get(Users.class, id);
	}

	@Override
	public List<Users> getAll() throws Exception {
		final String hql = "FROM Users ORDER BY id";
		return session.createQuery(hql, Users.class).getResultList();
	}

	@Override
	public Users update(Users newUser) {
		if (newUser != null) {
			newUser = session.merge(newUser);
			return newUser;
		}
		return null;
	}

	@Override
	public int deleteByUserId(Integer userId) {
		Users users = session.load(Users.class, userId);
		session.remove(users);
		return 1;
	}

	@Override
	public Users selectByUserName(String userName) {
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Users> criteriaQuery = criteriaBuilder.createQuery(Users.class);
		Root<Users> root = criteriaQuery.from(Users.class);
		criteriaQuery.where(criteriaBuilder.equal(root.get("userName"), userName));
		return session.createQuery(criteriaQuery).uniqueResult();
	}

	@Override
	public Users selectByUserAcct(String userAcct) {
		String hql = "FROM Users WHERE userAcct = :userAcct";
		return session.createQuery(hql, Users.class).setParameter("userAcct", userAcct).uniqueResult();
	}

	@Override
	public Users selectForLogin(String userAcct, String password) {
		final String sql = "select * from USERS " + "where USERACCT = :userAcct and PASSWORD = :password";
		return session.createNativeQuery(sql, Users.class).setParameter("userAcct", userAcct)
				.setParameter("password", password).uniqueResult();
	}

	@Override
	public String getAllInit() {
		String result = gson.toJson(session.createQuery("FROM Users", Users.class).getResultList());
		return result;
	}

	@Override
	public String getAllBySearch(String searchCase, String searchSelect) {
		String hql = "FROM Users WHERE " + searchSelect + " LIKE :searchCase";
		return gson.toJson(session.createQuery(hql, Users.class).setParameter("searchCase", "%" + searchCase + "%")
				.getResultList());
	}

	@Override
	public String selectPasswordByUserAcct(String userAcct) {
		String sql = "SELECT `password` FROM Users WHERE userAcct ='" + userAcct + "'";
		return session.createNativeQuery(sql, String.class).uniqueResult();
	}

	@Override
	public Users selectBuUserId(String userId) {
		String hql = "FROM Users WHERE userId = :userId ";
		return session.createQuery(hql, Users.class).setParameter("userId", userId).uniqueResult();
	}

}
