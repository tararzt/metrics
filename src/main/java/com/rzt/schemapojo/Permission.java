package com.rzt.schemapojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "permission" )
public class Permission {

	Integer id;
	String name;
	String description;

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
	@Column( name = "name" )
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
	 * @return the description
	 */
	@Column( name = "description" )
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription( String description )
	{
		this.description = description;
	}

}
