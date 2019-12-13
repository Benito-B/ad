package controller.dao;

import controller.HashUtils;
import model.User;
import view.dialog.MessageDialogHelper;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class UserDAO extends PersistenceDAO<User> {

    public User login(User login){
        EntityManager em = entityManagerFactory.createEntityManager();
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :name and u.password = :pass");
        query.setParameter("name", login.getUsername());
        query.setParameter("pass", HashUtils.Sha256(login.getPassword()));
        List<User> users = query.getResultList();
        if(users.size() < 1){
            MessageDialogHelper.ShowErrorMessage("El usuario introducido no existe", "Datos incorrectos");
            System.out.println("[ERROR] El usuario no existe.");
            em.close();
            return null;
        }
        em.close();
        return users.get(0);
    }
}
