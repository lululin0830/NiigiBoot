package tw.idv.tibame.core.aspect;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Aspect
@Component
public class Authorization {
	
	@Autowired
	private Gson gson;
	
	@Around("@annotation(tw.idv.tibame.core.LoginRequired)")
	public ResponseEntity<String> beforeAdvice() {
		System.out.println("before");
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(String.format("error:%s , errMsg: %s", "some_error_code","使用者尚未登入"));
	}
		
		
		
}
