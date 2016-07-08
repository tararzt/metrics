package com.rzt.schema_pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table( name = "role_to_permissions" )
public class RoleToPermissions {

	Integer id;
	Role role;
	Permission permission;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "id" )
	public Integer getId()
	{
		return id;
	}

	public void setId( Integer id )
	{
		this.id = id;
	}

	@ManyToOne
	@JoinColumn( name = "role_id", referencedColumnName = "id" )
	public Role getRole()
	{
		return role;
	}

	public void setRole( Role role )
	{
		this.role = role;
	}

	@ManyToOne
	@JoinColumn( name = "permission_id", referencedColumnName = "id" )
	public Permission getPermission()
	{
		return permission;
	}

	/**
	 * @param permission
	 *            the permission to set
	 */
	public void setPermission( Permission permission )
	{
		this.permission = permission;
	}

}
