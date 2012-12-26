/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.arquillian.example.dao.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.arquillian.example.dao.UserDAO;
import org.arquillian.example.dao.UserDAOException;
import org.arquillian.example.model.User;

/**
 * @author <a href="https://community.jboss.org/people/smikloso">Stefan Miklosovic</a>
 */
@Stateless
public class UserDAOImpl implements UserDAO {

	private static final Logger logger =
			Logger.getLogger(UserDAOImpl.class.getName());
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	public Long createUser(User user) throws UserDAOException {
		try {
			em.persist(user);
			return user.getId();
		} catch (Exception e) {
			logger.log(Level.INFO, "Unable to create a user {0}.", user);
			throw new UserDAOException();
		}
	}

	@Override
	public void deleteUser(User user) throws UserDAOException {
		try {
			em.remove(user);
		} catch (Exception e) {
			logger.log(Level.INFO, "Unable to delete a user {0}.", user);
			throw new UserDAOException();
		}
	}

	@Override
	public User findByName(String name) throws UserDAOException {
        try {
            Query q = em.createQuery("select u from User as u where u.username = :name");
            q.setParameter("name", name);
            List<?> result = q.getResultList();
            if (result.size() > 0) {
            	return (User) result.get(0);
            } else {
            	return null;
            }
        } catch (Exception ex) {
            logger.log(Level.INFO, ex.getMessage());
            throw new UserDAOException();
        }
	}

	@Override
	public boolean canLogin(User user) {
		try {
			Query q = em.createQuery("select u from User as u where " +
					"u.username = :username and u.password = :password");
			q.setParameter("username", user.getUsername());
			q.setParameter("password", user.getPassword());
			List<?> result = q.getResultList();
			return result.size() == 1;
		} catch(Exception ex) {
			logger.log(Level.INFO, ex.getMessage());
			throw new UserDAOException();
		}
	}
}
