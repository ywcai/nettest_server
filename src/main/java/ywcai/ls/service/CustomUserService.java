package ywcai.ls.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import ywcai.ls.repository.UserRepository;
@Service
@Qualifier(value="customUserService")
public class CustomUserService implements UserDetailsService {

	@Autowired
	UserRepository UserRepository;
	@Override
	public UserDetails loadUserByUsername(String username) {
		// TODO Auto-generated method stub
		UserDetails userDetails=null;
		try {
			 userDetails=UserRepository.findByUsername(username).get(0);
		} catch (Exception e) {
			// TODO: handle exception
    		return null;
		}
		return userDetails;
	}

}
