package com.rzt.controller.base;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.rzt.exception.ActionFailureException;
import com.rzt.exception.InsufficientInputException;
import com.rzt.schemapojo.Employee;
import com.rzt.utils.ErrorCode;
import com.rzt.utils.JSONUtil;
import com.rzt.utils.SessionKey;

public class BaseController implements ServletRequestListener {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BaseController.class.getName());

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
		// logger.info("-----------------------------------------------------------");
		requestStartTimeMillies = System.currentTimeMillis();

	}

	@Override
	public void requestDestroyed( ServletRequestEvent req )
	{
		requestEndTimeMillies = System.currentTimeMillis();
		// logger.info("Exec Time:\t " + (requestEndTimeMillies - requestStartTimeMillies) + " ms");
		// logger.info("-----------------------------------------------------------");
	}

	public void logServiceDetails( APIResponse response )
	{
		logger.info("-----------------------------------------------------------");
		logger.info("Req URI:\t " + response.getApiPath());
		logger.info("Remote Addr:\t" + getSerRequest().getRemoteAddr());
		logger.info("Serviced:\t " + response.getServiced());
		if( response.getServiced() == false )
		{
			logger.info(response.getErrorCode());
		}

		requestEndTimeMillies = System.currentTimeMillis();
		logger.info("Exec Time:\t " + (requestEndTimeMillies - requestStartTimeMillies) + " ms");
		logger.info("-----------------------------------------------------------");
	}

	public String jsonStringifyResponse( APIResponse response )
	{
		response.setApiPath(getSerRequest().getRequestURI());
		logServiceDetails(response);
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

	public void handleActionFailureException( APIResponse response, ActionFailureException e )
	{
		response.setErrorCode(e.getMessage());
		response.setLogParameter("error", e.getValue());
		response.setServiced(false);
		logger.error(ErrorCode.COMMON_EXCEPTION, e);
	}

	public void handleInsufficientInputException( APIResponse response, InsufficientInputException e )
	{
		response.setErrorCode(e.getMessage());
		response.setLogParameter("error", e.getFields());
		response.setServiced(false);
		logger.error(ErrorCode.COMMON_EXCEPTION, e);
	}

	public void handleGlobalException( APIResponse response, Exception e )
	{
		response.setServiced(false);
		response.setErrorCode("common.server.error");
		logger.error(ErrorCode.COMMON_EXCEPTION, e);
	}

	public void handleSecuredServices( APIResponse response ) throws IOException, ServletException
	{
		logger.error(ErrorCode.USER_NOT_LOGGED_IN);
		response.setData(null);
		response.setErrorCode(ErrorCode.USER_NOT_LOGGED_IN);
		getSerResponse().getOutputStream().println(this.jsonStringifyResponse(response));
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