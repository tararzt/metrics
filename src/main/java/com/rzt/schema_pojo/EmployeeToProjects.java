package com.rzt.schema_pojo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table( name = "employee_to_project" )
public class EmployeeToProjects {

	Integer id;
	Employee employee;
	Project project;
	Integer roleId;
	Float billingAmount;
	Boolean is_active;
	Date startDate;
	Date endDate;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "id", nullable = false, unique = true )
	public Integer getId()
	{
		return id;
	}

	public void setId( Integer id )
	{
		this.id = id;
	}

	@ManyToOne
	@JoinColumn( name = "employee_id", referencedColumnName = "id" )
	public Employee getEmployee()
	{
		return employee;
	}

	public void setEmployee( Employee employee )
	{
		this.employee = employee;
	}

	@ManyToOne
	@JoinColumn( name = "project_id", referencedColumnName = "id" )
	public Project getProject()
	{
		return project;
	}

	public void setProject( Project project )
	{
		this.project = project;
	}

	@Column( name = "employee_role_id" )
	public Integer getRoleId()
	{
		return roleId;
	}

	public void setRoleId( Integer roleId )
	{
		this.roleId = roleId;
	}

	@Column( name = "billing_amount" )
	public Float getBillingAmount()
	{
		return billingAmount;
	}

	public void setBillingAmount( Float billingAmount )
	{
		this.billingAmount = billingAmount;
	}

	@Column( name = "is_active" )
	public Boolean getIs_active()
	{
		return is_active;
	}

	public void setIs_active( Boolean is_active )
	{
		this.is_active = is_active;
	}

	@Column( name = "start_date" )
	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate( Date startDate )
	{
		this.startDate = startDate;
	}

	@Column( name = "end_date" )
	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate( Date endDate )
	{
		this.endDate = endDate;
	}

}
