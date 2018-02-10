package ywcai.ls.mobileutil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ywcai.ls.mobileutil.entity.MyUser;
import ywcai.ls.mobileutil.repository.UserRepository;
import ywcai.ls.mobileutil.tools.LsTime;

@Service
@Qualifier(value="UserService")
public class UserService {
	@Autowired
	UserRepository userRepository;
	public MyUser createNewUser(String openID, int channelID) {
		// TODO Auto-generated method stub
		MyUser myUser =new MyUser();
		long userID=getUserId();
		myUser.setOpenid(openID);
		myUser.setChannelid(channelID);
		myUser.setUserid(userID);
		myUser.setActive(true);
		myUser.setNickname("yk"+userID);
		myUser.setCreatedate(LsTime.getDetailTime());
		MyUser temp=userRepository.saveAndFlush(myUser);
		return temp;
	}
	private long getUserId()
	{
	 
		return 2000;
	}

	public String createSign() {
		// TODO Auto-generated method stub
		//现在状态表中存储状态数据
		return "MD5";
	}

	public MyUser getUser(String openID, int channelID) {
		// TODO Auto-generated method stub
		MyUser myUser=userRepository.findTop1ByOpenidAndChannelid(openID, channelID);
		return myUser;
	}
	
	
	public MyUser getUser(long userid) {
		MyUser myUser=userRepository.findTop1ByUserid(userid);
		return myUser;
	}
	
	public MyUser saveUser(MyUser myUser) {
		// TODO Auto-generated method stub
		MyUser temp=userRepository.saveAndFlush(myUser);
		return temp;
	}
}
