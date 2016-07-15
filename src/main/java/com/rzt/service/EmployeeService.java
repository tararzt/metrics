package com.rzt.service;

import java.util.List;
import com.rzt.exception.InsufficientInputException;
import com.rzt.google.GoogleUserInfo;
import com.rzt.schemapojo.Employee;

/**
 * Service for Employee Operations
 */
public interface EmployeeService {

	/**
	 * Add employee Deatils from Google API
	 * 
	 * @param googleUserInfo
	 * @return
	 */
	public Employee addEmployeeDetailsFromGoogle( GoogleUserInfo googleUserInfo );

	/**
	 *
	 * @param employeeToAdd
	 * @return
	 * @throws InsufficientInputException
	 */
	public Employee addEmployee( Employee employeeToAdd ) throws InsufficientInputException;

	/**
	 * Get list of Active Employees
	 * 
	 * @return
	 */
	public List<Employee> getActiveEmployees();

	/**
	 * 
	 * @param employee
	 * @return
	 * @throws InsufficientInputException
	 */
	public Employee updateEmployee( Employee employee ) throws InsufficientInputException;

	/**
	 * 
	 * @param employeeId
	 * @return
	 * @throws InsufficientInputException
	 */
	public Boolean delete( Integer employeeId ) throws InsufficientInputException;

}
