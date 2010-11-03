package com.acme.jpa;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class InvoiceRepository extends AbstractRepository<Invoice>
{
   @PersistenceContext(unitName = "b")
   private EntityManager em;

   @Override
   public EntityManager getPersistenceContext()
   {
      return em;
   }

   @Override
   public String getEntityName()
   {
      return Invoice.class.getSimpleName();
   }
}
