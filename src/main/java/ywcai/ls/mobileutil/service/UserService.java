package ywcai.ls.mobileutil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ywcai.ls.mobileutil.entity.MyUser;
import ywcai.ls.mobileutil.entity.NumberRes;

import ywcai.ls.mobileutil.repository.UserRepository;
import ywcai.ls.mobileutil.tools.LsTime;


@Service
@Qualifier(value="UserService")
public class UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ResService resService;

	@Transactional(rollbackFor=Exception.class)
	public MyUser createNewUser(String openID, int channelID)  throws Exception {
		// TODO Auto-generated method stub
		MyUser myUser =new MyUser();
		myUser.setOpenid(openID);
		myUser.setChannelid(channelID);
		myUser.setActive(true);
		myUser.setCreatedate(LsTime.getDetailTime());
		MyUser temp=userRepository.saveAndFlush(myUser);
		NumberRes bumberRes=resService.getNormalNumber(temp.getId());
		myUser.setUserid(bumberRes.id);
		myUser.setNickname("用户"+bumberRes.id);
		MyUser  temp2=userRepository.saveAndFlush(myUser);
		//如果要在该方法内处理异常，则必须增加下面方法手动回滚数据
		//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return temp2;
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
