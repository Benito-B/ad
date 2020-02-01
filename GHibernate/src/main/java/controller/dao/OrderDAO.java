package controller.dao;

import controller.EntityManagerHelper;
import model.Category;
import model.Client;
import model.Order;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * -GHibernate-
 *
 * @author Benito-B - 01/02/2020
 */
public class OrderDAO extends PersistenceDAO<Order> {

    public List<Order> getAllByClient(Client client){
        String queryString = "from Order where client = :client";
        TypedQuery<Order> query = EntityManagerHelper.getInstance().getEntityManager().createQuery(queryString, Order.class);
        return query.setParameter("client", client).getResultList();
    }

    @Override
    public List<Order> getAll() {
        return EntityManagerHelper.getInstance().getEntityManager().createQuery("from Order order by Id", Order.class).getResultList();
    }

    @Override
    public Order findById(long id) {
        return EntityManagerHelper.getInstance().getEntityManager().find(Order.class, id);
    }
}
