package org.jboss.arquillian.examples;

import org.jboss.arquillian.examples.Widget;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

public class WidgetListProducer
{
   @Inject @WidgetRepository
   EntityManager widgetRepository;
   
   @Produces
   @Named
   @RequestScoped
   @SuppressWarnings("unchecked")
   List<Widget> getWidgets() {
      return widgetRepository.createQuery("select w from Widget w order by w.name").getResultList();
   }
}
