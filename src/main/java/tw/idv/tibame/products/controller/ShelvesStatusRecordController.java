package tw.idv.tibame.products.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.idv.tibame.products.entity.ShelvesStatusRecord;
import tw.idv.tibame.products.service.ShelvesStatusRecordService;

@RestController
public class ShelvesStatusRecordController {
	
	@Autowired
	private ShelvesStatusRecordService shelvesStatusRecordService;
	
	
	@GetMapping("/getAllShelvesStatusRecord")
	public List<ShelvesStatusRecord> getAllShelvesStatusRecord()throws Exception {
		return shelvesStatusRecordService.getAllShelvesStatusRecord();
	}
	

	@PostMapping("/CompositeQueryShelvesStatusRecord")
	public List<ShelvesStatusRecord> SearchComposite(@RequestBody Map<String, String> requestData)throws Exception{
	    String searchValue = requestData.get("searchValue");
	    String selectValue = requestData.get("selectValue");
	    String startDate = requestData.get("startDate");
	    String endDate = requestData.get("endDate");
		return shelvesStatusRecordService.IntegratedSearchController(searchValue,selectValue,startDate,endDate);
	}
	
	
}
