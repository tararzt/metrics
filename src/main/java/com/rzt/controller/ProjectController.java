package com.rzt.controller;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.rzt.controller.base.APIResponse;
import com.rzt.controller.base.BaseController;
import com.rzt.repository.ProjectRepo;
import com.rzt.schemapojo.Project;

/**
 * Controller to Serve the requests related to Projects
 */
@Controller
@RequestMapping( "/project" )
public class ProjectController extends BaseController {

	Logger logger = Logger.getLogger(ProjectController.class);

	@Autowired
	ProjectRepo projectRepo;

	@RequestMapping( name = "/create", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.PUT )
	@ResponseBody
	public String createProject( @RequestBody Project project )
	{
		APIResponse response = new APIResponse();
		try
		{
			project = projectRepo.save(project);
		}
		catch( Exception e )
		{
			// TODO Auto-generated catch block
			response.setServiced(false);
			logger.error(e);
			e.printStackTrace();
		}
		response.setData(project);
		return jsonStringifyResponse(response);

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
		APIResponse response = new APIResponse();
		List<Project> projects = null;
		try
		{
			projects = projectRepo.findAll();
		}
		catch( Exception e )
		{
			response.setServiced(false);
			logger.error(e);
			e.printStackTrace();
		}
		response.setData(projects);
		return jsonStringifyResponse(response);

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
		APIResponse response = new APIResponse();
		try
		{
			project = projectRepo.save(project);
		}
		catch( Exception e )
		{
			response.setServiced(false);
			logger.error(e);
			e.printStackTrace();
		}
		response.setData(project);
		return jsonStringifyResponse(response);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping( value = "{/id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET )
	@ResponseBody
	public String getProject( @PathVariable Integer id )
	{
		APIResponse response = new APIResponse();
		Project project = null;
		try
		{
			project = projectRepo.findOne(id);
		}
		catch( Exception e )
		{
			response.setServiced(false);
			logger.error(e);
			e.printStackTrace();
		}
		response.setData(project);
		return jsonStringifyResponse(response);

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
	public String deleteProject( @PathVariable Integer id )
	{
		APIResponse response = new APIResponse();
		Project project = new Project();
		project.setId(id);
		try
		{
			projectRepo.delete(project);
		}
		catch( Exception e )
		{
			response.setServiced(false);
			logger.error(e);
			e.printStackTrace();
		}
		return jsonStringifyResponse(response);

	}

}
