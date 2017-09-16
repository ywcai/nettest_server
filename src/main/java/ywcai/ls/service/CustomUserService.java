package ywcai.ls.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import ywcai.ls.entity.User;
import ywcai.ls.repository.UserRepository;
@Service
@Qualifier(value="customUserService")
public class CustomUserService implements UserDetailsService {

	@Autowired
	UserRepository UserRepository;
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username){
		// TODO Auto-generated method stub
		User userDetails=null;
		try {
			 userDetails=UserRepository.findTop1ByUsername(username);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		if(userDetails==null)
		{
			//如果没有用户，则内置一个错误的账号信息
			userDetails=new User();
			userDetails.setUsername("ywcai");
			userDetails.setPassword("ywcai");
		}
		return userDetails;
	}
}
