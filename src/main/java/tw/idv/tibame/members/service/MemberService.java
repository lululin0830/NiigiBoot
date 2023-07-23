package tw.idv.tibame.members.service;

import tw.idv.tibame.members.entity.Members;

public interface MemberService {

	// 註冊
	public String register(Members newMember)throws Exception;

	// 登入
	public String logIn (Members member)throws Exception;


}
