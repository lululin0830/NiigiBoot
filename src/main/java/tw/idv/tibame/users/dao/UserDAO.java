package tw.idv.tibame.users.dao;

import tw.idv.tibame.core.dao.CoreDAO;
import tw.idv.tibame.users.entity.Users;


public interface UserDAO extends CoreDAO<Users, String>{
	// 萬用更新
		public int update(Users newUser);

		// 刪除
		public int deleteByUserId(Integer userId);

		// 找名稱
		public Users selectByUserName(String userName);

		// 找帳號
		public Users selectByUserAcct(String userAcct);

		// 登入
		public Users selectForLogin(String userAcct, String password);
}
