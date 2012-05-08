/*
 * Copyright 2012, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.arquillian.example.ui;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author <a href="http://community.jboss.org/people/dan.j.allen">Dan Allen</a>
 */
@Named
@SessionScoped
public class LoginController implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private static final String SUCCESS_MESSAGE = "Welcome!";
    private static final String FAILURE_MESSAGE = "Incorrect username and password combination.";

    private User currentUser;
    
    @Inject
    private Credentials credentials;
    
    public String login() {
        if ("user1".equals(credentials.getUsername()) &&
            "demo".equals(credentials.getPassword())) {
            currentUser = new User("demo");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SUCCESS_MESSAGE));
            return "home.xhtml";
        }
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_WARN, FAILURE_MESSAGE, FAILURE_MESSAGE));
        return null;
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    @Produces
    public User getCurrentUser() {
        return currentUser;
    }
}
