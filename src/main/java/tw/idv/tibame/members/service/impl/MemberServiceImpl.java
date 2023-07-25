package tw.idv.tibame.members.service.impl;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;

import tw.idv.tibame.core.util.JwtUtil;
import tw.idv.tibame.core.util.PasswordEncryptor;
import tw.idv.tibame.members.dao.MemberDAO;
import tw.idv.tibame.members.entity.Members;
import tw.idv.tibame.members.service.MemberService;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	private static volatile int counter = 17;
	private static final Object counterLock = new Object();

	@Autowired
	MemberDAO memberDAO;

	public String generateId() throws Exception {

		String id;

		synchronized (counterLock) {
			String lastId = memberDAO.selectLastMember();
			String numericPart = lastId.substring(1); // 從第二個字元開始取得後面的數字部分

			int result = Integer.parseInt(numericPart);

			if (counter <= result) {
				counter = result + 1;
				id = "M" + String.format("%09d", counter);
			} else {
				counter++;
				id = "M" + String.format("%09d", counter);
			}
		}
		return id;

	}

	@Override
	public String register(Members newMember) throws Exception {

		if (newMember.getMemberAcct() == null || newMember.getMemberAcct().isBlank()) {
			return "使用者帳號未輸入";
		}

		if (newMember.getPassword() == null || newMember.getPassword().isBlank()) {
			return "密碼未輸入";
		}

		if (newMember.getName() == null || newMember.getPassword().isBlank()) {
			return "姓名未輸入";
		}

		if (memberDAO.selectOneByMemberAcct(newMember.getMemberAcct()) != null) {
			return "此帳號已被註冊";
		}

		newMember.setMemberId(generateId());
		String encrypted = PasswordEncryptor.encrypt(newMember.getPassword());
		newMember.setPassword(encrypted);
		memberDAO.insert(newMember);

		return "註冊成功";

	}

	@Override
	public String logIn(Members member) throws NoSuchAlgorithmException {
		final String memberAcct = member.getMemberAcct();
		final String password = member.getPassword();

		if (memberAcct == null || memberAcct.isBlank()) {
			return "使用者帳號未輸入";
		}

		if (password == null || password.isBlank()) {
			return "密碼未輸入";
		}

		String encryptedPassword = PasswordEncryptor.encrypt(password);

		if (memberDAO.selectOneByMemberAcct(member.getMemberAcct()) == null) {
			return "尚未成為會員";
		}

		String storedEncryptedPassword = memberDAO.selectPasswordByMemberAcct(memberAcct);

		if (!encryptedPassword.equals(storedEncryptedPassword)) {
			return "密碼錯誤";
		}

		return JwtUtil.generateJwtToken(memberAcct, member.getMemberId());
	}

	@Override
	public String initForHeader(JsonObject memberInfo) {
		return null;
	}

}
