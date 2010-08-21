package org.jboss.arquillian.examples;

import org.jboss.arquillian.examples.WidgetRepository;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Disposes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class WidgetRepositoryProducer
{
   // NOTE cannot use producer field because Weld attempts to close EntityManager
   @PersistenceContext EntityManager em;

   public @Produces @WidgetRepository
   EntityManager retrieveEntityManager() {
      return em;
   }

   public void disposeEntityManager(@Disposes @WidgetRepository EntityManager em) {}
}
