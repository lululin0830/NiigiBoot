package tw.idv.tibame.members.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.idv.tibame.members.service.MemberService;

@RestController
@RequestMapping("/member")
@CrossOrigin(origins = "*")
public class MemberChangeController {
	@Autowired
	private MemberService service;
	
	@PostMapping("/changePassword")
	public ResponseEntity<String> changePassword(@RequestParam String memberId,
												 @RequestParam String oldPassword,
												 @RequestParam String newPassword){
		boolean passwordChange = service.changePassword(memberId, oldPassword, newPassword);
		
		if (passwordChange) {
            return ResponseEntity.ok("密碼更改成功");
        } else {
            return ResponseEntity.badRequest().body("密碼更改失敗");
        }
	}
}
