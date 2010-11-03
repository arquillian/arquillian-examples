/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
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
package com.acme.jpa;

import static org.junit.Assert.assertEquals;

import javax.ejb.EJB;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class TwoPhaseCommitTestCase
{
   @Deployment
   public static Archive<?> createDeployment()
   {
      return ShrinkWrap.create(WebArchive.class, "test.war")
            .addPackage(Game.class.getPackage())
            .addManifestResource("test-persistence.xml", "persistence.xml");
   }
 
   @EJB
   DualRepositoryService service;
   
   protected void purge()
   {
      service.purge();
   }
   
   @Test
   public void should_not_modify_database_transaction_fails()
   {
      purge();
      
      try
      {
         service.succeedFirstFailSecondInTx();
      }
      catch (Exception e)
      {
      }
      
      assertEquals(0, service.getGameCount());
      assertEquals(0, service.getInvoiceCount());
   }
   
   @Test
   public void should_modify_database_if_no_transaction() throws Exception
   {
      purge();
      
      try
      {
         service.succeedFirstFailSecondWithoutTx();
      }
      catch (Exception e)
      {
      }
      
      assertEquals(1, service.getGameCount());
      assertEquals(0, service.getInvoiceCount());
   }
   
   @Test
   public void should_not_modify_database_if_rollback_transaction() throws Exception
   {
      purge();
      
      service.insertBothThenRollbackInTx();
      
      assertEquals(0, service.getGameCount());
      assertEquals(0, service.getInvoiceCount());
   }
}
