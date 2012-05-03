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
package com.acme.jpa.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acme.jpa.model.LineItem;
import com.acme.jpa.model.Record;

@RunWith(Arquillian.class)
public class RepositoryTestCase
{
   @Deployment
   public static Archive<?> createDeployment()
   {
      return ShrinkWrap.create(JavaArchive.class)
         .addClasses(Record.class, LineItem.class,
               JavaPersistenceHelper.class, JavaPersistenceHelperBean.class,
               Repository.class, RepositoryBean.class)
         .addAsManifestResource("test-persistence.xml", "persistence.xml");
   }

   @EJB
   JavaPersistenceHelper helper;
   
   @EJB
   Repository repository;

   private static List<Record> seedRecords;
   
   private static long idOfFirstRecord;
   
   @Before
   public void seed_database()
   {
      // emulate @BeforeClass on instance
      // nice if this was supported in Arquillian
      // sucks that seedRecords has to be static
      if (seedRecords == null)
      {
         seedRecords = helper.seed(true);
         idOfFirstRecord = seedRecords.get(0).getId();
      }
   }

   /**
    * A lazy loading operation should succeed when performed inside the context
    * of the transaction retrieving it. The type of persistence context does
    * not matter in this case.
    */
   @Test
   public void lazy_load_should_succeed_within_transaction()
   {
      Record record = repository.retrieveById(Record.class, idOfFirstRecord, new EntityInitializer<Record>()
      {
         @Override
         public Record initialize(Record record)
         {
            record.getLineItems().size();
            return record;
         }
      });
      int numLineItemsExpected = 1;
      assertEquals(numLineItemsExpected, record.getLineItems().size());
   }
   
   /**
    * The entity should remain managed outside of an active stateful EJB when
    * the entity is retrieved from an extended persistence context owned by that EJB.
    */
   @Test
   public void entity_should_be_managed_by_extended_pc_outside_active_ejb()
   {
      Record record = repository.retrieveById(Record.class, idOfFirstRecord);
      assertTrue(repository.isManaging(record));
   }

   /**
    * The entity should not be managed outside of an active stateful EJB when
    * the entity is retrieved from a transaction-scoped persistence context owned by that EJB.
    */
   @Test
   public void entity_should_not_be_managed_by_transaction_scoped_pc_outside_active_ejb()
   {
      Record record = helper.retrieveById(Record.class, idOfFirstRecord);
      assertFalse(helper.isManaging(record));
   }
   
   /**
    * A lazy loading operation should succeed outside of an active stateful EJB
    * when the entity is retrieved by an extended persistence context owned by
    * that EJB.
    */
   @Test
   public void lazy_load_should_succeed_outside_of_active_ejb_with_extended_pc()
   {
      Record record = repository.retrieveById(Record.class, idOfFirstRecord);
      int numLineItemsExpected = 1;
      int numLineItemsActual = 0;
      try
      {
         numLineItemsActual = record.getLineItems().size();
      }
      catch (Exception e)
      {
      }
      assertEquals(numLineItemsExpected, numLineItemsActual);
   }
   
   /**
    * A lazy loading operation should fail outside of a stateful EJB
    * which has been removed. The persistence context is no longer
    * available at this point.
    */
   @Test
   public void lazy_load_should_fail_outside_of_removed_ejb()
   {
      Record record = repository.retrieveById(Record.class, idOfFirstRecord);
      repository.close();
      // Some JPA providers (EclipseLink) allow LAZY relationships to be accessed after session is closed
      int numLineItemsExpected = helper.isLazyLoadingPermittedOnClosedSession() ? 1 : 0;
      int numLineItemsActual = 0;
      try
      {
         numLineItemsActual = record.getLineItems().size();
      }
      catch (Exception e)
      {
      }
      assertEquals(numLineItemsExpected, numLineItemsActual);
   }
   
   /**
    * A lazy loading operation should fail outside of an EJB when the entity is
    * retrieved by a transaction-scoped persistence context owned by that EJB.
    * Certain JPA providers (e.g., EclipseLink) allow lazy relationships to be
    * accessed after the session is closed.
    */
   @Test
   public void lazy_load_should_fail_outside_transaction_when_entity_retrieved_by_transaction_scoped_pc()
   {
      Record record = helper.retrieveById(Record.class, idOfFirstRecord);
      assertFalse(helper.isManaging(record));
      // Some JPA providers (e.g., EclipseLink) allow lazy relationships to be accessed after session is closed
      int numLineItemsExpected = helper.isLazyLoadingPermittedOnClosedSession() ? 1 : 0;
      int numLineItemsActual = 0;
      try
      {
         numLineItemsActual = record.getLineItems().size();
      }
      catch (Exception e)
      {
      }
      // verify the line items can't be fetched outside of EJB
      assertEquals(numLineItemsExpected, numLineItemsActual);
   }
   
   /**
    * A dirty (outstanding) change to an entity should be flushed when a transactional
    * method is invoked on a stateful EJB which has an extended persistence context
    * that is managing the entity.
    */
   @Test
   public void dirty_change_should_be_flushed_by_extended_pc_when_transactional_method_on_same_ejb_called()
   {
      Record record = repository.retrieveById(Record.class, idOfFirstRecord);
      assertTrue(repository.isManaging(record));
      String name = record.getName();
      record.setName(name + "-renamed");
      repository.update(record);
      List<Record> results = repository.retrieveByQuery(Record.class, "select r from Record r where r.name = ?1", name + "-renamed");
      assertEquals(1, results.size());
   }
   
   /**
    * A dirty (outstanding) change to an entity should be flushed when any
    * transactional method is invoked when an extended persistence context is
    * managing the entity.
    */
   @Test
   public void dirty_change_should_be_flushed_by_extended_pc_when_transactional_method_on_another_ejb_called()
   {
      Record record = repository.retrieveById(Record.class, idOfFirstRecord);
      assertTrue(repository.isManaging(record));
      assertFalse(helper.isManaging(record));
      String name = record.getName();
      record.setName(name + "-renamed");
      helper.transact();
      List<Record> results = repository.retrieveByQuery(Record.class, "select r from Record r where r.name = ?1", name + "-renamed");
      assertEquals(1, results.size());
   }
}
