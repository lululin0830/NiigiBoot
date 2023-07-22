package tw.idv.tibame.members.dao;

import java.util.List;

import tw.idv.tibame.members.entity.MemberCouponList;

public interface MemberCouponListDAO {
	public void insert(MemberCouponList memberCouponList);

	// 更新折價券狀態
	public void updateCoupon(MemberCouponList memberCouponList);
	//找偉豪合作
	//查詢折價券by日期
	
	public MemberCouponList findByPrimaryKey(String memberId, String couponCode);
	//找全部 getall有參數，是member_id
	public List<MemberCouponList> getAll();

}
