package ywcai.ls.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled=true) 
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter{

	@Autowired
	UserDetailsService customUserService;

	
    @Autowired 
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {  
        auth.userDetailsService(customUserService);  
        auth.authenticationProvider(authenticationProvider());
    }
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http
		.csrf().disable();
		http
		.authorizeRequests()
		.anyRequest().authenticated()
		.and()
		.formLogin()//这个方法则会取消框架默认的弹出密码登录框。
		.loginPage("/login")
		.defaultSuccessUrl("/index",true)
		.permitAll()
		.and()
		.logout()
		.invalidateHttpSession(true)
		.permitAll();
	}


	@Bean  
	public PasswordEncoder passwordEncoder() {  
		return new BCryptPasswordEncoder();  
	}  


	@Bean  
	public DaoAuthenticationProvider authenticationProvider() {  
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();  
		authenticationProvider.setUserDetailsService(customUserService); 
		authenticationProvider.setPasswordEncoder(passwordEncoder());  
		return authenticationProvider;  
	}  
	
}
