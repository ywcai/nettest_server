package ywcai.ls.repository;


import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ywcai.ls.entity.User;
@Repository
@Table(name="user")
@Qualifier("userRepository")
public interface UserRepository extends JpaRepository<User, Long > {
	User findTop1ByUsername(String username);
	int deleteByUsername(String username);
	void deleteByUsernameIsNull();
}
