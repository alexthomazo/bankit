/*
 * Copyright (C) 2012 Alexandre Thomazo
 *
 * This file is part of BankIt.
 *
 * BankIt is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BankIt is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BankIt. If not, see <http://www.gnu.org/licenses/>.
 */
package org.alexlg.bankit.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Interface defining the common operation on
 * objects in the database. This interface will
 * be implemented in {@link AbstractJPADAOImpl}
 * 
 * @author Alexandre Thomazo
 *
 * @param <DomainObject> Type of the object to handle
 * @param <KeyType> Type of the key (eg. Integer.class)
 */
public abstract class AbstractDao <T, KeyType extends Serializable> {
	
	/** Entity Manager used to talk with the database */
	@PersistenceContext
	private EntityManager em;
	
	/** Class of the object */
	protected Class<T> domainClass = getDomainClass();

	/** Criteria Builder used to build criteria based query */
	private CriteriaBuilder builder;
	
	/**
	 * Get an object from the database by his id
	 * @param id Id of the object to retrieve
	 * @return Object if found or null
	 */
	public T get(KeyType id) {
		return em.find(domainClass, id);
	}

	/**
	 * Retrieve an instance of the object without loading
	 * it from the database (the returned object is a lazy
	 * loaded proxy)
	 * @param id Id of the object
	 * @return Proxy of the object
	 */
	public T load(KeyType id) {
		return em.getReference(domainClass, id);
	}
	/**
	 * Insert an object in the database
	 * @param object Object to insert
	 */
	public void insert(T t) {
		em.persist(t);
	}

	/**
	 * Update an object in database.
	 * @param object Object to update
	 */
	public void save(T t) {
		em.merge(t);
	}

	/**
	 * Delete an object from the database
	 * @param object Object to delete
	 */
	public void delete(T t) {
		em.remove(t);
	}

	/**
	 * Get list of all objects
	 * @return List of all objects
	 */
	public List<T> getList() {
		CriteriaQuery<T> criteria = getBuilder().createQuery(domainClass);
		criteria.select(criteria.from(domainClass));
		return em.createQuery(criteria).getResultList();
	}

	/**
	 * Delete all the objects in the database
	 */
	public void deleteAll() {
		String hqlDelete = "delete " + domainClass.getName();
		em.createQuery(hqlDelete).executeUpdate();
	}

	/**
	 * Count the objects in the database
	 * @return Number of objects
	 */
	public int count() {
		CriteriaBuilder b = getBuilder();
		CriteriaQuery<Long> criteria = b.createQuery(Long.class);
		criteria.select(b.count(criteria.from(domainClass)));

		return em.createQuery(criteria).getSingleResult().intValue();
	}
	
	/**
	 * Utility method for subclasses to retrieve the Criteria Builder
	 * @return Criteria Builder from entity manager
	 */
	protected CriteriaBuilder getBuilder() {
		if (builder == null) {
			builder = em.getCriteriaBuilder();
		}
		return builder;
	}

	/**
	 * Utility method for subclasses to retrieve the Entity Manager
	 * @return Entity Manager
	 */
	protected EntityManager getEm() {
		return em;
	}
	
	/**
	 * Method used to retrieve Class of Domain Object
	 * @return Domain Object Class
	 */
	@SuppressWarnings("unchecked")
	private Class<T> getDomainClass() {
		//getting class of the first parameterized type
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		Class<T> clazz = (Class<T>) type.getActualTypeArguments()[0];
		
		return clazz;
	}
}
