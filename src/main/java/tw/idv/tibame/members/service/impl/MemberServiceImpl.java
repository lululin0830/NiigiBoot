package tw.idv.tibame.members.service.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import tw.idv.tibame.core.util.JwtUtil;
import tw.idv.tibame.core.util.PasswordEncryptor;
import tw.idv.tibame.members.dao.MemberDAO;
import tw.idv.tibame.members.dao.impl.MemberDAOImpl;
import tw.idv.tibame.members.entity.Members;
import tw.idv.tibame.members.service.MemberService;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	private static volatile int counter = 17;
	private static final Object counterLock = new Object();

	@Autowired
	MemberDAO memberDAO;

	@Autowired
	MemberDAOImpl memberDAOImpl;

	@Autowired
	Gson gson;

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

		// 驗證 memberAcct (email 格式)
		String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
		Pattern pattern = Pattern.compile(emailPattern);
		Matcher matcher = pattern.matcher(newMember.getMemberAcct());
		if (!matcher.matches()) {
			return "email格式不對";
		}

		// 驗證 phone (09xx-xxx-xxx 格式)
		String phonePattern = "^09\\d{2}-\\d{3}-\\d{3}$";
		pattern = Pattern.compile(phonePattern);
		matcher = pattern.matcher(newMember.getPhone());
		if (!matcher.matches()) {
			return "手機格式不對，應為09xx-xxx-xxx";
		}

		if (newMember.getMemberAcct() == null || newMember.getMemberAcct().isBlank()) {
			return "使用者帳號未輸入";
		}

		if (newMember.getPassword() == null || newMember.getPassword().isBlank()) {
			return "密碼未輸入";
		}

		if (newMember.getName() == null || newMember.getName().isBlank()) {
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
		
		member = memberDAO.selectOneByMemberAcct(memberAcct);

		return JwtUtil.generateJwtToken(member.getMemberId(), memberAcct);
	}

	@Override
	public String initForHeader(JsonObject memberInfo) {
		return null;
	}

	@Override
	public List<Members> getAll() {
		List<Members> getAll = null;

		try {
			getAll = memberDAOImpl.getAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return getAll;
	}

	@Override
	public String getBySearch(JsonObject searchCondition) {
		String searchcase = searchCondition.get("searchcase").getAsString();

		String SearchSelect = searchCondition.get("searchway").getAsString();

		String startDateString = searchCondition.get("StartDate").getAsString();

		Timestamp startDate, closeDate;

		if (startDateString.length() > 0) {
			startDateString += " 00:00:00";
			startDate = Timestamp.valueOf(startDateString);
		} else {
			startDate = Timestamp.valueOf("1970-01-01 00:00:00");
		}

		String closeDateString = searchCondition.get("EndDate").getAsString();

		if (closeDateString.length() > 0) {
			closeDateString += " 00:00:00";
			closeDate = Timestamp.valueOf(closeDateString);
		} else {
			closeDate = Timestamp.valueOf(LocalDateTime.now());
		}

		String dateSelect = searchCondition.get("DateSelect").getAsString();

		String result = null;

		try {
			result = memberDAOImpl.getAllBySearch(searchcase, SearchSelect, startDate, closeDate, dateSelect);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean changePassword(String memberId, String oldPassword, String newPassword) {
		Members members = memberDAO.selectOneByMemberId(memberId);

		if (members == null) {
			return false;
		}
		// 檢查輸入的舊密碼是否與資料庫中的密碼相符
		String oldPasswordHash = null;
		try {
			oldPasswordHash = PasswordEncryptor.encrypt(oldPassword);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false; // 處理加密錯誤
		}

		if (!members.getPassword().equals(oldPasswordHash)) {
			return false; // 舊密碼不正確
		}

		// 將新密碼加密並更新到資料庫
		String newPasswordHash = null;
		try {
			newPasswordHash = PasswordEncryptor.encrypt(newPassword);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false; // 處理加密錯誤
		}

		members.setPassword(newPasswordHash);
		memberDAO.update(members);
		return true;
	}

	@Override
	public boolean updateMember(String memberId, String name, String phone, String backupEmail, byte[] memPhoto) {
		Members members = memberDAO.selectOneByMemberId(memberId);

		if (members == null) {
		} else {

			boolean updated = false;
			if (name != null) {
				members.setName(name);
				updated = true;
			}
			if (phone != null && isValidPhoneNumber(phone)) {
				members.setPhone(phone);
				updated = true;
			}
			if (backupEmail != null && isValidEmail(backupEmail)) {
				if (members.getBackupEmail() == null) {
					members.setBackupEmail(backupEmail);
				} else {
					members.setBackupEmail(backupEmail);
				}
				updated = true;
			}
			if (memPhoto != null) {
				if (members.getMemPhoto() == null) {
					members.setMemPhoto(memPhoto);
				} else {
					members.setMemPhoto(memPhoto);
				}
				updated = true;
			}
			if (updated) {
				memberDAO.update(members);
				return true;
			}
		}
		return false;
	}

	private boolean isValidPhoneNumber(String phoneNumber) {
		// 手機號碼格式正則表達式
		String regex = "09\\d{2}-\\d{3}-\\d{3}";
		return phoneNumber.matches(regex);
	}

	private boolean isValidEmail(String email) {
		// 信箱格式正則表達式
		String regex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";
		return email.matches(regex);
	}

	@Override
	public ResponseEntity<String> showAsideInfo(String jwtToken) {

		if(jwtToken.isBlank()) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"User not logged in\"}");
		}
		
		Map<String, String> result = JwtUtil.validateJwtTokenAndSendInfo(jwtToken);
		
		if(result.get("error")!= null) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.get("error"));
		}
		
		
		return ResponseEntity.ok(gson.toJson(result));
	}
}
