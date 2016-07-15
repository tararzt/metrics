package com.rzt.session;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.rzt.controller.base.APIResponse;
import com.rzt.controller.base.BaseController;
import com.rzt.utils.SessionKey;

@Component
public class AutherizedServiceFilter implements Filter {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AutherizedServiceFilter.class.getName());

	private BaseController baseController;

	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter( ServletRequest serRequest, ServletResponse serResponse, FilterChain chain )
			throws IOException, ServletException
	{
		logger.info("Begin Secured Service Request Filter........");
		HttpServletRequest request = (HttpServletRequest) serRequest;
		HttpServletResponse response = (HttpServletResponse) serResponse;

		// baseController.setSerRequest(request);
		// baseController.setSerResponse(response);
		if( request.getSession().getAttribute(SessionKey.USER) == null )
		{
			APIResponse apiResponse = new APIResponse();
			baseController.handleSecuredServices(apiResponse);
		}
		else
		{
			logger.info("User is Logged in, " + request.getRequestURI() + " is authenticated to Access.");
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init( FilterConfig filterConfig ) throws ServletException
	{
		// TODO Auto-generated method stub
		WebApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(filterConfig.getServletContext());
		// this.baseController = context.getBean(BaseController.class);

	}
}
