package ywcai.ls.repository;


import java.util.List;

import javax.persistence.Table;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ywcai.ls.entity.Orders;
@Repository
@Table(name="order")
@Qualifier("orderRepository")
public interface OrderRepository extends JpaRepository<Orders, Long > {
	int deleteByUid(int Uid);
	@Query(value="select orderstatus , count(1) as counts from Orders group by orderstatus ")
	List<Object> findGroupByOrderStatus();
	@Query(value="select productid , count(1) as counts from Orders group by productid ")
	List<Object> findGroupByProductid();
	@Query(value="SELECT left(timestamp,8),count(1) as counts FROM orders where left(timestamp,8) <=  date_format(NOW(),'Ymd')  group by left(timestamp,8)",nativeQuery=true)
	List<Object> findAllCountByDay();
	@Query(value="SELECT left(timestamp,8),count(1) as counts FROM orders where orderstatus= 0 and left(timestamp,8) <=  date_format(NOW(),'Ymd')  group by left(timestamp,8)" , nativeQuery=true)
	List<Object> findStatus0CountByDay( );
	@Query(value="SELECT left(timestamp,8),count(1) as counts FROM orders where orderstatus= 1 and left(timestamp,8) <=  date_format(NOW(),'Ymd')  group by left(timestamp,8)",nativeQuery=true)
	List<Object> findStatus1CountByDay( );
	@Query(value="SELECT left(timestamp,8),count(1) as counts FROM orders where orderstatus= 2 and left(timestamp,8) <=  date_format(NOW(),'Ymd')  group by left(timestamp,8)",nativeQuery=true)
	List<Object> findStatus2CountByDay( );
	@Query(value="SELECT left(timestamp,8),count(1) as counts FROM orders where orderstatus= 3 and left(timestamp,8) <=  date_format(NOW(),'Ymd')  group by left(timestamp,8)",nativeQuery=true)
	List<Object> findStatus3CountByDay( );
	@Query(value="SELECT left(timestamp,8),count(1) as counts FROM orders where orderstatus= 4 and left(timestamp,8) <=  date_format(NOW(),'Ymd')  group by left(timestamp,8)",nativeQuery=true)
	List<Object> findStatus4CountByDay( );
	@Query(value="SELECT left(timestamp,8),count(1) as counts FROM orders where orderstatus= 5 and left(timestamp,8) <=  date_format(NOW(),'Ymd')  group by left(timestamp,8)",nativeQuery=true)
	List<Object> findStatus5CountByDay();
	
}
