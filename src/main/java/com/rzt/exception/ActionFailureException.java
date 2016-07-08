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
public class ActionFailureException extends RuntimeException {

	private static final long serialVersionUID = -9140142714858056994L;
	private boolean logException = true;
	private Object value;

	public ActionFailureException()
	{
		super();
	}

	public ActionFailureException( String message )
	{
		super(message);
	}

	public ActionFailureException( String message, Object value )
	{
		super(message);
		this.value = value;
	}

	public ActionFailureException( String message, boolean logException, Object value )
	{
		super(message);
		this.logException = logException;
		this.value = value;
	}

	public ActionFailureException( String message, boolean logException )
	{
		super(message);
		this.logException = logException;
	}

	public ActionFailureException( Throwable nested )
	{
		super(nested);
	}

	public ActionFailureException( String message, Throwable nested )
	{
		super(message, nested);
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue( Object value )
	{
		this.value = value;
	}

	/**
	 * @return the logException
	 */
	public boolean isLogException()
	{
		return logException;
	}

	/**
	 * @param logException
	 *            the logException to set
	 */
	public void setLogException( boolean logException )
	{
		this.logException = logException;
	}

}
