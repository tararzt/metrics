package com.rzt.jpa;

import java.io.Serializable;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public abstract class AbstractRepositoryImpl<T extends Serializable, I extends Serializable>
		extends SimpleJpaRepository<T, I> implements GenericRepository<T, I> {

	public AbstractRepositoryImpl( Class<T> domainClass, EntityManager em )
	{
		super(domainClass, em);

	}

	public AbstractRepositoryImpl( JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager )
	{
		super(entityInformation, entityManager);

	}
}
