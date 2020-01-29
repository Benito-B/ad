package controller.dao;

import controller.EntityManagerHelper;
import model.Article;
import model.Client;

import java.util.List;

public class ClientDAO extends PersistenceDAO<Client> {

    @Override
    public List<Client> getAll() {
        return EntityManagerHelper.getInstance().getEntityManager().createQuery("from Client order by Id", Client.class).getResultList();
    }

    @Override
    public Client findById(long id) {
        return EntityManagerHelper.getInstance().getEntityManager().find(Client.class, id);
    }
}
