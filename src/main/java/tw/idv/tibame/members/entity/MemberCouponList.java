package tw.idv.tibame.members.entity;

public class MemberCouponList implements java.io.Serializable{

	/**
	 * 折價券清單
	 */
	private static final long serialVersionUID = 1790566556045772784L;
	
	private String memberId;
	private String couponCode;
	private String couponStatus;
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public String getCouponStatus() {
		return couponStatus;
	}
	public void setCouponStatus(String couponStatus) {
		this.couponStatus = couponStatus;
	}
}
