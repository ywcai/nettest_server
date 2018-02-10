package ywcai.ls.mobileutil.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import ywcai.ls.mobileutil.bean.LogEntity;


@Entity
@Table(name="record")
public class MyRecord {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="userid")
	private long userid;

	@Column(name="recordtype")
	private int recordtype ; 

	@Column(name="filename")
	private String filename ;

	@Column(name="aliasname")
	private String aliasname ;

	@Column(name="remarks")
	private String remarks   ;

	@Column(name="createtime")
	private String createtime  ;

	@Column(name="addr")
	private String addr;

	@Column(name="content")
	private String content;

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

	public int getRecordtype() {
		return recordtype;
	}

	public void setRecordtype(int recordtype) {
		this.recordtype = recordtype;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getAliasname() {
		return aliasname;
	}

	public void setAliasname(String aliasname) {
		this.aliasname = aliasname;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void saveForEntity(LogEntity logEntity) {
		// TODO Auto-generated method stub
		setRecordtype(logEntity.logIndex.cacheTypeIndex);
		setFilename(logEntity.logIndex.cacheFileName);
		setAliasname(logEntity.logIndex.aliasFileName);
		setRemarks(logEntity.logIndex.remarks);
		setCreatetime(logEntity.logIndex.logTime);
		setAddr(logEntity.logIndex.addr);
		setContent(logEntity.data);		
	}

}