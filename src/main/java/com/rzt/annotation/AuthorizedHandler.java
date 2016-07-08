package com.rzt.annotation;

import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

/**
 * This is an annotation handler that will be fed to Jackson so that, any field which is annotated
 * with a particular annotation will honor the rule mentioned in the include method.
 * 
 * Jackson includes the field annotated into the JSON string only if the condition mentioned in the
 * include is met.
 * 
 * @author deepak
 *
 */
public class AuthorizedHandler extends SimpleBeanPropertyFilter {

	private Boolean authorized = false;

	public AuthorizedHandler()
	{

	}

	public AuthorizedHandler( Boolean authorized )
	{
		this.authorized = authorized;
	}

	@Override
	protected boolean include( BeanPropertyWriter writer )
	{
		// deprecated since 2.3
		return true;
	}

	@Override
	protected boolean include( PropertyWriter writer )
	{
		if( writer instanceof BeanPropertyWriter )
		{
			return authorized || writer.getAnnotation(Authorized.class) == null;
		}
		return true;
	}
}
