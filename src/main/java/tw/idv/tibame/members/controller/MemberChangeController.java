package tw.idv.tibame.members.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tw.idv.tibame.members.dao.MemberDAO;
import tw.idv.tibame.members.entity.Members;
import tw.idv.tibame.members.service.MemberService;

@RestController
@RequestMapping("/member")
@CrossOrigin(origins = "*")
public class MemberChangeController {
	@Autowired
	private MemberService service;

	@Autowired
	private MemberDAO dao;

	// 更改密碼
	@PostMapping("/changePassword")
	public ResponseEntity<String> changePassword(@RequestParam String memberId, @RequestParam String oldPassword,
			@RequestParam String newPassword) {
		boolean passwordChange = service.changePassword(memberId, oldPassword, newPassword);

		if (passwordChange) {
			return ResponseEntity.ok("密碼更改成功");
		} else {
			return ResponseEntity.badRequest().body("密碼更改失敗");
		}
	}

	// 顯示個人資訊頁
	@GetMapping("/selectId")
	public ResponseEntity<Members> getMember(@RequestParam String memberId) {
		Members members = dao.selectOneByMemberId(memberId);
		if (members != null) {
			return ResponseEntity.ok(members);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// 更改個人資訊
	@PostMapping("/update")
	public ResponseEntity<String> updateMember(
	        @RequestParam String memberId,
	        @RequestParam(required = false) String name,
	        @RequestParam(required = false) String phone,
	        @RequestParam(required = false) String backupEmail,
	        @RequestPart(required = false) MultipartFile memPhoto) throws IOException {

	    boolean updated = service.updateMember(memberId, name, phone, backupEmail,
	            memPhoto != null ? memPhoto.getBytes() : null);

	    if (updated) {
	        return ResponseEntity.ok("successfull");
	    } else {
	        return ResponseEntity.badRequest().body("失敗");
	    }
	}

}
