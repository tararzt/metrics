/**
 * 
 */
package com.rzt.exception;

/**
 * This exception will be thrown in conditions where the error is a recoverable one i.e. the
 * application does not need to be frozen
 * 
 * @author Deepak
 */
public class ApplicationFailureException extends ActionFailureException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3182933581101481562L;

	public ApplicationFailureException( String message )
	{
		super(message);
	}

}
