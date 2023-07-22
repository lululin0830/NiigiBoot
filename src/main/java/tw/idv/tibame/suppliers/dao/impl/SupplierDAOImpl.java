package tw.idv.tibame.suppliers.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;

import com.google.gson.Gson;

import tw.idv.tibame.suppliers.dao.SupplierDAO;
import tw.idv.tibame.suppliers.entity.Suppliers;

public class SupplierDAOImpl implements SupplierDAO{
	

	@Override
	public Boolean insert(Suppliers entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Suppliers selectById(String id) throws Exception {
		return getSession().get(Suppliers.class, id);
	}

	@Override
	public List<Suppliers> getAll() throws Exception {
		return null;
		
		
	}

	@Override
	public String getAllBySearch(String searchCase, String searchSelect, Timestamp startDate, Timestamp closeDate,
			String dateSelect) {
		
		Session session = getSession();
		Gson gson = new Gson();
		
		String hql = "FROM Supplier WHERE " + searchSelect + " LIKE '%" + searchCase + "%' AND " + dateSelect + " BETWEEN '" + startDate +"' AND '" + closeDate + "'";		
		return gson.toJson(session.createQuery(hql,Suppliers.class).getResultList());
	}

	@Override
	public String getAllInit() {
		
		Session session = getSession();		
		Gson gson = new Gson();
		
		String result = gson.toJson(session.createQuery("FROM Supplier", Suppliers.class).getResultList());
		
		return result;
	}
	
	

}
