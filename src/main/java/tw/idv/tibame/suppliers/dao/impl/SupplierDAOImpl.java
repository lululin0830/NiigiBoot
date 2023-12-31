package tw.idv.tibame.suppliers.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;

import jakarta.persistence.PersistenceContext;
import tw.idv.tibame.suppliers.dao.SupplierDAO;
import tw.idv.tibame.suppliers.entity.Suppliers;

@Repository
public class SupplierDAOImpl implements SupplierDAO {

	@PersistenceContext
	private Session session;
	@Autowired
	private Gson gson;

	@Override
	public Boolean insert(Suppliers entity) throws Exception {
		return null;
	}

	@Override
	public Suppliers selectById(String id) throws Exception {
		return session.get(Suppliers.class, id);
	}

	@Override
	public List<Suppliers> getAll() throws Exception {
		return null;

	}

	@Override
	public String getAllBySearch(String searchCase, String searchSelect, Timestamp startDate, Timestamp closeDate,
			String dateSelect) {

		String hql = "FROM Suppliers WHERE " + searchSelect + " LIKE '%" + searchCase + "%' AND " + dateSelect
				+ " BETWEEN '" + startDate + "' AND '" + closeDate + "'";
		return gson.toJson(session.createQuery(hql, Suppliers.class).getResultList());
	}

	@Override
	public String getAllInit() {

		String result = gson.toJson(session.createQuery("FROM Suppliers", Suppliers.class).getResultList());

		return result;
	}

	@Override
	public Suppliers getShopVacation(String supplierId) {

		String hql = "SELECT new tw.idv.tibame.suppliers.entity.Suppliers(shopVacation,vacationEnd,pauseOrderAcceptance,pauseShipping) "
				+ "FROM Suppliers WHERE supplierId = :supplierId";

		return session.createQuery(hql, Suppliers.class).setParameter("supplierId", supplierId).uniqueResult();
	}

	@Override
	public Suppliers selectBysupplierMemberAcct(String supplierMemberAcct) {

		String hql = "FROM Suppliers WHERE supplierMemberAcct = :supplierMemberAcct";
		Query<Suppliers>query = session.createQuery(hql,Suppliers.class);
		query.setParameter("supplierMemberAcct", supplierMemberAcct);
		return query.uniqueResult();
	}
	
	

}
