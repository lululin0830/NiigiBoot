package tw.idv.tibame.products.service;

import java.util.List;

import tw.idv.tibame.products.dto1.ProductSpecManageDTO;
import tw.idv.tibame.products.entity.RestockRecord;

public interface RestockRecordService {
	
	
	//以下是獲取pk號碼使用
	public String concatPKID() throws Exception;
	
	// 針對庫存更新鈕
	public List<ProductSpecManageDTO> updateStockButton(String[] productSpecIds, String restockMemberId, String[] beforeRestockStock,
			String[] restockQuantity) throws Exception;
	
	// 列全部
	public List<RestockRecord> getAllRestockRecord() throws Exception;
	
	// 綜合查詢
	public List<RestockRecord> IntegratedSearchController(String searchValue, String selectValue, String startDate, String endDate)
			throws Exception;

}
