package tw.idv.tibame.users.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;

import tw.idv.tibame.users.dao.UserDAO;
import tw.idv.tibame.users.entity.Users;
import tw.idv.tibame.users.service.UserService;

@Service

public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Transactional
	@Override
	public Users register(Users user) throws Exception {
		if (user.getUserAcct() == null || user.getUserAcct().isBlank()) {
			user.setMessage("帳號未輸入");
			user.setSuccessful(false);
			return user;
		}
		if (user.getUserName() == null || user.getUserName().isBlank()) {
			user.setMessage("名稱未輸入");
			user.setSuccessful(false);
			return user;
		}
		if (user.getPassword() == null || user.getPassword().isBlank()) {
			user.setMessage("密碼未輸入");
			user.setSuccessful(false);
			return user;
		}
		if (userDAO.selectByUserAcct(user.getUserAcct()) != null) {
			user.setMessage("帳號已重複");
			user.setSuccessful(false);
			return user;
		}
		
		userDAO.insert(user);
		
		user.setMessage("新增成功");
		user.setSuccessful(true);
		return user;
	}

	@Override
	public Users login(Users user) {
		final String userAcct = user.getUserAcct();
		final String password = user.getPassword();

		if (userAcct == null || userAcct.isBlank()) {
			user.setMessage("帳號未輸入");
			user.setSuccessful(false);
			return user;
		}
		if (password == null || password.isBlank()) {
			user.setMessage("密碼未輸入");
			user.setSuccessful(false);
			return user;
		}

		user = userDAO.selectForLogin(userAcct, password);
		if (user == null) {
			user = new Users();
			user.setMessage("使用者名稱或密碼錯誤");
			user.setSuccessful(false);
			return user;
		}
		user.setMessage("新增成功");
		user.setSuccessful(true);
		return user;
	}

	@Transactional
	@Override
	public Users edit(Users user) {
		final Users oUsers = userDAO.selectByUserAcct(user.getUserAcct());
		user.setPassword(oUsers.getPassword());
		user.setFinancialAuthority(oUsers.getFinancialAuthority());
		user.setCustomerServiceAuthority(oUsers.getCustomerServiceAuthority());
		user.setHrAuthority(oUsers.getHrAuthority());
		user.setMarketingAuthority(oUsers.getMarketingAuthority());
		final int resultCount = userDAO.update(user);
		user.setSuccessful(resultCount > 0);
		user.setMessage(resultCount > 0 ? "修改成功" : "修改失敗");
		return user;
	}

	@Override
	public List<Users> findAll(JsonObject searchCondition) throws Exception {
		return userDAO.getAll();
	}

	@Transactional
	@Override
	public boolean remove(Integer userId) {
		return userDAO.deleteByUserId(userId) > 0;
	}

	@Transactional
	@Override
	public boolean save(Users user) {
		return userDAO.update(user) > 0;
	}

	@Override
	public String getAllInit() {
		String getAllInit = null;

		try {
			getAllInit = userDAO.getAllInit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return getAllInit;
	}

	@Override
	public String getBySearch(JsonObject searchCondition) {
		
		String searchcase = searchCondition.get("searchcase").getAsString();
		String SearchSelect = searchCondition.get("searchway").getAsString();
		String result ;
		
		result = userDAO.getAllBySearch(searchcase ,SearchSelect);
		return result;
	}

}
