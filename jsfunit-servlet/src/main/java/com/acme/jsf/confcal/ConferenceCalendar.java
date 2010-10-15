package com.acme.jsf.confcal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ConferenceCalendar
{
   private Set<Conference> conferenceDatabase = new TreeSet<Conference>();
   
   private List<Conference> conferences = new ArrayList<Conference>();
   
   @Produces @Named @Dependent
   public List<Conference> getConferences()
   {
      System.out.println("Retrieving " + conferences.size() + " conference(s)");
      for (Conference c : conferences)
      {
         System.out.println(c.getTitle());
      }
      return conferences;
   }
   
   public String submit(final Conference conference)
   {
      synchronized (conferenceDatabase)
      {
         System.out.println("Adding conference " + conference.getTitle());
         // copy to erase proxy
         conferenceDatabase.add(new Conference(conference));
         conferences = new ArrayList<Conference>(conferenceDatabase);
         System.out.println(conferences.size() + " conference(s) after insert");
         for (Conference c : conferences)
         {
            System.out.println(c.getTitle());
         }
      }
      return "success";
   }
   
   @Produces @RequestScoped @Named
   public Conference getConference()
   {
      System.out.println("Initializing new conference model");
      return new Conference();
   }
}
