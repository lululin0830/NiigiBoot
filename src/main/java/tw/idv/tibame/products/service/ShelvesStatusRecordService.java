package tw.idv.tibame.products.service;

public interface ShelvesStatusRecordService {
	
	//// 以下是獲取pk號碼使用
	public String concatPKID() throws Exception;
	
	//新增紀錄
	public Boolean insertShelvesStatusRecord(String[] productSpecIds,String shelvesMemberId,String shelvesStatus) throws Exception;

}
