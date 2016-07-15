package com.rzt.schemapojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "email_template" )
public class EmailTemplate {

	private static final long serialVersionUID = -8609202304448767251L;

	private String id;
	String displayName;
	String internalName;
	String subject;
	String body;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "id" )
	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	@Column( name = "display_name" )
	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName( String displayName )
	{
		this.displayName = displayName;
	}

	@Column( name = "internal_name" )
	public String getInternalName()
	{
		return internalName;
	}

	public void setInternalName( String internalName )
	{
		this.internalName = internalName;
	}

	@Column( name = "subject" )
	public String getSubject()
	{
		return subject;
	}

	public void setSubject( String subject )
	{
		this.subject = subject;
	}

	@Column( name = "body" )
	public String getBody()
	{
		return body;
	}

	public void setBody( String body )
	{
		this.body = body;
	}

}
