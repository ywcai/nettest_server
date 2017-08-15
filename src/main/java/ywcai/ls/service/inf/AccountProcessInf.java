package ywcai.ls.service.inf;


import java.util.List;
import ywcai.ls.entity.User;

public interface AccountProcessInf {

	String addUser(String username,String password,String role);
	List<User> getAllUser();
	String delUser(User user);
	String delUser(int[] userids);
	String updateUser(User user);
	String addRole(User user,String rolename);
	String removeRole(User user,String rolename);
	String updateRole(User user,String rolename);
}
