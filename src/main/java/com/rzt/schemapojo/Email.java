package com.rzt.schemapojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "email" )
public class Email {

	private static final long serialVersionUID = 2677857315058655655L;

	private String id;
	String toEmail;
	String fromEmail;
	String ccEmail;
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

	@Column( name = "cc_email" )
	public String getCcEmail()
	{
		return ccEmail;
	}

	public void setCcEmail( String ccEmail )
	{
		this.ccEmail = ccEmail;
	}

	@Column( name = "to_email" )
	public String getToEmail()
	{
		return toEmail;
	}

	public void setToEmail( String toEmail )
	{
		this.toEmail = toEmail;
	}

	@Column( name = "from_email" )
	public String getFromEmail()
	{
		return fromEmail;
	}

	public void setFromEmail( String fromEmail )
	{
		this.fromEmail = fromEmail;
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
