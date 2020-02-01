package view.window;

import controller.RowFilterUtil;
import controller.dao.ArticleDAO;
import controller.dao.ClientDAO;
import controller.dao.OrderDAO;
import model.Article;
import model.Client;
import model.Order;
import view.tablemodel.OrderLineTableModel;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * -GHibernate-
 *
 * @author Benito-B - 01/02/2020
 */
public class NewOrderWindow extends JDialog {

    private final ArticleDAO articleDAO;
    private final OrderDAO dao;
    private final ClientDAO clientDAO;
    private final Window parent;
    private JPanel base;
    private JTable itemsTable;
    private JTable linesTable;
    private OrderLineTableModel linesModel;
    private final Order newOrder;

    public NewOrderWindow(Window parent){
        this.parent = parent;
        this.articleDAO = new ArticleDAO();
        this.clientDAO = new ClientDAO();
        this.dao = new OrderDAO();
        this.newOrder = new Order();
        this.setTitle("Nuevo pedido");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        build();
        this.setAlwaysOnTop(true);
        this.pack();
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
        base = new JPanel();
        base.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        DefaultTableModel dtm = new DefaultTableModel(buildArticlesData(articleDAO.getAll()), new String[]{"id", "name", "category", "price"});
        itemsTable = new JTable(dtm);
        itemsTable.setFillsViewportHeight(true);
        itemsTable.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(itemsTable);
        scrollPane.setPreferredSize(new Dimension(scrollPane.getSize().width,100));
        scrollPane.setMaximumSize(new Dimension(base.getSize().width,100));
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 10;
        gbc.ipady = 10;
        gbc.gridwidth = 3;
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        base.add(filterPanel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        base.add(new JLabel("Lista de artículos:"), gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        base.add(scrollPane, gbc);
        JTextField filterField = RowFilterUtil.createRowFilter(itemsTable);
        filterPanel.add(new JLabel("Filtro: \t"));
        filterPanel.add(filterField);
        JButton btClearFilter = new JButton("Borrar filtro");
        btClearFilter.addActionListener(e -> {
            filterField.setText("");
        });
        filterPanel.add(btClearFilter);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        base.add(new JLabel("Pedido"), gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.5;
        gbc.gridwidth = 1;
        base.add(new JLabel("Cliente:"), gbc);
        JComboBox cbClients = new JComboBox(clientDAO.getAll().toArray());
        cbClients.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                newOrder.setClient((Client)e.getItem());
            }
        });
        newOrder.setClient((Client)cbClients.getSelectedItem());
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 1.5;
        gbc.gridwidth = 1;
        base.add(cbClients, gbc);
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");
        base.add(new JLabel(LocalDateTime.now().format(formatter) + "    ", SwingConstants.RIGHT), gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        linesModel = new OrderLineTableModel(null);
        linesTable = new JTable(linesModel);
        JScrollPane linesScrollPane = new JScrollPane(linesTable);
        base.add(linesScrollPane, gbc);
        linesModel.addRow(new String[]{"Test?", "4.44", "2", "8.88"});
        this.add(base);
    }

    private String[][] buildArticlesData(List<Article> articles){
        String[][] data = new String[articles.size()][4];
        int i = 0;
        for(Article a: articles){
            data[i][0] = a.getId().toString();
            data[i][1] = a.getName();
            data[i][2] = a.getCategory().getName();
            data[i][3] = a.getPrice().toString();
            i++;
        }
        return data;
    }
}
