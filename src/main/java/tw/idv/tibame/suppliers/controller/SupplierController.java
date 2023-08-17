package tw.idv.tibame.suppliers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.idv.tibame.core.LoginRequired;
import tw.idv.tibame.members.entity.Members;
import tw.idv.tibame.suppliers.entity.Suppliers;
import tw.idv.tibame.suppliers.service.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
	
	@Autowired
	SupplierService supplierService;
	
	@LoginRequired
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Members member,@RequestHeader("Authorization") String jwtToken) {

		String token = "";
		try {
			token = supplierService.logIn(member);
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
	
	@PostMapping("/supplierCenter")
	public ResponseEntity<String> showAside (@RequestHeader("Authorization") String jwtToken){

		return supplierService.showAsideInfo(jwtToken);
	}
	
	@PostMapping("/wakeup")
	public String wakeUp() {
		return "wakeUp";
	}
	
}
