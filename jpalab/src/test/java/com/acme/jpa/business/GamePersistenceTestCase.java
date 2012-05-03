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

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acme.jpa.model.Game;

@RunWith(Arquillian.class)
public class GamePersistenceTestCase
{
   private static final String[] GAME_TITLES =
   {
      "Super Mario Brothers",
      "Mario Kart",
      "F-Zero"
   };

   @Deployment
   public static Archive<?> createDeployment()
   {
      return ShrinkWrap.create(JavaArchive.class)
            .addClasses(Game.class, Games.class, GamesBean.class)
            .addAsManifestResource("test-persistence.xml", "persistence.xml");
   }
   
   @EJB
   Games games;

   @Test
   public void testInsert() throws Exception
   {
      // flushing database
      System.out.println("Clearing records...");
      games.clear();

      // insert records
      System.out.println("Inserting records...");
      for (String title : GAME_TITLES)
      {
         Game game = new Game(title);
         games.add(game);
      }

      List<Game> results;

      // query with JPQL
      System.out.println("Selecting (using JPQL)...");
      results = games.selectAllUsingJpql();
      System.out.println("Found " + results.size() + " games (using JPQL)");
      assertEquals(GAME_TITLES.length, results.size());
      for (int i = 0; i < GAME_TITLES.length; i++) {
         assertEquals(GAME_TITLES[i], results.get(i).getTitle());
         System.out.println(results.get(i));
      }
   }

}
