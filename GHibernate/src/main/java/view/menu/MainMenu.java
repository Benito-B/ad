package view.menu;

import controller.dao.ArticleDAO;
import controller.dao.CategoryDAO;
import controller.dao.ClientDAO;
import controller.dao.UserDAO;
import model.Article;
import model.Category;
import model.Client;
import model.User;
import view.window.EditItemWindow;
import view.window.ListItemsWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends JMenuBar{

    private User loggedUser;

    public MainMenu(User loggedUser){
        this.loggedUser = loggedUser;
        //Submenú categorías
        JMenu jmCategories = new JMenu("Categorías");
        this.add(jmCategories);
        JMenuItem miListCategories = new JMenuItem("Listar categorías");
        miListCategories.addActionListener(e -> {
            CategoryDAO categoryDAO = new CategoryDAO();
            List<Category> categories = categoryDAO.getAll();
            String[] fieldsToPrint = {"id", "name"};
            ListItemsWindow<Category> categoryWindow = new ListItemsWindow<>(categories, Category.class, fieldsToPrint);
        });
        jmCategories.add(miListCategories);
        JMenuItem miCreateCategory = new JMenuItem("Crear categoría");
        miCreateCategory.addActionListener(e -> {
            EditItemWindow<Category> catWindow = new EditItemWindow<>(new Category(), (Window)getTopLevelAncestor());
        });
        jmCategories.add(miCreateCategory);

        //Creo el submenú de Artículos
        JMenu jmArticles = new JMenu("Artículos");
        this.add(jmArticles);
        //Creo la opción para listar artículos y la añado al submenú correspondiente
        JMenuItem miListArticles = new JMenuItem("Listar artículos");
        miListArticles.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArticleDAO articleDAO = new ArticleDAO();
                List<Article> articles = articleDAO.getAll();
                String[] fieldsToPrint = {"id", "name", "price", "category"};
                ListItemsWindow<Article> articlesWindow = new ListItemsWindow<>(articles, Article.class, fieldsToPrint);
            }
        });
        jmArticles.add(miListArticles);
        JMenuItem miCreateArticle = new JMenuItem("Crear Artículo");
        miCreateArticle.addActionListener(e -> {
            EditItemWindow<Article> articleWindow = new EditItemWindow<>(new Article(), (Window)getTopLevelAncestor());
        });
        jmArticles.add(miCreateArticle);

        //Creo el submenú de Pedidos
        JMenu jmOrders = new JMenu("Pedidos");
        this.add(jmOrders);
        //Creo la opción para listar artículos y la añado al submenú correspondiente
        JMenuItem miListOrders = new JMenuItem("Listar pedidos");
        miListOrders.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO sacar pedidos de la DB
                //TODO crear ventana para mostrar pedidos
            }
        });
        jmOrders.add(miListOrders);

        //Creo el submenú de Clientes
        JMenu jmClients = new JMenu("Clientes");
        this.add(jmClients);
        //Creo la opción para listar artículos y la añado al submenú correspondiente
        JMenuItem miListClients = new JMenuItem("Listar clientes");
        miListClients.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientDAO clientDAO = new ClientDAO();
                List<Client> clients = clientDAO.getAll();
                String[] fieldsToPrint = {"id", "name"};
                ListItemsWindow<Client> articlesWindow = new ListItemsWindow<>(clients, Client.class, fieldsToPrint);
            }
        });
        jmClients.add(miListClients);
        JMenuItem miCreateClient = new JMenuItem("Crear Cliente");
        miCreateClient.addActionListener(e -> {
            EditItemWindow<Client> clientWindow = new EditItemWindow<>(new Client(), (Window)getTopLevelAncestor());
        });
        jmClients.add(miCreateClient);

        //Manejar usuarios - SOLO ADMIN
        if(loggedUser.isAdmin()) {
            JMenu jmUsers = new JMenu("Usuarios");
            JMenuItem miListUsers = new JMenuItem("Listar usuarios");
            miListUsers.addActionListener(evt -> {
                UserDAO userDAO = new UserDAO();
                String[] fieldsToPrint = {"userId", "username", "isAdmin"};
                ListItemsWindow<User> userWindow = new ListItemsWindow<>(userDAO.getAll(), User.class, fieldsToPrint);
            });
            jmUsers.add(miListUsers);
            this.add(jmUsers);
        }
        //Creo la opción de salir y la añado a la barra principal
        JMenuItem miSalir = new JMenuItem("Salir");
        miSalir.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.add(miSalir);
    }
}
