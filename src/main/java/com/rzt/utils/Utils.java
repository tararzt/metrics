package com.rzt.utils;

import com.rzt.exception.InsufficientInputException;

public class Utils {

	/**
	 * Check if the Mandatory Inputs are Present
	 * 
	 * @param objects
	 * @throws InsufficientInputException
	 */
	public static void mandatroyInputCheck( Object... objects ) throws InsufficientInputException
	{

		if( objects == null )
			throw new InsufficientInputException();

		for( Object object : objects )
		{
			if( object == null )
				throw new InsufficientInputException();

			if( object instanceof String && StringHelper.isNullOrNullString(object.toString()) )
				throw new InsufficientInputException();

		}
	}

}
