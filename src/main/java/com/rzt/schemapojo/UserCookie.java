package com.rzt.schemapojo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by tara on 7/12/16.
 */
@Entity
@Table( name = "user_cookie" )
public class UserCookie {

	Integer id;
	Integer employeeId;
	String cookie;
	String packet;
	Date createdDate;
	Date lastAccessed;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "id", nullable = false, unique = true )
	public Integer getId()
	{
		return id;
	}

	public void setId( Integer id )
	{
		this.id = id;
	}

	@Column( name = "employee_id" )
	public Integer getEmployeeId()
	{
		return employeeId;
	}

	public void setEmployeeId( Integer employeeId )
	{
		this.employeeId = employeeId;
	}

	@Column( name = "cookie" )
	public String getCookie()
	{
		return cookie;
	}

	public void setCookie( String cookie )
	{
		this.cookie = cookie;
	}

	@Column( name = "packet" )
	public String getPacket()
	{
		return packet;
	}

	public void setPacket( String packet )
	{
		this.packet = packet;
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

	@Column( name = "last_accessed" )
	public Date Accessed()
	{
		return lastAccessed;
	}

	public void setLastAccessed( Date lastUsed )
	{
		this.lastAccessed = lastUsed;
	}
}
