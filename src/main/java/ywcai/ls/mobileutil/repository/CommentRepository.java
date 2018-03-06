package ywcai.ls.mobileutil.repository;

import java.util.List;

import javax.persistence.Table;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; 
import ywcai.ls.mobileutil.entity.UserComment;

@Repository
@Table(name="comments")
@Qualifier("commentRepository")
public interface CommentRepository extends JpaRepository<UserComment, Long > {

	
 
	List<UserComment> findTop20ByArticleIdOrderByIdDesc(long articleId);

	List<UserComment> findTop20ByArticleIdAndIdGreaterThanOrderByIdAsc(long articleId, long startId);

	List<UserComment> findTop20ByArticleIdAndIdLessThanOrderByIdDesc(long articleId, long endId);

}
