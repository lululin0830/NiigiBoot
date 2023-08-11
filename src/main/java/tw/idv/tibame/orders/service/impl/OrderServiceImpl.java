package tw.idv.tibame.orders.service.impl;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import tw.idv.tibame.events.dao.EventApplicableProductsDAOImpl;
import tw.idv.tibame.members.dao.MemberDAO;
import tw.idv.tibame.orders.dao.MainOrderDAO;
import tw.idv.tibame.orders.dao.SubOrderDAO;
import tw.idv.tibame.orders.dao.SubOrderDetailDAO;
import tw.idv.tibame.orders.entity.MainOrder;
import tw.idv.tibame.orders.entity.SubOrder;
import tw.idv.tibame.orders.entity.SubOrderDetail;
import tw.idv.tibame.orders.service.OrderService;
import tw.idv.tibame.products.dao.ProductDAO;
import tw.idv.tibame.products.entity.Product;
import tw.idv.tibame.shoppingcart.dao.ShoppingCartDAO;
import tw.idv.tibame.shoppingcart.pojo.CartItem;
import tw.idv.tibame.shoppingcart.service.ShoppingCartService;
import tw.idv.tibame.suppliers.dao.SupplierDAO;
import tw.idv.tibame.suppliers.entity.Suppliers;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private static volatile int orderCounter = 1;
	private static final Object counterLock = new Object();

	@Autowired
	MainOrderDAO mainOrderDAO;
	@Autowired
	SubOrderDAO subOrderDAO;
	@Autowired
	SubOrderDetailDAO subOrderDetailDAO;
	@Autowired
	ProductDAO productDAO;
	@Autowired
	SupplierDAO supplierDAO;
	@Autowired
	ShoppingCartService cartService;
	@Autowired
	ShoppingCartDAO cartDAO;
	@Autowired
	EventApplicableProductsDAOImpl eventDAO;
	@Autowired
	MemberDAO memberDAO;
	@Autowired
	Gson gson;

	// 取得自動編號
	public String generateOrderId() throws Exception {

		String lastId = mainOrderDAO.selectLastOrder();
		LocalDate lastDate = null;
		if (lastId != null) {

			lastDate = LocalDate.parse(lastId.substring(0, 8), DateTimeFormatter.ofPattern("yyyyMMdd"));
		}

		LocalDate currentDate = LocalDate.now();
		String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

		String orderId = formattedDate + String.format("%09d", 1);

		synchronized (counterLock) {

			if (mainOrderDAO.selectById(orderId) == null || orderCounter >= 999999999) {
				orderCounter = 1;
			} else if (lastId != null && mainOrderDAO.selectById(orderId) != null && lastDate.isEqual(currentDate)) {
				orderCounter = Integer.parseInt(lastId.substring(8)) + 1;
			}
			orderId = formattedDate + String.format("%09d", orderCounter);
			orderCounter++;
		}
		return orderId;
	}

	@Override
	public String checkoutInit(String memberId) {
		
		return gson.toJson(memberDAO.selectForCheckout(memberId));
		
	}
	
	@Override
	public boolean createOrder(JsonObject orderData) throws Exception {

		// 主訂單資料處理
		String memberId = orderData.get("memberId").getAsString();
		String paymentType = orderData.get("paymentType").getAsString();
		String recipient = orderData.get("recipient").getAsString();
		String phoneNum = orderData.get("phoneNum").getAsString();
		String deliveryAddress = orderData.get("deliveryAddress").getAsString();
		MainOrder mainOrder = new MainOrder(memberId, paymentType, recipient, phoneNum, deliveryAddress);
		mainOrder.setOrderId(generateOrderId()); // 取得主訂單編號

		// 訂單明細資料處理(取出所有商品的規格編號、商品編號、商品售價、活動價、活動編號)

		String productIds = "''";
		StringBuilder stringBuilder = new StringBuilder();
		List<SubOrderDetail> subOrderDetails = new ArrayList<SubOrderDetail>();

		Type cartItemType = new TypeToken<List<CartItem>>(){}.getType();
		List<CartItem> cartList = gson.fromJson(cartService.init(memberId), cartItemType);


		int totalAmount = 0;

		for (CartItem cartItem : cartList) {

			SubOrderDetail item = new SubOrderDetail();

			Integer eventPrice = cartItem.getProductPrice();

			if (cartItem.getEventPrice() != null) {

				eventPrice = cartItem.getEventPrice();

			} else if (cartItem.getCouponPrice() != null) {

				eventPrice = cartItem.getCouponPrice();
			}

			List<String> giftList = cartItem.getGiftProductSpecId();
			List<String> eventIds = cartItem.getEventIds();
			List<Integer> eventDiscounts = cartItem.getEventDiscounts();

			item.setOrderId(mainOrder.getOrderId());
			item.setProductSpecId(cartItem.getProductSpecId());
			item.setProductId(cartItem.getProductId());
			item.setProductPrice(cartItem.getProductPrice());
			item.setEventPrice(eventPrice);
			item.setEventIds(gson.toJson(eventIds));
			item.setEvevtDiscounts(gson.toJson(eventDiscounts));

			if (giftList != null && !giftList.isEmpty()) {

				int count = 0;
				for (int i = 0; i < eventDiscounts.size(); i++) {

					if (eventDiscounts.get(i) == 0) {

						SubOrderDetail gift = new SubOrderDetail();
						gift.setOrderId(mainOrder.getOrderId());
						gift.setProductSpecId(giftList.get(count));
						gift.setProductId(Integer.parseInt(giftList.get(count).substring(0, 8)));
						gift.setProductPrice(0);
						gift.setEventPrice(0);
						gift.setEventIds(gson.toJson(new ArrayList<String>().add(eventIds.get(i))));
						gift.setEvevtDiscounts(gson.toJson(new ArrayList<Integer>().add(0)));

						subOrderDetails.add(gift);
						count++;

					}

				}

			}

			subOrderDetails.add(item);
			totalAmount += eventPrice != null ? eventPrice : cartItem.getProductPrice();
			stringBuilder.append(cartItem.getProductId() + ",");

		}

		int totalGrossProfit = (int) (totalAmount * 0.15);
		int pointsDiscount = orderData.get("pointsDiscount") != null ? orderData.get("pointsDiscount").getAsInt() : 0;
		int couponDiscount = orderData.get("couponDiscount") != null ? orderData.get("couponDiscount").getAsInt() : 0;
		int paidAmount = totalAmount - pointsDiscount - couponDiscount;
		double pDiscountRatio = pointsDiscount / totalAmount;
		double cDiscountRatio = couponDiscount / totalAmount;

		mainOrder.setTotalAmount(totalAmount);
		mainOrder.setTotalGrossProfit(totalGrossProfit);
		mainOrder.setPaidAmount(paidAmount);

		if (stringBuilder.length() > 0) {
			productIds = stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
		}

		// 新增主訂單
		mainOrderDAO.insert(mainOrder);

		// 子訂單資料處理

		// 取得廠商ID

		List<String> supplierIds = productDAO.getSupplierIdList(productIds);

		// 產生子訂單編號 + set訂單基本資訊(訂編、子訂編、商編、時間、收件資訊)
		for (int i = 0; i < supplierIds.size(); i++) {

			Suppliers supplier = supplierDAO.selectById(supplierIds.get(i));
			SubOrder temp = new SubOrder();

			temp.setOrderId(mainOrder.getOrderId());
			temp.setMemberId(memberId);
			temp.setSubOrderId(mainOrder.getOrderId() + "-" + String.format("%03d", i + 1));
			temp.setSupplierId(supplierIds.get(i));
			temp.setRecipient(recipient);
			temp.setPhoneNum(phoneNum);
			temp.setDeliveryAddress(deliveryAddress);

			// 訂單明細set子訂編、明細編號
			int count = 1;
			int paid = 0;

			for (SubOrderDetail sod : subOrderDetails) {

				Product product = productDAO.selectById(sod.getProductId());

				if (sod.getSubOrderId() == null && Objects.equals(product.getRegisterSupplier(), supplierIds.get(i))) {
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
		
		// 以上皆順利，則清空購物車
		cartDAO.delete(memberId);

		return true;

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

			result = subOrderDAO.getAllByOrderId(searchcase, SearchSelect, startDate, closeDate, dateSelect);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	@Override
	public String getAllInit() {

		String result = null;
		try {
			result = subOrderDAO.getAllInit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	// 前台 商家訂單中心 載入顯示全部
	@Override
	public String getSupplierSubOrderInit(String supplierId) {

		String result = null;
		try {
			result = subOrderDAO.getSupplierSubOrderInit(supplierId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 前台 商家訂單中心 條件查詢
	@Override
	public String getSupplierSubOrderBySearch(JsonObject SearchCondition) {

		String searchcase = SearchCondition.get("searchcase").getAsString();

		String SearchSelect = SearchCondition.get("searchway").getAsString();

		String startDateString = SearchCondition.get("StartDate").getAsString();

		Timestamp startDate, closeDate;

		if (startDateString.length() > 0) {
			startDateString += " 00:00:00";
			startDate = Timestamp.valueOf(startDateString);
		} else {
			startDate = Timestamp.valueOf("1970-01-01 00:00:00");
		}

		String closeDateString = SearchCondition.get("EndDate").getAsString();

		if (closeDateString.length() > 0) {
			closeDateString += " 00:00:00";
			closeDate = Timestamp.valueOf(closeDateString);
		} else {
			closeDate = Timestamp.valueOf(LocalDateTime.now());
		}

		String supplierId = SearchCondition.get("supplierId").getAsString();

		String result = null;
		try {

			result = subOrderDAO.getSupplierSubOrderBySearch(searchcase, SearchSelect, startDate, closeDate,
					supplierId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public String supplierSubOrderCancel(String subOrderId) {

		return (subOrderDAO.supplierSubOrderCancel(subOrderId));
	}

	public String memberCheckOrder(String memberId) {
		List<Object[]> list = subOrderDAO.memberCheckOrder2(memberId);

		Map<Object, List<Object[]>> result = list.stream()
				.collect(Collectors.groupingBy(li -> li[0], LinkedHashMap::new, Collectors.toList()));

		return gson.toJson(result);
	}

	public String checkOrderDetail(String subOrderId) {
		return subOrderDetailDAO.checkOrderDetail(subOrderId);
	}

	@Override
	public String subOrderReceipt(String subOrderId) {
		return subOrderDAO.ConfirmReceipt(subOrderId);

	}

	@Override
	public String cancelMainOrder(String mainOrderId) {

		return mainOrderDAO.cancelMainOrder(mainOrderId);
	}

	@Override
	public String cancelSubOrder(String subOrderId) {

		return subOrderDAO.cancelSubOrder(subOrderId);
	}

	@Override
	public String subOrderDetailcomment(String subOrderId) {
		
		return subOrderDAO.subOrderDetailcomment(subOrderId);
	}

	@Override
	public String updateSubOrderDetailComment(String json) {
		
//		Object[] jsonlist = gson.fromJson(json, Object[].class);
		JsonArray jsonlist = gson.fromJson(json, JsonArray.class);
		
		
		
		
//		System.out.println(jsonObject);
		
		for(int i=0;i<jsonlist.size();i++) {
			
			JsonElement temp = jsonlist.get(i);
			;
			
			int ratingStar = temp.getAsJsonObject().get("ratingStar").getAsInt();
			String comment = temp.getAsJsonObject().get("comment").getAsString();
			String orderDetailId = temp.getAsJsonObject().get("orderDetailId").getAsString();
			
			subOrderDetailDAO.updateSubOrderDetailComment(ratingStar,comment,orderDetailId);
		}
		
		return "評價成功";
		
	}

	@Override
	public String orderRefundUpdate(String json) {
		
		JsonObject jsonlist = gson.fromJson(json, JsonObject.class);
		
		String refundSubOrderId = jsonlist.get("refundSubOrderId").getAsString();
		String refundReason = jsonlist.get("refundReason").getAsString();
		String refundMark = jsonlist.get("refundMark").getAsString();

		
		subOrderDAO.orderRefundUpdate(refundSubOrderId,refundReason,refundMark);
		subOrderDetailDAO.refundMark(refundSubOrderId, refundReason, refundMark);
		return "評價成功";
	}
	
	
	
	
}
