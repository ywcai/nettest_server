package ywcai.ls.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ywcai.ls.entity.Roles;
import ywcai.ls.entity.User;
import ywcai.ls.repository.RolesRepository;
import ywcai.ls.repository.UserRepository;
import ywcai.ls.service.inf.AccountProcessInf;


@Service
@Qualifier(value="accountProcess")
public class AccountProcess implements AccountProcessInf {

	@Autowired
	UserRepository userRepository;
	@Autowired
	RolesRepository rolesRepository;
	
	@Transactional
	@Override
	public String addUser(String username, String password, String role) {
		// TODO Auto-generated method stub
		User user=new User();
		user.setUsername(username);
		user.setPassword(password);
		System.out.println(user.getUsername()+"|"+user.getPassword());
		if(userRepository.findByUsername(username) != null)
		{
			return "FAIL:账号已存在";	
		}
		
		Roles roles=new Roles();
		roles.setUser(user);
		roles.setRolename(role);
		Roles result=rolesRepository.saveAndFlush(roles);
		if(result==null)
		{
			return "FAIL:数据保存出错";	
		}
		
		return "SUCCESS";
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delUser(int[] userids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addRole(User user, String rolename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeRole(User user, String rolename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateRole(User user, String rolename) {
		// TODO Auto-generated method stub
		return null;
	}

}
