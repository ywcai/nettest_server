package ywcai.ls.mobileutil;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

//@SpringBootApplication
//public class MainApplication   {
//	public static void main(String[] args) {
//		SpringApplication.run(MainApplication.class, args);
//	}
//}	





@SpringBootApplication
public class MainApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		
		
		// TODO Auto-generated method stub
		return builder.sources(MainApplication.class);
		
	}

}


