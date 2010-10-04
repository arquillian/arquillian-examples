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
package com.acme.jpa.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "RECORD")
public class Record implements Serializable
{
   private Long id;
   private String name;
   private Set<LineItem> lineItems;

   public Record()
   {
   }

   public Record(String name)
   {
      this.name = name;
   }

   @Id
   @GeneratedValue
   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   @OneToMany(mappedBy = "record", cascade = CascadeType.ALL)
   public Set<LineItem> getLineItems()
   {
      return lineItems;
   }

   public void setLineItems(Set<LineItem> lineItems)
   {
      this.lineItems = lineItems;
   }

   public void addLineItem(LineItem e)
   {
      if (lineItems == null)
      {
         lineItems = new HashSet<LineItem>();
      }
      lineItems.add(e);
      e.setRecord(this);
   }

   @Override
   public String toString()
   {
      return "Record@" + hashCode() + "[id = " + id + "; name = " + name + "]";
   }
}
