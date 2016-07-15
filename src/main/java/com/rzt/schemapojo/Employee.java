package com.rzt.schemapojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.rzt.google.GoogleUserInfo;

/**
 * @author tara Entity Employee which contains all employee Information
 */
@Entity
@Table( name = "employee" )
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	Integer id;
	String name;
	String email;
	Boolean isActive;
	Date lastLogin;
	Integer phone;
	Date createdDate;

	/**
	 * Constructor to Instantiate Employee Object out of GoogleUserInfo
	 * 
	 * @param userInfo
	 */
	public Employee( GoogleUserInfo userInfo )
	{
		super();

		this.name = userInfo.getGivenName() + " " + userInfo.getFamilyName();
		this.email = userInfo.getEmail();
		this.isActive = true;
		this.lastLogin = new Date();
	}

	@Id
	@Column( name = "id", unique = true, nullable = false )
	@GeneratedValue( strategy = GenerationType.AUTO )
	public Integer getId()
	{
		return id;
	}

	public void setId( Integer id )
	{
		this.id = id;
	}

	@Column( name = "name", nullable = false )
	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	@Column( name = "email_id" )
	public String getEmail()
	{
		return email;
	}

	public void setEmail( String emailId )
	{
		this.email = emailId;
	}

	@Column( name = "is_active" )
	public Boolean getIsActive()
	{
		return isActive;
	}

	public void setIsActive( Boolean active )
	{
		isActive = active;
	}

	@Column( name = "last_login" )
	public Date getLastLogin()
	{
		return lastLogin;
	}

	public void setLastLogin( Date lastLogin )
	{
		this.lastLogin = lastLogin;
	}

	@Column( name = "phone" )
	public Integer getPhone()
	{
		return phone;
	}

	public void setPhone( Integer phone )
	{
		this.phone = phone;
	}

	@Column( name = "created_date" )
	public Date getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate( Date createdDate )
	{
		this.createdDate = createdDate;
	}

}
