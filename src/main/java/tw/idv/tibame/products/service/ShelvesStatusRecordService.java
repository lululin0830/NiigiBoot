package tw.idv.tibame.products.service;

import java.util.List;

import tw.idv.tibame.products.entity.ShelvesStatusRecord;

public interface ShelvesStatusRecordService {
	
	//// 以下是獲取pk號碼使用
	public String concatPKID() throws Exception;
	
	//新增紀錄
	public Boolean insertShelvesStatusRecord(String[] productSpecIds,String shelvesMemberId,String shelvesStatus) throws Exception;

	//列全部
	public List<ShelvesStatusRecord> getAllShelvesStatusRecord()throws Exception;
		
	//綜合查詢
	public List<ShelvesStatusRecord> IntegratedSearchController(String searchValue,String selectValue,String startDate,String endDate)throws Exception;
}
