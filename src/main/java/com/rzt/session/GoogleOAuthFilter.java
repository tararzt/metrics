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
import org.springframework.core.env.Environment;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.rzt.google.GoogleApiHelper;
import com.rzt.google.GoogleUserInfo;
import com.rzt.schemapojo.Employee;
import com.rzt.service.EmployeeService;
import com.rzt.service.SessionService;
import com.rzt.utils.StringHelper;

/**
 * Filter for Google Authentication
 */
public class GoogleOAuthFilter implements Filter {

	Logger logger = Logger.getLogger(GoogleOAuthFilter.class);

	private GoogleApiHelper apiHelper;

	private EmployeeService userService;

	private SessionService sessionService;

	private Environment env;

	/**
	 * Method of Filter which needs to be implemented
	 */
	@Override
	public void destroy()
	{

	}

	@Override
	public void doFilter( ServletRequest serRequest, ServletResponse serResponse, FilterChain filterChain )
			throws IOException, ServletException
	{

		HttpServletRequest request = (HttpServletRequest) serRequest;
		HttpServletResponse response = (HttpServletResponse) serResponse;

		String redirectUrl = request.getParameter("redirectUrl");

		// the auth code returned by google for this user.
		String code = serRequest.getParameter("code");

		try
		{
			// Read the profile f
			GoogleUserInfo userInfo = apiHelper.getUserInfoJson(code);

			Employee employee = userService.addEmployeeDetailsFromGoogle(userInfo);

			sessionService.setSessions(request, response, employee, true);

			logger.info("Google Auth Successful! Redirecting the control to " + redirectUrl);
			response.sendRedirect(
					!StringHelper.isNullOrNullString(redirectUrl) ? redirectUrl : this.env.getProperty("web.host.url"));

		}
		catch( IOException e )
		{
			logger.error(e);

		}

	}

	@Override
	public void init( FilterConfig filterConfig ) throws ServletException
	{

		// Spring context
		WebApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(filterConfig.getServletContext());

		// Initialize some of the beans required in this filter from the spring
		// context.
		this.apiHelper = context.getBean(GoogleApiHelper.class);
		this.userService = context.getBean(EmployeeService.class);
		this.sessionService = context.getBean(SessionService.class);
		this.env = context.getEnvironment();

	}

}
