package controller.dao;

import model.Client;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ClientDAO extends PersistenceDAO<Client> {

    public Client getClient(String name){
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityGraph<Client> eg = (EntityGraph<Client>) em.getEntityGraph("fullClient");
        Query query = em.createQuery("SELECT c FROM Client c WHERE c.username = :name");
        query.setParameter("name", name);
        query.setHint("javax.persistence.loadgraph", eg);
        List<Client> clients = query.getResultList();
        if(clients.size() < 1){
            em.close();
            return null;
        }
        em.close();
        return clients.get(0);
    }
}
