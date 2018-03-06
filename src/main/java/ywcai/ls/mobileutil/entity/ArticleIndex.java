package ywcai.ls.mobileutil.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="article")
public class ArticleIndex {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	public long articleId;
	
	@Column(name="articletype")
	public int articleType=1;//1,2,3,4,5
	
	@Column(name="pv")
	public int pv=2000;
	
	@Column(name="top")
	public int  top=100;
	
	@Column(name="comment")
	public int comment=50;
	
	@Column(name="title")
	public String title="文章标题";
	
	@Column(name="remarks")
	public String remarks="文章摘要";
	
	@Column(name="thumburl")
	public String thumbUrl="";
	
	@Column(name="authimg")
	public String authImg="";
	
	@Column(name="authnickname")
	public String authNickname="作者昵称";
	
	@Column(name="createtime")
	public String createTime="文章发表时间";
	
	
	@Column(name="articlecontent")
	public String articleContent="正文";
	
	@Column(name="quoteurl")
	public String quoteUrl="引用地址";
	
}
