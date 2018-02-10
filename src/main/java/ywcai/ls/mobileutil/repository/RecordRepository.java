package ywcai.ls.mobileutil.repository;


import java.util.Collection;
import java.util.List;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ywcai.ls.mobileutil.bean.LogEntity;
import ywcai.ls.mobileutil.entity.MyRecord;
@Repository
@Table(name="record")
@Qualifier("recordRepository")
public interface RecordRepository extends JpaRepository<MyRecord, Long > {

	List<MyRecord> findByUseridOrderByCreatetimeDesc(long userid);
	LogEntity findByUseridAndId(long userid, long id);
	Page<MyRecord> findByUserid(long userid, Pageable pageable);
	List<MyRecord> findByUseridAndRecordtypeIn(long userid, Collection<Integer> collection);
	MyRecord findByIdAndUserid(long recordid, long userid);

}
