package controller.dao;

import controller.EntityManagerHelper;
import controller.HashUtils;
import model.Client;
import model.User;
import view.dialog.MessageDialogHelper;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class UserDAO extends PersistenceDAO<User> {

    public User login(User login){
        EntityManager em = EntityManagerHelper.getInstance().getEntityManager();
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :name and u.password = :pass");
        query.setParameter("name", login.getUsername());
        query.setParameter("pass", HashUtils.Sha256(login.getPassword()));
        List<User> users = query.getResultList();
        if(users.size() < 1){
            MessageDialogHelper.ShowErrorMessage("El usuario introducido no existe", "Datos incorrectos");
            System.out.println("[ERROR] El usuario no existe.");
            return null;
        }
        return users.get(0);
    }

    @Override
    public List<User> getAll() {
        return EntityManagerHelper.getInstance().getEntityManager().createQuery("from User order by userId", User.class).getResultList();
    }
}
