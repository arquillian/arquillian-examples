package com.acme.jpa;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class GameRepository extends AbstractRepository<Game>
{
   @PersistenceContext(unitName = "a")
   private EntityManager em;

   @Override
   public EntityManager getPersistenceContext()
   {
      return em;
   }

   @Override
   public String getEntityName()
   {
      return Game.class.getSimpleName();
   }
}
