/**
 * 
 */
package com.rzt.google;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rzt.annotation.Authorized;

/**
 * @author Deepak
 *
 *
 *         private static final long serialVersionUID = 1L; public String sub; public String name;
 *         public String given_name; public String family_name; public String picture; public String
 *         email; public String email_verified; public String gender; public String locale;
 * 
 */

@JsonFilter( "authorized-filter" )
public class GoogleUserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String sub;
	public String name;
	@JsonProperty( "given_name" )
	public String givenName;
	@JsonProperty( "family_name" )
	public String familyName;
	public String picture;
	public String email;
	@JsonProperty( "email_verified" )
	public String emailVerified;
	public String gender;
	public String locale;
	public String profile;

	@Authorized
	public String accessToken;

	public String refreshToken;

	public String getSub()
	{
		return sub;
	}

	public String getName()
	{
		return name;
	}

	public GoogleUserInfo()
	{

	}

	public GoogleUserInfo( String sub, String name, String given_name, String family_name, String picture, String email,
			String email_verified, String gender, String locale )
	{
		super();
		this.sub = sub;
		this.name = name;
		this.givenName = given_name;
		this.familyName = family_name;
		this.picture = picture;
		this.email = email;
		this.emailVerified = email_verified;
		this.gender = gender;
		this.locale = locale;
	}

	public String getGivenName()
	{
		return givenName;
	}

	public void setGivenName( String givenName )
	{
		this.givenName = givenName;
	}

	public String getFamilyName()
	{
		return familyName;
	}

	public void setFamilyName( String familyName )
	{
		this.familyName = familyName;
	}

	public String getPicture()
	{
		return picture;
	}

	public void setPicture( String picture )
	{
		this.picture = picture;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail( String email )
	{
		this.email = email;
	}

	public String getEmailVerified()
	{
		return emailVerified;
	}

	public void setEmailVerified( String emailVerified )
	{
		this.emailVerified = emailVerified;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender( String gender )
	{
		this.gender = gender;
	}

	public String getLocale()
	{
		return locale;
	}

	public void setLocale( String locale )
	{
		this.locale = locale;
	}

	public String getAccessToken()
	{
		return accessToken;
	}

	public void setAccessToken( String accessToken )
	{
		this.accessToken = accessToken;
	}

	public String getRefreshToken()
	{
		return refreshToken;
	}

	public void setRefreshToken( String refreshToken )
	{
		this.refreshToken = refreshToken;
	}

	public void setSub( String sub )
	{
		this.sub = sub;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getProfile()
	{
		return profile;
	}

	public void setProfile( String profile )
	{
		this.profile = profile;
	}

}
