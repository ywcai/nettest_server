package ywcai.ls.mobileutil.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="numberres")
public class NumberRes {
	
	//号码列表
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	public Long id;
	
	//对应用户实际ID
	@Column(name="usergid")
	public long usergid;

	//是否是特号
	@Column(name="ispretty")
	public boolean isPretty=false;
	
	//是否有效
	@Column(name="isvalid")
	public boolean isValid=false;
	
}