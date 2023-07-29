package tw.idv.tibame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ServletComponentScan("tw.idv.tibame.*.controller")
@EnableAspectJAutoProxy
public class NiigiBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(NiigiBootApplication.class, args);
	}

}
