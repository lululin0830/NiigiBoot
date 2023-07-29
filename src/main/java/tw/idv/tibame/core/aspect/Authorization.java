package tw.idv.tibame.core.aspect;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Aspect
@Component
public class Authorization {
	
	@Autowired
	private Gson gson;
	
//	@Around("execution(* tw.idv.tibame.orders.dao.impl.SubOrderDAOImpl.*(..))")
//	public String beforeAdvice() {
//		System.out.println("before");
//		return gson.toJson("{\"error\": \"尚未登入\"}");
//		}
}
