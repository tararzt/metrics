package com.rzt.google;

import java.io.IOException;
import java.security.SecureRandom;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.rzt.utils.JSONUtil;

/**
 * 
 * @author deepak Spring version of the GoogleAuthHelper. This makes use of the google client api
 *         provided to make calls to the Google API.
 */
@Component
public class GoogleApiHelper {

	@Autowired
	private GoogleApiAuthConfig apiConfig;

	/**
	 * Callback URI that google will redirect to after successful authentication
	 */

	private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo";
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	private GoogleAuthorizationCodeFlow flow;

	/**
	 * Constructor initializes the Google Authorization Code Flow with CLIENT ID, SECRET, and SCOPE
	 * 
	 * @throws CommonException
	 */
	public GoogleApiHelper()
	{
		generateStateToken();
	}

	@PostConstruct
	public void setFlow()
	{
		flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, apiConfig.getClientId(),
				apiConfig.getClientSecret(), apiConfig.getScope()).build();
	}

	/**
	 * Builds a login URL based on client ID, secret, callback URI, and scope
	 */
	public String getLoginUrl()
	{

		String stateToken = generateStateToken();
		final GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
		return url.setRedirectUri(apiConfig.getRedirectUri()).setState(stateToken).setAccessType("offline").build();
	}

	/**
	 * Generates a secure state token
	 */
	private static String generateStateToken()
	{
		SecureRandom sr1 = new SecureRandom();
		return "google;" + sr1.nextInt();

	}

	/**
	 * Expects an Authentication Code, and makes an authenticated request for the user's profile
	 * information
	 * 
	 * @return JSON formatted user profile information
	 * @param authCode
	 *            authentication code provided by google
	 */
	public GoogleUserInfo getUserInfoJson( final String authCode ) throws IOException
	{

		final GoogleTokenResponse response = flow.newTokenRequest(authCode).setRedirectUri(apiConfig.getRedirectUri())
				.execute();

		final Credential credential = flow.createAndStoreCredential(response, null);
		credential.refreshToken();
		final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);
		// Make an authenticated request
		final GenericUrl url = new GenericUrl(USER_INFO_URL);
		final HttpRequest request = requestFactory.buildGetRequest(url);
		request.getHeaders().setContentType("application/json");

		final String jsonIdentity = request.execute().parseAsString();
		GoogleUserInfo info = JSONUtil.convertStringToObject(jsonIdentity, GoogleUserInfo.class);
		info.setAccessToken(response.getAccessToken());
		info.setRefreshToken(response.getRefreshToken());

		return info;
	}

}
