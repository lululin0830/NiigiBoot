package tw.idv.tibame.users.dao;

import java.util.List;

import tw.idv.tibame.users.entity.Users;

public interface UserDAO {
	public void insert(Users user);

	// 更新一個權限，換四個權限
	public void updatePermissions(Users user);
	//更新密碼
	public void updatePassword(Users user);

	public void delete(Integer userId);

	public Users findByPrimaryKey(Integer userId);

	public List<Users> getAll();
}
