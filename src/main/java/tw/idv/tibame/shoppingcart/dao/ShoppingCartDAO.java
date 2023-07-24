package tw.idv.tibame.shoppingcart.dao;

import java.util.List;

public interface ShoppingCartDAO {

	/**
	 * 檢查資料庫中是否已有該會員的購物車資料
	 * @param memberId
	 * @return
	 */
	boolean hasExistsCart(String memberId);
	
	/**
	 * 建立購物車&新增第一筆商品至購物車
	 * @param memberId
	 * @param productSpecId
	 * @return
	 */
	boolean insert(String memberId, int productSpecId);
	
	/**
	 * 新增商品至購物車
	 * @param memberId
	 * @param productSpecId
	 * @return
	 */
	boolean update(String memberId, int productSpecId);

	/**
	 * 查詢購物車內商品清單
	 * @param memberId
	 * @return
	 */
	List<String> getCartList(String memberId);

	/**
	 * 移除購物車內單項商品
	 * @param memberId
	 * @param productSpecId
	 * @return
	 */
	boolean delete(String memberId, int productSpecId);
	
	/**
	 * 訂單成立後刪除購物車
	 * @param memberId
	 * @return
	 */
	boolean delete (String memberId);
	
	/**
	 * 取得購物車內商品總數量
	 * @param memberId
	 * @return
	 */
	int getCount(String memberId);
}
