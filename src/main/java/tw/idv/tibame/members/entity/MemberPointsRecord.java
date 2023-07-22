package tw.idv.tibame.members.entity;

import java.util.Date;

public class MemberPointsRecord implements java.io.Serializable{

	/**
	 * 會員點數紀錄
	 */
	private static final long serialVersionUID = -5516777768072509736L;
	private Integer pointRecordId;
	private String memberId;
	private String pointStatusModify;
	private Date pointAdiustTome;
	private String subOrderId;
	private Integer pointReduced;
	private Integer pointIncreased;
	private Date pointExp;
	private Integer dealerUserId;
	private String reasonFoChangePt;
	public Integer getPointRecordId() {
		return pointRecordId;
	}
	public void setPointRecordId(Integer pointRecordId) {
		this.pointRecordId = pointRecordId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getPointStatusModify() {
		return pointStatusModify;
	}
	public void setPointStatusModify(String pointStatusModify) {
		this.pointStatusModify = pointStatusModify;
	}
	public Date getPointAdiustTome() {
		return pointAdiustTome;
	}
	public void setPointAdiustTome(Date pointAdiustTome) {
		this.pointAdiustTome = pointAdiustTome;
	}
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	public Integer getPointReduced() {
		return pointReduced;
	}
	public void setPointReduced(Integer pointReduced) {
		this.pointReduced = pointReduced;
	}
	public Integer getPointIncreased() {
		return pointIncreased;
	}
	public void setPointIncreased(Integer pointIncreased) {
		this.pointIncreased = pointIncreased;
	}
	public Date getPointExp() {
		return pointExp;
	}
	public void setPointExp(Date pointExp) {
		this.pointExp = pointExp;
	}
	public Integer getDealerUserId() {
		return dealerUserId;
	}
	public void setDealerUserId(Integer dealerUserId) {
		this.dealerUserId = dealerUserId;
	}
	public String getReasonFoChangePt() {
		return reasonFoChangePt;
	}
	public void setReasonFoChangePt(String reasonFoChangePt) {
		this.reasonFoChangePt = reasonFoChangePt;
	}
}
