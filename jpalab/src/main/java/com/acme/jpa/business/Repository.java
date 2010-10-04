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

/**
 * A generic repository interface for inserting and retrieving entities from the
 * database. Designed with the capabilities of JPA in mind.
 */
public interface Repository
{
   public void create(Serializable entity);
   
   public void delete(Serializable entity);
   
   public <T> T retrieveById(Class<T> entityType, Object primaryKey);
   
   public <T> T retrieveById(Class<T> entityType, Object primaryKey, EntityInitializer<T> callback);
   
   public <T> List<T> retrieveAll(Class<T> entityType);
   
   public <T> List<T> retrieveByQuery(Class<T> entityType, String query, String... params);
   
   public void update(Serializable entity);
   
   public <T extends Serializable> T save(T entity);
   
   public <T extends Serializable> T saveNonManaged(T entity);
   
   public boolean isManaging(Serializable entity);
   
   public void close();
}
