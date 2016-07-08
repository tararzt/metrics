package com.rzt.controller.base;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.rzt.exception.ActionFailureException;
import com.rzt.exception.InsufficientInputException;
import com.rzt.schema_pojo.Employee;
import com.rzt.utils.JSONUtil;
import com.rzt.utils.SessionKey;

public class BaseController implements ServletRequestListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

	protected APIResponse response = new APIResponse();

	Long requestStartTimeMillies = null;
	Long requestEndTimeMillies = null;

	/* @Context private ServletContext servletContext; */

	public Employee getLoggedInUser()
	{
		if( getSerRequest().getSession().getAttribute(SessionKey.USER) != null )
		{
			return (Employee) getSerRequest().getSession().getAttribute(SessionKey.USER);
		}
		return null;
	}

	// Servlet Request Listeners
	@Override
	public void requestInitialized( ServletRequestEvent req )
	{
		// LOGGER.info("-----------------------------------------------------------");
		requestStartTimeMillies = System.currentTimeMillis();

	}

	@Override
	public void requestDestroyed( ServletRequestEvent req )
	{
		requestEndTimeMillies = System.currentTimeMillis();
		// LOGGER.info("Exec Time:\t " + (requestEndTimeMillies - requestStartTimeMillies) + " ms");
		// LOGGER.info("-----------------------------------------------------------");
	}

	public void logServiceDetails()
	{
		LOGGER.info("-----------------------------------------------------------");
		LOGGER.info("Req URI:\t " + response.getApiPath());
		LOGGER.info("Remote Addr:\t" + getSerRequest().getRemoteAddr());
		LOGGER.info("Serviced:\t " + response.getServiced());
		if( response.getServiced() == false )
		{
			LOGGER.info(response.getErrorCode());
		}

		requestEndTimeMillies = System.currentTimeMillis();
		LOGGER.info("Exec Time:\t " + (requestEndTimeMillies - requestStartTimeMillies) + " ms");
		LOGGER.info("-----------------------------------------------------------");
	}

	public String jsonStringifyResponse()
	{
		response.setApiPath(getSerRequest().getRequestURI());
		logServiceDetails();
		return JSONUtil.convertObjectToString(response, true);
	}

	public HttpServletRequest getSerRequest()
	{
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

	}

	public HttpServletResponse getSerResponse()
	{
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

	}

	public void handleActionFailureException( ActionFailureException e )
	{
		response.setErrorCode(e.getMessage());
		response.setLogParameter("error", e.getValue());
		response.setServiced(false);
	}

	public void handleInsufficientInputException( InsufficientInputException e )
	{
		response.setErrorCode(e.getMessage());
		response.setLogParameter("error", e.getFields());
		response.setServiced(false);
	}

	public void handleGlobalException( Exception e )
	{
		response.setServiced(false);
		response.setErrorCode("common.server.error");
	}

	public void handleSecuredServices() throws IOException, ServletException
	{
		LOGGER.info("User is not Logged in, User must Login Inorder to Access: " + getSerRequest().getRequestURI());
		this.response.setData(null);
		this.response.setErrorCode("Unthorized Access");
		getSerResponse().getOutputStream().println(this.jsonStringifyResponse());
	}

	public String getIpAddr()
	{
		String ip = getSerRequest().getHeader("x-forwarded-for");
		if( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) )
		{
			ip = getSerRequest().getHeader("Proxy-Client-IP");
		}
		if( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) )
		{
			ip = getSerRequest().getHeader("WL-Proxy-Client-IP");
		}
		if( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) )
		{
			ip = getSerRequest().getRemoteAddr();
		}

		return ip;
	}

}