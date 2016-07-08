package com.rzt.controller;

import java.util.List;
import javax.websocket.server.PathParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.rzt.controller.base.APIResponse;
import com.rzt.controller.base.BaseController;
import com.rzt.exception.ActionFailureException;
import com.rzt.schema_pojo.Project;
import com.rzt.service.repository.ProjectRepo;

@Controller
@RequestMapping( "/project" )
public class ProjectController extends BaseController {

	Logger LOGGER = Logger.getLogger(ProjectController.class);

	@Autowired
	ProjectRepo projectRepo;

	@RequestMapping( name = "/create", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.PUT )
	@ResponseBody
	public String createProject( @RequestBody Project project )
	{
		response = new APIResponse();

		try
		{
			project = projectRepo.save(project);
		}
		catch( Exception e )
		{
			// TODO Auto-generated catch block
			response.setServiced(false);
			LOGGER.error(e);
			e.printStackTrace();
		}
		response.setData(project);
		return jsonStringifyResponse();

	}

	/**
	 * Get all Projects
	 * 
	 * @return
	 */
	@RequestMapping( value = "/projects", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET )
	@ResponseBody
	public String getAllProjects()
	{
		response = new APIResponse();
		List<Project> projects = null;
		try
		{
			projects = projectRepo.findAll();
		}
		catch( Exception e )
		{
			response.setServiced(false);
			LOGGER.error(e);
			e.printStackTrace();
		}
		response.setData(projects);
		return jsonStringifyResponse();

	}

	/**
	 * Update Project details
	 * 
	 * @param project
	 * @return
	 */
	@RequestMapping( value = "/update", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST )
	@ResponseBody
	public String updateProject( @RequestBody Project project )
	{
		response = new APIResponse();
		try
		{
			project = projectRepo.save(project);
		}
		catch( ActionFailureException e )
		{
			handleActionFailureException(e);
			response.setServiced(false);
			LOGGER.error(e);
			e.printStackTrace();
		}
		catch( Exception e )
		{
			response.setServiced(false);
			LOGGER.error(e);
			e.printStackTrace();
		}
		response.setData(project);
		return jsonStringifyResponse();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping( produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET )
	@ResponseBody
	public String getProject( Integer id )
	{
		response = new APIResponse();
		Project project = null;
		try
		{
			project = projectRepo.findOne(id);
		}
		catch( Exception e )
		{
			response.setServiced(false);
			LOGGER.error(e);
			e.printStackTrace();
		}
		response.setData(project);
		return jsonStringifyResponse();

	}

	/**
	 * delete Project
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping( value = "/delete/{id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.DELETE )
	@ResponseBody
	public String deleteProject( @PathParam( value = "id" ) Integer id )
	{
		response = new APIResponse();
		Project project = new Project();
		project.setId(id);
		try
		{
			projectRepo.delete(project);
		}
		catch( Exception e )
		{
			response.setServiced(false);
			LOGGER.error(e);
			e.printStackTrace();
		}
		return jsonStringifyResponse();

	}

}
