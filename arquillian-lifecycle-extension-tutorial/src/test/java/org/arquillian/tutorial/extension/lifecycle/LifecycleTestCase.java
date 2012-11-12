package org.arquillian.tutorial.extension.lifecycle;
/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
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

import org.arquillian.tutorial.extension.lifecycle.api.AfterDeploy;
import org.arquillian.tutorial.extension.lifecycle.api.AfterUnDeploy;
import org.arquillian.tutorial.extension.lifecycle.api.BeforeDeploy;
import org.arquillian.tutorial.extension.lifecycle.api.BeforeUnDeploy;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * DeclarativeTestCase
 *
 * @author <a href="mailto:aslak@redhat.com">Aslak Knutsen</a>
 * @version $Revision: $
 */
@RunWith(Arquillian.class)
public class LifecycleTestCase
{
   @Deployment
   public static WebArchive deploy() {
      return ShrinkWrap.create(WebArchive.class);
   }
   
   @BeforeDeploy
   public static void doBeforeDeploy() {
      System.out.println("@BeforeDeploy");
   }

   @AfterDeploy
   public static void doAfterDeploy() {
      System.out.println("@AfterDeploy");
   }

   @BeforeUnDeploy
   public static void doBeforeUnDeploy() {
      System.out.println("@BeforeUnDeploy");
   }

   @AfterUnDeploy
   public static void doAfterUnDeploy() {
      System.out.println("@AfterUnDeploy");
   }

   @Test
   public void shouldBeAbleToOne() throws Exception {
      System.out.println("@Test - 1");
   }

   @Test
   public void shouldBeAbleToTwo() throws Exception {
      System.out.println("@Test - 2");
   }
}
