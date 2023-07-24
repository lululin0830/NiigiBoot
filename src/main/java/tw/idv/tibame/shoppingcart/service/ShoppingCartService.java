package tw.idv.tibame.shoppingcart.service;

public interface ShoppingCartService {

	/**
	 * 購物車頁面預設載入全部商品
	 * @param memberId
	 * @return
	 */
	String init(String memberId);
}
