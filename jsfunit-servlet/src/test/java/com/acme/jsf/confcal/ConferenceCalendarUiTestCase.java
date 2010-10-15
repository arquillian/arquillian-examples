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
package com.acme.jsf.confcal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.jsfunit.jsfsession.JSFClientSession;
import org.jboss.jsfunit.jsfsession.JSFServerSession;
import org.jboss.jsfunit.jsfsession.JSFSession;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ConferenceCalendarUiTestCase
{
   @Deployment
   public static Archive<?> createDeployment()
   {
      return ShrinkWrap.create(WebArchive.class, "test.war")
            .addClasses(Conference.class, ConferenceCalendar.class)
            .addResource("confcal/submit.xhtml", "submit.xhtml")
            .addResource("confcal/submission.xhtml", "submission.xhtml")
            .addWebResource("faces-config.xml")
            .addWebResource(EmptyAsset.INSTANCE, "beans.xml")
            .setWebXML("jsf-web.xml");
   }
   
   private static boolean printRenderedOutput = false;

   @Test
   public void submittedConferenceShouldBeSaved() throws Exception
   {
      JSFSession jsfSession = new JSFSession("/submit.jsf");
      
      JSFServerSession server = jsfSession.getJSFServerSession();
      assertEquals("/submit.xhtml", server.getCurrentViewID());
      
      assertEquals(null, server.getManagedBeanValue("#{conference.title}"));
      assertEquals(null, server.getManagedBeanValue("#{conference.startDate}"));
      assertEquals(null, server.getManagedBeanValue("#{conference.endDate}"));
      assertEquals(null, server.getManagedBeanValue("#{conference.location}"));
      assertEquals(null, server.getManagedBeanValue("#{conference.topic}"));
      
      JSFClientSession client = jsfSession.getJSFClientSession();
      if (printRenderedOutput)
      {
         System.out.println("GET /submit.jsf HTTP/1.1\n\n" + client.getPageAsText());
      }

      client.setValue("conference:title", "Devoxx");
      client.setValue("conference:startDate", "2010-11-15");
      client.setValue("conference:endDate", "2010-11-19");
      client.setValue("conference:location", "Metropolis, Antwerp, Belgium");
      client.setValue("conference:topic", "Java");
      client.click("conference:submit");
      
      assertEquals("/submission.xhtml", server.getCurrentViewID());

      @SuppressWarnings("unchecked")
      List<Conference> conferences = (List<Conference>) server.getManagedBeanValue("#{conferences}");
      assertNotNull(conferences);
      assertEquals(1, conferences.size());
      Conference conference = conferences.get(0);
      assertEquals("Devoxx", conference.getTitle());
      assertEquals(buildDate(2010, 11, 15).toString(), conference.getStartDate().toString());
      assertEquals(buildDate(2010, 11, 19).toString(), conference.getEndDate().toString());
      assertEquals("Metropolis, Antwerp, Belgium", conference.getLocation());
      assertEquals("Java", conference.getTopic());
    
      if (printRenderedOutput)
      {
         System.out.println("POST /submission.jsf HTTP/1.1\n\n" + client.getPageAsText());
      }

      // not sure why these are null
      //assertEquals("Devoxx", server.getManagedBeanValue("#{conference.title}"));
      //assertEquals("Devoxx", server.getComponentValue("title"));
   }
   
   private Date buildDate(int year, int month, int date)
   {
      Calendar cal = Calendar.getInstance();
      cal.set(year, month - 1, date, 0, 0, 0);
      return cal.getTime();
   }
}
