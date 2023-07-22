package tw.idv.tibame.users.entity;

import java.util.Date;

public class AccountSuspendRecord implements java.io.Serializable{

	/**
	 * 用戶停權紀錄
	 */
	private static final long serialVersionUID = 4152170575553792634L;
	private Integer accountSuspendId;
	private String memberId;
	private String suspendReason;
	private Integer dealerUserId;
	private Date suspendStart;
	private Date suspendEnd;
	private String suspendDuration;
	private String suspendStatusChange;
	private Integer changeUserId;

	public Integer getAccountSuspendId() {
		return accountSuspendId;
	}

	public void setAccountSuspendId(Integer accountSuspendId) {
		this.accountSuspendId = accountSuspendId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getSuspendReason() {
		return suspendReason;
	}

	public void setSuspendReason(String suspendReason) {
		this.suspendReason = suspendReason;
	}

	public Integer getDealerUserId() {
		return dealerUserId;
	}

	public void setDealerUserId(Integer dealerUserId) {
		this.dealerUserId = dealerUserId;
	}

	public Date getSuspendStart() {
		return suspendStart;
	}

	public void setSuspendStart(Date suspendStart) {
		this.suspendStart = suspendStart;
	}

	public Date getSuspendEnd() {
		return suspendEnd;
	}

	public void setSuspendEnd(Date suspendEnd) {
		this.suspendEnd = suspendEnd;
	}

	public String getSuspendDuration() {
		return suspendDuration;
	}

	public void setSuspendDuration(String suspendDuration) {
		this.suspendDuration = suspendDuration;
	}

	public String getSuspendStatusChange() {
		return suspendStatusChange;
	}

	public void setSuspendStatusChange(String suspendStatusChange) {
		this.suspendStatusChange = suspendStatusChange;
	}

	public Integer getChangeUserId() {
		return changeUserId;
	}

	public void setChangeUserId(Integer changeUserId) {
		this.changeUserId = changeUserId;
	}
}
