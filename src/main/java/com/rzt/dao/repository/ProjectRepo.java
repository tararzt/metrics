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

	@Override
	public List<Project> findAll();

	@Override
	public void delete( Project project );

	@Override
	public boolean exists( Integer id );

	@Override
	public long count();

	@Override
	public Project findOne( Integer id );

	public List<Project> findByName( String name );

	public List<Project> findByClient( String client );

	public Project findByNameAndClientAndIsActive( String name, String client, Boolean isActive );

	public Project findByIdAndIsActive( Integer id, Boolean isActive );

	public List<Project> findByIsActive( Boolean isActive );

}
