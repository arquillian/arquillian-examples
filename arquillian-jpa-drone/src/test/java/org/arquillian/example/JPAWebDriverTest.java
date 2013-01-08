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
package org.arquillian.example;

import java.io.File;
import java.net.URL;

import junit.framework.Assert;

import org.arquillian.example.controller.LoginController;
import org.arquillian.example.controller.RegisterController;
import org.arquillian.example.dao.UserDAO;
import org.arquillian.example.dao.UserDAOException;
import org.arquillian.example.dao.impl.UserDAOImpl;
import org.arquillian.example.model.Credentials;
import org.arquillian.example.model.User;
import org.arquillian.example.security.Authenticator;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author <a href="https://community.jboss.org/people/smikloso">Stefan Miklosovic</a>
 */
@RunWith(Arquillian.class)
public class JPAWebDriverTest {
	
	private static final String WEBAPP_SRC = "src/main/webapp";
	
	private static final By REGISTER_USERNAME_FIELD = By.id("registerForm:username");
	
    private static final By REGISTER_PASSWORD_FIELD = By.id("registerForm:password");
    
    private static final By REGISTER_BUTTON = By.id("registerForm:register");
    
    private static final By LOGIN_USERNAME_FIELD = By.id("loginForm:username");
    
    private static final By LOGIN_PASSWORD_FIELD = By.id("loginForm:password");
    
    private static final By LOGIN_TEXT = By.xpath("//h3[contains(text(), 'Log in')]");

    private static final By LOGIN_BUTTON = By.id("loginForm:login");
	
	private static final String USERNAME = "JohnDoe";
	
	private static final String PASSWORD = "PASSWORD";

    private static final By WELCOME = By.xpath("//p[contains(text(), 'You are signed in as " + USERNAME + ".')]");
	
	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "login.war")
				.addClasses(User.class,
						UserDAO.class,
						UserDAOImpl.class,
						UserDAOException.class,
						Authenticator.class, 
						Credentials.class,
						LoginController.class, 
						RegisterController.class)
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource("jbossas-ds.xml")				
				.addAsWebResource(new File(WEBAPP_SRC, "login.xhtml"))
				.addAsWebResource(new File(WEBAPP_SRC, "home.xhtml"))
				.addAsWebResource(new File(WEBAPP_SRC, "register.xhtml"))
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource(
						new StringAsset("<faces-config version=\"2.0\"/>"),
						"faces-config.xml");
	}
	
	@Drone
	WebDriver driver;
	
	@ArquillianResource
	URL deploymentUrl;
	
	@Test
	public void register() {
	    // Register
	    driver.get(deploymentUrl + "register.jsf");
	    driver.findElement(REGISTER_USERNAME_FIELD).sendKeys(USERNAME);
	    driver.findElement(REGISTER_PASSWORD_FIELD).sendKeys(PASSWORD);
	    driver.findElement(REGISTER_BUTTON).click();
	    
	    Assert.assertTrue(driver.findElement(LOGIN_TEXT).isDisplayed());
	    
	    // And try to log in
	    Assert.assertTrue("User should be registered and redirected to login page!"
	            , driver.findElement(LOGIN_USERNAME_FIELD).isDisplayed()
	            && driver.findElement(LOGIN_PASSWORD_FIELD).isDisplayed());
	    
	    driver.findElement(LOGIN_USERNAME_FIELD).clear();
	    driver.findElement(LOGIN_USERNAME_FIELD).sendKeys(USERNAME);
	    driver.findElement(LOGIN_PASSWORD_FIELD).clear();
	    driver.findElement(LOGIN_PASSWORD_FIELD).sendKeys(PASSWORD);
	    driver.findElement(LOGIN_BUTTON).click();
	    
	    Assert.assertTrue("User should be at welcome page!",
	            driver.findElement(WELCOME).isDisplayed());
	}
	
}
