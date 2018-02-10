package ywcai.ls.mobileutil.repository;


import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ywcai.ls.mobileutil.entity.MyUser;
@Repository
@Table(name="myuser")
@Qualifier("userRepository")
public interface UserRepository extends JpaRepository<MyUser, Long > {
	MyUser findTop1ByOpenidAndChannelid(String openid,int channelid);
	MyUser findTopByOrderByUseridDesc();
	MyUser findTop1ByUserid(long userid);
}
