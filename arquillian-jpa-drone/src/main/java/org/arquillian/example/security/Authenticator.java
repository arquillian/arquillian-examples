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
package org.arquillian.example.security;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.arquillian.example.dao.UserDAO;
import org.arquillian.example.dao.UserDAOException;
import org.arquillian.example.model.User;

/**
 * @author <a href="https://community.jboss.org/people/smikloso">Stefan Miklosovic</a>
 */
@Startup
@Singleton
public class Authenticator {

	private static final Logger logger = 
			Logger.getLogger(Authenticator.class.getName());
	
	User user;
	
	@Inject
	UserDAO userDAO;

	@Lock(LockType.READ)
	public boolean login(User user) {
		try {
			return userDAO.canLogin(user);
		} catch (UserDAOException ex) {
			logger.log(Level.INFO, "Unable to login a user{0}", user);
			return false;
		}
	}

	@Lock(LockType.WRITE)
	public boolean register(User user) {
		try {
			if (userDAO.findByName(user.getUsername()) == null) {
				userDAO.createUser(user);
				return true;
			}
			return false;
		} catch (UserDAOException ex) {
			logger.log(Level.INFO, "Unable to register a user{0}", user);
			return false;
		}
	}
}
