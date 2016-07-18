package com.rzt.junit;

import com.rzt.exception.InsufficientInputException;
import com.rzt.schemapojo.Employee;
import com.rzt.schemapojo.Project;
import com.rzt.service.EmployeeService;
import com.rzt.service.impl.EmployeeServiceImpl;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by tara on 7/18/16.
 */


public class ProjectTest {


    EmployeeService employeeService = null;
    Employee employee = null;


    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void initializeAddEmployee(){
        employeeService = new EmployeeServiceImpl();
        employee = new Employee();
        employee.setName("Tara SUdarshan");
        employee.setEmail("tara.sudarshan@razorthink.com");
    }


    @Test
    public void throwInsufficientInputExceptionIfaddEmployeeInputIsNull() throws InsufficientInputException {
       // exception.expect(InsufficientInputException.class);
       // employeeService.addEmployee(null);
    }

    @Test
    public void AddEmployeeAndGetAddedEmployee() throws InsufficientInputException {
       // employeeService.addEmployee(employee);
    }

}
