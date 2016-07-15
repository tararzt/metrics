package com.rzt.service.impl;

import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rzt.exception.ActionFailureException;
import com.rzt.exception.InsufficientInputException;
import com.rzt.google.GoogleUserInfo;
import com.rzt.schemapojo.Employee;
import com.rzt.service.EmailService;
import com.rzt.service.EmployeeService;
import com.rzt.repository.EmployeeRepo;
import com.rzt.utils.ErrorCode;
import com.rzt.utils.StringHelper;
import com.rzt.utils.Utils;

/**
 * service implementation for employee operations
 * 
 * @author tara
 *
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

	Logger logger = Logger.getLogger(EmployeeServiceImpl.class);

	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	EmailService emailService;

	/**
	 * <li>Check if the user with same email ID exists</li>
	 * <li>Update user last login if the user already exists in the system and return User object
	 * </li>
	 * <li>If user does not exists, add the user into the system and return User Object</li>
	 */
	@Override
	public Employee addEmployeeDetailsFromGoogle( GoogleUserInfo googleUserInfo )
	{
		Employee employee = employeeRepo.findByEmail(googleUserInfo.getEmail());

		if(employee == null)
			throw new ActionFailureException(ErrorCode.USER_NOT_FOUND);

		employee.setLastLogin(new Date());
		if( StringHelper.isNullOrNullString(employee.getName()) )
			employee.setName(googleUserInfo.getName());

		employee = employeeRepo.save(employee);

		return employee;
	}

	/**
	 * Add EMployee if the employee is not added
	 * 
	 * @param employee
	 * @return
	 * @throws InsufficientInputException
	 * @throws ActionFailureException
	 */
	@Override
	public Employee addEmployee( Employee employee ) throws InsufficientInputException
	{
		//Check for Mandatory Inputs
		Utils.mandatroyInputCheck(employee);
		Utils.mandatroyInputCheck(employee.getEmail());

		Employee existingEmployee = employeeRepo.findByEmailAndIsActive(employee.getEmail(), true);
		if( existingEmployee != null )
			throw new ActionFailureException(ErrorCode.ACTIVE_EMPLOYEE_PRESENT);

		employee.setCreatedDate(new Date());
		return employeeRepo.save(employee);
	}

	/**
	 * Service implmeentation to get all Active EMployees
	 */
	@Override
	public List<Employee> getActiveEmployees()
	{
		return employeeRepo.findByIsActive(true);
	}

	/**
	 * Service to Update Employee Deatails
	 * 
	 * @throws InsufficientInputException
	 */
	@Override
	public Employee updateEmployee( Employee updatedEmployee ) throws InsufficientInputException
	{
		//Mandatory Input Check
		Utils.mandatroyInputCheck(updatedEmployee);
		Utils.mandatroyInputCheck(updatedEmployee.getId(), updatedEmployee.getEmail());

		//Check if the Employee to be updated is Present, if not throw an error message
		Employee currentEmployee = employeeRepo.findOne(updatedEmployee.getId());

		if( currentEmployee == null )
			throw new ActionFailureException(ErrorCode.USER_NOT_FOUND);

		//Check if the new emailto be updated is Different and if any employee exist with same emailId
		if( !currentEmployee.getEmail().equals(updatedEmployee.getEmail()) )
		{

			Employee emplWithSameEmail = employeeRepo.findByEmailAndIsActive(updatedEmployee.getEmail(), true);
			if( emplWithSameEmail != null )
				throw new ActionFailureException(ErrorCode.ACTIVE_EMPLOYEE_PRESENT);

		}

		return employeeRepo.save(updatedEmployee);
	}

	/**
	 * Service implementation to delete an employee
	 * 
	 * @throws InsufficientInputException
	 */
	@Override
	public Boolean delete( Integer employeeId ) throws InsufficientInputException
	{
		Boolean isInActivated;
		Utils.mandatroyInputCheck(employeeId);

		//Check if the Employee  is Present, if not throw an error message
		Employee employee = employeeRepo.findOne(employeeId);
		if( employee == null )
			throw new ActionFailureException(ErrorCode.USER_NOT_FOUND);

		employee.setActive(false);
		employeeRepo.save(employee);
		isInActivated = true;
		return isInActivated;
	}

}