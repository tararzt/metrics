/**
 * 
 */
package com.rzt.exception;

import java.util.ArrayList;
import java.util.List;
import com.rzt.utils.ErrorCode;
import com.rzt.utils.StringHelper;

/**
 * This exception will be thrown in conditions where the error is a recoverable one i.e. the
 * application does not need to be frozen
 * 
 * @author Deepak
 */
public class InsufficientInputException extends Exception {

	private static final long serialVersionUID = -9140142714858056994L;

	List<String> fields = new ArrayList<String>();
	String errorMessage;

	public InsufficientInputException( String inputField, String errorMessage )
	{
		super(errorMessage);
		if( StringHelper.isNullOrNullString(errorMessage) )
		{
			errorMessage = "Can not be blank";
		}
		fields.add(inputField);

	}

	public InsufficientInputException( List<String> fields )
	{
		super(ErrorCode.INSUFFICIENT_INPUT);
		this.fields = fields;
		this.errorMessage = ErrorCode.INSUFFICIENT_INPUT;
	}

	public InsufficientInputException()
	{
		super(ErrorCode.INSUFFICIENT_INPUT);
	}

	public List<String> getFields()
	{
		return fields;
	}

	public void setFields( List<String> fields )
	{
		this.fields = fields;
	}

}
