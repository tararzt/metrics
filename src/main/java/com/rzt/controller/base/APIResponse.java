package com.rzt.controller.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonRootName( "apiResponse" )
public class APIResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3641100020390748804L;

	private Object data;
	private String apiPath;
	private Boolean serviced = true;
	private String errorCode;
	private Map<String, Object> logs;

	public Object getData()
	{
		return data;
	}

	public void setData( Object data )
	{
		this.data = data;
	}

	public String getApiPath()
	{
		return apiPath;
	}

	public void setApiPath( String apiPath )
	{
		this.apiPath = apiPath;
	}

	public Boolean getServiced()
	{
		return serviced;
	}

	public void setServiced( Boolean serviced )
	{
		this.serviced = serviced;
	}

	public String getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode( String errorCode )
	{
		this.errorCode = errorCode;
	}

	public Map<String, Object> getLogs()
	{
		return logs;
	}

	public void setLogs( Map<String, Object> logs )
	{
		this.logs = logs;
	}

	public void setLogParameter( String key, Object value )
	{
		if( logs == null )
		{
			logs = new HashMap<String, Object>();
		}
		logs.put(key, value);
	}

}
