package ywcai.ls.repository;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ywcai.ls.entity.Roles;
@Repository
@Table(name="roles")
@Qualifier("rolesRepository")
public interface RolesRepository extends JpaRepository<Roles, Long > {
	
}
