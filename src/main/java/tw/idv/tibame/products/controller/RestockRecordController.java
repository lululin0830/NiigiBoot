package tw.idv.tibame.products.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import tw.idv.tibame.core.LoginRequired;
import tw.idv.tibame.products.dto.ProductSpecManageDTO;
import tw.idv.tibame.products.entity.RestockRecord;
import tw.idv.tibame.products.entity.ShelvesStatusRecord;
import tw.idv.tibame.products.service.RestockRecordService;

@RestController
public class RestockRecordController {
	
	@Autowired
	private RestockRecordService restockRecordService;
	
	@LoginRequired
	@PostMapping("/updateStock")
	public List<ProductSpecManageDTO> updateStock(@RequestBody Map<String, Object> requestData, @RequestHeader("Authorization") String jwtToken)throws Exception {
//		String [] productSpecIds = new String [] {"10000001001", "10000001002"};
//		String restockMemberId = "M000000001";
//		String[] beforeRestockStock = new String [] {"200", "0"};
//		String[] restockQuantity = new String [] {"100","200"};
		
		List<Long>  productSpecIdsList  =  (List<Long>) requestData.get("productSpecIds");
		String[] productSpecIds = new String[productSpecIdsList.size()];
	      // 將 ArrayList 中的元素逐個裝入 String[] 陣列中
        for (int i = 0; i < productSpecIdsList.size(); i++) {
        	productSpecIds[i] = productSpecIdsList.get(i).toString();
        }
		
		String restockMemberId = (String) requestData.get("restockMemberId");
		
		List<String> beforeRestockStocksList  =  (List<String>) requestData.get("beforeRestockStocks");
		String[] beforeRestockStocks = new String[beforeRestockStocksList.size()];
	      // 將 ArrayList 中的元素逐個裝入 String[] 陣列中
        for (int i = 0; i < beforeRestockStocksList.size(); i++) {
        	beforeRestockStocks[i] = beforeRestockStocksList.get(i).toString();
        }
		
		List<String> restockQuantitysList  =  (List<String>) requestData.get("restockQuantitys");
		String[] restockQuantitys = new String[restockQuantitysList.size()];
	      // 將 ArrayList 中的元素逐個裝入 String[] 陣列中
        for (int i = 0; i < restockQuantitysList.size(); i++) {
        	restockQuantitys[i] = restockQuantitysList.get(i).toString();
        }
        
		return restockRecordService.updateStockButton(productSpecIds,restockMemberId,beforeRestockStocks,restockQuantitys);
//        return null;
	}
	
	@GetMapping("/getAllRestockRecord")
	public List<RestockRecord> getAllShelvesStatusRecord()throws Exception {
		return restockRecordService.getAllRestockRecord();
	}
	
	@PostMapping("/CompositeQueryRestockRecord")
	public List<RestockRecord> SearchComposite(@RequestBody Map<String, String> requestData)throws Exception{
	    String searchValue = requestData.get("searchValue");
	    String selectValue = requestData.get("selectValue");
	    String startDate = requestData.get("startDate");
	    String endDate = requestData.get("endDate");
		return restockRecordService.IntegratedSearchController(searchValue,selectValue,startDate,endDate);
	}
	

}
