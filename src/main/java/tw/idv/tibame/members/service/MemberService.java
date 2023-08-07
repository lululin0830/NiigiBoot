package tw.idv.tibame.members.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

import com.google.gson.JsonObject;

import tw.idv.tibame.core.service.CoreService;
import tw.idv.tibame.members.entity.Members;

public interface MemberService extends CoreService<JsonObject> {

	// 註冊
	String register(Members newMember) throws Exception;

	// 登入
	String logIn(Members member) throws Exception;

	public List<Members> getAll();

	public String getBySearch(JsonObject searchCondition);

	// 更改密碼
	public boolean changePassword(String memberId, String oldPassword, String newPassword);

	public boolean updateMember(String memberId, String name, String phone, String backupEmail, byte[] memPhoto);

	/**
	 * 會員中心側邊欄顯示會員資訊
	 * @param jwtToken
	 * @return 
	 */
	ResponseEntity<String> showAsideInfo(String jwtToken);
}
