package com.helloworld.template.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="T_USER")
@DynamicInsert(true) //只插入我们输入的字段。
@DynamicUpdate(true) //只更新我们修改过的字段，这两个注解在一定程度上可以增加与数据库操作相关的速度，可以节省SQL语句的执行时间，提高程序的运行效率。
public class User implements Serializable {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="USERNAME", length=50, nullable=false, unique=true)
	private String userName;
	
	@Column(name="PASSWORD", length=50, nullable=false)
	private String password;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	

}
