package tw.idv.tibame.orders.dao;

import java.sql.Timestamp;

import tw.idv.tibame.core.dao.CoreDAO;
import tw.idv.tibame.orders.entity.SubOrder;

public interface SubOrderDAO extends CoreDAO<SubOrder, String>{
	
	public SubOrder update(SubOrder newSubOrder)throws Exception;
            
    public String getAllByOrderId(String searchcase,String SearchSelect,Timestamp startDate,Timestamp closeDate,String dateSelect) throws Exception;
    
    public String getAllInit();
    //商家訂單中心 預設載入
    public String getSupplierSubOrderInit(String supplierId);
    //商家訂單中心 條件查詢
    public String getSupplierSubOrderBySearch(String searchcase,String SearchSelect,Timestamp startDate,Timestamp closeDate,String supplierId);
  
}
