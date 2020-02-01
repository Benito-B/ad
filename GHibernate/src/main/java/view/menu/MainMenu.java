package view.menu;

import controller.dao.*;
import model.*;
import view.WindowType;
import view.window.EditItemWindow;
import view.window.ListItemsWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainMenu extends JMenuBar{

    private User loggedUser;
    private final Map<WindowType, JDialog> itemWindows;

    public MainMenu(User loggedUser){
        this.loggedUser = loggedUser;
        this.itemWindows = new HashMap<>();
        //Submenú categorías
        JMenu jmCategories = new JMenu("Categorías");
        this.add(jmCategories);
        JMenuItem miListCategories = new JMenuItem("Listar categorías");
        miListCategories.addActionListener(e -> {
            CategoryDAO categoryDAO = new CategoryDAO();
            List<Category> categories = categoryDAO.getAll();
            String[] fieldsToPrint = {"id", "name"};
            if(itemWindows.containsKey(WindowType.LIST_CATEGORY))
                itemWindows.get(WindowType.LIST_CATEGORY).requestFocus();
            else {
                ListItemsWindow<Category> categoryWindow = new ListItemsWindow<>(categories, Category.class, fieldsToPrint, loggedUser);
                categoryWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        itemWindows.remove(WindowType.LIST_CATEGORY);
                    }
                });
                itemWindows.put(WindowType.LIST_CATEGORY, categoryWindow);
            }
        });
        jmCategories.add(miListCategories);
        JMenuItem miCreateCategory = new JMenuItem("Crear categoría");
        miCreateCategory.addActionListener(e -> {
            if(itemWindows.containsKey(WindowType.DETAIL_CATEGORY))
                itemWindows.get(WindowType.DETAIL_CATEGORY).requestFocus();
            else {
                EditItemWindow<Category> catWindow = new EditItemWindow<>(new Category(), (Window) getTopLevelAncestor());
                catWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        itemWindows.remove(WindowType.DETAIL_CATEGORY);
                    }
                });
                itemWindows.put(WindowType.DETAIL_CATEGORY, catWindow);
            }
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
                if(itemWindows.containsKey(WindowType.LIST_ARTICLE))
                    itemWindows.get(WindowType.LIST_ARTICLE).requestFocus();
                else{
                    ListItemsWindow<Article> articlesWindow = new ListItemsWindow<>(articles, Article.class, fieldsToPrint, loggedUser);
                    articlesWindow.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            itemWindows.remove(WindowType.LIST_ARTICLE);
                        }
                    });
                    itemWindows.put(WindowType.LIST_ARTICLE, articlesWindow);
                }
            }
        });
        jmArticles.add(miListArticles);
        JMenuItem miCreateArticle = new JMenuItem("Crear Artículo");
        miCreateArticle.addActionListener(e -> {
            if(itemWindows.containsKey(WindowType.DETAIL_ARTICLE))
                itemWindows.get(WindowType.DETAIL_ARTICLE).requestFocus();
            else {
                EditItemWindow<Article> articleWindow = new EditItemWindow<>(new Article(), (Window) getTopLevelAncestor());
                articleWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        itemWindows.remove(WindowType.DETAIL_ARTICLE);
                    }
                });
                itemWindows.put(WindowType.DETAIL_ARTICLE, articleWindow);
            }
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
                OrderDAO dao = new OrderDAO();
                List<Order> orders = dao.getAll();
                String[] fieldsToPrint = {"id", "client", "date", "total"};
                ListItemsWindow<Order> ordersWindow = new ListItemsWindow<>(orders, Order.class, fieldsToPrint, loggedUser);
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
                if(itemWindows.containsKey(WindowType.LIST_CLIENT))
                    itemWindows.get(WindowType.LIST_CLIENT).requestFocus();
                else {
                    ListItemsWindow<Client> clientsWindow = new ListItemsWindow<>(clients, Client.class, fieldsToPrint, loggedUser);
                    clientsWindow.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            itemWindows.remove(WindowType.LIST_CLIENT);
                        }
                    });
                    itemWindows.put(WindowType.LIST_CLIENT, clientsWindow);
                }
            }
        });
        jmClients.add(miListClients);
        JMenuItem miCreateClient = new JMenuItem("Crear Cliente");
        miCreateClient.addActionListener(e -> {
            if(itemWindows.containsKey(WindowType.DETAIL_CLIENT))
                itemWindows.get(WindowType.DETAIL_CLIENT).requestFocus();
            else {
                EditItemWindow<Client> clientWindow = new EditItemWindow<>(new Client(), (Window) getTopLevelAncestor());
                clientWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        itemWindows.remove(WindowType.DETAIL_CLIENT);
                    }
                });
                itemWindows.put(WindowType.DETAIL_CLIENT, clientWindow);
            }
        });
        jmClients.add(miCreateClient);

        //Manejar usuarios - SOLO ADMIN
        if(loggedUser.isAdmin()) {
            JMenu jmUsers = new JMenu("Usuarios");
            JMenuItem miListUsers = new JMenuItem("Listar usuarios");
            miListUsers.addActionListener(evt -> {
                UserDAO userDAO = new UserDAO();
                String[] fieldsToPrint = {"userId", "username", "isAdmin"};
                ListItemsWindow<User> userWindow = new ListItemsWindow<>(userDAO.getAll(), User.class, fieldsToPrint, loggedUser);
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
