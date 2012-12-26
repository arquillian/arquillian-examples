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
package org.arquillian.example.dao;

import javax.ejb.Local;

import org.arquillian.example.model.User;

/**
 * @author <a href="https://community.jboss.org/people/smikloso">Stefan Miklosovic</a>
 */
@Local
public interface UserDAO {

	/**
	 * This method create a new user.
	 * 
	 * @param user
	 *            user to create
	 * @return id of a newly created user or null
	 */
	Long createUser(User user);

	/**
	 * This method deletes some user.
	 * 
	 * @param user
	 *            user to delete
	 */
	void deleteUser(User user);

	/**
	 * This method finds a user by its name.
	 * 
	 * @param name
	 *            name of user to find
	 * @return user of specified name or null
	 */
	User findByName(String name);

	/**
	 * Checks if user with such name and password is in the system
	 * 
	 * @param user
	 *            user to check if he can login
	 * @return true if user can login, false otherwise
	 */
	boolean canLogin(User user);
}
