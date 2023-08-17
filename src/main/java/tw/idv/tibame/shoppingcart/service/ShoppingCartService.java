package tw.idv.tibame.shoppingcart.service;

import org.springframework.http.ResponseEntity;

import com.google.gson.JsonObject;

public interface ShoppingCartService {

	/**
	 * 購物車頁面載入全部商品
	 * @param memberId
	 * @return
	 */
	String init(String memberId) throws Exception;
	
	/**
	 * 加入購物車
	 */
	boolean addToCart(JsonObject data);
	
	/**
	 * 從購物車移除商品
	 */
	ResponseEntity<String> removeFromCart(JsonObject data);
	
	Long getCount(String memberId);
	
}
