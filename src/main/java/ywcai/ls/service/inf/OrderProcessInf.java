package ywcai.ls.service.inf;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ywcai.ls.entity.Orders;


public interface OrderProcessInf {
	Page<Orders> getAllOrder(Pageable pageable,Sort sort);
	String updateOrderInfo(Orders orders);
	String delOrder(int[] uids);
}
