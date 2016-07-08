package com.rzt.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import com.rzt.exception.ActionFailureException;

public class HTTPUtil {

	private static HttpClient httpClient = HttpClientBuilder.create().build();

	private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.LogManager
			.getLogger(HTTPUtil.class.getName());

	private static final String NO_SERVER_RESPONSE = "Unable to get a response from server";
	private static final String SERVER_RESPONSE = "Server responded with status code ";

	/**
	 * 
	 */
	private HTTPUtil()
	{
		super();
	}

	/**
	 * Validate http response
	 * 
	 * @param response
	 * @return
	 */
	private static boolean validateHttpReponse( HttpResponse response )
	{
		StatusLine statusLine = response.getStatusLine();
		if( statusLine == null )
		{
			LOGGER.error(NO_SERVER_RESPONSE);
			throw new ActionFailureException(NO_SERVER_RESPONSE);
		}

		int statusCode = statusLine.getStatusCode();
		if( statusCode < 200 || statusCode >= 300 )
		{
			LOGGER.error(SERVER_RESPONSE + statusCode + " : " + statusLine);
			throw new ActionFailureException(SERVER_RESPONSE + statusCode + " : " + statusLine);
		}

		return true;
	}

	/**
	 * Get http post object with all parameters added to post request
	 * 
	 * @param url
	 * @param requestParams
	 * @param headers
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static <T> HttpPost getHttpPostRequest( String url, T requestParams, Map<String, String> headers )
			throws UnsupportedEncodingException
	{
		HttpPost postRequest = new HttpPost(url);

		if( null != headers && !headers.isEmpty() )
		{
			for( Entry<String, String> e : headers.entrySet() )
			{
				postRequest.addHeader(e.getKey(), e.getValue());
			}
		}

		if( requestParams != null )
		{
			postRequest.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
			StringEntity params = new StringEntity(JSONUtil.convertObjectToString(requestParams, true));
			postRequest.setEntity(params);
		}

		return postRequest;
	}

	/**
	 * Custom http post method using HttpClient, reads the response and returns response header
	 * 
	 * @param <T>
	 * 
	 * @param url
	 *            : URL
	 * @param requestParams
	 *            : POST request parameters
	 * @param classType
	 *            : Return type for the response
	 * @param headers
	 *            : Optional headers for request
	 * @return <U>
	 */
	public static <T> Map<String, String> doPost( String url, T requestParams, Map<String, String> headers )
	{
		try
		{
			Map<String, String> responseHeader = new LinkedHashMap<String, String>();

			HttpPost postRequest = getHttpPostRequest(url, requestParams, headers);

			HttpResponse response = httpClient.execute(postRequest);
			validateHttpReponse(response);

			if( response.getAllHeaders() != null )
			{
				for( Header header : response.getAllHeaders() )
				{
					responseHeader.put(header.getName(), header.getValue());
				}
			}

			return responseHeader;

		}
		catch( Exception e )
		{
			throw new ActionFailureException(e);
		}
	}

	/**
	 * 
	 * Executes http post method using HttpClient, reads the response and converts response to json
	 * representation object specified
	 * 
	 * @param url
	 *            : URL
	 * @param requestParams
	 *            : POST request parameters
	 * @param classType
	 *            : Return type for the response
	 * @param headers
	 *            : Optional headers for request
	 * @return <U>
	 */
	public static <T, U> U doPost( String url, T requestParams, Class<U> classType, Map<String, String> headers )
	{
		try
		{
			HttpPost postRequest = getHttpPostRequest(url, requestParams, headers);

			HttpResponse response = httpClient.execute(postRequest);
			validateHttpReponse(response);

			return convertReponseToObject(response, classType);
		}
		catch( Exception e )
		{
			throw new ActionFailureException(e);
		}
	}

	/**
	 * Executes http put method using HttpClient, reads the response and converts response to json
	 * representation object specified
	 * 
	 * @param url
	 * @param requestParams
	 * @param headers
	 * @return
	 */
	public static <T> Boolean doPut( String url, T requestParams, Map<String, String> headers )
	{
		try
		{
			HttpPut putRequest = new HttpPut(url);

			if( null != headers && !headers.isEmpty() )
			{
				for( Entry<String, String> e : headers.entrySet() )
				{
					putRequest.addHeader(e.getKey(), e.getValue());
				}
			}

			if( requestParams != null )
			{
				putRequest.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
				StringEntity params = new StringEntity(JSONUtil.convertObjectToString(requestParams, true));
				putRequest.setEntity(params);
			}

			HttpResponse response = httpClient.execute(putRequest);
			boolean isValid = validateHttpReponse(response);

			return isValid;
		}
		catch( Exception e )
		{
			throw new ActionFailureException(e);
		}
	}

	/**
	 * Executes http get method using HttpClient, reads the response and converts response to json
	 * representation object specified
	 * 
	 * @param url
	 *            : URL
	 * @param classType
	 *            : Return type for the response
	 * @param headers
	 *            : Optional headers for request
	 * @return <T>
	 */
	public static <T> T doGet( String url, Class<T> classType, Map<String, String> headers )
	{
		try
		{
			HttpGet request = new HttpGet(url);

			if( null != headers && !headers.isEmpty() )
			{
				for( Entry<String, String> e : headers.entrySet() )
				{
					request.addHeader(e.getKey(), e.getValue());
				}
			}

			HttpResponse response = httpClient.execute(request);
			validateHttpReponse(response);

			return convertReponseToObject(response, classType);
		}
		catch( Exception e )
		{
			throw new ActionFailureException(e);
		}
	}

	/**
	 * Method that reads the response content into buffer and converts it to specified json
	 * representation object (classType)
	 * 
	 */
	private static <T> T convertReponseToObject( HttpResponse response, Class<T> classType ) throws IOException
	{
		InputStream contentStream = null;
		String responseBody = null;

		HttpEntity responseEntity = response.getEntity();
		contentStream = responseEntity.getContent();

		Reader isReader = new InputStreamReader(contentStream, "UTF-8");
		int contentSize = (int) responseEntity.getContentLength();
		if( contentSize < 0 )
		{
			contentSize = 8 * 1024;
		}

		StringWriter strWriter = new StringWriter(contentSize);
		char[] buffer = new char[8 * 1024];
		int n = 0;
		while( (n = isReader.read(buffer)) != -1 )
		{
			strWriter.write(buffer, 0, n);
		}

		responseBody = strWriter.toString();
		isReader.close();
		contentStream.close();

		return JSONUtil.convertStringToObject(responseBody, classType);
	}

	/**
	 * Executes http delete method using HttpClient
	 * 
	 * @param queryUrl
	 * @param headers
	 * @return
	 */
	public static boolean doDelete( String queryUrl, Map<String, String> headers )
	{
		try
		{
			HttpDelete deleteRequest = new HttpDelete(queryUrl);

			if( null != headers && !headers.isEmpty() )
			{
				for( Entry<String, String> e : headers.entrySet() )
				{
					deleteRequest.addHeader(e.getKey(), e.getValue());
				}
			}

			HttpResponse response = httpClient.execute(deleteRequest);
			boolean isValid = validateHttpReponse(response);

			return isValid;
		}
		catch( Exception e )
		{
			throw new ActionFailureException(e);
		}
	}
}
