package tw.idv.tibame.suppliers.service;

import org.springframework.http.ResponseEntity;

import com.google.gson.JsonObject;

import tw.idv.tibame.members.entity.Members;
import tw.idv.tibame.suppliers.entity.Suppliers;

public interface SupplierService{
	
	public String getAllInit();
	
	public String getBySearch(JsonObject searchCondition);
	
	public String logIn(Members member)throws Exception;

	public ResponseEntity<String> showAsideInfo(String jwtToken);
		
	
	
}
