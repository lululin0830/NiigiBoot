package tw.idv.tibame.suppliers.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import tw.idv.tibame.core.util.JwtUtil;
import tw.idv.tibame.core.util.PasswordEncryptor;
import tw.idv.tibame.members.entity.Members;
import tw.idv.tibame.orders.dao.impl.SubOrderDAOImpl;
import tw.idv.tibame.orders.entity.SubOrder;
import tw.idv.tibame.suppliers.dao.impl.SupplierDAOImpl;
import tw.idv.tibame.suppliers.entity.Suppliers;
import tw.idv.tibame.suppliers.service.SupplierService;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	SupplierDAOImpl dao;
	
	@Autowired
	SubOrderDAOImpl sdao;
	
	@Autowired
	Gson gson;
		
	@Override
	public String getAllInit() {
		
		String getAllInit = null;
		
		try {
			getAllInit = dao.getAllInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return getAllInit;
	}

	@Override
	public String getBySearch(JsonObject searchCondition) {
		
		String searchcase = searchCondition.get("searchcase").getAsString();

		String SearchSelect = searchCondition.get("searchway").getAsString();

		String startDateString = searchCondition.get("StartDate").getAsString();

		Timestamp startDate, closeDate;

		if (startDateString.length() > 0) {
			startDateString += " 00:00:00";
			startDate = Timestamp.valueOf(startDateString);
		} else {
			startDate = Timestamp.valueOf("1970-01-01 00:00:00");
		}

		String closeDateString = searchCondition.get("EndDate").getAsString();

		if (closeDateString.length() > 0) {
			closeDateString += " 00:00:00";
			closeDate = Timestamp.valueOf(closeDateString);
		} else {
			closeDate = Timestamp.valueOf(LocalDateTime.now());
		}

		String dateSelect = searchCondition.get("DateSelect").getAsString();
		
		String result = null;
		
		try {
			result = dao.getAllBySearch(searchcase, SearchSelect, startDate, closeDate, dateSelect);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	@Override
	public String logIn(Members member) throws Exception {
		
		final String supplierMemberAcct = member.getMemberAcct();

		Suppliers supplier =  dao.selectBysupplierMemberAcct(supplierMemberAcct);
		
		if (supplier == null) {
			return "尚未成為商家";
		}

		return JwtUtil.generateJwtToken(supplier.getSupplierId(), supplierMemberAcct);
	}

	@Override
	public ResponseEntity<String> showAsideInfo(String jwtToken) {
	if(jwtToken.isBlank()) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"User not logged in\"}");
		}
		
		Map<String, String> result = JwtUtil.validateJwtTokenAndSendInfo(jwtToken);
		
		if(result.get("error")!= null) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.get("error"));
		}
		
		
		return ResponseEntity.ok(gson.toJson(result));
	}

	
}
