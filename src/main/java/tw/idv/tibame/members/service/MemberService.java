package tw.idv.tibame.members.service;

import tw.idv.tibame.members.entity.Members;

public interface MemberService {

	// 註冊
	public boolean register(Members newMember);

	// 登入
	public boolean logIn (Members member);

}
