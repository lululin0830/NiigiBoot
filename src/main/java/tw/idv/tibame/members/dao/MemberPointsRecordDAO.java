package tw.idv.tibame.members.dao;

import java.util.List;

import tw.idv.tibame.members.entity.MemberPointsRecord;

public interface MemberPointsRecordDAO {
	// 新增點數記錄編號，會員編號，狀態變更類型1，變更時間，子訂單編號，增加數量，點數有效期限
		public void insertEnum1(MemberPointsRecord memberPointsRecord);

		// 新增點數記錄編號，會員編號，狀態變更類型2，變更時間，子訂單編號，減少數量，點數有效期限
		public void insertEnum2(MemberPointsRecord memberPointsRecord);

		// 新增點數記錄編號，會員編號，狀態變更類型3，變更時間，子訂單編號，增加數量，點數有效期限，經手人，人工變更原因
		public void insertEnum3(MemberPointsRecord memberPointsRecord);

		// 新增點數記錄編號，會員編號，狀態變更類型4，變更時間，子訂單編號，減少數量，點數有效期限，經手人，人工變更原因
		public void insertEnum4(MemberPointsRecord memberPointsRecord);
		//前台
		//查詢日期
		public MemberPointsRecord findByPointAdjustTime(String pointRecordId);
		//查詢訂單編號
		public MemberPointsRecord findBySubOrderId(String SubOrderId);
		//查詢所有點數紀錄然後做加總  2個方法
		public MemberPointsRecord findAllByPointRecordId(String pointRecordId);
		//查詢狀態變更類型1
		public MemberPointsRecord findByPointStatusModify1(String PointStatusModify);
		//查詢狀態變更類型2
		public MemberPointsRecord findByPointStatusModify2(String PointStatusModify);
		//查詢狀態變更類型3
		public MemberPointsRecord findByPointStatusModify3(String PointStatusModify);
		//查詢狀態變更類型4
		public MemberPointsRecord findByPointStatusModify4(String PointStatusModify);
		
		//後臺用戶金流的點數紀錄查詢會員編號
		public MemberPointsRecord findByPrimaryKey(String pointRecordId);

		public List<MemberPointsRecord> getAll();
}
