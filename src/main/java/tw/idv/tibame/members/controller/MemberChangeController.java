package tw.idv.tibame.members.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	//更改密碼
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
	//顯示個人資訊頁
	@GetMapping("/selectId")
	public ResponseEntity<Members> getMember(@RequestParam String memberId){
		Members members = dao.selectOneByMemberId(memberId);
		if (members != null) {
			return ResponseEntity.ok(members);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
