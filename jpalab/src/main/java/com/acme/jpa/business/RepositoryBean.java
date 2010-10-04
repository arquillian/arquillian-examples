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
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

@Stateful
@Local(Repository.class)
public class RepositoryBean implements Repository
{
   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   private EntityManager em;

   @Override
   public void create(final Serializable entity)
   {
      em.persist(entity);
   }

   @Override
   public void delete(final Serializable entity)
   {
      em.remove(entity);
   }

   @Override
   public <T> T retrieveById(final Class<T> entityType, final Object primaryKey)
   {
      return (T) em.find(entityType, primaryKey);
   }
   
   @Override
   public <T> T retrieveById(final Class<T> entityType, final Object primaryKey, final EntityInitializer<T> callback)
   {
      return callback.initialize(retrieveById(entityType, primaryKey));
   }
   
   @Override
   @SuppressWarnings("unchecked")
   public <T> List<T> retrieveAll(final Class<T> entityType)
   {
      return em.createQuery("select e from " + entityType.getSimpleName() + " e").getResultList();
   }
   
   @Override
   @SuppressWarnings("unchecked")
   public <T> List<T> retrieveByQuery(final Class<T> entityType, final String query, final String... params)
   {
      Query q = em.createQuery(query);
      for (int i = 0; i < params.length; i++)
      {
         q.setParameter(i + 1, params[i]);
      }
      return (List<T>) q.getResultList();
   }
   
   @Override
   public void update(final Serializable entity)
   {
      // any transaction in which the entity manager is enlisted will flush the changes
   }

   public <T extends Serializable> T save(final T entity)
   {
      if (!em.contains(entity))
      {
         return em.merge(entity);
      }
      // any transaction in which the entity manager is enlisted will flush the changes
      return entity;
   };
   
   @Override
   public <T extends Serializable> T saveNonManaged(final T entity)
   {
      return em.merge(entity);
   }

   @Override
   public boolean isManaging(final Serializable entity)
   {
      return em.contains(entity);
   }

   /**
    * Make sure the persistence context is flushed, cleared and closed == dead
    */
   @Override
   @Remove
   public void close()
   {
      em.flush();
      em.clear();
      em.close();
   }
}
