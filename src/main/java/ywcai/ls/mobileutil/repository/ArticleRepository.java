package ywcai.ls.mobileutil.repository;

import java.util.List;
import javax.persistence.Table;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ywcai.ls.mobileutil.entity.ArticleIndex;

@Repository
@Table(name="article")
@Qualifier("articleRepository")
public interface ArticleRepository extends JpaRepository<ArticleIndex, Long > {
	
	//第一次更新请求数据时则是查询最新的20条数据，倒序
	List<ArticleIndex> findTop20ByArticleTypeOrderByArticleIdDesc( int type);
	

	//查询更多旧数据
	List<ArticleIndex> findTop20ByArticleTypeAndArticleIdLessThanOrderByArticleIdDesc( int type,long oldId);
	
	//查询更多新数据
	List<ArticleIndex> findTop20ByArticleTypeAndArticleIdGreaterThanOrderByArticleIdAsc(int type,long newId);
	
	
	List<ArticleIndex> findTop2ByArticleTypeAndArticleIdGreaterThanOrderByArticleIdAsc(int type, long centerId);
	
	
	List<ArticleIndex> findTop2ByArticleTypeAndArticleIdLessThanOrderByArticleIdDesc(int type, long centerId);
}
