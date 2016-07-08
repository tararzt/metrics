/**
 * 
 */
package com.rzt.exception;

import java.util.HashMap;
import java.util.Map;
import com.rzt.utils.StringHelper;

/**
 * This exception will be thrown in conditions where the error is a recoverable one i.e. the
 * application does not need to be frozen
 * 
 * @author Deepak
 */
public class AccessFailureException extends RuntimeException {

	private static final long serialVersionUID = -9140142714858056994L;
	private String inputField;
	private String errorMessage;

	public AccessFailureException()
	{

	}

	public AccessFailureException( String inputField, String errorMessage )
	{
		super();
		this.inputField = inputField;
		this.errorMessage = errorMessage;
	}

	public String getInputField()
	{
		return inputField;
	}

	public void setInputField( String inputField )
	{
		this.inputField = inputField;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage( String errorMessage )
	{
		this.errorMessage = errorMessage;
	}

	public Map<String, String> getResponseEntry()
	{
		Map<String, String> ret = new HashMap<String, String>();

		if( StringHelper.isNullOrNullString(errorMessage) )
		{
			errorMessage = "Can not be blank";
		}
		ret.put(inputField, errorMessage);
		return ret;
	}
}
