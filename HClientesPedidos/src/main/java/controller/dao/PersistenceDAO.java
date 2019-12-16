package controller.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class PersistenceDAO <T>{

    protected EntityManagerFactory entityManagerFactory;

    public PersistenceDAO(){
        entityManagerFactory = Persistence.createEntityManagerFactory("com.bentie.ghibernate");
    }

    public void persist(T object){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void close(){
        entityManagerFactory.close();
    }
}
