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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import com.acme.jpa.model.LineItem;
import com.acme.jpa.model.Record;

@Stateless
@Local(JavaPersistenceHelper.class)
public class JavaPersistenceHelperBean implements JavaPersistenceHelper
{
   @PersistenceContext(type = PersistenceContextType.TRANSACTION)
   private EntityManager em;
   
   /**
    * Seed the database with sample records
    */
   @Override
   public List<Record> seed(boolean clear)
   {
      if (clear)
      {
         em.createQuery("delete from Record").executeUpdate();
      }
      List<Record> records = new ArrayList<Record>();
      Record a = new Record("Record A");
      LineItem l1 = new LineItem(new BigDecimal(50));
      a.addLineItem(l1);
      em.persist(a);
      records.add(a);
      return records;
   }

   /**
    * Retrieve an entity using a transaction-scoped persistence context
    */
   @Override
   public <T> T retrieveById(Class<T> entityType, Long primaryKey)
   {
      return em.find(entityType, primaryKey);
   }
   
   /**
    * Checks whether the supplied entity is managed by this persistence
    * context. Should almost always return false, since the persistence context
    * lives and dies by the transaction.
    */
   @Override
   public boolean isManaging(Serializable entity)
   {
      return em.contains(entity);
   }
   
   /**
    * Forces a transaction to be cycled
    */
   @Override
   public void transact()
   {
   }
   
   /**
    * Gets the class of the JPA provider
    */
   public String getProvider()
   {
      return em.getDelegate().getClass().getName();
   }
   
   /**
    * Checks whether the current JPA provider supports lazy loading after the
    * session (i.e., EntityManager) has been closed
    */
   public boolean isLazyLoadingPermittedOnClosedSession()
   {
      return "org.eclipse.persistence.internal.jpa.EntityManagerImpl".equals(getProvider()) ? true : false;
   }
   
}
