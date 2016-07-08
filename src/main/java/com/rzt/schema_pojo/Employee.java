package com.rzt.schema_pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "employee" )
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	Integer id;
	String name;

	/**
	 * @return the id
	 */
	@Id
	@Column( name = "id", unique = true, nullable = false )
	@GeneratedValue( strategy = GenerationType.AUTO )
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

}
