package ywcai.ls.mobileutil.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="myuser")
public class MyUser {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="userid")
	private long userid;

	@Column(name="mobileid")
	private String mobileid;

	@Column(name="email")
	private String email;

	@Column(name="openid")
	private String openid;

	@Column(name="channelid")
	private int channelid;


	@Column(name="username")
	private String username;

	@Column(name="password")
	private String password;


	@Column(name="nickname")
	private String nickname;

	@Column(name="createdate")
	private String createdate;


	@Column(name="active")
	private boolean active;

	@Column(name="userimg")
	public String userImg;
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public long getUserid() {
		return userid;
	}


	public void setUserid(long userid) {
		this.userid = userid;
	}


	public String getMobileid() {
		return mobileid;
	}


	public void setMobileid(String mobileid) {
		this.mobileid = mobileid;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getOpenid() {
		return openid;
	}


	public void setOpenid(String openid) {
		this.openid = openid;
	}


	public int getChannelid() {
		return channelid;
	}


	public void setChannelid(int channelid) {
		this.channelid = channelid;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public String getCreatedate() {
		return createdate;
	}


	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


}