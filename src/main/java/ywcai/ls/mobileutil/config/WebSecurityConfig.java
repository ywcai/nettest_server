package ywcai.ls.mobileutil.config;

import org.springframework.context.annotation.Configuration; 
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
 

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled=true) 
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter{

	 
	
//    @Autowired 
//	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {  
////        auth.userDetailsService(customUserService);  
////        auth.authenticationProvider(authenticationProvider());
//    }
//	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.headers().frameOptions().disable();
		 http
		 .csrf().disable();
		 http
		.authorizeRequests()
		.antMatchers( 
				"/login",
				"/user/**",
				"/record/**",
				"/open/**").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin()//这个方法则会取消框架默认的弹出密码登录框。
		.loginPage("/login")
		.and()
		.logout()
		.invalidateHttpSession(true)
		;
	}

//
//	@Bean  
//	public PasswordEncoder passwordEncoder() {  
//		return new BCryptPasswordEncoder();
//	}  
//
//
//	@Bean  
//	public DaoAuthenticationProvider authenticationProvider() {  
//		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();  
////		authenticationProvider.setUserDetailsService(customUserService); 
////		authenticationProvider.setPasswordEncoder(passwordEncoder());  
//		return authenticationProvider;
//	}  
//	
	
	
}
