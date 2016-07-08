package com.rzt.google;

import java.util.Arrays;
import java.util.Collection;
import org.springframework.core.env.Environment;
import com.rzt.utils.StringHelper;

/**
 * 
 * @author deepak
 *
 *         Google API Config for sublease
 *
 * 
 *         google.client.id=
 *         "366660359606-slv60t5qincpa0i9ucj0sq5rg4sbbs9u.apps.googleusercontent.com"
 *         google.client.secret=bdtHyz2_dT0fCatA80PUGUHd
 *         google.auth.scope=https://www.googleapis.com/auth/userinfo.email,
 *         https://www.googleapis.com/auth/userinfo.profile
 *         google.token.uri=https://accounts.google.com/o/oauth2/token
 *         google.auth.uri=https://accounts.google.com/o/oauth2/auth
 *         google.authorization.code=authorization_code
 *         google.redirect.url=http://localhost:8082/oauth2callback
 *         google.project.id=traderental-1149 *
 */

public class GoogleApiAuthConfig {

	private String projectId;
	private String clientId;
	private String authUri;
	private String tokenUri;
	private String clientSecret;
	private String redirectUri;
	private Collection<String> scope;
	private Collection<String> responseTypes;

	public GoogleApiAuthConfig( Environment env )
	{

		if( env != null )
		{
			this.clientId = env.getProperty("google.client.id");
			this.projectId = env.getProperty("google.project.id");
			this.authUri = env.getProperty("google.auth.uri");
			this.tokenUri = env.getProperty("google.token.uri");
			this.clientSecret = env.getProperty("google.client.secret");
			this.redirectUri = env.getProperty("google.redirect.url");

			if( !StringHelper.isNullOrNullString(env.getProperty("google.auth.scope")) )
			{
				this.scope = Arrays.asList(env.getProperty("google.auth.scope").split(","));
			}

			if( !StringHelper.isNullOrNullString(env.getProperty("google.response.type")) )
			{

				this.responseTypes = Arrays.asList(env.getProperty("google.response.type").split(","));
			}
		}

	}

	public Collection<String> getResponseTypes()
	{
		return responseTypes;
	}

	public void setResponseTypes( Collection<String> responseTypes )
	{
		this.responseTypes = responseTypes;
	}

	public Collection<String> getScope()
	{
		return scope;
	}

	public void setScope( Collection<String> scope )
	{
		this.scope = scope;
	}

	public String getProjectId()
	{
		return projectId;
	}

	public void setProjectId( String projectId )
	{
		this.projectId = projectId;
	}

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId( String clientId )
	{
		this.clientId = clientId;
	}

	public String getAuthUri()
	{
		return authUri;
	}

	public void setAuthUri( String authUri )
	{
		this.authUri = authUri;
	}

	public String getTokenUri()
	{
		return tokenUri;
	}

	public void setTokenUri( String tokenUri )
	{
		this.tokenUri = tokenUri;
	}

	public String getClientSecret()
	{
		return clientSecret;
	}

	public void setClientSecret( String clientSecret )
	{
		this.clientSecret = clientSecret;
	}

	public String getRedirectUri()
	{
		return redirectUri;
	}

	public void setRedirectUri( String redirectUri )
	{
		this.redirectUri = redirectUri;
	}
}
