package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerHelper {

    private static EntityManagerHelper instance = null;
    private EntityManager entityManager;

    private EntityManagerHelper(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.bentie.ghibernate");
        this.entityManager = emf.createEntityManager();
    }

    public static EntityManagerHelper getInstance() {
        if(instance == null)
            instance = new EntityManagerHelper();
        return instance;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void beginTransaction(){
        entityManager.getTransaction().begin();
    }

    public void commitTransaction(){
        entityManager.getTransaction().commit();
    }

    public void rollbackTransaction(){
        entityManager.getTransaction().rollback();
    }
}
