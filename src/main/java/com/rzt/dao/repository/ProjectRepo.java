package com.rzt.dao.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import com.rzt.schemapojo.Project;

@Transactional
public interface ProjectRepo extends CrudRepository<Project, Integer> {

	/**
	 * Adding a project
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public Project save( Project project );

	/**
	 * FInd all the Projects
	 * @return
     */
	@Override
	public List<Project> findAll();

	/**
	 * Delete a Project
	 * @param project
     */
	@Override
	public void delete( Project project );

	/**
	 * Check if the project exists with the input Id
	 * @param id
	 * @return
     */
	@Override
	public boolean exists( Integer id );

	/**
	 * Count the total Projects
	 * @return
     */
	@Override
	public long count();

	/**
	 * Find the PRoject by Id
	 * @param id
	 * @return
     */
	@Override
	public Project findOne( Integer id );

	/**
	 * Find Project By Name
	 * @param name
	 * @return
     */
	public List<Project> findByName( String name );

	/**
	 * Find Project By Client
	 * @param client
	 * @return
     */
	public List<Project> findByClient( String client );

	/**
	 * Find Active Project by Client name
	 * @param name
	 * @param client
	 * @param isActive
     * @return
     */
	public Project findByNameAndClientAndIsActive( String name, String client, Boolean isActive );

	/**
	 * FInd Project by Id and IsActive
	 * @param id
	 * @param isActive
     * @return
     */
	public Project findByIdAndIsActive( Integer id, Boolean isActive );

	/**
	 * Find Project is IsActive
	 * @param isActive
	 * @return
     */
	public List<Project> findByIsActive( Boolean isActive );

	/**
	 * Find Project by Is Active and end Date
	 * @param isActive
	 * @return
     */
	public List<Project> findByIsActiveOrEndDateNotNull(Boolean isActive);

}
