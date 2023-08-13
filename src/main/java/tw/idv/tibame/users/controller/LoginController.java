package tw.idv.tibame.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.idv.tibame.users.entity.Users;
import tw.idv.tibame.users.service.UserService;

@RestController
@RequestMapping("users")
@CrossOrigin(origins = "*")
public class LoginController {

	@Autowired
	private UserService userService;

	@PostMapping("Login")
	public ResponseEntity<String> login(@RequestBody Users users) {

		String token = "";
		try {
			token = userService.login(users);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(String.format("登入錯誤:%s , errMsg: %s", "系統繁忙中...請稍後再試", e.getMessage()));
		}
		if (token.length() > 13) {
			return ResponseEntity.status(HttpStatus.OK).body(token);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(token);

	}
}
