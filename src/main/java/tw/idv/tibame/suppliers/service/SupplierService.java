package tw.idv.tibame.suppliers.service;

import com.google.gson.JsonObject;

import tw.idv.tibame.core.service.CoreService;

public interface SupplierService extends CoreService{
	
	public String getAllInit();
	
	public String getBySearch(JsonObject searchCondition);

}
