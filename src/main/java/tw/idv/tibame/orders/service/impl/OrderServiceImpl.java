package tw.idv.tibame.orders.service.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import tw.idv.tibame.orders.dao.MainOrderDAO;
import tw.idv.tibame.orders.dao.SubOrderDetailDAO;
import tw.idv.tibame.orders.dao.impl.MainOrderDAOImpl;
import tw.idv.tibame.orders.dao.impl.SubOrderDAOImpl;
import tw.idv.tibame.orders.dao.impl.SubOrderDetailDAOImpl;
import tw.idv.tibame.orders.entity.MainOrder;
import tw.idv.tibame.orders.entity.SubOrder;
import tw.idv.tibame.orders.entity.SubOrderDetail;
import tw.idv.tibame.orders.service.OrderService;
import tw.idv.tibame.products.dao.ProductDAO;
import tw.idv.tibame.products.dao.impl.ProductDAOImpl;
import tw.idv.tibame.suppliers.dao.SupplierDAO;
import tw.idv.tibame.suppliers.dao.impl.SupplierDAOImpl;
import tw.idv.tibame.suppliers.entity.Suppliers;

public class OrderServiceImpl implements OrderService {

	private static volatile int orderCounter = 1;
	private static final Object counterLock = new Object();

	MainOrderDAO mainOrderDAO;
	SubOrderDAOImpl subOrderDAO;
	SubOrderDetailDAO subOrderDetailDAO;

	public OrderServiceImpl() {

		mainOrderDAO = new MainOrderDAOImpl();
		subOrderDAO = new SubOrderDAOImpl();
		subOrderDetailDAO = new SubOrderDetailDAOImpl();
	}

	// 取得自動編號
	public static String generateOrderId() {
		LocalDate currentDate = LocalDate.now();
		String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

		MainOrderDAO subOrderDAO = new MainOrderDAOImpl();

		String orderId = formattedDate + String.format("%09d", 1);

		synchronized (counterLock) {

			if (subOrderDAO.getSession().get(MainOrder.class, orderId) == null || orderCounter >= 999999999) {
				orderCounter = 1;
			}
			orderId = formattedDate + String.format("%09d", orderCounter);
			orderCounter++;
		}
		return orderId;
	}

	@Override
	public boolean createOrder(JsonObject orderData) {

		// 主訂單資料處理
		String memberId = orderData.get("memberId").getAsString();
		Integer totalAmount = orderData.get("totalAmount").getAsInt();
		Integer totalGrossProfit = (int) (totalAmount * 0.15);
		Integer pointsDiscount = orderData.get("pointsDiscount").getAsInt();
		Integer couponDiscount = orderData.get("couponDiscount").getAsInt();
		Integer paidAmount = totalAmount - pointsDiscount - couponDiscount;
		String paymentType = orderData.get("paymentType").getAsString();
		String recipient = orderData.get("recipient").getAsString();
		String phoneNum = orderData.get("phoneNum").getAsString();
		String deliveryAddress = orderData.get("deliveryAddress").getAsString();

		double pDiscountRatio = pointsDiscount / totalAmount;
		double cDiscountRatio = couponDiscount / totalAmount;

		MainOrder mainOrder = new MainOrder(memberId, totalAmount, totalGrossProfit, pointsDiscount, couponDiscount,
				paidAmount, paymentType, recipient, phoneNum, deliveryAddress);

		// 取得主訂單編號
		mainOrder.setOrderId(generateOrderId());

		// 訂單明細資料處理(取出所有商品的規格編號、商品編號、商品售價、活動價、活動編號)

		JsonArray itemListJsonArray = orderData.getAsJsonArray("itemList");

		String productIds = "''";
		StringBuilder stringBuilder = new StringBuilder();
		List<String> eventIdList = new ArrayList<>();
		List<SubOrderDetail> subOrderDetails = new ArrayList<SubOrderDetail>();

		for (JsonElement itemElement : itemListJsonArray) {
			JsonObject itemObject = itemElement.getAsJsonObject();
			SubOrderDetail temp = new SubOrderDetail();

			temp.setOrderId(mainOrder.getOrderId());
			temp.setProductSpecId(itemObject.get("productSpecId").getAsString());
			temp.setProductId(Integer.parseInt(itemObject.get("productSpecId").getAsString().substring(8)));
			temp.setProductPrice(itemObject.get("price").getAsInt());
			temp.setEventPrice(itemObject.get("eventPrice").getAsInt());

			subOrderDetails.add(temp);

			stringBuilder.append(temp.getProductId());

			JsonArray eventIdArray = itemObject.getAsJsonArray("eventId");
			for (JsonElement eventIdElement : eventIdArray) {
				eventIdList.add(eventIdElement.getAsString());
			}
		}

		if (stringBuilder.length() > 0) {
			productIds = stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
		}

		try {
			// 開始交易控制
			beginTransaction();

			// 新增主訂單
			mainOrderDAO.insert(mainOrder);
			mainOrder = mainOrderDAO.selectById(mainOrder.getOrderId());

			// 子訂單資料處理

			// 取得廠商ID
			ProductDAO productDAO = new ProductDAOImpl();
			SupplierDAO supplierDAO = new SupplierDAOImpl();
			List<String> supplierIds = productDAO.getSupplierIdList(productIds);

			// 產生子訂單編號 + set訂單基本資訊(訂編、子訂編、商編、時間、收件資訊)
			for (int i = 0; i < supplierIds.size(); i++) {

				Suppliers supplier = supplierDAO.selectById(supplierIds.get(i));
				SubOrder temp = new SubOrder();

				temp.setOrderId(mainOrder.getOrderId());
				temp.setSubOrderId(mainOrder.getOrderId() + "-" + String.format("%03d", i + 1));
				temp.setSupplierId(supplierIds.get(i));
				temp.setOrderCreateTime(mainOrder.getOrderCreateTime());
				temp.setRecipient(recipient);
				temp.setPhoneNum(phoneNum);
				temp.setDeliveryAddress(deliveryAddress);

				// 訂單明細set子訂編、明細編號
				int count = 1;
				int paid = 0;

				for (SubOrderDetail sod : subOrderDetails) {

					if (sod.getSubOrderId() == null
							&& productDAO.selectById(sod.getProductId()).getRegisterSupplier() == supplierIds.get(i)) {
						sod.setSubOrderId(temp.getSubOrderId());
						sod.setOrderDetailId(temp.getSubOrderId() + "-" + String.format("%03d", count));
						count++;
						paid += sod.getEventPrice();
					}

				}

				// 子訂單set 聚合欄位
				temp.setSubPaidAmount(paid);
				temp.setSubPointsDiscount((int) (paid * pDiscountRatio));
				temp.setSubCouponDiscount((int) (paid * cDiscountRatio));
				temp.setGrossProfit((int) (paid * supplier.getGrossProfitRatio()));
				temp.setPointsReward((int) (paid * supplier.getPointRewardsRatio()));

				subOrderDAO.insert(temp);
			}

			// 新增子訂單明細
			for (SubOrderDetail sodTemp : subOrderDetails) {
				subOrderDetailDAO.insert(sodTemp);
			}

			commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			return false;
		}

	}

	@Override
	public boolean cancelOrder() {
		return false;
	}

	@Override
	public boolean refund() {
		return false;
	}

	@Override
	public boolean comment() {
		return false;
	}

	@Override
	public boolean updatepayment() {
		return false;
	}

	@Override
	public boolean updateStatus() {
		return false;
	}

	@Override
	public boolean closeOrder() {
		return false;
	}

	// 後臺訂單列表-取得查詢結果
	@Override
	public String orderlist(JsonObject searchCondition) {

		String searchcase = searchCondition.get("searchcase").getAsString();

		String SearchSelect = searchCondition.get("searchway").getAsString();

		String startDateString = searchCondition.get("StartDate").getAsString();

		Timestamp startDate, closeDate;

		if (startDateString.length() > 0) {
			startDateString += " 00:00:00";
			startDate = Timestamp.valueOf(startDateString);
		} else {
			startDate = Timestamp.valueOf("1970-01-01 00:00:00");
		}

		String closeDateString = searchCondition.get("EndDate").getAsString();

		if (closeDateString.length() > 0) {
			closeDateString += " 00:00:00";
			closeDate = Timestamp.valueOf(closeDateString);
		} else {
			closeDate = Timestamp.valueOf(LocalDateTime.now());
		}

		String dateSelect = searchCondition.get("DateSelect").getAsString();

		String result = null;
		try {

			beginTransaction();
			result = subOrderDAO.getAllByOrderId(searchcase, SearchSelect, startDate, closeDate, dateSelect);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	@Override
	public String getAllInit() {

		String result = null;
		try {
			beginTransaction();
			result = subOrderDAO.getAllInit();
			commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	
	//前台 商家訂單中心
	@Override
	public String getSupplierSubOrderInit(String supplierId) {
		
		String result = null;
		try {
			beginTransaction();
			result = subOrderDAO.getSupplierSubOrderInit("");
			comment();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String getSupplierSubOrderSearch() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
