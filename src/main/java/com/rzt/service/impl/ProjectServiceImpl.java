package com.rzt.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;
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

	static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

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
		logger.info("Adding a  new Project "+project.getName());

		checkForDuplicateProject(project.getName(), project.getClient());
		project.setCreatedDate(new Date());
		logger.info("Adding a  new Project "+project.getName()+" Completed");
		return projectRepo.save(project);

	}

	/**
	 * Update Project Details maintaintaing Version. Everytime when the update request recieved, Inactivate the current row and insert a new row
	 * 
	 * @param project
	 * @return
	 * @throws InsufficientInputException
	 */
	@Override
	public Project updateProject( Project project ) throws InsufficientInputException
	{

		Utils.mandatroyInputCheck(project);

		String name = project.getName();
		String client = project.getClient();
		Utils.mandatroyInputCheck(name, client, project.getId());
		logger.info(" Updating a project "+project.getName());

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
	 * Delete project from DB -- Hard Delete, Delete the row from DB
	 * 
	 * @param projectId
	 * @return
	 * @throws InsufficientInputException
	 */
	@Override
	public Boolean deleteProject( Integer projectId ) throws InsufficientInputException
	{
		Utils.mandatroyInputCheck(projectId);
		logger.info(" Deleting a Project with the Id "+projectId);
		Project project = projectRepo.findOne(projectId);
		if( project == null )
			throw new ActionFailureException(ErrorCode.PROJECT_NOT_PRESENT);

		projectRepo.delete(project);
		logger.info(" Deleting a Project with the Id "+projectId+" Completed");

		return true;

	}

	/**
	 * Inactivate Project -- Soft Delete. Make the Project Inactive and Update the End Date
	 * 
	 * @param projectId
	 * @return
	 * @throws InsufficientInputException
	 */
	@Override
	public Boolean endProject( Integer projectId ) throws InsufficientInputException
	{
		Utils.mandatroyInputCheck(projectId);
		logger.info(" Ending a Project with the Id "+projectId);


		Project project = projectRepo.findOne(projectId);
		if( project == null )
			throw new ActionFailureException(ErrorCode.PROJECT_NOT_PRESENT);

		project.setIsActive(false);
		project.setEndDate(new Date());

		projectRepo.save(project);
		logger.info(" Deleting a Project with the Id "+projectId+" completed");

		return true;
	}

	/**
	 *Get all activate Projects. Projects with the flag isActive is true
	 * @return
	 */
	@Override
	public List<Project> getAllActiveProjects()
	{
		return projectRepo.findByIsActive(true);
	}

	/**
	 * Get the projects list which includes currently active projects as well as inActivated/Ended Projects
	 * @return
     */
	@Override
	public List<Project> getAllProjects(){
		return projectRepo.findByIsActiveOrEndDateNotNull(true);
	}

	/**
	 * Get Project Details by Id
	 * @param id
	 * @return
	 * @throws InsufficientInputException
     */
	@Override
	public Project getProject(Integer id) throws InsufficientInputException{

		Utils.mandatroyInputCheck(id);
		Project project = projectRepo.findOne(id);
		if(project == null)
			throw new ActionFailureException(ErrorCode.PROJECT_NOT_PRESENT);

		return project;
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
