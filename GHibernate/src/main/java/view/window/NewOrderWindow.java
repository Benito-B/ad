package view.window;

import controller.dao.ArticleDAO;
import controller.dao.OrderDAO;

import javax.swing.*;

/**
 * -GHibernate-
 *
 * @author Benito-B - 01/02/2020
 */
public class NewOrderWindow extends JDialog {

    private final ArticleDAO articleDAO;
    private final OrderDAO orderDAO;

    public NewOrderWindow(){
        this.articleDAO = new ArticleDAO();
        this.orderDAO = new OrderDAO();
        this.setTitle("Nuevo pedido");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.pack();
        build();
        this.setVisible(true);
    }

    private void build(){

    }
}
