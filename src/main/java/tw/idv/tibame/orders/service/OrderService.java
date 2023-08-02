package tw.idv.tibame.orders.service;

import com.google.gson.JsonObject;

public interface OrderService {
	
	public boolean createOrder(JsonObject orderData);

	public boolean cancelOrder();
	
	public boolean refund();
	
	public boolean comment();
	
	public boolean updatepayment();
	
	public boolean updateStatus();
	
	public boolean closeOrder();
	
	//======================後台訂單列表區塊======================
	
	public String orderlist(JsonObject SearchCondition);	
	
	public String getAllInit();
	
	//======================後台訂單列表區塊======================
	
	//======================前台商家訂單區塊======================
	
	public String getSupplierSubOrderInit(String supplierId);
	
	public String getSupplierSubOrderBySearch(JsonObject SearchCondition);
	
	public String supplierSubOrderCancel(String subOrderId);
	
	//======================前台商家訂單區塊======================
	
	//======================前台會員訂單中心======================
	public String memberCheckOrder(String memberId);
	
	public String checkOrderDetail(String subOrderId);
	
	public String subOrderReceipt(String subOrderId);
}
