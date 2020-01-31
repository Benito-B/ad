package controller.dao;

import controller.EntityManagerHelper;
import model.Client;

import java.util.List;

public abstract class PersistenceDAO <T>{


    public PersistenceDAO(){
    }

    public abstract List<T> getAll();

    public void persist(T object){
        EntityManagerHelper.getInstance().beginTransaction();
        EntityManagerHelper.getInstance().getEntityManager().persist(object);
        EntityManagerHelper.getInstance().commitTransaction();
    }

    public abstract T findById(long id);

    public void delete(T object){
        EntityManagerHelper.getInstance().beginTransaction();
        EntityManagerHelper.getInstance().getEntityManager().remove(object);
        EntityManagerHelper.getInstance().commitTransaction();
    }

}
