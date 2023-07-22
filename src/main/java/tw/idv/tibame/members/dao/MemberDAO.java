package tw.idv.tibame.members.dao;

import java.sql.Timestamp;

import tw.idv.tibame.core.dao.CoreDAO;
import tw.idv.tibame.members.entity.Members;

public interface MemberDAO extends CoreDAO<Members, String>{
	// 新增會員(編號，帳號，密碼，姓名，手機，性別，生日)
//	public void insert(Member member);

	// 更新開通狀態
	public Members updateReg(String memberId);

	// 更新備用信箱帳號
	public Members updatBackupEmail(String memberId);

	// 更新備用信箱開通狀態
	public Members updateBackup(String memberId);

	// 更新姓名
	public Members updateName(String memberId);

	// 更新手機
	public Members updatePhone(String memberId);

	// 更新會員圖片
	public Members updatePhoto(String memberId);

	// 更新停權狀態
	public Members updateBanStatus(String memberId);

	// 更新會員點數餘額
	public Members updateMemberPointBlance(String memberId);

	// 更新會員點數最短有效期限
	public Members updateMemberPointMinExp(String memberId);

	// 更新卡號，持卡人姓名，安全碼，後面傳四個參數
	public Members updateCreditCard(String memberId);

	// 更新地址
	public Members updateAddress(String memberId);

	// 更新常用收件人，常用地址，常用收件電話
	public Members updateAnotherPeople(String memberId);

	// 找單一會員 找編號
	public Members findOneByMemberId(String memberId);

	// 找單一會員 找帳號
	public Members findOneByMemberAcct(String memberId);

	// 找多個會員 找編號
	public Members findManyByMemberId(String memberId);

	// 找多個會員 帳號
	public Members findManyByMemberAcct(String memberId);

	// 找多個會員 註冊日期，參數兩個
	public Members findManyByPrimaryKeyAndDate(String memberId, Timestamp regTime);

	// 找多個會員 查詢帳號狀態
	public Members findManyByPrimaryKeyAndRegStatusOpen(String memberId, String regStatusOpen);

	// 需要單獨查詢常用收件人，常用地址，常用收件電話
	public Members findLastByRecientAndPhoneNumAndDeliveryAddress(String lastRecipient, String lastPhoneNum,
			String lastDeliveryAddress);

}
