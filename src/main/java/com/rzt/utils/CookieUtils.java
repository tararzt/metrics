/**
 * 
 */
package com.rzt.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author deepak
 * 
 */
public class CookieUtils {

	private static final Logger LOGGER = LogManager.getLogger(CookieUtils.class.getName());
	private static int rememberMeTokenValiditySeconds = 30 * 24 * 60 * 60;
	private static int sessionTokenValiditySeconds = 45 * 60;

	private CookieUtils()
	{
		super();
	}

	public static void setCookie( HttpServletResponse response, String name, String value, Boolean rememberMe )
	{
		// Make a session cookie (maxAge==-1 means that it will expire when the
		// browser closes)

		int tokenValiditySeconds = 0;
		if( rememberMe )
		{
			tokenValiditySeconds = rememberMeTokenValiditySeconds;
		}
		else
		{
			tokenValiditySeconds = sessionTokenValiditySeconds;
		}
		response.addCookie(makeValidCookie(tokenValiditySeconds, value, name));
	}

	public static void extendCookie( HttpServletRequest request, HttpServletResponse response, String cookieName,
			Boolean rememberMe )
	{

		Cookie cookie = getCookieByName(request, cookieName);
		if( cookie != null )
		{
			if( rememberMe )
				cookie.setMaxAge(rememberMeTokenValiditySeconds);
			else
			{
				cookie.setMaxAge(sessionTokenValiditySeconds);
			}
			cookie.setPath("/");
			response.addCookie(cookie);
		}
	}

	public static void cancelCookie( HttpServletRequest request, HttpServletResponse response, String cookieName )
	{

		Cookie cookie = makeCancelCookie(request, cookieName);

		if( cookie == null )
			return;

		response.addCookie(makeCancelCookie(request, cookieName));
	}

	public static String cookieToString( Cookie c )
	{
		StringBuilder result = new StringBuilder();

		result.append("domain:" + c.getDomain());
		result.append("\ncomment:" + c.getComment());
		result.append("\nmax age:" + c.getMaxAge());
		result.append("\nname:" + c.getName());
		result.append("\npath:" + c.getPath());
		result.append("\nvalue:" + c.getValue());

		return result.toString();
	}

	protected static Cookie makeCancelCookie( HttpServletRequest request, String cookieName )
	{

		Cookie cookie = getCookieByName(request, cookieName);
		if( cookie != null )
		{
			cookie.setMaxAge(0);
			cookie.setPath("/");
			return cookie;
		}

		return null;
	}

	protected static Cookie makeValidCookie( int expiryTime, String value, String name )
	{
		Cookie cookie = new Cookie(name, value);
		cookie.setSecure(true);
		cookie.setMaxAge(expiryTime);
		cookie.setPath("/");

		return cookie;
	}

	public static Cookie getCookieByName( HttpServletRequest request, String cookieName )
	{
		if( request != null && request.getCookies() != null )
		{
			Cookie[] cookies = request.getCookies();
			LOGGER.debug("Fetching cookie ");
			for( int i = 0; i < cookies.length; i++ )
			{
				Cookie cookie = cookies[i];
				if( cookie.getName().equalsIgnoreCase(cookieName) )
				{
					return cookie;
				}
			}
		}

		return null;

	}

	public static String getCookieValue( HttpServletRequest request, String cookieName )
	{
		String cookieValue = "";
		Cookie cookie = getCookieByName(request, cookieName);
		if( cookie != null && cookie.getValue() != null && !"".equals(cookie.getValue().trim()) )
		{
			cookieValue = cookie.getValue().replaceAll("%2D", "-");
		}

		return cookieValue;

	}

}
