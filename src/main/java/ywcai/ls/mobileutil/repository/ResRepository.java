package ywcai.ls.mobileutil.repository;


import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ywcai.ls.mobileutil.entity.NumberRes;
@Repository
@Table(name="numberres")
@Qualifier("resRepository")
public interface ResRepository extends JpaRepository<NumberRes, Long > {

	NumberRes findTop1ByIsPrettyTrueAndIsValidFalseOrderByIdAsc();

	NumberRes findTop1ByIsPrettyFalseAndIsValidFalseOrderByIdAsc();
}
