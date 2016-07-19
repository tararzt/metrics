package com.rzt.controller;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.rzt.controller.base.APIResponse;
import com.rzt.controller.base.BaseController;
import com.rzt.exception.ActionFailureException;
import com.rzt.exception.InsufficientInputException;
import com.rzt.schemapojo.Project;
import com.rzt.service.ProjectService;

/**
 * Controller to Serve the requests related to Projects
 */
@Controller
@RequestMapping( "/project" )
public class ProjectController extends BaseController {

	static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	ProjectService projectService;

	/**
	 * Create a new Project
	 * 
	 * @param project
	 * @return
	 */
	@RequestMapping( name = "/create", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.PUT )
	@ResponseBody
	public String createProject( @RequestBody Project project )
	{
		logger.info("Request Received to Create a new Project...");
		APIResponse response = new APIResponse();
		try
		{
			response.setData(projectService.addProject(project));
			logger.info("Sending back the response for create new project request");
		}
		catch( ActionFailureException e )
		{
			handleActionFailureException(response, e);
		}
		catch( InsufficientInputException e )
		{
			handleInsufficientInputException(response, e);
		}
		catch( Exception e )
		{
			handleGlobalException(response, e);
		}

		return jsonStringifyResponse(response);

	}

	/**
	 * Update Project Details
	 * 
	 * @param project
	 * @return
	 */
	@RequestMapping( name = "/update", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST )
	@ResponseBody
	public String updateProject( @RequestBody Project project )
	{

		logger.info("Request Received to update a Project...");
		APIResponse response = new APIResponse();

		try
		{
			response.setData(projectService.updateProject(project));
			logger.info("Sending back the response for update project request");

		}
		catch( ActionFailureException e )
		{
			handleActionFailureException(response, e);
		}
		catch( InsufficientInputException e )
		{
			handleInsufficientInputException(response, e);
		}
		catch( Exception e )
		{
			handleGlobalException(response, e);
		}
		return jsonStringifyResponse(response);

	}

	/**
	 * Get all the Projects Active/Inactive
	 * 
	 * @return
	 */
	@RequestMapping( value = "/projects", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET )
	@ResponseBody
	public String getAllProjects()
	{
		logger.info("Request received to Get all the Projects...");
		APIResponse response = new APIResponse();
		try
		{
			response.setData(projectService.getAllProjects());
			logger.info("Sending the response back for get all projects request");

		}
		catch( Exception e )
		{
			handleGlobalException(response, e);
		}
		return jsonStringifyResponse(response);

	}

	/**
	 * Get all Active Projects
	 * 
	 * @return
	 */
	@RequestMapping( name = "/active-projects", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET )
	@ResponseBody
	public String getAllActiveProjects()
	{
		logger.info("Request received to Get all active Projects...");
		APIResponse response = new APIResponse();

		try
		{
			response.setData(projectService.getAllActiveProjects());
			logger.info("Sending the response back for get all Active projects request");

		}
		catch( Exception e )
		{
			handleGlobalException(response, e);
		}
		return jsonStringifyResponse(response);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping( value = "{/id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET )
	@ResponseBody
	public String getProject( @PathVariable Integer id )
	{
		logger.info("Request Received to Project details...");
		APIResponse response = new APIResponse();
		try
		{
			response.setData(projectService.getProject(id));
			logger.info("Sending back the response for get Project Details");
		}
		catch( InsufficientInputException e )
		{
			handleInsufficientInputException(response, e);
		}
		catch( Exception e )
		{
			handleGlobalException(response, e);
		}
		return jsonStringifyResponse(response);

	}

	/**
	 * delete Project
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping( value = "/delete/{id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.DELETE )
	@ResponseBody
	public String deleteProject( @PathVariable Integer id )
	{
		logger.info("Request Received to delete a Project...");
		APIResponse response = new APIResponse();
		try
		{
			response.setData(projectService.deleteProject(id));
			logger.info("Sending back the response for deleting a project");
		}
		catch( ActionFailureException e )
		{
			handleActionFailureException(response, e);
		}
		catch( InsufficientInputException e )
		{
			handleInsufficientInputException(response, e);
		}
		catch( Exception e )
		{
			handleGlobalException(response, e);
		}
		return jsonStringifyResponse(response);

	}

	/**
	 * InActivate Project
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping( value = "/inActivate/{id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.DELETE )
	@ResponseBody
	public String inActiveProject( @PathVariable Integer id )
	{
		logger.info("Request Received to inActivate a Project...");
		APIResponse response = new APIResponse();
		try
		{
			response.setData(projectService.endProject(id));
			logger.info("Sending back the response for inActivating a project request");
		}
		catch( ActionFailureException e )
		{
			handleActionFailureException(response, e);
		}
		catch( InsufficientInputException e )
		{
			handleInsufficientInputException(response, e);
		}
		catch( Exception e )
		{
			handleGlobalException(response, e);
		}
		return jsonStringifyResponse(response);

	}



}
