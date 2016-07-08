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
import com.rzt.schema_pojo.Employee;
import com.rzt.service.EmployeeDao;
import com.rzt.service.repository.EmployeeRepo;

@Controller
@RequestMapping( "/employee" )
public class EmployeeController extends BaseController {

	Logger LOGGER = Logger.getLogger(EmployeeController.class);

	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	EmployeeDao employeeDao;

	/**
	 * Add Employee
	 * 
	 * @param employee
	 * @return
	 */
	@RequestMapping( value = "/create", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.PUT )
	@ResponseBody
	public String createEmployee( @RequestBody Employee employee )
	{
		response = new APIResponse();
		try
		{
			employee = employeeRepo.save(employee);
		}
		catch( Exception e )
		{
			// TODO Auto-generated catch block
			response.setServiced(false);
			LOGGER.error(e);
			e.printStackTrace();
		}
		response.setData(employee);
		return jsonStringifyResponse();
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
		response = new APIResponse();
		List<Employee> employees = null;
		try
		{
			employees = employeeRepo.findAll();
		}
		catch( ActionFailureException e )
		{
			handleActionFailureException(e);
			response.setServiced(false);
			LOGGER.error(e);
			e.printStackTrace();
		}
		catch( Exception e )
		{
			response.setServiced(false);
			LOGGER.error(e);
			e.printStackTrace();
		}
		response.setData(employees);
		return jsonStringifyResponse();

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
		response = new APIResponse();
		try
		{
			//employee = employeeDao.update(employee);
			employee = employeeRepo.save(employee);
		}
		catch( Exception e )
		{
			response.setServiced(false);
			LOGGER.error(e);
			e.printStackTrace();
		}
		response.setData(employee);
		return jsonStringifyResponse();
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
		response = new APIResponse();
		Employee employee = null;
		try
		{
			employee = employeeRepo.findOne(id);
		}
		catch( Exception e )
		{
			response.setServiced(false);
			LOGGER.error(e);
			e.printStackTrace();
		}
		response.setData(employee);
		return jsonStringifyResponse();

	}

	/**
	 * get Employee
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping( value = "/delete/{id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.DELETE )
	@ResponseBody
	public String deleteEmployee( @PathVariable Integer id )
	{
		response = new APIResponse();
		Employee employee = new Employee();
		employee.setId(id);
		try
		{
			employeeRepo.delete(employee);
		}
		catch( Exception e )
		{
			response.setServiced(false);
			LOGGER.error(e);
			e.printStackTrace();
		}
		return jsonStringifyResponse();

	}

}
