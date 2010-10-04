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

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class GamePersistenceTestCase
{
   @Deployment
   public static Archive<?> createDeployment()
   {
      return ShrinkWrap.create(WebArchive.class, "test.war")
            .addPackage(Game.class.getPackage())
            .addManifestResource("test-persistence.xml", "persistence.xml")
            .addWebResource(EmptyAsset.INSTANCE, "beans.xml");
   }
 
   private static final String[] GAME_TITLES =
   {
      "Super Mario Brothers",
      "Mario Kart",
      "F-Zero"
   };
   
   @PersistenceContext
   EntityManager em;
   
   @Inject
   UserTransaction utx;

   public void insertSampleRecords() throws Exception
   {
      // clear database
      utx.begin();
      em.joinTransaction();
    
      System.out.println("Clearing the database...");
      em.createQuery("delete from Game").executeUpdate();
    
      // insert records
      System.out.println("Inserting records...");
      for (String title : GAME_TITLES)
      {
         Game game = new Game(title);
         em.persist(game);
      }
    
      utx.commit();
   }
   
   @Test
   public void should_be_able_to_select_games_using_jpql() throws Exception
   {
      insertSampleRecords();
      
      utx.begin();
      em.joinTransaction();
    
      System.out.println("Selecting (using JPQL)...");
      List<Game> games =
         em.createQuery("select g from Game g order by g.id", Game.class)
         .getResultList();
      System.out.println("Found " + games.size() + " games (using JPQL)");
      assertEquals(GAME_TITLES.length, games.size());
    
      for (int i = 0; i < GAME_TITLES.length; i++) {
         assertEquals(GAME_TITLES[i], games.get(i).getTitle());
         System.out.println(games.get(i));
      }
    
      utx.commit();
   }
   
   @Test
   public void should_be_able_to_select_games_using_criteria_api() throws Exception
   {
      insertSampleRecords();
      
      utx.begin();
      em.joinTransaction();
    
      CriteriaBuilder builder = em.getCriteriaBuilder();
      CriteriaQuery<Game> criteria = builder.createQuery(Game.class);
      // FROM clause
      Root<Game> game = criteria.from(Game.class);
      // SELECT clause
      criteria.select(game);
      // ORDER BY clause
      criteria.orderBy(builder.asc(game.get(Game_.id)));
      // No WHERE clause, select all
    
      System.out.println("Selecting (using Criteria)...");
      List<Game> games = em.createQuery(criteria).getResultList();
      System.out.println("Found " + games.size() + " games (using Criteria)");
      assertEquals(GAME_TITLES.length, games.size());
    
      for (int i = 0; i < GAME_TITLES.length; i++) {
         assertEquals(GAME_TITLES[i], games.get(i).getTitle());
         System.out.println(games.get(i));
      }
    
      utx.commit();
   }
}
