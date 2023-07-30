package tw.idv.tibame.orders.entity;

import java.io.Serializable;
import java.sql.Date;

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
	private Integer productPrice;
	private Integer itemCouponDiscount;
	private String itemCouponCode;
	private Integer eventPrice;
	private String eventIds;
	@Column(insertable = false)
	private Integer ratingStar;
	@Column(insertable = false)
	private String comment;
	@Column(insertable = false)
	private Date commentDate;
	@Column(insertable = false)
	private Date refundDeadline;
	@Column(insertable = false)
	private String refundReason;
	@Column(insertable = false)
	private String refundRemark;
	@Column(insertable = false)
	private String itemStatus;
	
}
