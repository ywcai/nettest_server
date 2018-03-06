package ywcai.ls.mobileutil.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="comments")
public class UserComment {

	public  int praise = 0;
	public  String userImg = "";
	public String nickName = "";
	private String sign="";

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="articleid")
	private long articleId;

	@Column(name="userid")
	private long userId;

	@Column(name="createtime")
	private String createTime  ;

	@Column(name="createaddr")
	private String createAddr;

	@Column(name="detail")
	private String detail;

	//是文章的评论还是评论的评论，0代表文章的评论，1代表评论的评论
	@Column(name="commenttype")
	public long commentType;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateAddr() {
		return createAddr;
	}

	public void setCreateAddr(String createAddr) {
		this.createAddr = createAddr;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
 
	@Override
	public String toString() {
		return "UserComment [sign=" + sign + ", id=" + id + ", articleId=" + articleId + ", userId=" + userId
				+ ", createTime=" + createTime + ", createAddr=" + createAddr + ", detail=" + detail + ", cType="
				 + "]";
	}


}