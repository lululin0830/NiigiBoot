package tw.idv.tibame.shoppingcart.service.impl;

import java.util.ArrayList;
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
	private Gson gson;

	@Override
	public String init(String memberId) throws Exception {

//		// 購物車是否存在
//		if (cartDAO.hasExistsCart(memberId)) {
//			// 排序
//			cartDAO.sort(memberId);
//
//			// 取出購物車列表
//			List<String> cartList = cartDAO.getCartList(memberId);
//
//			// 取出商品資料
//			List<ProductSpec> productList = new ArrayList<ProductSpec>();
//			for (String temp : cartList) {
//				productList.add(specDAO.selectById(temp));
//			}
//
//			// 取出所有商品ID
//			Integer[] productIds = new Integer[cartList.size()];
//
//			for (int i = 0; i < productIds.length; i++) {
//				productIds[i] = productList.get(i).getProductId();
//			}
//
//			List<String> list = new ArrayList<>();
//
//			// 核對單品折價券活動資訊+取最優價
//			for (ProductSpec temp : productList) {
//				Integer productId = temp.getProductId();
//				Integer price = temp.getProduct().getProductPrice();
//
//				List<EventApplicableProducts> coupons = eventDAO.selectCoupontByProductId(productId);
//				Map<Integer, String> priceMap = new TreeMap<>();
//
//				if (!coupons.isEmpty()) {
//					int i = 0;
//
//					for (EventApplicableProducts coupon : coupons) {
//
//						ThresholdType type = coupon.getEventSingleThreshold().getThresholdType();
//						String couponCode = coupon.getEventSingleThreshold().getCouponCode();
//
//						if (type == ThresholdType.FULL_PURCHASE) {
//
//							Integer minPurchaseAmount = coupon.getEventSingleThreshold().getMinPurchaseAmount();
//							Double discountRate = coupon.getEventSingleThreshold().getDiscountRate();
//							Integer discountAmount = coupon.getEventSingleThreshold().getDiscountAmount();
//
//							if (price > minPurchaseAmount) {
//
//								if (discountRate != null) {
//									priceMap.put((int) (price * discountRate), couponCode);
//								}
//								if (discountAmount != null) {
//									priceMap.put(price - discountAmount, couponCode);
//								}
//							}
//							i++;
//						}
//					}
//
//					Set<Integer> set = priceMap.keySet();
//					String couponCode = "";
//
//					for (Integer key : set) {
//						price = key;
//						couponCode = priceMap.get(key);
//						break;
//					}
//
//					String jsonString = gson.toJson(temp);
//					jsonString = jsonString.substring(0, jsonString.lastIndexOf("}")) + ",\"couponPrice\":"
//							+ price.toString() + ",\"couponCode\":" + couponCode + "}";
//
//					list.add(jsonString);
//
//				}
//
//			}
//
//			Integer[] productIds = new Integer[cartList.size()];
//
//			for (int i = 0; i < productIds.length; i++) {
//				productIds[i] = productList.get(i).getProductId();
//			}
//			
//			// 取出所有商品活動
//			List<EventApplicableProducts> allEvents = eventDAO.selectByCartList(productIds);
//
//			for (EventApplicableProducts event : allEvents) {
//
//				EventType eventType = event.getEventSingleThreshold().getEventType();
//				ThresholdType thresholdType = event.getEventSingleThreshold().getThresholdType();
//
//				switch (eventType) {
//
//				case PRODUCT_DISCOUNT:
//
//					break;
//
//				case PRODUCT_GIFT:
//					break;
//
//				default:
//					break;
//				}
//
//			}
//
//			return null;
//		} else
//
//		{
//
		return "購物車內尚無商品";
//		}

	}

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

	public String init2(String memberId) throws Exception {

		// 購物車是否存在
		if (cartDAO.hasExistsCart(memberId)) {
			// 排序
			cartDAO.sort(memberId);

			// 取出購物車列表
			List<String> cartList = cartDAO.getCartList(memberId);

			// 取出商品資料，檢查上架狀態、折價券，裝進CartItem方便後續輸出
			List<ProductSpec> productList = new ArrayList<ProductSpec>();
			List<CartItem> list = new ArrayList<CartItem>();
			Integer[] productIds = new Integer[cartList.size()];
			int i = 0;
			for (String temp : cartList) {
				ProductSpec ps = specDAO.selectById(temp);
				if (ps.getShelvesStatus().equals("0") && ps.getSpecStock() > 0) { // 上架中&有庫存才往下走
					productList.add(ps);
					Integer productId = ps.getProductId();
					Integer price = ps.getProduct().getProductPrice();
					productIds[i] = productId;
					CartItem ci = new CartItem();
					ci.setProductId(productId);
					ci.setProductSpecId(ps.getProductSpecId());
					ci.setProductName(ps.getProduct().getProductName());
					ci.setProductPrice(price);
					ci.setSpecInfo1(ps.getSpecInfo1());
					ci.setSpecInfo2(ps.getSpecInfo2());
					ci.setRegisterSupplier(ps.getProduct().getRegisterSupplier());
					ci.setSpecStock(ps.getSpecStock());

					List<EventApplicableProducts> coupons = eventDAO.selectCoupontByProductId(productId);
					if (!coupons.isEmpty()) { // 確認有單品折價券可使用
						Map<Integer, String> priceMap = new TreeMap<>();
						for (EventApplicableProducts coupon : coupons) {
							ThresholdType type = coupon.getEventSingleThreshold().getThresholdType();
							String couponCode = coupon.getEventSingleThreshold().getCouponCode();
							if (type == ThresholdType.FULL_PURCHASE) {
								Integer minPurchaseAmount = coupon.getEventSingleThreshold().getMinPurchaseAmount();
								Double discountRate = coupon.getEventSingleThreshold().getDiscountRate();
								Integer discountAmount = coupon.getEventSingleThreshold().getDiscountAmount();
								if (price > minPurchaseAmount) {
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
						}
					}
					i++;
					list.add(ci);
				} else {
					cartDAO.delete(memberId, ps.getProductSpecId());
				} // if

			} // for

			// 取出所有商品活動，並將相同的活動集中
			List<EventApplicableProducts> allEvents = eventDAO.selectByCartList(productIds);
			Map<String, Integer[]> eventMap = new TreeMap<String, Integer[]>();
			int count = allEvents.size(), idCount = 0;
			Integer[] eventProductIds = new Integer[count];

			for (EventApplicableProducts event : allEvents) {

				String eventId = event.getEventId();

				// 若有重複的活動，取出商品編號後從List移除
				for (EventApplicableProducts temp : allEvents) {

					if (Objects.equals(eventId, temp.getEventId())) {
						eventProductIds[idCount] = temp.getProductId();
						idCount++;
						allEvents.remove(temp);
					}
				}
				// 集中活動的商品編號
				eventMap.put(eventId, eventProductIds);
				idCount = 0;
			}

			// 確認商品
			for (Entry<String, Integer[]> entry : eventMap.entrySet()) {
				String key = entry.getKey();
				Integer[] val = entry.getValue();
				int a = 0;
				EventSingleThreshold eventInfo = eventInfoDAO.selectById(key);

				EventType eventType = eventInfo.getEventType();
				Integer minPurchase = eventInfo.getMinPurchaseAmount();
				Integer minQuantity = eventInfo.getMinPurchaseQuantity();
				Integer discountA = eventInfo.getDiscountAmount();
				Double discountR = eventInfo.getDiscountRate();

				if (val.length > 1) {

					switch (eventInfo.getThresholdType()) {
					case FULL_PURCHASE:
						break;
					case QUANTITY_PURCHASE:
						break;
					default:
						break;
					}

					for (int j = 0; j < val.length; j++) {
						for (CartItem item : list) {

						}
					}

				} else { // 只有一樣商品符合此活動
					for (CartItem item : list) {
						if (item.getProductId().equals(val[1])) {
							item.setEventIds(new ArrayList<String>());
							item.getEventIds().add(key);

							Integer eventPrice = item.getEventPrice();
							Integer couponPrice = item.getCouponPrice();
							Integer price = item.getProductPrice();

							switch (eventInfo.getThresholdType()) {
							case FULL_PURCHASE:
								if (eventPrice != null && eventPrice > minPurchase) {
									if (eventType == EventType.PRODUCT_DISCOUNT) {
										if (discountA != null) {
											item.setEventPrice(eventPrice * discountA);
										} else {
											item.setEventPrice((int) (eventPrice * discountR));
										}
									} else {
										item.setGiftProductSpecId(new ArrayList<>());
										item.getGiftProductSpecId().add(eventInfo.getGiftProductSpecId());
									}
								} else if (couponPrice != null && couponPrice > minPurchase) {
									if (eventType == EventType.PRODUCT_DISCOUNT) {
										if (discountA != null) {
											item.setEventPrice(eventPrice * discountA);
										} else {
											item.setEventPrice((int) (eventPrice * discountR));
										}
									} else {
										item.setGiftProductSpecId(new ArrayList<>());
										item.getGiftProductSpecId().add(eventInfo.getGiftProductSpecId());
									}
								} else if (price > minPurchase) {
									if (eventType == EventType.PRODUCT_DISCOUNT) {
										if (discountA != null) {
											item.setEventPrice(eventPrice * discountA);
										} else {
											item.setEventPrice((int) (eventPrice * discountR));
										}
									} else {
										item.setGiftProductSpecId(new ArrayList<>());
										item.getGiftProductSpecId().add(eventInfo.getGiftProductSpecId());
									}
								}
								break;
							case QUANTITY_PURCHASE:
								if (minQuantity == 1) {
									if (eventType == EventType.PRODUCT_DISCOUNT) {
										if (discountA != null) {
											item.setEventPrice(eventPrice * discountA);
										} else {
											item.setEventPrice((int) (eventPrice * discountR));
										}
									} else {
										item.setGiftProductSpecId(new ArrayList<>());
										item.getGiftProductSpecId().add(eventInfo.getGiftProductSpecId());
									}
								}
								break;
							default:
								if (minQuantity == 1) {
									if (eventPrice != null && eventPrice > minPurchase) {
										if (eventType == EventType.PRODUCT_DISCOUNT) {
											if (discountA != null) {
												item.setEventPrice(eventPrice * discountA);
											} else {
												item.setEventPrice((int) (eventPrice * discountR));
											}
										} else {
											item.setGiftProductSpecId(new ArrayList<>());
											item.getGiftProductSpecId().add(eventInfo.getGiftProductSpecId());
										}
									} else if (couponPrice != null && couponPrice > minPurchase) {
										if (eventType == EventType.PRODUCT_DISCOUNT) {
											if (discountA != null) {
												item.setEventPrice(eventPrice * discountA);
											} else {
												item.setEventPrice((int) (eventPrice * discountR));
											}
										} else {
											item.setGiftProductSpecId(new ArrayList<>());
											item.getGiftProductSpecId().add(eventInfo.getGiftProductSpecId());
										}
									} else if (price > minPurchase) {
										if (eventType == EventType.PRODUCT_DISCOUNT) {
											if (discountA != null) {
												item.setEventPrice(eventPrice * discountA);
											} else {
												item.setEventPrice((int) (eventPrice * discountR));
											}
										} else {
											item.setGiftProductSpecId(new ArrayList<>());
											item.getGiftProductSpecId().add(eventInfo.getGiftProductSpecId());
										}
									}
								}
								break;
							}
						}
					}
				}
			} // for

			return null;
		} else {

			return "購物車內尚無商品";
		}
	}

}
