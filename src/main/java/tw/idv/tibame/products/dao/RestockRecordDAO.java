package tw.idv.tibame.products.dao;

import java.util.List;

import tw.idv.tibame.core.dao.CoreDAO;
import tw.idv.tibame.products.entity.RestockRecord;

public interface RestockRecordDAO extends CoreDAO<RestockRecord, String> {
	
	//用於計算補貨單號(新增時用的)
	public Integer selectByRestockDate (String restockDate);
	
	//以商品編號模糊查詢
	public List<RestockRecord> selectByProductId(Integer productId) throws Exception;
	
	//以規格編號模糊查詢
	public List<RestockRecord> selectBySpecId(String productSpecId) throws Exception;
	
	//以補貨日期查詢
	public List<RestockRecord> selectByDate(String beforeDate,String afterDate) throws Exception;
	
	
	//綜合查詢
	public List<RestockRecord> selectAll(String optionName,String inputText,String beforeDate,String afterDate) throws Exception;
	
}
