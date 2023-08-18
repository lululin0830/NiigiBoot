package tw.idv.tibame.core.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import tw.idv.tibame.core.util.JwtUtil;

@Aspect
@Component
public class Authorization {
	
	
	@Around("@annotation(tw.idv.tibame.core.LoginRequired)")
	public Object isLoggedIn(ProceedingJoinPoint joinPoint) {
		
		
		Object[] args = joinPoint.getArgs();
        String jwtToken = null;

        for (Object arg : args) {
            if (arg instanceof String && ((String) arg).startsWith("Bearer ")) {
                jwtToken = (String) arg;
                break;
            }
        }
        
        if (jwtToken == null || !JwtUtil.validateJwtToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: 使用者尚未登入");
        }

		try {
			
			 return joinPoint.proceed();
			 
		} catch (Throwable e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(String.format("error:%s , errMsg: %s", "some_error_code","系統繁忙中...請稍後再試"));
		}
	}
		
		
		
}
