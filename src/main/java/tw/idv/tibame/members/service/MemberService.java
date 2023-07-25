package tw.idv.tibame.members.service;

import com.google.gson.JsonObject;

import tw.idv.tibame.core.service.CoreService;
import tw.idv.tibame.members.entity.Members;

public interface MemberService extends CoreService<JsonObject> {

	// 註冊
	String register(Members newMember) throws Exception;

	// 登入
	String logIn(Members member) throws Exception;

}
