package ywcai.ls.service.inf;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ywcai.ls.bean.RoleBody;
import ywcai.ls.entity.User;

public interface AccountProcessInf {

	String addUser(User user,String role);
	Page<User> getAllUser(Pageable pageable);
	String delUser(User user);
	String updateUser(User user);
	String addRole(String username,String[] rolename)  ;
	String editRole(RoleBody roleBody);
	String removeRole(String username, String rolename);
}
