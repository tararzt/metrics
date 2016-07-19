package com.rzt.dao.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import com.rzt.schemapojo.Employee;

/**
 * Repository which Offers Crud Operations of Employee
 */
@Transactional
public interface EmployeeRepo extends CrudRepository<Employee, Integer> {

	@SuppressWarnings( "unchecked" )
	@Override
	/**
	 * Method Which Saves Employee
	 */
	public Employee save( Employee employee );

	@Override
	public List<Employee> findAll();

	@Override
	public void delete( Employee employee );

	@Override
	public boolean exists( Integer id );

	@Override
	public long count();

	@Override
	public Employee findOne( Integer id );

	/**
	 * FInd Employee by Name
	 * @param name
	 * @return
     */
	public List<Employee> findByName( String name );

	/**
	 * Find employee by EmailId
	 * @param email
	 * @return
     */
	public Employee findByEmail( String email );

	/**
	 * Find Active Employees with EmailId
	 * @param email
	 * @param isActive
     * @return
     */
	public Employee findByEmailAndIsActive( String email, Boolean isActive );

	/**
	 * Find Active Employees
	 * @param isActive
	 * @return
     */
	public List<Employee> findByIsActive( Boolean isActive );

}
