package tw.idv.tibame.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.idv.tibame.users.bean.req.UpdateReq;
import tw.idv.tibame.users.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UpdateController {
	@Autowired
	UserService service;

	@PostMapping("/update")
	public ResponseEntity<String> updateUser(@RequestBody UpdateReq updateReq){
		
		boolean updated = service.updateUser(updateReq.getUserId(), updateReq.getPassword(), updateReq.getFinancialAuthority(), updateReq.getCustomerServiceAuthority(), updateReq.getMarketingAuthority(), updateReq.getHrAuthority());
		
				if (updated) {
					return ResponseEntity.ok("成功");
				}else {
					return ResponseEntity.badRequest().body("失敗1");
				}
	}
}

