package tw.idv.tibame.users.service;

import java.util.List;

import com.google.gson.JsonObject;

import tw.idv.tibame.users.entity.Users;

public interface UserService {

		Users register(Users user) throws Exception;
		
		Users login(Users user);
		
		Users edit(Users user);
		
		List<Users> findAll(JsonObject searchCondition) throws Exception;
		
		boolean remove(Integer userId);
		
		boolean save(Users user);
		
		public String getAllInit();
		
		public String getBySearch(JsonObject searchCondition);
}
