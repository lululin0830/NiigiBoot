package tw.idv.tibame.products.service;

import java.util.List;

import tw.idv.tibame.products.DTO.ProductSpecManageDTO;

public interface RestockRecordService {
	
	
	//以下是獲取pk號碼使用
	public String concatPKID() throws Exception;
	
	// 針對庫存更新鈕
	public List<ProductSpecManageDTO> updateStockButton(String[] productSpecIds, String restockMemberId, String[] beforeRestockStock,
			String[] restockQuantity) throws Exception;

}
