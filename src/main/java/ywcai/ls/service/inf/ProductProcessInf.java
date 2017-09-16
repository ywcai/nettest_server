package ywcai.ls.service.inf;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ywcai.ls.entity.Product;


public interface ProductProcessInf {
	Page<Product> getAllProduct(Pageable pageable,Sort sort);
	String updateProductInfo(Product product);
	String delProducts(int[] uids);
	String createProduct(Product product);
}
