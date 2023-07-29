package tw.idv.tibame.orders.entity;

import java.sql.Date;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "SubOrder")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubOrder implements java.io.Serializable{

	private static final long serialVersionUID = -5618369222849326060L;
	
	@Id
	private String subOrderId;
	private String orderId;	
	private String supplierId;
	private String memberId;
	@Column(insertable = false)
	private Timestamp orderCreateTime;
	@Column(insertable = false)
	private Timestamp orderCloseTime;
	@Column(insertable = false)
	private String subOrderStatus;
	private Integer subPaidAmount;
	private Integer subPointsDiscount;
	private Integer subCouponDiscount;
	private Integer grossProfit;
	private Integer pointsReward;
	@Column(insertable = false)
	private String grantStatus;
	@Column(insertable = false)
	private Date grantDate;
	private String recipient;
	private String phoneNum;
	private String deliveryAddress;

}
