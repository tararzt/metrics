package com.rzt.schemapojo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "project" )
public class Project {

	Integer id;
	String name;
	String client;
	Date startDate;
	Date endDate;
	Float totalBilling;
	Date createdDate;
	Boolean isActive;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "id", unique = true, nullable = false )
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

	@Column( name = "client" )
	public String getClient()
	{
		return client;
	}

	public void setClient( String client )
	{
		this.client = client;
	}

	@Column( name = "start_date" )
	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate( Date startDate )
	{
		this.startDate = startDate;
	}

	@Column( name = "end_date" )
	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate( Date endDate )
	{
		this.endDate = endDate;
	}

	@Column( name = "total_billing" )
	public Float getTotalBilling()
	{
		return totalBilling;
	}

	public void setTotalBilling( Float billing )
	{
		this.totalBilling = billing;
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

	@Column( name = "is_active" )
	public Boolean getIsActive()
	{
		return isActive;
	}

	public void setIsActive( Boolean active )
	{
		isActive = active;
	}
}
