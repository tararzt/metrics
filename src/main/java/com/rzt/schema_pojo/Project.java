package com.rzt.schema_pojo;

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

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "id", unique = true, nullable = false )
	public Integer getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId( Integer id )
	{
		this.id = id;
	}

	/**
	 * @return the name
	 */
	@Column( name = "name", nullable = false )
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName( String name )
	{
		this.name = name;
	}

	/**
	 * @return the client
	 */
	@Column( name = "client" )
	public String getClient()
	{
		return client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient( String client )
	{
		this.client = client;
	}

	/**
	 * @return the startDate
	 */
	@Column( name = "start_date" )
	public Date getStartDate()
	{
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate( Date startDate )
	{
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	@Column( name = "end_date" )
	public Date getEndDate()
	{
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate( Date endDate )
	{
		this.endDate = endDate;
	}

}
