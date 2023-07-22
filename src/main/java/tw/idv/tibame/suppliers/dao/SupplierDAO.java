package tw.idv.tibame.suppliers.dao;

import java.sql.Timestamp;

import tw.idv.tibame.core.dao.CoreDAO;
import tw.idv.tibame.suppliers.entity.Suppliers;

public interface SupplierDAO extends CoreDAO<Suppliers,String>{
	
	public String getAllBySearch(String searchCase,String searchSelect,Timestamp startDate,Timestamp closeDate,String dateSelect);
	
	public String getAllInit();
}
