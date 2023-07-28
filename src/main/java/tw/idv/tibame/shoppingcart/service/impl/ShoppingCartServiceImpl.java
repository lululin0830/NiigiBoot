package tw.idv.tibame.shoppingcart.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import tw.idv.tibame.events.dao.EventApplicableProductsDAOImpl;
import tw.idv.tibame.events.dao.EventSingleThresholdDAOImpl;
import tw.idv.tibame.events.entity.EventApplicableProducts;
import tw.idv.tibame.events.entity.EventSingleThreshold;
import tw.idv.tibame.events.entity.enumtype.EventType;
import tw.idv.tibame.events.entity.enumtype.ThresholdType;
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
	public boolean removeFromCart(JsonObject data) {
		final String memberId = data.get("memberId").getAsString();
		final String productSpecId = data.get("productSpecId").getAsString();

		return cartDAO.delete(memberId, productSpecId);
	}

	@Override
	public String init(String memberId) throws Exception {

		/* ===購物車是否存在，若存在才往下走=== */
		if (cartDAO.hasExistsCart(memberId)) {

			cartDAO.sort(memberId); // 購物車內商品按商品規格編號排序
			List<String> cartList = cartDAO.getCartList(memberId); // 取出購物車列表

			/* ===取出商品資料，檢查上架狀態、折價券，裝進CartItem方便後續輸出=== */
			List<CartItem> list = new ArrayList<CartItem>();
			Integer[] productIds = new Integer[cartList.size()];
			int i = 0; // productIds index 計數

			for (String temp : cartList) {

				ProductSpec ps = specDAO.selectById(temp); // 從資料庫取資料

				if (ps.getShelvesStatus().equals("0") && ps.getSpecStock() > 0) { // 上架中&有庫存才往下走

					Integer productId = ps.getProductId(), price = ps.getProduct().getProductPrice();
					List<EventApplicableProducts> coupons = eventDAO.selectCoupontByProductId(productId);
					CartItem ci = new CartItem();

					productIds[i] = productId;

					ci.setProductId(productId);
					ci.setProductSpecId(ps.getProductSpecId());
					ci.setProductName(ps.getProduct().getProductName());
					ci.setProductPrice(price);
					ci.setSpecInfo1(ps.getSpecInfo1());
					ci.setSpecInfo2(ps.getSpecInfo2());
					ci.setRegisterSupplier(ps.getProduct().getRegisterSupplier());
					ci.setSpecStock(ps.getSpecStock());
					
					/* ===確認商家休假狀態，並加入資料中=== */
					Suppliers supplier = supplierDAO.getShopVacation(ci.getRegisterSupplier());
					String vacation = supplier.getShopVacation();
					
					if (vacation != null && !vacation.isBlank()) {
						
						ci.setShopVacation(vacation);
						ci.setPauseOrderAcceptance(supplier.getPauseOrderAcceptance());
						ci.setPauseShipping(supplier.getPauseShipping());
						ci.setVacationEnd(supplier.getVacationEnd());
					}

					/* ===確認商品是否有單品折價券可使用=== */
					if (!coupons.isEmpty()) { 

						Map<Integer, String> priceMap = new TreeMap<>();

						for (EventApplicableProducts coupon : coupons) {

							ThresholdType type = coupon.getEventSingleThreshold().getThresholdType();
							String couponCode = coupon.getEventSingleThreshold().getCouponCode();

							if (type == ThresholdType.FULL_PURCHASE) {

								Double discountRate = coupon.getEventSingleThreshold().getDiscountRate();
								Integer discountAmount = coupon.getEventSingleThreshold().getDiscountAmount();

								if (price > coupon.getEventSingleThreshold().getMinPurchaseAmount()) {
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
								ci.setCouponPrice(key);
								ci.setCouponCode(priceMap.get(key));
								break; // 只取最優惠的那張
							}

							EventSingleThreshold event = eventInfoDAO.selectEventInfoByCouponCode(ci.getCouponCode());
							ci.setCouponName(event.getEventName());
							ci.setCouponInfo(event.getEventInfo());
						}
					}
					i++;
					list.add(ci);
				} else {
					cartDAO.delete(memberId, ps.getProductSpecId());
				}

			} // 第一個ForEach迴圈結束(cartList)

			// TODO 單品折價券檢查可使用數量

			/* ===取出所有商品的活動id，並將相同iD的活動合併為一筆資料=== */
			List<EventApplicableProducts> allEvents = eventDAO.selectByCartList(productIds);
			Map<String, Integer[]> eventMap = new TreeMap<String, Integer[]>();
			int size = allEvents.size(), idCount = 0; // eventProductIds size & index count
			Integer[] eventProductIds = new Integer[size];

			if (!allEvents.isEmpty()) { // 如果有活動，才往下走

				for (EventApplicableProducts event : allEvents) {

					String eventId = event.getEventId();

					for (EventApplicableProducts temp : allEvents) {

						if (Objects.equals(eventId, temp.getEventId())) { // 若有重複的活動，取出商品編號後從List移除

							eventProductIds[idCount] = temp.getProductId();
							idCount++;
							allEvents.remove(temp);
						}
					}
					// 將所有活動id & 適用整理成Map
					eventMap.put(eventId, eventProductIds);
					idCount = 0;
				} // 第二個ForEach迴圈結束(allEvents)
				
				/* ===確認商品是否符合活動門檻，計算活動價&set贈品ID=== */
				for (Entry<String, Integer[]> entry : eventMap.entrySet()) {

					String key = entry.getKey();
					EventSingleThreshold eventInfo = eventInfoDAO.selectById(key);
					Integer[] val = entry.getValue();
					Integer minPurchase = eventInfo.getMinPurchaseAmount(),
							minQuantity = eventInfo.getMinPurchaseQuantity();

					if (val.length > 1) { // 單一活動有多樣商品適用

						switch (eventInfo.getThresholdType()) {

						case FULL_PURCHASE:

							int ttl = 0; // 訂購金額加總

							for (CartItem ci : list) {

								if (Arrays.asList(val).contains(ci.getProductId())) {

									ttl += (ci.getCouponPrice() == null ? ci.getProductPrice() : ci.getCouponPrice());
								}
							}
							if (ttl >= minPurchase) { // 總訂購金額有到門檻

								for (CartItem item : list) {

									if (Arrays.asList(val).contains(item.getProductId())) {

										updateWithEventInfo(item, eventInfo);
										updateEventPrice(item, eventInfo);
									}
								}
							}
							break;

						case QUANTITY_PURCHASE:

							int productCount = 0; // 商品總數

							for (CartItem ci : list) {

								if (Arrays.asList(val).contains(ci.getProductId())) {

									productCount++;
								}
							}
							if (productCount >= minQuantity) {

								for (CartItem item : list) {

									if (Arrays.asList(val).contains(item.getProductId())) {

										updateWithEventInfo(item, eventInfo);
										updateEventPrice(item, eventInfo);
									}
								}
							}
							break;

						default:

							int ttlBoth = 0, bothCount = 0;
							
							for (CartItem ci : list) {
							
								if (Arrays.asList(val).contains(ci.getProductId())) {
								
									ttlBoth += (ci.getCouponPrice() == null ? ci.getProductPrice()
											: ci.getCouponPrice());
									bothCount++;
								}
							}
							if (ttlBoth >= minPurchase && bothCount >= minQuantity) {
								
								for (CartItem item : list) {
								
									if (Arrays.asList(val).contains(item.getProductId())) {
										updateWithEventInfo(item, eventInfo);
										updateEventPrice(item, eventInfo);
									}
								}
							}
							break;
						}
					} else { // 單一活動只有一樣商品適用
						
						for (CartItem item : list) {
							
							if (item.getProductId().equals(val[1])) {
								
								switch (eventInfo.getThresholdType()) {
								
								case FULL_PURCHASE:
									
									updateEventPriceForSingleItem(item, eventInfo);
									break;
									
								case QUANTITY_PURCHASE:
									
									if (minQuantity == 1) {
										
										updateWithEventInfo(item, eventInfo);
										updateEventPrice(item, eventInfo);
									}
									break;
									
								default:
									
									if (minQuantity == 1) {
										
										updateEventPriceForSingleItem(item, eventInfo);
									}
									break;
								}
							}
						}
					}
				} // 第三個ForEach迴圈結束(eventMap)

				
				
			} 

			return gson.toJson(list);
			
		} else {
			return "購物車內尚無商品";
		}
	}

	private void updateWithEventInfo(CartItem item, EventSingleThreshold eventInfo) {

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

	private void updateGift(CartItem item, String giftProductSpecId) {
	
		item.setGiftProductSpecId(
				item.getGiftProductSpecId() == null ? new ArrayList<>() : item.getGiftProductSpecId());
		item.getGiftProductSpecId().add(giftProductSpecId);
	}

	private void updateEventPrice(CartItem item, EventSingleThreshold eventInfo) {
		
		EventType eventType = eventInfo.getEventType();
		Integer discountA = eventInfo.getDiscountAmount();
		Double discountR = eventInfo.getDiscountRate();
		Integer eventPrice = item.getEventPrice();
		Integer couponPrice = item.getCouponPrice();
		Integer price = item.getProductPrice();

		if (eventType == EventType.PRODUCT_DISCOUNT) {
			
			if (discountA != null) {
				
				if (eventPrice != null) {
					
					item.setEventPrice(eventPrice * discountA);
					
				} else if (couponPrice != null) {
					
					item.setEventPrice(couponPrice * discountA);
					
				} else {
					
					item.setEventPrice(price * discountA);
					
				}
			} else if (discountR != null) {
				
				if (eventPrice != null) {
					
					item.setEventPrice((int) (eventPrice * discountR));
					
				} else if (couponPrice != null) {
					
					item.setEventPrice((int) (couponPrice * discountR));
					
				} else {
					
					item.setEventPrice((int) (price * discountR));
				}
			}
		} else {
			updateGift(item, eventInfo.getGiftProductSpecId());
		}
	}

	private void updateEventPriceForSingleItem(CartItem item, EventSingleThreshold eventInfo) {

		EventType eventType = eventInfo.getEventType();
		Integer discountA = eventInfo.getDiscountAmount();
		Double discountR = eventInfo.getDiscountRate();
		Integer minPurchase = eventInfo.getMinPurchaseAmount();
		Integer eventPrice = item.getEventPrice();
		Integer couponPrice = item.getCouponPrice();
		Integer price = item.getProductPrice();

		if (eventType == EventType.PRODUCT_DISCOUNT) {
			
			if (discountA != null) {
				
				if (eventPrice != null && eventPrice > minPurchase) {
					
					updateWithEventInfo(item, eventInfo);
					item.setEventPrice(eventPrice * discountA);
					
				} else if (couponPrice != null && couponPrice > minPurchase) {
					
					updateWithEventInfo(item, eventInfo);
					item.setEventPrice(couponPrice * discountA);
					
				} else if (price > minPurchase) {
					
					updateWithEventInfo(item, eventInfo);
					item.setEventPrice(price * discountA);
				}
			} else if (discountR != null) {
				
				if (eventPrice != null && eventPrice > minPurchase) {
					
					updateWithEventInfo(item, eventInfo);
					item.setEventPrice((int) (eventPrice * discountR));
					
				} else if (couponPrice != null && couponPrice > minPurchase) {
					
					updateWithEventInfo(item, eventInfo);
					item.setEventPrice((int) (couponPrice * discountR));
					
				} else if (price > minPurchase) {
					
					updateWithEventInfo(item, eventInfo);
					item.setEventPrice((int) (price * discountR));
				}
			}
		} else {
			
			if (eventPrice != null && eventPrice > minPurchase) {
				
				updateWithEventInfo(item, eventInfo);
				updateGift(item, eventInfo.getGiftProductSpecId());
				
			} else if (couponPrice != null && couponPrice > minPurchase) {
				
				updateWithEventInfo(item, eventInfo);
				updateGift(item, eventInfo.getGiftProductSpecId());
				
			} else if (price > minPurchase) {
				
				updateWithEventInfo(item, eventInfo);
				updateGift(item, eventInfo.getGiftProductSpecId());
			}
		}
	}

}
