package com.rzt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import com.rzt.schemapojo.EmailTemplate;

/**
 * Repository which Offers Crud Operations of Email Template
 */
@Transactional
public interface EmailTemplateRepo extends CrudRepository<EmailTemplate, Integer> {

	@Override
	public EmailTemplate findOne( Integer id );


	/**
	 * Find Email Template by Display Name
	 * @param name
	 * @return
     */
	public EmailTemplate findByDisplayName( String name );


	/**
	 * Find Email Template by Internal Name
	 * @param name
	 * @return
     */
	public EmailTemplate findByInternalName( String name );


	/**
	 * Find Email Template by Subject
	 * @param name
	 * @return
     */
	public EmailTemplate findBySubject( String name );

}
