package tw.idv.tibame.suppliers.entity;

import java.io.Serializable;
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
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Suppliers implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6143346746562984521L;

	@Id
	private String supplierId;
	private String supplierMemberAcct;
	private String businessId;
	private String ownerId;
	private String supplierAddress;
	private String bankCode;
	private String bankAcct;
	private String shopName;
	private String shopInfo;
	private byte[] logo;
	private byte[] shopBackground;
	@Column(insertable = false)
	private String supplierBanStatus;
	@Column(insertable = false)
	private String shopVacation;
	@Column(insertable = false)
	private Date vacationStart;
	@Column(insertable = false)
	private Date vacationEnd;
	@Column(insertable = false)
	private String pauseOrderAcceptance;
	@Column(insertable = false)
	private String pauseShipping;
	@Column(insertable = false)
	private String pauseNotification;
	@Column(insertable = false)
	private String approvalStatus;
	@Column(insertable = false)
	private String approvalRemark;
	@Column(insertable = false)
	private Timestamp enableTime;
	@Column(insertable = false)
	private Double grossProfitRatio;
	@Column(insertable = false)
	private Double pointRewardsRatio;
	
	public Suppliers(String shopVacation, Date vacationEnd, String pauseOrderAcceptance, String pauseShipping) {
		this.shopVacation = shopVacation;
		this.vacationEnd = vacationEnd;
		this.pauseOrderAcceptance = pauseOrderAcceptance;
		this.pauseShipping = pauseShipping;
	}

}
