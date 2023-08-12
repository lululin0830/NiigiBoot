package tw.idv.tibame.products.dao;

import tw.idv.tibame.core.dao.CoreDAO;
import tw.idv.tibame.products.entity.ShelvesStatusRecord;

public interface ShelvesStatusRecordDAO extends CoreDAO<ShelvesStatusRecord, String> {

	// 用於計算pk單號(新增時用的)
	public Integer selectByShelvesStatusDate(String restockDate)throws Exception;
}
