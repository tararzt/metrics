package com.rzt.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rzt.repository.UserCookieRepo;
import com.rzt.schemapojo.Employee;
import com.rzt.schemapojo.UserCookie;
import com.rzt.service.SessionService;
import com.rzt.utils.CookieUtils;
import com.rzt.utils.JSONUtil;
import com.rzt.utils.SessionKey;

/**
 * Service implementation for session Handling
 */
@Service
@SuppressWarnings( "unchecked" )
public class SessionServiceImpl implements SessionService {

	private static final Logger logger = LoggerFactory.getLogger(SessionServiceImpl.class.getName());
	@Autowired
	UserCookieRepo userCookieRepository;

	@Override
	public UserCookie saveOrUpdateSession( HttpServletRequest request )
	{
		Employee employee = (Employee) request.getSession().getAttribute(SessionKey.USER);
		String sessionId = (String) request.getSession().getAttribute(SessionKey.SESSION_ID);
		UserCookie cookie = null;
		if( employee != null )
		{
			cookie = userCookieRepository.findByCookieAndEmployeeId(sessionId, employee.getId());

			if( cookie != null )
			{
				cookie.setPacket(this.serializeSessionPacket(request, null));

				userCookieRepository.save(cookie);

				return cookie;
			}

			cookie = new UserCookie();
			cookie.setCookie(sessionId);
			cookie.setEmployeeId(employee.getId());

			cookie.setPacket(this.serializeSessionPacket(request, null));

			cookie = userCookieRepository.save(cookie);

			logger.info("Session in db updated successfully");

		}

		return cookie;
	}

	private String serializeSessionPacket( HttpServletRequest request, Map<String, Object> updateSessionPacket )

	{
		Map<String, Object> sessionPacket = new HashMap<String, Object>();

		if( updateSessionPacket != null )
		{
			sessionPacket = updateSessionPacket;
		}

		Enumeration<String> attributeNames = request.getSession().getAttributeNames();

		while( attributeNames.hasMoreElements() )
		{
			String attributeName = attributeNames.nextElement();
			if( request.getSession().getAttribute(attributeName) != null )
			{
				sessionPacket.put(attributeName, request.getSession().getAttribute(attributeName));
			}
		}

		String jsonString = JSONUtil.convertObjectToString(sessionPacket, true);

		logger.info("Session packet - " + jsonString);

		return jsonString;
	}

	private Map<String, Object> deSerializeSessionPacket( String sessionPacketString )
	{

		Map<String, Object> sessionPacket = new HashMap<String, Object>();

		Map<String, Object> deserializedSessionPacket = JSONUtil.convertStringToObject(sessionPacketString, Map.class);

		for( Entry<String, Object> attribute : deserializedSessionPacket.entrySet() )
		{
			if( attribute.getValue() == null )
				continue;

			if( attribute.getKey().toLowerCase().contains("object") )
			{
				if( attribute.getKey().equals(SessionKey.USER) )
				{
					Employee employee = JSONUtil.convertObject(deserializedSessionPacket.get(SessionKey.USER),
							Employee.class);
					sessionPacket.put(SessionKey.USER, employee);
				}

			}
			else
			{
				sessionPacket.put(attribute.getKey(), attribute.getValue());
			}
		}

		return sessionPacket;
	}

	@Override
	public void setSessionAttribute( HttpServletRequest request, String attributeName, Object attributeValue )
	{
		request.getSession().setAttribute(attributeName, attributeValue);
		this.saveOrUpdateSession(request);
	}

	@Override
	public <T extends Object> T getSessionAttribute( HttpServletRequest request, String attributeName )
	{
		if( request.getSession().getAttribute(attributeName) == null )
		{
			return null;
		}

		return (T) request.getSession().getAttribute(attributeName);
	}

	@Override
	public UserCookie createSessionFromDB( HttpServletRequest request )
	{
		String sessionId = CookieUtils.getCookieValue(request, SessionKey.SESSION_ID);

		if( sessionId != null )
		{
			logger.info("Restoring session from db " + sessionId);

			Calendar dttm = Calendar.getInstance();
			dttm.set(Calendar.MINUTE, dttm.get(Calendar.MINUTE) - 45);

			UserCookie us = userCookieRepository.findByCookie(sessionId);

			if( us != null )
			{
				Map<String, Object> sessionPacket = this.deSerializeSessionPacket(us.getPacket());

				for( Entry<String, Object> ele : sessionPacket.entrySet() )
				{
					request.getSession().setAttribute(ele.getKey(), ele.getValue());
				}
				userCookieRepository.save(us);
				return us;
			}
		}
		return null;
	}

	@Override
	public void updateLastUsedForCookie( String cookieValue )
	{
		UserCookie cookie = userCookieRepository.findByCookie(cookieValue);

		if( cookie != null )
		{
			cookie.setLastAccessed(new Date());

			userCookieRepository.save(cookie);

		}

	}

	@Override
	public void setSessions( HttpServletRequest request, HttpServletResponse response, Employee employee,
			Boolean rememberMe )
	{

		String sessionId = CookieUtils.getCookieValue(request, SessionKey.SESSION_ID);

		request.getSession().setAttribute(SessionKey.USER, employee);
		request.getSession().setAttribute(SessionKey.SESSION_ID, sessionId);

		CookieUtils.extendCookie(request, response, SessionKey.SESSION_ID, rememberMe);

		saveOrUpdateSession(request);

		logger.info("Set session : session id - {}", sessionId);

		UserCookie cookie = userCookieRepository.findByCookie(sessionId);

		if( cookie == null )
		{
			cookie = new UserCookie();
			cookie.setCookie(sessionId);
			cookie.setEmployeeId(employee.getId());
			cookie.setCreatedDate(new Date());
		}
		else
		{
			cookie.setLastAccessed(new Date());
		}

		userCookieRepository.save(cookie);

	}

	/**
	 * Delete Session details of User from DB
	 * 
	 * @param cookie
	 * @param employee
	 */
	@Override
	public void deleteSessionFromDB( String cookie, Employee employee )
	{
		UserCookie userCookie = userCookieRepository.findByCookieAndEmployeeId(cookie, employee.getId());

		if( userCookie != null )
		{
			userCookieRepository.delete(userCookie);
		}
		logger.info("deleted Usercookie with cookie value from DB: " + cookie);
	}
}
