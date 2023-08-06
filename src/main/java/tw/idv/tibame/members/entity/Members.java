package tw.idv.tibame.members.entity;

import java.sql.Date;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Members implements java.io.Serializable {

	/**
	 * 會員資料
	 */
	private static final long serialVersionUID = 2365248678814032865L;

	@Id
	private String memberId;
	private String memberAcct;
	private String password;
	private String phone;
	private String name;
	private String gender;
	private Date birthday;
	@Column(insertable = false)
	private Timestamp regTime;
	@Column(insertable = false)
	private String banStatus;
	@Column(insertable = false)
	private Integer memPointBalance;
	@Column(insertable = false)
	private Date memPointMinExp;
	@Column(insertable = false)
	private String backupEmail;
	@Column(insertable = false)
	private byte[] memPhoto;
	@Column(insertable = false)
	private String memberAddress;
	@Column(insertable = false)
	private String lastRecipient;
	@Column(insertable = false)
	private String lastPhoneNum;
	@Column(insertable = false)
	private String lastDeliveryAddress;
	@Column(insertable = false)
	private String creditNum;
	@Column(insertable = false)
	private String cardholder;
	@Column(insertable = false)
	private String creditExp;
	@Column(insertable = false)
	private String cvv;
	@Column(insertable = false)
	private String regStatusOpen;
	@Column(insertable = false)
	private String backupStatusOpen;

	public Members(Integer memPointBalance, Date memPointMinExp, String name, String phone, String memberAddress,
			String lastRecipient, String lastPhoneNum, String lastDeliveryAddress) {
		super();
		this.phone = phone;
		this.name = name;
		this.memPointBalance = memPointBalance;
		this.memPointMinExp = memPointMinExp;
		this.memberAddress = memberAddress;
		this.lastRecipient = lastRecipient;
		this.lastPhoneNum = lastPhoneNum;
		this.lastDeliveryAddress = lastDeliveryAddress;
	}

}
