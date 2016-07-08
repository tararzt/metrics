/**
 * 
 */
package com.rzt.exception;

import java.util.List;
import com.rzt.utils.ErrorCode;

/**
 * This exception will be thrown in conditions where the error is a recoverable one i.e. the
 * application does not need to be frozen
 * 
 * @author Deepak
 */
public class InvalidInputException extends InsufficientInputException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5022221963166706011L;

	public InvalidInputException( String inputField )
	{
		super(inputField, ErrorCode.INVALID_INPUT);
	}

	public InvalidInputException( List<String> fields )
	{
		super(fields);
	}

}
