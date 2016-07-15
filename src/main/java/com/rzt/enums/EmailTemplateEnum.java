package com.rzt.enums;

public enum EmailTemplateEnum {
		PASSWORD_RESET_REQUEST_EMAIL("PASSWORD_RESET_REQUEST_EMAIL"), WELCOME_EMAIL("WELCOME_EMAIL");

	private String name;

	EmailTemplateEnum( String name )
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}
}
