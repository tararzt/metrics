package com.rzt.service.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import com.rzt.schema_pojo.Project;

@Component
public interface ProjectRepo extends CrudRepository<Project, Integer> {

	@SuppressWarnings( "unchecked" )
	public Project save( Project project );

	public List<Project> findAll();

	public void delete( Project project );

	public boolean exists( Integer id );

	public long count();

	public Project findOne( Integer id );

	public List<Project> findByName( String name );

	public List<Project> findByClient( String client );
}
