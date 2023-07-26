package tw.idv.tibame.shoppingcart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import tw.idv.tibame.events.dao.EventApplicableProductsDAOImpl;
import tw.idv.tibame.events.entity.EventApplicableProducts;
import tw.idv.tibame.events.entity.enumtype.ThresholdType;
import tw.idv.tibame.products.dao.ProductSpecDAO;
import tw.idv.tibame.products.entity.ProductSpec;
import tw.idv.tibame.shoppingcart.dao.ShoppingCartDAO;
import tw.idv.tibame.shoppingcart.service.ShoppingCartService;

@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private ShoppingCartDAO cartDAO;
	@Autowired
	private ProductSpecDAO specDAO;
	@Autowired
	EventApplicableProductsDAOImpl eventDAO;
	@Autowired
	private Gson gson;

	@Override
	public String init(String memberId) throws Exception {

		// 購物車是否存在
		if (cartDAO.hasExistsCart(memberId)) {
			// 排序
			cartDAO.sort(memberId);

			// 取出購物車列表
			List<String> cartList = cartDAO.getCartList(memberId);

			// 取出商品資料
			List<ProductSpec> productList = new ArrayList<ProductSpec>();
			for (String temp : cartList) {
				productList.add(specDAO.selectById(temp));
			}

			// 取出所有商品ID
			Integer[] productIds = new Integer[cartList.size()];

			for (int i = 0; i < productIds.length; i++) {
				productIds[i] = productList.get(i).getProductId();
			}

			// 取出所有活動
			List<EventApplicableProducts> allEvents = eventDAO.selectByCartList(productIds);

			List<String> list = new ArrayList<>();

			// 核對單品折價券活動資訊+取最優價
			for (ProductSpec temp : productList) {
				Integer productId = temp.getProductId();
				Integer price = temp.getProduct().getProductPrice();

				List<EventApplicableProducts> coupons = eventDAO.selectCoupontByProductId(productId);
				Integer[] priceArray = new Integer[coupons.size()];
				Map<Integer,String> priceMap = new TreeMap<>();
				
				if (!coupons.isEmpty()) {
					int i = 0;

					for (EventApplicableProducts coupon : coupons) {

						ThresholdType type = coupon.getEventSingleThreshold().getThresholdType();
						String couponCode = coupon.getEventSingleThreshold().getCouponCode();

						if (type == ThresholdType.FULL_PURCHASE) {

							Integer minPurchaseAmount = coupon.getEventSingleThreshold().getMinPurchaseAmount();
							Double discountRate = coupon.getEventSingleThreshold().getDiscountRate();
							Integer discountAmount = coupon.getEventSingleThreshold().getDiscountAmount();

							if (price > minPurchaseAmount) {

								if (discountRate != null) {
									priceMap.put((int) (price * discountRate),couponCode);
								}
								if (discountAmount != null) {
									priceMap.put(price - discountAmount,couponCode);
								}
							}
							i++;
						}
					}

					Set<Integer> set = priceMap.keySet();
					String couponCode = "" ;
					
					for (Integer key : set) {
						price = key;
						couponCode = priceMap.get(key);
			            break;
			        }
					
					
					String jsonString = gson.toJson(temp);
					jsonString = jsonString.substring(0, jsonString.lastIndexOf("}")) 
							+ ",\"couponPrice\":" + price.toString() + ",\"couponCode\":" + couponCode +"}";
					
					list.add(jsonString);

				}

			}

			return null;
		} else

		{

			return "購物車內尚無商品";
		}

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


}
