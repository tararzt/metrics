package com.rzt.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rzt.dao.EmployeeDao;
import com.rzt.exception.ActionFailureException;
import com.rzt.schemapojo.Employee;
import com.rzt.service.repository.EmployeeRepo;

@Service
public class EmployeeDaoImpl implements EmployeeDao {

	@Autowired
	EmployeeRepo employeeRepo;

	/**
	 * Update Employee Details if the employee is found in the system
	 */
	public Employee update( Employee employee )
	{
		try
		{
			Employee current = employeeRepo.findOne(employee.getId());
			if( current != null )
			{
				employee = employeeRepo.save(employee);
			}
			else
			{
				throw new ActionFailureException("Employee not found!");
			}

		}
		catch( Exception e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return employee;
	}

}
