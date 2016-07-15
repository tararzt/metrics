package com.rzt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.rzt.controller.base.APIResponse;
import com.rzt.controller.base.BaseController;
import com.rzt.google.GoogleApiHelper;
import com.rzt.service.SessionService;
import com.rzt.utils.SessionKey;

/**
 * Controller to Service Authetication Requests
 */
@Controller
public class AuthController extends BaseController {

	static final Logger logger = LoggerFactory.getLogger(AuthController.class);


	@Autowired
	GoogleApiHelper googleApiHelper;

	@Autowired
	SessionService sessionService;


	/**
	 * Controller Service to get Google API Url
	 */
	@RequestMapping( value = "/googleloginurl", produces = {
			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET )
	@ResponseBody
	public String getGoogleUrl()
	{
		logger.info("Getting Google Api login URL...");

		APIResponse response = new APIResponse();
		try
		{
			response.setData(googleApiHelper.getLoginUrl());
			logger.info("Getting Google Api login URL is Completed");
		}
		catch( Exception e )
		{
			handleGlobalException(response, e);
		}
		return jsonStringifyResponse(response);
	}

	/**
	 * Controller Service to Login User
	 * 
	 * @return String
	 */
	@RequestMapping( value = "/loggedinuser", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET )
	@ResponseBody
	public String loggedinUser()
	{
		logger.info("Getting logged in User details...");

		APIResponse response = new APIResponse();
		try
		{
			response.setData(getLoggedInUser());
		}
		catch( Exception e )
		{
			handleGlobalException(response, e);
		}
		return jsonStringifyResponse(response);
	}

	/**
	 * Controller Service to Loggout User
	 * 
	 * @return
	 */
	@RequestMapping( value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET )
	@ResponseBody
	public String logout()
	{
		logger.info("logging out user....");
		APIResponse response = new APIResponse();

		try
		{
			String sessionId = (String) getSerRequest().getSession().getAttribute(SessionKey.SESSION_ID);
			sessionService.deleteSessionFromDB(sessionId, getLoggedInUser());

			getSerRequest().getSession().invalidate();

			response.setData(true);
			logger.info("User log out request is completed successfully!");
		}
		catch( Exception e )
		{
			handleGlobalException(response, e);
		}
		return jsonStringifyResponse(response);
	}

}
