package ywcai.ls.mobileutil.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ywcai.ls.mobileutil.entity.ArticleIndex;
import ywcai.ls.mobileutil.entity.MyUser;
import ywcai.ls.mobileutil.entity.UserComment;
 
import ywcai.ls.mobileutil.repository.ArticleRepository;
import ywcai.ls.mobileutil.repository.CommentRepository;
import ywcai.ls.mobileutil.repository.UserRepository;
import ywcai.ls.mobileutil.tools.MD5;


@Service
@Qualifier(value="CommentService")
public class CommentService {
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ArticleRepository articleRepository;

	public Boolean checkSign(UserComment userComment) {
		// TODO Auto-generated method stub
		MyUser myUser=userRepository.findTop1ByUserid(userComment.getUserId());
		if(myUser==null)
		{
			return false;
		}
		String openId=myUser.getOpenid();
//		String accessToken="JIMI";//通过userId在内存库中获取，
		String sign = MD5.md5(userComment.getArticleId() 
				+userComment.getUserId()
				+userComment.getCreateTime()
				+userComment.getDetail()
				+openId
//				+accessToken
				+"YWCAI");
		return sign.equals(userComment.getSign());
	}
	public boolean checkRule(UserComment userComment) {
		// TODO Auto-generated method stub
		if(userComment.getDetail()==null)
		{
			return false;
		}
		if(userComment.getDetail().length()<5||userComment.getDetail().length()>300)
		{
			return false;
		}
		return true;
	}
	
	//过滤敏感字符
	public String filterIllegalWork(String details) {
		// TODO Auto-generated method stub
		return details;
	}
	
	//转换非法标签，主要包括sql特殊字、html标签
	public String filterIllegalTag(String details) {
		// TODO Auto-generated method stub
		return details;
	}
 
	
	public int addComment(UserComment userComment) throws Exception
	{
		UserComment newComment=commentRepository.saveAndFlush(userComment);
		if(newComment==null)
		{
			return 0;
		}
		ArticleIndex article=articleRepository.findOne(newComment.getArticleId());
		if(article==null)
		{
			return 0;
		}
		article.comment++;
		return article.comment;
	}



	//置顶的N条
	public List<UserComment> getGreatComment()
	{
		return null;
	}

	public List<UserComment> getNewComment(long articleId, long startId)
	{
		List<UserComment> temp ;
		if(startId==-1)
		{
			temp=commentRepository.findTop20ByArticleIdOrderByIdDesc(articleId);
		}
		else
		{
			temp=commentRepository.findTop20ByArticleIdAndIdGreaterThanOrderByIdAsc(articleId,startId);
			if(temp!=null)
			{
				Collections.reverse(temp);
			}
		}
		if(temp==null)
		{
			temp=new ArrayList<UserComment>();
		}
		return temp;
	}
	public List<UserComment> getOldComment(long articleId, long endId)
	{
		
		List<UserComment> temp ;
		temp=commentRepository.findTop20ByArticleIdAndIdLessThanOrderByIdDesc(articleId,endId);
		if(temp==null)
		{
			temp=new ArrayList<UserComment>();
		}
		return temp;
	}





}
