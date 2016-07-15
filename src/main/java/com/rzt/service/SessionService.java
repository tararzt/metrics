package com.rzt.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.rzt.schemapojo.Employee;
import com.rzt.schemapojo.UserCookie;

public interface SessionService {

	/**
	 * Check if the session exists in the system. if so, update the session or create a new session
	 * in the system
	 * 
	 * @param request
	 * @return UserSession
	 */
	UserCookie saveOrUpdateSession( HttpServletRequest request );

	/**
	 * Check if the session exists in the system. if so, Set the session attribute and update the
	 * system
	 * 
	 * @param request
	 * @param attributeName
	 * @param attributeValue
	 */
	void setSessionAttribute( HttpServletRequest request, String attributeName, Object attributeValue );

	/**
	 * Reads the session attribute value for a given session attribute
	 * 
	 * @param request
	 * @param attributeName
	 * @return
	 */
	<T extends Object> T getSessionAttribute( HttpServletRequest request, String attributeName )
			throws ClassCastException;

	/**
	 * Check if the session exists in the system. if so, deserialize the existing session packet,
	 * set all session attributes from session packet and update the last accessed time in the
	 * system
	 * 
	 * @param request
	 * @return UserSession
	 */
	UserCookie createSessionFromDB( HttpServletRequest request );

	void updateLastUsedForCookie( String cookieValue );

	void setSessions( HttpServletRequest request, HttpServletResponse response, Employee employee, Boolean rememberMe );

	void deleteSessionFromDB( String cookie, Employee employee );

}
