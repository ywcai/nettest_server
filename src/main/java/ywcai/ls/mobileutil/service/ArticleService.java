package ywcai.ls.mobileutil.service;

 
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ywcai.ls.mobileutil.entity.ArticleIndex;
import ywcai.ls.mobileutil.repository.ArticleRepository;

@Service
@Qualifier(value="ArticleService")
public class ArticleService {
	@Autowired
	ArticleRepository articleRepository;
	
	public List<ArticleIndex> getOlderArticle(int type,int size,long oldId)
	{
		 List<ArticleIndex> articles=
				 articleRepository.findTop20ByArticleTypeAndArticleIdLessThanOrderByArticleIdDesc(type,oldId);
		return articles;
	}
	public List<ArticleIndex> getNewArticle(int type,int size,long newId)
	{
		//第一次请求则无论如何都是拉去最新的数据
		if(newId==-1)
		{
			 List<ArticleIndex> articles=
					 articleRepository.findTop20ByArticleTypeOrderByArticleIdDesc(type);
			return articles;
		}
		//如果不是第一次请求，则拉取和上一次数据最新的20条新数据
		 List<ArticleIndex> articles=
				 articleRepository.findTop20ByArticleTypeAndArticleIdGreaterThanOrderByArticleIdAsc(type, newId);
		 if(articles!=null)
		 {
			 Collections.reverse(articles);
		 }
		return articles;
	}
	public List<ArticleIndex> getNearArticle(int type, int size, long centerId) {
		  ArticleIndex nowArticle=
				 articleRepository.findOne(centerId);
		 List<ArticleIndex> newArticles=
				 articleRepository.findTop2ByArticleTypeAndArticleIdGreaterThanOrderByArticleIdAsc(type, centerId);
		 List<ArticleIndex> oldArticles=
				 articleRepository.findTop2ByArticleTypeAndArticleIdLessThanOrderByArticleIdDesc(type, centerId);
		 List<ArticleIndex> articles=new ArrayList<ArticleIndex>();
		 articles.add(nowArticle);
		 if(newArticles!=null)
		 {
			 Collections.reverse(newArticles);
			 articles.addAll(newArticles);
		 }
		 if(oldArticles!=null)
		 {

			 articles.addAll(oldArticles);
		 }
		return articles;
	}
}
