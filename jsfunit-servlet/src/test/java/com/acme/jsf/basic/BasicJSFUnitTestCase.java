/*
 * JBoss, Home of Professional Open Source
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
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
package com.acme.jsf.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.faces.application.ProjectStage;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.jsfunit.framework.Environment;
import org.jboss.jsfunit.jsfsession.JSFServerSession;
import org.jboss.jsfunit.jsfsession.JSFSession;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class BasicJSFUnitTestCase
{
   @Deployment
   public static Archive<?> createDeployment()
   {
      return ShrinkWrap.create(WebArchive.class, "test.war")
            .addClass(HitchhikersGuide.class)
            .addResource("basic/index.xhtml", "index.xhtml")
            .addWebResource("faces-config.xml")
            .setWebXML("jsf-web.xml");
   }

   @Test
   public void shouldExecutePage() throws Exception
   {
      validateManagedBeanValueOnIndexPage();
   }

   @Test
   public void shouldExecutePageAgain() throws Exception
   {
      validateManagedBeanValueOnIndexPage();
   }

   protected void validateManagedBeanValueOnIndexPage() throws Exception
   {
      JSFSession jsfSession = new JSFSession("/index.jsf");
      System.out.println("GET /index.jsf HTTP/1.1\n\n" + jsfSession.getJSFClientSession().getPageAsText());
      assertTrue(Environment.is12Compatible());
      assertTrue(Environment.is20Compatible());
      assertEquals(2, Environment.getJSFMajorVersion());
      assertEquals(0, Environment.getJSFMinorVersion());

      JSFServerSession server = jsfSession.getJSFServerSession();

      assertEquals("42", server.getManagedBeanValue("#{hitchhikersGuide.ultimateAnswer}"));
      assertEquals(ProjectStage.Development, server.getManagedBeanValue("#{hitchhikersGuide.journeyStage}"));
      assertEquals(ProjectStage.Development, server.getFacesContext().getApplication().getProjectStage());
   }
}
