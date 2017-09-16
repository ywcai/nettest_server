package ywcai.ls.repository;


import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ywcai.ls.entity.Orders;
@Repository
@Table(name="order")
@Qualifier("orderRepository")
public interface OrderRepository extends JpaRepository<Orders, Long > {
	int deleteByUid(int Uid);
}
