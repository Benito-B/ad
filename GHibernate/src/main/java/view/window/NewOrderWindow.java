package view.window;

import controller.RowFilterUtil;
import controller.dao.ArticleDAO;
import controller.dao.ClientDAO;
import controller.dao.OrderDAO;
import model.Article;
import model.Client;
import model.Order;
import model.OrderLine;
import view.celleditor.AutoselectCellEditor;
import view.tablemodel.OrderLineTableModel;

import java.awt.event.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

/**
 * -GHibernate-
 *
 * @author Benito-B - 01/02/2020
 */
public class NewOrderWindow extends JDialog {

    private final ArticleDAO articleDAO;
    private final OrderDAO dao;
    private final ClientDAO clientDAO;
    private Window parent;
    private final List<Article> articles;
    private JPanel base;
    private JTable itemsTable;
    private JTable linesTable;
    private OrderLineTableModel linesModel;
    private Order newOrder;

    public NewOrderWindow(Window parent){
        this.parent = parent;
        this.articleDAO = new ArticleDAO();
        this.clientDAO = new ClientDAO();
        this.dao = new OrderDAO();
        this.newOrder = new Order();
        this.articles = articleDAO.getAll();
        this.setTitle("Nuevo pedido");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        build();
        this.setAlwaysOnTop(true);
        this.pack();
        parent.setEnabled(false);
        this.addWindowListener(new WindowAdapter() {

            //This is called when closing the window from the X
            @Override
            public void windowClosing(WindowEvent e) {
                if(parent != null)
                    parent.setEnabled(true);
            }

            //This is called when disposing the window
            @Override
            public void windowClosed(WindowEvent e) {
                if(parent != null)
                    parent.setEnabled(true);
            }

        });
        this.setVisible(true);
    }

    private void build(){
        base = new JPanel();
        base.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        DefaultTableModel dtm = new DefaultTableModel(buildArticlesData(articles), new String[]{"id", "name", "category", "price"});
        itemsTable = new JTable(dtm);
        itemsTable.setFillsViewportHeight(true);
        itemsTable.setDefaultEditor(Object.class, null);
        itemsTable.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getClickCount() != 2)
                    return;
                int clickedRow = itemsTable.rowAtPoint(e.getPoint());
                if(clickedRow == -1)
                    return;
                linesModel.addRow(new Object[]{ articles.get(clickedRow),
                                    1, itemsTable.getValueAt(clickedRow, 3),
                                    itemsTable.getValueAt(clickedRow, 3)});
            }
        });
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
        cbClients.addItemListener(e -> newOrder.setClient((Client)e.getItem()));
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
        newOrder.setDate(LocalDateTime.now());
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        linesModel = new OrderLineTableModel(null);
        linesTable = new JTable(linesModel);
        linesTable.setFillsViewportHeight(true);
        //Modifico el cellEditor de esas dos columnas para que sea el que auto selecciona al editar
        linesTable.getColumnModel().getColumn(1).setCellEditor(new AutoselectCellEditor(new JTextField()));
        linesTable.getColumnModel().getColumn(2).setCellEditor(new AutoselectCellEditor(new JTextField()));
        JScrollPane linesScrollPane = new JScrollPane(linesTable);
        linesScrollPane.setPreferredSize(new Dimension(scrollPane.getSize().width,150));
        linesScrollPane.setMaximumSize(new Dimension(base.getSize().width,150));
        base.add(linesScrollPane, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        JButton btBack = new JButton("Cancelar");
        btBack.addActionListener(e -> {
            newOrder = null;
            closeAndUpdate();
        });
        base.add(btBack, gbc);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        JButton btConfirm = new JButton("Confirmar");
        btConfirm.addActionListener(e -> {
            for(Vector v: (Vector<Vector>)linesModel.getDataVector()){
                OrderLine orderLine = new OrderLine(newOrder);
                orderLine.setArticulo((Article)v.get(0));
                orderLine.setPrice(BigDecimal.valueOf(Double.parseDouble(v.get(2).toString())));
                orderLine.setAmount(BigDecimal.valueOf(Double.parseDouble(v.get(1).toString())));
            }
            closeAndUpdate();
        });
        base.add(btConfirm, gbc);
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

    private void closeAndUpdate(){
        parent.setEnabled(true);
        System.out.println(parent.getClass());
        if(parent instanceof ListItemsWindow) {
            System.out.println("UPDATIN'");
            ((ListItemsWindow) parent).addItem(newOrder);
            ((ListItemsWindow) parent).reloadData(dao.getAll());
        }
        if(newOrder != null) {
            dao.persist(newOrder);
        }
        dispose();
    }
}
