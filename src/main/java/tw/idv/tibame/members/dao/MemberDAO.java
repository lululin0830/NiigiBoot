package tw.idv.tibame.members.dao;

import java.sql.Timestamp;
import java.util.List;

import tw.idv.tibame.core.dao.CoreDAO;
import tw.idv.tibame.members.entity.Members;

public interface MemberDAO extends CoreDAO<Members, String>{
	// 新增會員(編號，帳號，密碼，姓名，手機，性別，生日)

		// 萬用更新
		public Members update(Members newMember);

		// 登入 找帳號,密碼
		Members selectByLogin(String memberAcct, String password);

		// 找單一會員 找編號
		public Members selectOneByMemberId(String memberId);

		// 找單一會員 找帳號
		public Members selectOneByMemberAcct(String memberAcct);

		// 找多個會員 找編號
		public Members selectManyByMemberId(String memberId);

		// 找多個會員 帳號
		public Members selectManyByMemberAcct(String memberAcct);

		// 找多個會員 註冊日期，參數兩個
		public Members selectManyByMemberIdDate(String memberId, Timestamp regTime);

		// 找多個會員 查詢帳號狀態
		public Members selectManyByMemberIdRegStatusOpen(String memberId, String regStatusOpen);

		// 需要單獨查詢常用收件人，常用地址，常用收件電話
		public Members selectByRecientPhoneNumDeliveryAddress(String lastRecipient, String lastPhoneNum,
				String lastDeliveryAddress);

		String selectLastMember();

		public String selectPasswordByMemberAcct(String memberAcct);
		
		//搜尋按鈕
		public String getAllBySearch(String searchCase,String searchSelect,Timestamp startDate,Timestamp closeDate,String dateSelect);
}
