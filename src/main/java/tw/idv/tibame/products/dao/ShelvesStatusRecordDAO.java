package tw.idv.tibame.products.dao;

import java.util.List;

import tw.idv.tibame.core.dao.CoreDAO;
import tw.idv.tibame.products.entity.ShelvesStatusRecord;

public interface ShelvesStatusRecordDAO extends CoreDAO<ShelvesStatusRecord, String> {

	// 用於計算pk單號(新增時用的)
	public Integer selectByShelvesStatusDate(String restockDate) throws Exception;

	// 綜合查詢中的日期查詢
	public List<ShelvesStatusRecord> selectByDate(String startDate, String endDate) throws Exception;

	// 綜合查詢中的select的條件查詢
	public List<ShelvesStatusRecord> selectByOptionValue(String searchValue, String selectValue) throws Exception;

	// 綜合查詢(日期+select)
	public List<ShelvesStatusRecord> selectByOptionValueDate(String searchValue, String selectValue, String startDate,
			String endDate) throws Exception;


}
