package tw.idv.tibame.users.dao;

import java.util.List;

import tw.idv.tibame.users.entity.AccountSuspendRecord;

public interface AccountSuspendRecordDAO {
	public void insert(AccountSuspendRecord accountSuspendRecord);

	// 更新停權變更，停權結束時間，變更人，停權天數
	public void update(AccountSuspendRecord accountSuspendRecord);
	//查詢停權紀錄by會員id回傳list
	//查商家查詢停權紀錄by會員帳號回傳list
	public AccountSuspendRecord findByPrimaryKey(Integer accountSuspendId);

	public List<AccountSuspendRecord> getAll();
}
