package view.menu;

import model.Article;
import model.User;
import view.window.ListItemsWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends JMenuBar{

    private User loggedUser;

    public MainMenu(User loggedUser){
        this.loggedUser = loggedUser;
        //Creo el submenú de Artículos
        JMenu jmArticles = new JMenu("Artículos");
        this.add(jmArticles);
        //Creo la opción para listar artículos y la añado al submenú correspondiente
        JMenuItem miListArticles = new JMenuItem("Listar artículos");
        miListArticles.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO listar artículos
                List<Article> articles = new ArrayList<>();
                articles.add(new Article(1L, "test"));
                articles.add(new Article(2L, "test2"));
                String[] fieldsToPrint = {"id", "name"};
                ListItemsWindow<Article> articlesWindow = new ListItemsWindow<>(articles, Article.class, fieldsToPrint);
            }
        });
        jmArticles.add(miListArticles);
        //Manejar usuarios - SOLO ADMIN
        if(loggedUser.isAdmin()) {
            JMenu jmUsers = new JMenu("Usuarios");
            JMenuItem miListUsers = new JMenuItem("Listar usuarios");
            miListUsers.addActionListener(evt -> {
                System.out.println("Listando usuarios");
                //TODO listar usuarios
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
