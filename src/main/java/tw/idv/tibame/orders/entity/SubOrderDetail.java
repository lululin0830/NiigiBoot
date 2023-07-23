package tw.idv.tibame.orders.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubOrderDetail implements Serializable{

	
	private static final long serialVersionUID = -3459181800840488062L;

	@Id
	private String orderDetailId;
	private String subOrderId;
	private String orderId ;
	private Integer productId;
	private String productSpecId;
	private int productPrice;
	@Column(insertable = false)
	private int itemCouponDiscount;
	private int eventPrice;
	@Column(insertable = false)
	private int ratingStar;
	@Column(insertable = false)
	private String comment;
	@Column(insertable = false)
	private Date commentDate;
	@Column(insertable = false)
	private Date refundDeadline;
	@Column(insertable = false)
//	private Date refundDate;
//	@Column(insertable = false)
	private String refundReason;
	@Column(insertable = false)
	private String refundRemark;
	@Column(insertable = false)
	private String itemStatus;
	
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(orderDetailId);
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this == obj) 
			return true;
		if(obj!=null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		SubOrderDetail other = (SubOrderDetail) obj;
		return Objects.equals(orderDetailId,other.orderDetailId);
		
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String result = "order_detail_id:" + orderDetailId + 
						"sub_order_id:" + subOrderId +
						"order_id:" + orderId +
						"product_id:" + productId +
						"product_spec_id:" + productSpecId +
						"product_price:" + productPrice +
						"item_coupon_discount:" + itemCouponDiscount +
						"event_price:" + eventPrice +
						"rating_price:" + ratingStar + 
						"comment:" + comment +
						"comment_date:" + commentDate +
						"refund_deadline:" + refundDeadline +
						"refund_date:" + refundReason +
						"refund_reason:" + refundReason +
						"refund_remark:" + refundRemark +
						"item_status:" + itemStatus;
		return result;
	}
	
}
