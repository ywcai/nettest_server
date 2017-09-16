package ywcai.ls.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ywcai.ls.bean.RoleBody;
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
	@Autowired  
    private PasswordEncoder passwordEncoder;  
	
	@Transactional
	@Override
	public String addUser(User user, String role) {
		// TODO Auto-generated method stub
		String md5psw=passwordEncoder.encode(user.getPassword());
		User temp =null;
		try {
			temp=userRepository.findTop1ByUsername(user.getUsername());
		} catch (Exception e) {
			// TODO: handle exception
			return "FAIL:未知数据库操作错误"+e.toString();	
		}
		if(temp!=null)
		{
			return "FAIL:该用户名已存在";	
		}
		user.setPassword(md5psw);
		Roles roles=new Roles();
		roles.setUser(user);
		roles.setUsername(user.getUsername());
		//添加用户时默认添加为USER角色
		roles.setRolename("USER");
		try {
			rolesRepository.saveAndFlush(roles);
		} catch (Exception e) {
			// TODO: handle exception
			return "FAIL:保存角色数据失败"+e.toString();	
		}	
		return "SUCCESS";
	}


	@Override
	public Page<User> getAllUser(Pageable pageable) {
		PageRequest pageRequest=new PageRequest(pageable.getPageNumber()-1, pageable.getPageSize(),pageable.getSort());
		Page<User> page=userRepository.findAll(pageRequest);
		return page;
	}

	@Transactional
	@Override
	public String delUser(User user) {
		System.out.println("del:"+user.getUsername());
		// TODO Auto-generated method stub
		int row=0;
		try {
			userRepository.deleteByUsernameIsNull();
			row=userRepository.deleteByUsername(user.getUsername());
		} catch (Exception e) {
			return "FAIL:删除角色数据失败"+e.toString();	
		}
		if(row==0)
		{
			return "Fail:没有改账号";		
		}
		return "SUCCESS";
	}


	@Override
	public String updateUser(User user) {
		// TODO Auto-generated method stub
		String result="SUCCESS";
		try {
			User temp=userRepository.findTop1ByUsername(user.getUsername());
			if(temp==null)
			{
				result="FAIL:没有该账号或该账号已被删除!";	
			}
			String md5psw=passwordEncoder.encode(user.getPassword());
			temp.setPassword(md5psw);
			userRepository.saveAndFlush(temp);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			result="FAIL:数据操作失败!"+e;
		}
		return result;
	}

	@Override
	public String addRole(String username, String[] roleNames)   {
		String result="SUCCESS";
		User temp=null; 
		try {
			temp=userRepository.findTop1ByUsername(username);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			result="FAIL:读取数据库操作失败";
			return result;
		}
		if(temp==null)
		{
			result="FAIL:未找到用户名";
			return result;
		}
		for (String rolename : roleNames) {
			Roles roles=new Roles();
			roles.setUser(temp);
			roles.setUsername(temp.getUsername());
			roles.setRolename(rolename);
			temp.getRolelist().add(roles);
		}
		try {
			userRepository.saveAndFlush(temp);
		} catch (Exception e) {
			System.out.println(e);
			result="FAIL:数据库更新操作失败";	
		}
		return result;
	}


	

	@Override
	public String removeRole(String username, String rolename)  {
		String result="SUCCESS";
		if(username.equals(""))
		{
			result="FAIL:用户名不能为空";
			return result;
		}
		if(rolename.equals("USER"))
		{
			result="FAIL:USER权限不能被删除";
			return result;
		}
		int row=0;
		try {
		     row=rolesRepository.deleteByUsernameAndRolename(username, rolename);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			result="FAIL:数据操作失败"+e.toString();	
		}
		if(row==0)
		{
			result="FAIL:该账号没有 [" +rolename+"] 权限!";	
			return result;	
		}
		return result;
	}


	@Override
	public String editRole(RoleBody roleBody) {
		// TODO Auto-generated method stub
		String result="";
		if(roleBody.add.length!=0)
		{
			result=addRole(roleBody.account, roleBody.add);
		}
		if(roleBody.del.length!=0)
		{
			for (String rolename : roleBody.del) {
				result=removeRole(roleBody.account, rolename);				
			}
		}
		return result;
	}

}
