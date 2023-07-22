package tw.idv.tibame.members.service;

import tw.idv.tibame.core.service.CoreService;
import tw.idv.tibame.members.entity.Members;

public interface MemberService extends CoreService {

	// 註冊
	public boolean register(Members newMember);

	// 登入
	public boolean logIn (Members member);

}
