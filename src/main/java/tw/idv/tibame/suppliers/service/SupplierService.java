package tw.idv.tibame.suppliers.service;

import com.google.gson.JsonObject;

public interface SupplierService{
	
	public String getAllInit();
	
	public String getBySearch(JsonObject searchCondition);

}
