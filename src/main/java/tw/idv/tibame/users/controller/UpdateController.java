package tw.idv.tibame.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.idv.tibame.users.service.UserService;

@RestController
@RequestMapping("/User")
@CrossOrigin(origins = "*")
public class UpdateController {
	@Autowired
	UserService service;
	
	@PostMapping("/update")
	public ResponseEntity<String> updateUser(
			@RequestParam String userId,
			@RequestParam(required = false) String changePassword,
			@RequestParam(required = false) String financialAuthority,
			@RequestParam(required = false) String customerServiceAuthorit,
			@RequestParam(required = false) String marketingAuthority,
			@RequestParam(required = false) String hrAuthority
			){
		
		boolean updated = service.updateUser(userId, changePassword, financialAuthority, customerServiceAuthorit, marketingAuthority, hrAuthority);
		
				if (updated) {
					return ResponseEntity.ok("成功");
				}else {
					return ResponseEntity.badRequest().body("失敗");
				}
		
	}
}
