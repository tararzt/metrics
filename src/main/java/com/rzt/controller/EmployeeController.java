package com.rzt.controller;

import java.util.List;
import org.slf4j.LoggerFactory;
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
import com.rzt.dao.repository.EmployeeRepo;
import com.rzt.exception.ActionFailureException;
import com.rzt.exception.InsufficientInputException;
import com.rzt.schemapojo.Employee;
import com.rzt.service.EmployeeService;

/**
 * Controller to server the requests related to Employee
 */
@Controller
@RequestMapping( "/employee" )
public class EmployeeController extends BaseController {

	static final org.slf4j.Logger logger = LoggerFactory.getLogger(EmployeeController.class);

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
		logger.info("Received Request to Add a new Employee...");
		APIResponse response = new APIResponse();
		try
		{
			Employee employeeAdded = employeeService.addEmployee(employee);
			response.setData(employeeAdded);
			logger.info("Sending back the response to Add a new Employee");

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
	@RequestMapping( value = "/active-employees", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET )
	@ResponseBody
	public String getAllActiveEmployees()
	{
		logger.info("Received Request to get all Active Employees...");
		APIResponse response = new APIResponse();
		List<Employee> employees = null;
		try
		{
			employees = employeeService.getActiveEmployees();
			response.setData(employees);
			logger.info("Sending back the response to get all Active employees");
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
		logger.info("Received Request get all Employees...");

		APIResponse response = new APIResponse();
		List<Employee> employees = null;
		try
		{
			employees = employeeService.getAllEmployees();
			response.setData(employees);
			logger.info("logger.info(\"Sending back the response to get all employees");
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
		logger.info("Received Request to update an Employee...");
		APIResponse response = new APIResponse();
		try
		{
			Employee updatedEmployee = employeeService.updateEmployee(employee);
			response.setData(employee);
			logger.info("Sending back the response to update new Employee reuqest");

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
	 * Get Employee details
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping( value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET )
	@ResponseBody
	public String getEmployee( @PathVariable Integer id )
	{
		logger.info("Received Request to get an employee details...");
		APIResponse response = new APIResponse();
		try
		{
			Employee employee =  employeeService.getEmployee(id);
			response.setData(employee);
			logger.info("Sending back the response to get employee details");

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
		logger.info("Received Request to delete an Employee...");
		APIResponse response = new APIResponse();
		try
		{
			response.setData(employeeService.delete(id));
			logger.info("Sending back the response to delete an Employee");
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
	@RequestMapping( value = "/inactivate/{id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.DELETE )
	@ResponseBody
	public String inActivateEmployee( @PathVariable Integer id )
	{
		logger.info("Received Request to inactivate an Employee...");
		APIResponse response = new APIResponse();
		try
		{
			response.setData(employeeService.inActivate(id));
			logger.info("Sending back the response to inActivat an Employee");
		}
		catch( Exception e )
		{
			handleGlobalException(response, e);
		}
		return jsonStringifyResponse(response);

	}

}
