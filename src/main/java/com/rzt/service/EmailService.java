package com.rzt.service;

import java.util.Map;
import com.rzt.schemapojo.Employee;

/**
 * Created by tara on 7/11/16.
 */
public interface EmailService {

	/**
	 *
	 * @param toUser
	 * @param etName
	 * @param attributes
	 */
	void sendEmail( Employee toUser, String etName, Map<String, Object> attributes );
}
