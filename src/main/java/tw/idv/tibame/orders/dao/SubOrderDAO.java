package tw.idv.tibame.orders.dao;

import java.sql.Timestamp;
import java.util.List;

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
    //商家訂單中心 取消子訂單
    public String supplierSubOrderCancel(String subOrderId);
    //會員訂單心中 取全部(停用)
    public String memberCheckOrder(String memberId);
  //會員訂單心中 取全部
    public List<Object[]> memberCheckOrder2(String memberId);
    //會員訂單中心 確認取貨
    public String ConfirmReceipt(String subOrderId);
    //會員訂單中心 取消子訂單
    public String cancelSubOrder(String subOrderId);
    //會員訂單中心 評價訂單明細
    public String subOrderDetailcomment(String subOrderId);
    //會員訂單中心 退貨
    public String orderRefundUpdate(String refundSubOrderId,String refundReason,String refundMark);
    //商家訂單中心 訂單狀態變更(待處理->配送中)
    public String orderStatusDeliver(String data);
    //商家訂單中心 訂單狀態變更(配送中->已完成)
    public String orderStatusComplete(String data);
    //商家訂單中心 訂單狀態變更(待退貨->已取消)
    public String orderStatusCancel(String data);
}
