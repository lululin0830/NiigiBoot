package tw.idv.tibame.suppliers.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Suppliers implements Serializable{

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
	private String supplierBanStatus;
	private String shopVacation;
	private Date vacationStart;
	private Date vacationEnd;
	private String pauseOrderAcceptance;
	private String pauseNotification;
	private String approvalRemark;
	private Timestamp enableTime;
	private Double grossProfitRatio;
	private Double pointRewardsRatio;
}
