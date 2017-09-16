package ywcai.ls.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ywcai.ls.entity.Orders;
import ywcai.ls.repository.OrderRepository;
import ywcai.ls.service.inf.OrderProcessInf;


@Service
@Qualifier(value="orderProcess")
public class OrderProcess implements OrderProcessInf {

	
	@Autowired
	private OrderRepository orderRepository;

	@Override
	public Page<Orders> getAllOrder(Pageable pageable, Sort sort) {
		// TODO Auto-generated method stub
		
		PageRequest pageRequest;
	    pageRequest=new PageRequest(pageable.getPageNumber()-1, pageable.getPageSize(),sort);
		Page<Orders> page=orderRepository.findAll(pageRequest);
		return page;
	}

	@Override
	public String updateOrderInfo(Orders orders) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public String delOrder(int[] uids) {
		// TODO Auto-generated method stub
		int deleteNum=0;
		for (int uid : uids) {
			try {
				deleteNum+=orderRepository.deleteByUid(uid);
			} catch (Exception e) {
				System.out.println(e.toString());
				return "FAIL:成功删除数据"+deleteNum+"条!";
			}
		}
		return "SUCCESS";
	}
	
}
