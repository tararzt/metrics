package com.rzt.enums;

public enum EmailCode {
		TO_USER_NAME("|:to_user_name:|"), HOST_URL("|:host_url:|"), CURRENT_DATETIME("|:current_datetime:|"),
		USER_ID("|:user_id:|"), PASSWORD_RESET_TOKEN("|:password_reset_token:|"),
		TO_USER_EMAIL_ID("|:to_user_email_id:|"), USER_FIRST_TIME_LOGIN_KEY("|:user_firsttime_login_key:|");

	private String code;

	EmailCode( String code )
	{
		this.code = code;
	}

	public String getCode()
	{
		return this.code;
	}
}
