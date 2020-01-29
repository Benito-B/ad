package controller.dao;

import controller.EntityManagerHelper;
import model.Article;

import java.util.List;

public class ArticleDAO extends PersistenceDAO<Article> {

    @Override
    public List<Article> getAll() {
        return EntityManagerHelper.getInstance().getEntityManager().createQuery("from Article order by Id", Article.class).getResultList();
    }

    @Override
    public Article findById(long id) {
        return EntityManagerHelper.getInstance().getEntityManager().find(Article.class, id);
    }
}
