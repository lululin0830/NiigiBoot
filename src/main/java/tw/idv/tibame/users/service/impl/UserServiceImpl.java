package tw.idv.tibame.users.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;

import tw.idv.tibame.core.util.JwtUtil;
import tw.idv.tibame.core.util.PasswordEncryptor;
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

		String encrypted = PasswordEncryptor.encrypt(user.getPassword());
		user.setPassword(encrypted);
		userDAO.insert(user);

		user.setMessage("新增成功");
		user.setSuccessful(true);
		return user;
	}

	@Override
	public String login(Users user) throws NoSuchAlgorithmException {
		final String userAcct = user.getUserAcct();
		final String password = user.getPassword();

		if (userAcct == null || userAcct.isBlank()) {

			return "帳號未輸入";
		}
		if (password == null || password.isBlank()) {

			return "密碼未輸入";
		}
		if (userDAO.selectByUserAcct(user.getUserAcct()) == null) {
			return "尚未成為使用者";
		}
		String encryptedPassword = PasswordEncryptor.encrypt(password);

		String storedEncryptedPassword = userDAO.selectPasswordByUserAcct(userAcct);

		if (!encryptedPassword.equals(storedEncryptedPassword)) {
			return "密碼錯誤";
		}
		user = userDAO.selectByUserAcct(userAcct);

		String userIdString = String.valueOf(user.getUserId());

		return JwtUtil.generateJwtToken(userIdString, userAcct);
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
		String result;

		result = userDAO.getAllBySearch(searchcase, SearchSelect);
		return result;
	}

	@Transactional
	@Override
	public boolean updateUser(String userId, String changePassword, String financialAuthority,
			String customerServiceAuthorit, String marketingAuthority, String hrAuthority) {
		Users users = userDAO.selectBuUserId(userId);

		if (users == null) {
			return false;
		} else {
			boolean updated = false;
			if (changePassword != null) {
				String changePasswordHash = "";
				try {
					changePasswordHash = PasswordEncryptor.encrypt(changePassword);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
					return false;
				}
				users.setPassword(changePasswordHash);
				userDAO.update(users);
				updated = true;
			}
			if (financialAuthority != null) {
				users.setFinancialAuthority(financialAuthority);
				updated = true;
			}
			if (customerServiceAuthorit != null) {
				users.setCustomerServiceAuthority(customerServiceAuthorit);
				updated = true;
			}
			if (marketingAuthority != null) {
				users.setMarketingAuthority(marketingAuthority);
				updated = true;
			}
			if (hrAuthority != null) {
				users.setHrAuthority(hrAuthority);
				updated = true;
			}
			if (updated) {
				userDAO.update(users);
				return true;
			}
		}
		return false;
	}

}
