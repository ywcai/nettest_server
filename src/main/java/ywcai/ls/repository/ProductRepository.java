package ywcai.ls.repository;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ywcai.ls.entity.Product;

@Repository
@Table(name="product")
@Qualifier("productRepository")
public interface ProductRepository  extends JpaRepository<Product, Long > {
	int deleteByUid(int Uid);
}
