package com.rzt.controller;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.rzt.controller.base.APIResponse;
import com.rzt.controller.base.BaseController;
import com.rzt.exception.ActionFailureException;
import com.rzt.exception.InsufficientInputException;
import com.rzt.schemapojo.Employee;
import com.rzt.service.EmployeeService;
import com.rzt.repository.EmployeeRepo;

/**
 * Controller to server the requests related to Employee
 */
@Controller
@RequestMapping( "/employee" )
public class EmployeeController extends BaseController {

	Logger logger = Logger.getLogger(EmployeeController.class);

	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	EmployeeService employeeService;

	/**
	 * Add Employee
	 * 
	 * @param employee
	 * @return
	 */
	@RequestMapping( value = "/create", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.PUT )
	@ResponseBody
	public String addEmployee( @RequestBody Employee employee )
	{
		logger.info("Adding a new Employee...");
		APIResponse response = new APIResponse();
		try
		{
			Employee employeeAdded = employeeService.addEmployee(employee);
			response.setData(employeeAdded);
			logger.info("Adding a new Employee " + employeeAdded.getEmail() + " is completed");

		}
		catch( ActionFailureException e )
		{
			handleActionFailureException(response, e);
		}
		catch( InsufficientInputException e )
		{
			handleInsufficientInputException(response, e);
		}
		catch( Exception e )
		{
			handleGlobalException(response, e);
		}
		return jsonStringifyResponse(response);
	}

	/**
	 * Get all Employees
	 * 
	 * @return
	 */
	@RequestMapping( value = "/employees", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET )
	@ResponseBody
	public String getAllEmployees()
	{
		logger.info("Getting all Active Employees...");
		APIResponse response = new APIResponse();
		List<Employee> employees = null;
		try
		{
			employees = employeeService.getActiveEmployees();
			response.setData(employees);
			logger.info("Getting all Active Employees completed.");
		}
		catch( Exception e )
		{
			handleGlobalException(response, e);
		}
		return jsonStringifyResponse(response);

	}

	/**
	 * Update employee details
	 * 
	 * @param employee
	 * @return
	 */
	@RequestMapping( value = "/update", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST )
	@ResponseBody
	public String updateEmployee( @RequestBody Employee employee )
	{
		logger.info("Updating Employee Deatils...");
		APIResponse response = new APIResponse();
		try
		{
			Employee updatedEmployee = employeeService.updateEmployee(employee);
			response.setData(employee);
			logger.info("Updating Employee " + updatedEmployee.getEmail() + " Deatils Completed.");

		}
		catch( ActionFailureException e )
		{
			handleActionFailureException(response, e);
		}
		catch( InsufficientInputException e )
		{
			handleInsufficientInputException(response, e);
		}
		catch( Exception e )
		{
			handleGlobalException(response, e);
		}

		return jsonStringifyResponse(response);
	}

	/**
	 * Delete Employee
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping( value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET )
	@ResponseBody
	public String getEmployee( @PathVariable Integer id )
	{
		APIResponse response = new APIResponse();
		Employee employee = null;
		try
		{
			employee = employeeRepo.findOne(id);
			response.setData(employee);

		}
		catch( Exception e )
		{
			handleGlobalException(response, e);
		}
		return jsonStringifyResponse(response);

	}

	/**
	 * delete Employee
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping( value = "/delete/{id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.DELETE )
	@ResponseBody
	public String deleteEmployee( @PathVariable Integer id )
	{
		logger.info("Inactivating an Employee with the Id " + id + " ...");
		APIResponse response = new APIResponse();
		try
		{
			response.setData(employeeService.delete(id));
			logger.info("Inactivating an Employee is Completed.");
		}
		catch( Exception e )
		{
			handleGlobalException(response, e);
		}
		return jsonStringifyResponse(response);

	}

}
