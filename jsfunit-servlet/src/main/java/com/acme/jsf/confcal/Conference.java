package com.acme.jsf.confcal;

import java.util.Date;

public class Conference implements Comparable<Conference>
{
   private String title;
   private Date startDate;
   private Date endDate;
   private String location;
   private String topic;

   public Conference() {}
   
   public Conference(String title, Date startDate, Date endDate, String location, String topic)
   {
      this.title = title;
      this.startDate = startDate;
      this.endDate = endDate;
      this.location = location;
      this.topic = topic;
   }
   
   public Conference(Conference source)
   {
      this.title = source.getTitle();
      this.startDate = source.getStartDate();
      this.endDate = source.getEndDate();
      this.location = source.getLocation();
      this.topic = source.getTopic();
   }
   
   public String getTitle()
   {
      return title;
   }

   public void setTitle(String title)
   {
      this.title = title;
   }

   public Date getStartDate()
   {
      return startDate;
   }

   public void setStartDate(Date startDate)
   {
      this.startDate = startDate;
   }

   public Date getEndDate()
   {
      return endDate;
   }

   public void setEndDate(Date endDate)
   {
      this.endDate = endDate;
   }

   public String getLocation()
   {
      return location;
   }

   public void setLocation(String location)
   {
      this.location = location;
   }

   public String getTopic()
   {
      return topic;
   }

   public void setTopic(String topic)
   {
      this.topic = topic;
   }


   @Override
   public int compareTo(Conference o)
   {
      int r = getStartDate().compareTo(o.getStartDate());
      if (r == 0)
      {
         r = getTitle().compareTo(o.getTitle());
      }
      return r;
   }
}
