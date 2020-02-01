package view.window;

import controller.dao.ArticleDAO;
import controller.dao.OrderDAO;
import model.Order;

import javax.swing.*;
import java.awt.*;

/**
 * -GHibernate-
 *
 * @author Benito-B - 01/02/2020
 */
public class NewOrderWindow extends JDialog {

    private final ArticleDAO articleDAO;
    private final OrderDAO dao;
    private final Window parent;

    public NewOrderWindow(Window parent){
        this.parent = parent;
        this.articleDAO = new ArticleDAO();
        this.dao = new OrderDAO();
        this.setTitle("Nuevo pedido");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.pack();
        build();
        this.setVisible(true);
    }

    private void closeAndUpdate(Order order){
        dao.persist(order);
        if(parent instanceof ListItemsWindow) {
            parent.setEnabled(true);
            ((ListItemsWindow) parent).addItem(order);
            ((ListItemsWindow) parent).reloadData(dao.getAll());
        }
        dispose();
    }

    private void build(){

    }
}
