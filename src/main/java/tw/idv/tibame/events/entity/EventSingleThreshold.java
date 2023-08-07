package tw.idv.tibame.events.entity;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tw.idv.tibame.events.entity.enumtype.EventType;
import tw.idv.tibame.events.entity.enumtype.ThresholdType;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventSingleThreshold implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -570933023050711722L;
	
	@Id
	private String eventId;
	private String eventRegisterSupplier;
	private String eventType;
	private String eventName;
	private String eventInfo;
	private Date eventStart;
	private Date eventEnd;
	private String thresholdType;
	private Integer minPurchaseQuantity;
	private Integer minPurchaseAmount;
	private Double discountRate;
	private Integer discountAmount;
	private String giftProductSpecId;
	@Column(unique = true)
	private String couponCode;
	private Integer couponAvailableAmount;
	private Integer couponUsedAmount;
	private Integer couponAvailablePerPurchase;
	
	public EventSingleThreshold(String eventName, String eventInfo) {
		this.eventName = eventName;
		this.eventInfo = eventInfo;
	}
	
}
