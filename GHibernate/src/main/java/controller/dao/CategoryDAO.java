package controller.dao;

import controller.EntityManagerHelper;
import model.Category;

import java.util.List;

public class CategoryDAO extends PersistenceDAO<Category> {
    @Override
    public List<Category> getAll() {
        return EntityManagerHelper.getInstance().getEntityManager().createQuery("from Category order by Id", Category.class).getResultList();
    }
}
