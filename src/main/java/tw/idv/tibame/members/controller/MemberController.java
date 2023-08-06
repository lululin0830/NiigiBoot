package tw.idv.tibame.members.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.idv.tibame.members.entity.Members;
import tw.idv.tibame.members.service.MemberService;

@RestController
@RequestMapping("/member")
@CrossOrigin(origins = "*")
public class MemberController {

	@Autowired
	MemberService service;

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody Members newMember) {

		String message;
		try {
			message = service.register(newMember);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("系統繁忙中...請稍後再試");
		}

		if (Objects.equals(message, "註冊成功")) {

			return ResponseEntity.status(HttpStatus.OK).body(message);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}

	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Members member) {

		String token = "";
		try {
			token = service.logIn(member);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(String.format("登入錯誤:%s , errMsg: %s", "系統繁忙中...請稍後再試", e.getMessage()));
		}

		if (token.length() > 13) {
			return ResponseEntity.status(HttpStatus.OK).body(token);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(token);
		}
	}
}
