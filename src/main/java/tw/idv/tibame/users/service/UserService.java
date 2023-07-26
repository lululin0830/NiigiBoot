package tw.idv.tibame.users.service;

import java.util.List;

import tw.idv.tibame.users.entity.Users;

public interface UserService {

		Users register(Users user) throws Exception;
		
		Users login(Users user);
		
		Users edit(Users user);
		
		List<Users> findAll() throws Exception;
		
		boolean remove(Integer userId);
		
		boolean save(Users user);
}
