package tw.idv.tibame.products.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.idv.tibame.products.dao.ShelvesStatusRecordDAO;
import tw.idv.tibame.products.entity.ShelvesStatusRecord;
import tw.idv.tibame.products.service.ShelvesStatusRecordService;

@Service
@Transactional
public class ShelvesStatusRecordServiceImpl implements ShelvesStatusRecordService {

	@Autowired
	private ShelvesStatusRecordDAO shelvesStatusRecordDAO;

	//// 以下是獲取pk號碼使用
	@Override
	public String concatPKID() throws Exception {
		// 取得日期
		LocalDate today = LocalDate.now();

		// 搜尋補貨記錄當天已有幾個紀錄
		String dateString = today.toString();
		Integer a = shelvesStatusRecordDAO.selectByShelvesStatusDate(dateString);

		// 將當天日期轉為yyyymmdd格式
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String dateString1 = today.format(formatter);

		// 進行補貨ID拼接
		String formattedText = String.format("%06d", a + 1);
		String restockId = dateString1 + formattedText;

		return restockId;
	}

	// 新增紀錄
	@Override
	public Boolean insertShelvesStatusRecord(String[] productSpecIds, String shelvesMemberId, String shelvesStatus)
			throws Exception {

		for (int i = 0; i < productSpecIds.length; i++) {
			ShelvesStatusRecord shelvesStatusRecord = new ShelvesStatusRecord();
			shelvesStatusRecord.setShelvesStatusId(concatPKID());
			shelvesStatusRecord.setProductId(Integer.parseInt(productSpecIds[i].substring(0, 8)));
			shelvesStatusRecord.setProductSpecId(productSpecIds[i]);
			shelvesStatusRecord.setShelvesMemberId(shelvesMemberId);
			shelvesStatusRecord.setStatusModify(shelvesStatus);
			shelvesStatusRecordDAO.insert(shelvesStatusRecord);
		}
		return true;
	}

	// 列全部
	@Override
	public List<ShelvesStatusRecord> getAllShelvesStatusRecord() throws Exception {
		return shelvesStatusRecordDAO.getAll();
	}

	// 綜合查詢
	@Override
	public List<ShelvesStatusRecord> IntegratedSearchController(String searchValue, String selectValue, String startDate, String endDate)
			throws Exception {
		if (searchValue == null || searchValue.isBlank()) {
			return shelvesStatusRecordDAO.selectByDate(startDate,endDate);
		} else if ((startDate == null || startDate.isBlank()) && (endDate == null || endDate.isBlank())) {
			return shelvesStatusRecordDAO.selectByOptionValue(searchValue,selectValue);
		} else {
			return shelvesStatusRecordDAO.selectByOptionValueDate(searchValue,selectValue,startDate,endDate);
		}

		
	}

}
