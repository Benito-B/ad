import view.window.LoginWindow;

public class HibernateMain {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            //MainWindow w = new MainWindow();
            LoginWindow l = new LoginWindow();
        });

 /*       EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("com.bentie.ghibernate");

        Categoria categoria = new Categoria();
        categoria.setName("Categoría desde Hibernate");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(categoria);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();*/
    }
}
