package com.rzt.utils;

public class ErrorCode {

	public static final String MISSING_MANDATORY_FIELD = "missing.mandatory.field";
	public static final String DUPLICATE = "duplicate";
	public static final String IN_VALID = "invalid";
	public static final String LOGIN_FAILURE = "login.fail";
	public static final String REGISTRATION_FAILURE = "registration.fail";

	public static final String INVALID_CREDENTIALS = "invalid_credentials";
	public static final String USER_NOT_FOUND = "Employee not found";
	public static final String REQUIRES_LINKING = "REQUIRES_LINKING";
	public static final String GOOGLE = "Google";
	public static final String FACEBOOK = "Facebook";
	public static final String REQUIRES_ACTIVATION = "REQUIRES_ACTIVATION";
	public static final String INSUFFICIENT_INPUT = "Insufficient Input";
	public static final String DUPLICATE_EMAIL = "DUPLICATE_EMAIL";
	public static final String DUPLICATE_USER = "Duplicate User";
	public static final String INVALID_INPUT = "Invalid Input";

	public static final String NO_USER_LOGGED_IN = "User not found";
	public static final String ACTIVE_EMPLOYEE_PRESENT = "An active employee is already present with the same emailId";
	public static final String USER_NOT_LOGGED_IN = "User is not Logged in!";
	public static final String CLIENT_PROJECT_PRESENT = "Project Already present for the same Client";
	public static final String PROJECT_NOT_PRESENT = "Project not Present";

	public static final String COMMON_EXCEPTION = "Exception has Occured!";

	private ErrorCode()
	{
	}
}
