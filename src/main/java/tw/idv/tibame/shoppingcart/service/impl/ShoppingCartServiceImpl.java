package tw.idv.tibame.shoppingcart.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import tw.idv.tibame.events.dao.EventApplicableProductsDAOImpl;
import tw.idv.tibame.events.dao.EventSingleThresholdDAOImpl;
import tw.idv.tibame.events.entity.EventApplicableProducts;
import tw.idv.tibame.events.entity.EventSingleThreshold;
import tw.idv.tibame.products.dao.ProductSpecDAO;
import tw.idv.tibame.products.entity.ProductSpec;
import tw.idv.tibame.shoppingcart.dao.ShoppingCartDAO;
import tw.idv.tibame.shoppingcart.pojo.CartItem;
import tw.idv.tibame.shoppingcart.service.ShoppingCartService;
import tw.idv.tibame.suppliers.dao.SupplierDAO;
import tw.idv.tibame.suppliers.entity.Suppliers;

@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

	private static final String FULL_PURCHASE = "1";
	private static final String QUANTITY_PURCHASE = "2";
	private static final String PRODUCT_DISCOUNT = "3";

	@Autowired
	private ShoppingCartDAO cartDAO;
	@Autowired
	private ProductSpecDAO specDAO;
	@Autowired
	private EventApplicableProductsDAOImpl eventDAO;
	@Autowired
	private EventSingleThresholdDAOImpl eventInfoDAO;
	@Autowired
	private SupplierDAO supplierDAO;
	@Autowired
	private Gson gson;

	@Override
	public boolean addToCart(JsonObject data) {

		JsonArray temp = data.get("productSpecIds").getAsJsonArray();
		final String memberId = data.get("memberId").getAsString();
		final String[] productSpecIds = gson.fromJson(temp, String[].class);

		return cartDAO.hasExistsCart(memberId) ? cartDAO.update(memberId, productSpecIds)
				: cartDAO.insert(memberId, productSpecIds);
	}

	@Override
	public ResponseEntity<String> removeFromCart(JsonObject data) {

		final String memberId = data.get("memberId").getAsString();
		final String productSpecId = data.get("productSpecId").getAsString();

		if (cartDAO.delete(memberId, productSpecId)) {
			return ResponseEntity.status(HttpStatus.OK).body("移除成功");
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("系統繁忙中...請稍後再試");
	}

	@Override
	public String init(String memberId) throws Exception {

		/* ===購物車是否存在，若存在才往下走=== */
		if (cartDAO.hasExistsCart(memberId)) {

			cartDAO.sort(memberId); // 購物車內商品按商品規格編號排序
			List<String> cartList = cartDAO.getCartList(memberId); // 取出購物車列表

			/* ===取出商品資料，檢查上架狀態、折價券，裝進CartItem方便後續輸出=== */
			List<CartItem> list = new ArrayList<CartItem>();
			List<Integer> productIds = new ArrayList<>();
			Map<String, List<Integer>> couponMap = new HashMap<String, List<Integer>>();

			for (String temp : cartList) {

				ProductSpec ps = specDAO.selectById(temp); // 從資料庫取資料
				Suppliers supplier = supplierDAO.getShopVacation(ps.getProduct().getRegisterSupplier());

				if (ps.getShelvesStatus().equals("0") && ps.getSpecStock() > 0
						&& !supplier.getPauseOrderAcceptance().equals("1")) { // 上架中&有庫存&商家沒有暫停接單才往下走

					Integer productId = ps.getProductId(), price = ps != null ? ps.getProduct().getProductPrice() : 0;
					CartItem ci = new CartItem();

					productIds.add(productId);

					ci.setProductId(productId);
					ci.setProductSpecId(ps.getProductSpecId());
					ci.setProductName(ps.getProduct().getProductName());
					ci.setProductPrice(price);
					ci.setSpecInfo1(ps.getSpecInfo1());
					ci.setSpecInfo2(ps.getSpecInfo2());
					ci.setRegisterSupplier(ps.getProduct().getRegisterSupplier());
					ci.setSpecStock(ps.getSpecStock());

					/* ===確認商家休假狀態，並加入資料中=== */

					String vacation = supplier.getShopVacation();

					if (vacation != null && !vacation.isBlank()) {

						ci.setShopVacation(vacation);
						ci.setPauseShipping(supplier.getPauseShipping());
						ci.setVacationEnd(supplier.getVacationEnd());

					}
					list.add(ci);
				} else {
					cartDAO.delete(memberId, ps.getProductSpecId());
				}

			} // 第一個ForEach迴圈結束(cartList)

			/* ===確認商品是否有單品折價券可使用=== */
			List<CartItem> sortedList = list.stream()
					.sorted(Comparator.comparingInt(CartItem::getProductPrice).reversed()).collect(Collectors.toList());

			list = sortedList;

			for (CartItem ci : list) {

				int price = ci.getProductPrice();

				List<EventSingleThreshold> coupons = eventDAO.selectCoupontByProductId(ci.getProductId());
				if (!coupons.isEmpty()) {

					Map<Integer, String> priceMap = new TreeMap<>();

					for (EventSingleThreshold coupon : coupons) {

						String type = coupon.getThresholdType();
						String couponCode = coupon.getCouponCode();

						if (type.equals(FULL_PURCHASE)) {

							Double discountRate = coupon.getDiscountRate();
							Integer discountAmount = coupon.getDiscountAmount();

							if (price > coupon.getMinPurchaseAmount()) {

								if (discountRate != null) {
									priceMap.put((int) (price * discountRate), couponCode);
								}
								if (discountAmount != null) {
									priceMap.put(price - discountAmount, couponCode);
								}
							}
						}

						Set<Integer> set = priceMap.keySet();
						for (Integer key : set) {
							String code = priceMap.get(key);
							List<Integer> ids = couponMap.get(code);
							int available = coupon.getCouponAvailableAmount(),
									availableP = coupon.getCouponAvailablePerPurchase();
							/* ===檢查折價券可使用量&單筆可使用量=== */
							if (ids == null) { // 這張折價券第一次出現，直接收下
								ids = new ArrayList<Integer>();
								ids.add(ci.getProductId());
								ci.setCouponPrice(key);
								ci.setCouponCode(code);
								break;
							} else {
								if (ids.size() < available && ids.size() < availableP) {
									ids.add(ci.getProductId());
									ci.setCouponPrice(key);
									ci.setCouponCode(code);
									break;

								} else if (ids.size() > available || ids.size() > availableP) {
									continue;
								}
							}
						}

						EventSingleThreshold event = eventInfoDAO.selectEventInfoByCouponCode(ci.getCouponCode());
						ci.setCouponName(event.getEventName());
						ci.setCouponInfo(event.getEventInfo());
					}
				}
			}

			/* ===商品活動計算=== */
			List<EventApplicableProducts> allDiscountR = eventDAO.selectDiscountRateByCartList(productIds);
			List<EventApplicableProducts> allDiscountA = eventDAO.selectDiscountAmountByCartList(productIds);
			List<EventApplicableProducts> allGift = eventDAO.selectGiftByCartList(productIds);

			if (!allDiscountR.isEmpty()) {
				Map<String, Integer[]> discountMap = getEventMap(allDiscountR);
				processEventMap(discountMap, list);

			}

			if (!allDiscountA.isEmpty()) {

				Map<String, Integer[]> discountMap = getEventMap(allDiscountA);
				processEventMap(discountMap, list);
			}

			if (!allGift.isEmpty()) {

				Map<String, Integer[]> giftMap = getEventMap(allGift);
				processEventMap(giftMap, list);
			}

			return gson.toJson(list);

		} else {
			return null;
		}
	}

	/**
	 * 彙整活動資料，將相同ID的活動整理成一筆資料 Key：eventId , Value：productIds
	 * 
	 * @param allEvents
	 * @return
	 */
	public Map<String, Integer[]> getEventMap(List<EventApplicableProducts> allEvents) {
		List<EventApplicableProducts> copy = new ArrayList<EventApplicableProducts>(allEvents);
		Map<String, Integer[]> eventMap = new TreeMap<>();
		int size = allEvents.size(), idCount = 0; // eventProductIds size & index count
		Integer[] eventProductIds = new Integer[size];

		for (EventApplicableProducts event : copy) {

			String eventId = event.getEventId();

			for (EventApplicableProducts temp : allEvents) {

				if (Objects.equals(eventId, temp.getEventId())) { // 若有重複的活動，取出商品編號後從List移除

					eventProductIds[idCount] = temp.getProductId();
					idCount++;
					allEvents.remove(temp);
					break; // 離開迴圈 ConcurrentModificationException
				}
			}
			// 將所有活動id & 適用整理成Map
			eventMap.put(eventId, Arrays.copyOf(eventProductIds, idCount));
			idCount = 0;
		}

		return eventMap;
	}

	/**
	 * 商品活動門檻計算，設定活動價&贈品
	 * 
	 * @param discountMap
	 * @param list
	 * @throws Exception
	 */
	public void processEventMap(Map<String, Integer[]> discountMap, List<CartItem> list) throws Exception {

		for (Map.Entry<String, Integer[]> entry : discountMap.entrySet()) {
			String key = entry.getKey();
			Integer[] val = entry.getValue();
			EventSingleThreshold eventInfo = eventInfoDAO.selectById(key);
			int minPurchase = eventInfo.getMinPurchaseAmount() != null ? eventInfo.getMinPurchaseAmount() : 0;
			int minQuantity = eventInfo.getMinPurchaseQuantity() != null ? eventInfo.getMinPurchaseQuantity() : 1;

			if (val.length > 1) {
				// 單一活動有多樣商品適用的情況
				switch (eventInfo.getThresholdType()) {
				case FULL_PURCHASE:
					int ttl = list.stream().filter(ci -> Arrays.asList(val).contains(ci.getProductId()))
							.mapToInt(ci -> ci.getCouponPrice() == null ? ci.getProductPrice() : ci.getCouponPrice())
							.sum();

					if (ttl >= minPurchase) {
						list.stream().filter(item -> Arrays.asList(val).contains(item.getProductId())).forEach(item -> {
							updateWithEventInfo(item, eventInfo);
							updateEventPrice(item, eventInfo);
						});
					}
					break;
				case QUANTITY_PURCHASE:
					int productCount = (int) list.stream()
							.filter(item -> Arrays.asList(val).contains(item.getProductId())).count();

					if (productCount >= minQuantity) {
						list.stream().filter(item -> Arrays.asList(val).contains(item.getProductId())).forEach(item -> {
							updateWithEventInfo(item, eventInfo);
							updateEventPrice(item, eventInfo);
						});
					}
					break;
				default:
					int ttlBoth = 0;
					int bothCount = 0;

					for (CartItem ci : list) {
						if (Arrays.asList(val).contains(ci.getProductId())) {
							ttlBoth += (ci.getCouponPrice() == null ? ci.getProductPrice() : ci.getCouponPrice());
							bothCount++;
						}
					}

					if (ttlBoth >= minPurchase && bothCount >= minQuantity) {
						list.stream().filter(item -> Arrays.asList(val).contains(item.getProductId())).forEach(item -> {
							updateWithEventInfo(item, eventInfo);
							updateEventPrice(item, eventInfo);
						});
					}
					break;
				}
			} else {
				// 單一活動只有一樣商品適用的情況
				if (minQuantity == 1) {

					list.stream().filter(item -> item.getProductId().equals(val[0])).filter(item -> {

						Integer eventPrice = Optional.ofNullable(item.getEventPrice()).orElse(0);
						Integer couponPrice = Optional.ofNullable(item.getCouponPrice()).orElse(0);
						return item.getProductPrice() > minPurchase || eventPrice > minPurchase
								|| couponPrice > minPurchase;
					}).forEach(item -> {

						updateWithEventInfo(item, eventInfo);
						updateEventPrice(item, eventInfo);

					});

				}

			}
		}
	}

	/**
	 * 更新活動資訊
	 * 
	 * @param item
	 * @param eventInfo
	 */
	public void updateWithEventInfo(CartItem item, EventSingleThreshold eventInfo) {

		String eventName = eventInfo.getEventName();
		String eventInfos = eventInfo.getEventInfo();
		String key = eventInfo.getEventId();

		item.setEventIds(item.getEventIds() == null ? new ArrayList<>() : item.getEventIds());
		item.getEventIds().add(key);

		item.setEventName(item.getEventName() == null ? new ArrayList<>() : item.getEventName());
		item.getEventName().add(eventName);

		item.setEventInfo(item.getEventInfo() == null ? new ArrayList<>() : item.getEventInfo());
		item.getEventInfo().add(eventInfos);
	}

	/**
	 * 更新活動價&贈品
	 * 
	 * @param item
	 * @param eventInfo
	 */
	public void updateEventPrice(CartItem item, EventSingleThreshold eventInfo) {

		String eventType = eventInfo.getEventType();
		Integer discountA = eventInfo.getDiscountAmount();
		Double discountR = eventInfo.getDiscountRate();
		Integer eventPrice = item.getEventPrice();
		Integer couponPrice = item.getCouponPrice();
		Integer price = item.getProductPrice();

		if (eventType.equals(PRODUCT_DISCOUNT)) {

			if (discountA != null) {

				if (eventPrice != null) {

					item.setEventPrice(eventPrice * discountA);
					item.setEventDiscounts(
							item.getEventDiscounts() == null ? new ArrayList<>() : item.getEventDiscounts());
					item.getEventDiscounts().add(discountA);

				} else if (couponPrice != null) {

					item.setEventPrice(couponPrice * discountA);
					item.setEventDiscounts(
							item.getEventDiscounts() == null ? new ArrayList<>() : item.getEventDiscounts());
					item.getEventDiscounts().add(discountA);

				} else {

					item.setEventPrice(price * discountA);
					item.setEventDiscounts(
							item.getEventDiscounts() == null ? new ArrayList<>() : item.getEventDiscounts());
					item.getEventDiscounts().add(discountA);

				}
			} else if (discountR != null) {

				if (eventPrice != null) {

					item.setEventPrice((int) (eventPrice * discountR));
					item.setEventDiscounts(
							item.getEventDiscounts() == null ? new ArrayList<>() : item.getEventDiscounts());
					item.getEventDiscounts().add(eventPrice - item.getEventPrice());

				} else if (couponPrice != null) {

					item.setEventPrice((int) (couponPrice * discountR));
					item.setEventDiscounts(
							item.getEventDiscounts() == null ? new ArrayList<>() : item.getEventDiscounts());
					item.getEventDiscounts().add(couponPrice - item.getEventPrice());

				} else {

					item.setEventPrice((int) (price * discountR));
					item.setEventDiscounts(
							item.getEventDiscounts() == null ? new ArrayList<>() : item.getEventDiscounts());
					item.getEventDiscounts().add(price - item.getEventPrice());
				}
			}
		} else {
			item.setGiftProductSpecId(
					item.getGiftProductSpecId() == null ? new ArrayList<>() : item.getGiftProductSpecId());
			item.getGiftProductSpecId().add(eventInfo.getGiftProductSpecId());
			item.getEventDiscounts().add(0);
		}
	}

	
	/**
	 * 取得購物車商品數
	 * @param memberId
	 */
	@Override
	public Long getCount(String memberId){
		return cartDAO.hasExistsCart(memberId) ?  cartDAO.getCount(memberId) : 0;
	}

	
	@Override
	public void deleteCart(String memberId) {
		
		cartDAO.delete(memberId);
	}

}
