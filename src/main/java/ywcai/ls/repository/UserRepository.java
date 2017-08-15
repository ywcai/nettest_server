package ywcai.ls.repository;


import java.util.List;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ywcai.ls.entity.User;
@Repository
@Table(name="user")
@Qualifier("userRepository")
public interface UserRepository extends JpaRepository<User, Long > {
	List<User> findByUsername(String username);
}
