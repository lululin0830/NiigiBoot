package tw.idv.tibame.orders.dao;

import java.sql.Date;
import java.util.List;

import tw.idv.tibame.core.dao.CoreDAO;
import tw.idv.tibame.orders.entity.SubOrderDetail;


public interface SubOrderDetailDAO extends CoreDAO<SubOrderDetail, String>{
	
	//更新評價
	public void upadte_rate(SubOrderDetail SubOrderDetail); 	
	
	//更新退貨期限
	public void upadte_refundline(Date refundDeadline);	
	
	//更新退貨事件(退貨日期、退貨原因、備註)
	public void update_refund(SubOrderDetail SubOrderDetail);	
	
	//更新商品狀態
	public void item_status(String itemStatus);
		
	public void delete(SubOrderDetail SubOrderDetail);
	
	List<Object[]> selectCommentByProductId(Integer productId);
	
	double selectAvgRatingByProductId(Integer productId);
	
	//=======前台 會員訂單中心 查看訂單明細
	public String checkOrderDetail(String subOrderId);
	
	//
	public String updateSubOrderDetailComment(int ratingStar,String comment,String orderDetailId);
	
	//
	public String refundMark (String refundSubOrderId,String refundReason,String refundMark);
}
