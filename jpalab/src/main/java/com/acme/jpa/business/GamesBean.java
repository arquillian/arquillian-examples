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

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.acme.jpa.model.Game;

@Stateless
@Local(Games.class)
public class GamesBean implements Games
{
   @PersistenceContext
   private EntityManager em;
   
   @Override
   public void clear()
   {
      em.createQuery("delete from Game").executeUpdate();
   }

   @Override
   public void add(Game game)
   {
      em.persist(game);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<Game> selectAllUsingJpql()
   {
      return em.createQuery("select g from Game g order by g.id").getResultList();
   }
}
