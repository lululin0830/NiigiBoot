package tw.idv.tibame.shoppingcart.pojo;

import java.util.List;

import lombok.Data;

@Data
public class CartItem {
	private String productSpecId;
	private Integer productId;
	private String productName;
	private String specInfo1;
	private String specInfo2;
	private Integer productPrice;
	private String registerSupplier;
	private String shopVacation;
	private Integer specStock;
	private String shelvesStatus;
	private String couponCode;
	private Integer couponPrice;
	private List<String> eventIds;
	private String eventName;
	private String eventInfo;
	private Integer eventPrice;
	private List<String> giftProductSpecId;
}
