package com.rzt.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import com.rzt.schemapojo.UserCookie;

/**
 * Created by tara on 7/12/16.
 */
@Transactional
public interface UserCookieRepo extends CrudRepository<UserCookie, Integer> {

	/**
	 * Find Cookie by Cookie String
	 * 
	 * @param cookie
	 * @return
	 */
	public UserCookie findByCookie( String cookie );

	/**
	 * Find Cookie by Cookie String and Employee Id
	 * 
	 * @param cookie
	 * @param employeeId
	 * @return
	 */
	public UserCookie findByCookieAndEmployeeId( String cookie, Integer employeeId );

	/**
	 *
	 * @param userCookie
	 */
	public void delete( UserCookie userCookie );

}
