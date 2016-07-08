package com.rzt.service.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import com.rzt.schema_pojo.Employee;

@Component
public interface EmployeeRepo extends CrudRepository<Employee, Integer> {

	@SuppressWarnings( "unchecked" )
	public Employee save( Employee employee );

	public List<Employee> findAll();

	public void delete( Employee employee );

	public boolean exists( Integer id );

	public long count();

	public Employee findOne( Integer id );

	public List<Employee> findByName( String name );
}
