package ywcai.ls.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ywcai.ls.entity.Product;
import ywcai.ls.repository.ProductRepository;
import ywcai.ls.service.inf.ProductProcessInf;


@Service
@Qualifier(value="productProcess")
public class ProductProcess implements ProductProcessInf {
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Page<Product> getAllProduct(
			Pageable pageable,Sort sort) {
		// TODO Auto-generated method stub
		PageRequest pageRequest;
	    pageRequest=new PageRequest(pageable.getPageNumber()-1, pageable.getPageSize(),sort);
		Page<Product> page=productRepository.findAll(pageRequest);
		return page;
	}

	@Override
	public String updateProductInfo(Product product) {
		// TODO Auto-generated method stub
		Product newProduct=null;
		try {
			newProduct=productRepository.saveAndFlush(product);
		} catch (Exception e) {
			return "FAIL:"+e.toString();
		}
		if(!product.toString().equals(newProduct.toString()))
		{
			System.out.println(product.toString()+"\n\n"+newProduct.toString());
			return "FAIL:数据更新出现未知错误!";
		}
		return "SUCCESS";
	}

	@Transactional
	@Override
	public String delProducts(int[] uids) {
		// TODO Auto-generated method stub
		int deleteNum=0;
		for (int uid : uids) {
			try {
				deleteNum+=productRepository.deleteByUid(uid);
			} catch (Exception e) {
				System.out.println(e.toString());
				return "FAIL:成功删除数据"+deleteNum+"条!";
			}
		}
		return "SUCCESS";
	}

	@Override
	public String createProduct(Product product) {
		Sort sort=new Sort(Direction.DESC,"productid");
		PageRequest pageRequest=new PageRequest(0,1,sort);
		List<Product> temp=null;
		try {
		    temp=productRepository.findAll(pageRequest).getContent();
		} catch (Exception e) {
			return "FAIL:"+e.toString();
		}
		int nowProductid=(temp.size()==0)?10000:(temp.get(0).getProductid()+1);
		product.setProductid(nowProductid);
		String tempProvince=product.getProvince()+product.getOperator();
		product.setProvince(tempProvince);
		product.setDelflag(0);
		try {
			productRepository.save(product);
		} catch (Exception e) {
			return "FAIL:"+e.toString();
		}
		return "SUCCESS";
	}

}
