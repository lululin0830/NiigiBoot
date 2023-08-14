package tw.idv.tibame.users.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.google.gson.JsonObject;

import tw.idv.tibame.users.entity.Users;

public interface UserService {

	Users register(Users user) throws Exception;

	String login(Users user) throws NoSuchAlgorithmException;

	boolean updateUser(String userId, String password, String financialAuthority, String customerServiceAuthorit,
			String marketingAuthority, String hrAuthority);

	List<Users> findAll(JsonObject searchCondition) throws Exception;

	boolean remove(Integer userId);

	public String getAllInit();

	public String getBySearch(JsonObject searchCondition);
}
