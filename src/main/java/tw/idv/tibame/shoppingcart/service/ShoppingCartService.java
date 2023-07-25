package tw.idv.tibame.shoppingcart.service;

import com.google.gson.JsonObject;

public interface ShoppingCartService {

	/**
	 * 購物車頁面載入全部商品
	 * @param memberId
	 * @return
	 */
	String init(String memberId);
	
	/**
	 * 加入購物車
	 */
	String addToCart(JsonObject data);
	
	/**
	 * 從購物車移除商品
	 */
	boolean removeFromCart(JsonObject data);
	
	
}
