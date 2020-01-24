package controller.dao;

import controller.EntityManagerHelper;

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

}
