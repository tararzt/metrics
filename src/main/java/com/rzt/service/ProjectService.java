package com.rzt.service;

import com.rzt.exception.InsufficientInputException;
import com.rzt.schemapojo.Project;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for Project Operations
 */
@Service
public interface ProjectService {

	/**
	 * Add Project
	 * @param project
	 * @return
	 * @throws InsufficientInputException
     */
	public Project addProject( Project project ) throws InsufficientInputException;


	/**
	 * Update Project maintaining versioning
	 * @param project
	 * @return
	 * @throws InsufficientInputException
     */
	public Project updateProject( Project project ) throws InsufficientInputException;

	/**
	 * Delete Project(Hard Delete)
	 * @param projectId
	 * @return
	 * @throws InsufficientInputException
     */
	public Boolean deleteProject( Integer projectId ) throws InsufficientInputException;

	/**
	 * Inactivate Project(Soft Delete)
	 * @param projectId
	 * @return
	 * @throws InsufficientInputException
     */
	public Boolean endProject( Integer projectId ) throws InsufficientInputException;

	/**
	 * Get All Active Projects
	 * @return
     */
	public List<Project> getAllActiveProjects();

	/**
	 *Get the projects list which includes currently active projects as well as inActivated/Ended Projects
	 * @return
     */
	public List<Project> getAllProjects();

	/**
	 * Get the project details
	 * @param id
	 * @return
     */
	public Project getProject(Integer id) throws InsufficientInputException;

}
