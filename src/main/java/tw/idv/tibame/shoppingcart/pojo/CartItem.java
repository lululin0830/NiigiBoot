package tw.idv.tibame.shoppingcart.pojo;

import java.sql.Date;
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
	private String pauseOrderAcceptance;
	private String pauseShipping;
	private Date vacationEnd;
	private Integer specStock;
	private String shelvesStatus;
	private String couponCode;
	private String couponName;
	private String couponInfo;
	private Integer couponPrice;
	private List<String> eventIds;
	private List<String> eventName;
	private List<String> eventInfo;
	private Integer eventPrice;
	private List<String> giftProductSpecId;
}
