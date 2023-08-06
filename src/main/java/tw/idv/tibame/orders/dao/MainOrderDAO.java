package tw.idv.tibame.orders.dao;

import java.util.List;

import tw.idv.tibame.core.dao.CoreDAO;
import tw.idv.tibame.orders.entity.MainOrder;

public interface MainOrderDAO extends CoreDAO<MainOrder, String> {

	public MainOrder update(MainOrder newMainOrder);

	public List<MainOrder> selectByMemberID(String memberId);
	
	String selectLastOrder();
	
	//會員訂單中心 取消主訂單
	public String cancelMainOrder(String mainOrderId);
}
