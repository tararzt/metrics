package com.rzt.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rzt.dao.repository.ProjectRepo;
import com.rzt.exception.ActionFailureException;
import com.rzt.exception.InsufficientInputException;
import com.rzt.schemapojo.Project;
import com.rzt.service.ProjectService;
import com.rzt.utils.ErrorCode;
import com.rzt.utils.Utils;

/**
 * Service implmenetation of Project Operations
 * 
 * @author tara
 *
 */
@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectRepo projectRepo;

	/**
	 * Add new Project
	 * 
	 * @param project
	 * @return
	 * @throws InsufficientInputException
	 */
	@Override
	public Project addProject( Project project ) throws InsufficientInputException
	{
		Utils.mandatroyInputCheck(project);
		Utils.mandatroyInputCheck(project.getName(), project.getClient());
		checkForDuplicateProject(project.getName(), project.getClient());
		project.setCreatedDate(new Date());
		return projectRepo.save(project);

	}

	/**
	 * Update Project Details mainintaing Version
	 * 
	 * @param project
	 * @return
	 * @throws InsufficientInputException
	 */
	public Project updateProject( Project project ) throws InsufficientInputException
	{

		Utils.mandatroyInputCheck(project);

		String name = project.getName();
		String client = project.getClient();
		Utils.mandatroyInputCheck(name, client, project.getId());

		Project existingProject = projectRepo.findOne(project.getId());

		//Check if the Project to be updated is present
		if( existingProject == null )
			throw new ActionFailureException(ErrorCode.PROJECT_NOT_PRESENT);

		//Check if there is any update in name or client and check for duplicate if there is an update
		if( !name.equalsIgnoreCase(existingProject.getName()) || !client.equalsIgnoreCase(existingProject.getClient()) )
			checkForDuplicateProject(project.getName(), project.getClient());

		//Inactivate existing row on each update to maintain Versioning
		existingProject.setIsActive(false);
		projectRepo.save(existingProject);

		//Create and insert a new row in Db
		project.setId(null);
		project.setCreatedDate(new Date());
		return projectRepo.save(project);

	}

	/**
	 * Delete project from DB -- Hard Delete
	 * 
	 * @param projectId
	 * @return
	 * @throws InsufficientInputException
	 */
	public Boolean deleteProject( Integer projectId ) throws InsufficientInputException
	{
		Utils.mandatroyInputCheck(projectId);

		Project project = projectRepo.findOne(projectId);
		if( project == null )
			throw new ActionFailureException(ErrorCode.PROJECT_NOT_PRESENT);

		projectRepo.delete(project);

		return true;

	}

	/**
	 * Inactivate Project -- Soft Delete
	 * 
	 * @param projectId
	 * @return
	 * @throws InsufficientInputException
	 */
	public Boolean endProject( Integer projectId ) throws InsufficientInputException
	{
		Utils.mandatroyInputCheck(projectId);

		Project project = projectRepo.findOne(projectId);
		if( project == null )
			throw new ActionFailureException(ErrorCode.PROJECT_NOT_PRESENT);

		project.setIsActive(false);
		project.setEndDate(new Date());

		projectRepo.save(project);
		return true;
	}

	/**
	 *
	 * @return
	 */
	public List<Project> getAllActiveProjects()
	{
		List<Project> projects = projectRepo.findByIsActive(true);
		return projects;
	}

	/**
	 * Private method implementation to check for duplicates
	 * 
	 * @param name
	 * @param client
	 */
	private void checkForDuplicateProject( String name, String client )
	{
		Project existingProject = projectRepo.findByNameAndClientAndIsActive(name, client, true);
		if( existingProject != null )
			throw new ActionFailureException(ErrorCode.CLIENT_PROJECT_PRESENT);
	}

}
