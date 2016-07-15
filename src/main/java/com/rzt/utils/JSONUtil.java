package com.rzt.utils;

import java.io.IOException;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzt.exception.ActionFailureException;

/**
 * 
 * Utility class that provides functionality for converting between Java objects and matching JSON
 * constructs.
 * 
 */

public class JSONUtil {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JSONUtil.class);

	private JSONUtil()
	{
	}

	private static ObjectMapper getObjectMapper()
	{
		ObjectMapper jsonMapper = new ObjectMapper();
		jsonMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		return jsonMapper;
	}

	/**
	 * Method used to serialize any java object as a String.
	 * 
	 * @param t
	 * @return String
	 */
	@Deprecated
	public static <T> String convertObjectToString( T t )
	{

		try
		{
			return getObjectMapper().writeValueAsString(t);
		}
		catch( IOException e )
		{
			logger.error("Json conversion failed", e);
			throw new ActionFailureException(e.getMessage(), false);
		}
	}

	/**
	 * Method used to serialize any java object as a String.
	 * 
	 * @param t
	 * @return String
	 */
	public static <T> String convertObjectToString( T t, Boolean authorized )
	{

		try
		{
			return getObjectMapper().writeValueAsString(t);
		}
		catch( IOException e )
		{
			logger.error("Json conversion failed", e);
			throw new ActionFailureException(e.getMessage(), false);
		}
	}

	/**
	 * Method used to deserialize String to specified object.
	 * 
	 * @param jsonString
	 * @param classType
	 * @return <T>
	 */
	public static <T> T convertStringToObject( String jsonString, Class<T> classType )
	{
		try
		{
			T returnValue = null;
			if( !StringHelper.isNullOrNullString(jsonString) )
			{
				returnValue = getObjectMapper().readValue(jsonString, classType);
			}
			return returnValue;
		}
		catch( IOException e )
		{
			logger.error("Json conversion failed", e);
			throw new ActionFailureException(e.getMessage(), false);
		}
	}

	/**
	 * Convenience method for doing two-step conversion from given value, into instance of given
	 * value type.
	 * 
	 * @param <T>
	 * @param <U>
	 * @return <U>
	 */
	public static <T, U> U convertObject( T t, Class<U> classType )
	{
		return getObjectMapper().convertValue(t, classType);
	}
}
