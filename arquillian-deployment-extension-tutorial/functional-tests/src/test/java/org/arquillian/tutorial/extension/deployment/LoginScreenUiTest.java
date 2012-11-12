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
package org.arquillian.tutorial.extension.deployment;

import java.net.URL;

import org.arquillian.tutorial.extension.deployment.api.DeployProjectArtifact;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author <a href="http://community.jboss.org/people/dan.j.allen">Dan Allen</a>
 * @author <a href="http://community.jboss.org/people/kpiwko">Karel Piwko</a>
 */
@RunWith(Arquillian.class)
@DeployProjectArtifact
public class LoginScreenUiTest {
	
    @Drone
    WebDriver browser;
    
    @ArquillianResource
    URL deploymentUrl;
    
    @Test
    public void should_login_with_valid_credentials() throws Exception {
        browser.navigate().to(new URL(deploymentUrl, "login.jsf"));
        
        browser.findElement(By.id("loginForm:username")).sendKeys("user1");
        browser.findElement(By.id("loginForm:password")).sendKeys("demo");
        browser.findElement(By.id("loginForm:login")).click();       
        
        Assert.assertTrue("User should be logged in!",
            browser.findElement(By.xpath("//li[contains(text(),'Welcome')]")).isDisplayed());
    }
}
