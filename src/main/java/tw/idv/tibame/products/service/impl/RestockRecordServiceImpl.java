package tw.idv.tibame.products.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.idv.tibame.products.dao.ProductSpecDAO;
import tw.idv.tibame.products.dao.RestockRecordDAO;
import tw.idv.tibame.products.dto1.ProductSpecManageDTO;
import tw.idv.tibame.products.entity.ProductSpec;
import tw.idv.tibame.products.entity.RestockRecord;
import tw.idv.tibame.products.entity.ShelvesStatusRecord;
import tw.idv.tibame.products.service.RestockRecordService;

@Service
@Transactional
public class RestockRecordServiceImpl implements RestockRecordService {

	@Autowired
	private RestockRecordDAO restockRecordDAO;

	@Autowired
	private ProductSpecDAO productSpecDAO;

	// 以下是獲取pk號碼使用
	@Override
	public String concatPKID() throws Exception {
		// 取得日期
		LocalDate today = LocalDate.now();

		// 搜尋補貨記錄當天已有幾個紀錄
		String dateString = today.toString();
		Integer a = restockRecordDAO.selectByRestockDate(dateString);

		// 將當天日期轉為yyyymmdd格式
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String dateString1 = today.format(formatter);

		// 進行補貨ID拼接
		String formattedText = String.format("%06d", a + 1);
		String restockId = dateString1 + formattedText;

		return restockId;
	}

	// 針對庫存更新鈕
	@Override
	public List<ProductSpecManageDTO> updateStockButton(String[] productSpecIds, String restockMemberId, String[] beforeRestockStock,
			String[] restockQuantity) throws Exception {

		List<ProductSpecManageDTO> productSpecManageDTOList = new ArrayList<>(); // 建立一個列表來保存 ProductSpecManageDTO
		for (int i = 0; i < productSpecIds.length; i++) {
			
			RestockRecord restockRecord = new RestockRecord();
			restockRecord.setRestockId(concatPKID());
			restockRecord.setProductId(Integer.parseInt(productSpecIds[i].substring(0, 8)));
			restockRecord.setProductSpecId(productSpecIds[i]);
			restockRecord.setRestockMemberId(restockMemberId);

			Integer b = Integer.parseInt(beforeRestockStock[i]);
			restockRecord.setBeforeRestockStock(b);

			Integer q = Integer.parseInt(restockQuantity[i]);
			restockRecord.setRestockQuantity(q);

			Integer afterRestockStock = b + q;
			restockRecord.setAfterRestockStock(afterRestockStock);
			
			ProductSpecManageDTO productSpecManageDTO = new ProductSpecManageDTO();
			productSpecManageDTO.setProductSpecId(productSpecIds[i]);
			productSpecManageDTO.setSpecStock(afterRestockStock);
			productSpecManageDTOList.add(productSpecManageDTO); // 將 ProductSpecManageDTO 添加到列表中
			
			restockRecordDAO.insert(restockRecord);
			productSpecDAO.updateSpecStock(productSpecIds[i], afterRestockStock);
		}
		return productSpecManageDTOList;
	}
	
	
	// 列全部
	@Override
	public List<RestockRecord> getAllRestockRecord() throws Exception {
		return restockRecordDAO.getAll();
	}
	
	// 綜合查詢
	@Override
	public List<RestockRecord> IntegratedSearchController(String searchValue, String selectValue, String startDate, String endDate)
			throws Exception {
		if (searchValue == null || searchValue.isBlank()) {
			return restockRecordDAO.selectByDate(startDate,endDate);
		} else if ((startDate == null || startDate.isBlank()) && (endDate == null || endDate.isBlank())) {
			return restockRecordDAO.selectByOptionValue(searchValue,selectValue);
		} else {
			return restockRecordDAO.selectByOptionValueDate(searchValue,selectValue,startDate,endDate);
		}

		
	}
	
	

}
