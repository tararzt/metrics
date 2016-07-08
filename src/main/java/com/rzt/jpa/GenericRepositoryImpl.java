package com.rzt.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

public class GenericRepositoryImpl<T extends Serializable, I extends Serializable> extends AbstractRepositoryImpl<T, I>
		implements GenericRepository<T, I> {

	@Autowired
	private EntityManagerFactory emf;

	private EntityManager em;

	public GenericRepositoryImpl( Class<T> domainClass, EntityManager em )
	{
		super(domainClass, em);
		this.setEm(em);
	}

	public GenericRepositoryImpl( JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager )
	{
		super(entityInformation, entityManager);
		this.setEm(entityManager);
	}

	public EntityManagerFactory getEntityManagerFactory()
	{
		return emf;
	}

	/**
	 * 
	 * @param queryName
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */

	@SuppressWarnings( "rawtypes" )
	public List findByNamedQueryAndNamedParams( final String queryName, final Map<String, ?> params )

	{
		Query queryObject = em.createNamedQuery(queryName);
		if( params != null )
		{
			for( Map.Entry<String, ?> entry : params.entrySet() )
			{
				queryObject.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return queryObject.getResultList();

	}

	public Object findByEntityNamedQueryAndNamedParams( final String queryName, final Map<String, ?> params )

	{
		Query queryObject = em.createNamedQuery(queryName);
		if( params != null )
		{
			for( Map.Entry<String, ?> entry : params.entrySet() )
			{
				queryObject.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return queryObject.getSingleResult();

	}

	/**
	 * 
	 * @param queryName
	 * @param params
	 * @return
	 * @throws Exception
	 * @throws DataAccessException
	 */
	@SuppressWarnings( "unchecked" )
	public T findEntityByNamedQueryAndNamedParams( final String queryName, final Map<String, ?> params )

	{
		try
		{
			Query queryObject = em.createNamedQuery(queryName);
			if( params != null )
			{
				for( Map.Entry<String, ?> entry : params.entrySet() )
				{
					queryObject.setParameter(entry.getKey(), entry.getValue());
				}
			}
			return (T) queryObject.getSingleResult();
		}
		catch( NoResultException ne )
		{
			ne.printStackTrace();
			return null;
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return null;

		}

	}

	@SuppressWarnings( "rawtypes" )
	public List findByNamedQuery( String queryString, Class<T> clas )
	{

		try
		{
			Query queryObject = em.createNamedQuery(queryString, clas);

			return queryObject.getResultList();
		}

		catch( Exception e )
		{
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings( "rawtypes" )
	public List findByNativeQuery( String queryString, Class<?> class1 )
	{

		try
		{
			Query queryObject = em.createNativeQuery(queryString, class1);

			return queryObject.getResultList();
		}

		catch( Exception e )
		{
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings( "rawtypes" )
	public List findByNativeQuery( String queryString )
	{
		try
		{
			Query queryObject = em.createNativeQuery(queryString);

			return queryObject.getResultList();
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings( "rawtypes" )
	public List findByNamedQuery( String queryString )
	{
		try
		{
			Query queryObject = em.createNamedQuery(queryString);

			return queryObject.getResultList();
		}
		catch( DataAccessException e )
		{
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings( "unchecked" )
	public T findEntityByNamedQuery( String queryString )
	{
		try
		{
			Query queryObject = em.createNamedQuery(queryString);

			return (T) queryObject.getSingleResult();
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return null;
		}
	}

	public EntityManager getEm()
	{
		return em;
	}

	public void setEm( EntityManager em )
	{
		this.em = em;
	}
}