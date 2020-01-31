package view.window;

import controller.dao.ArticleDAO;
import controller.dao.CategoryDAO;
import controller.dao.ClientDAO;
import controller.dao.PersistenceDAO;
import model.Article;
import model.Category;
import model.Client;
import model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

public class ListItemsWindow<T> extends JDialog {

    private List<T> items;
    private Class c;
    private List<String> fieldsToPrint;
    private JTable innerTable;
    private JScrollPane scrollPane;
    private PersistenceDAO dao;
    private JButton btDelete;

    public ListItemsWindow(List<T> items, Class c, String[] fieldsToPrint){
        if(c.equals(Category.class))
            dao = new CategoryDAO();
        else if(c.equals(Article.class))
            dao = new ArticleDAO();
        else if(c.equals(Client.class))
            dao = new ClientDAO();
        this.items = items;
        this.c = c;
        this.fieldsToPrint = Arrays.asList(fieldsToPrint);
        init();
        pack();
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        this.setTitle("Listando: " + c.getSimpleName());
    }

    private void init(){
        JPanel base = new JPanel();
        this.add(base);
        base.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        reloadData(items);
        scrollPane = new JScrollPane(innerTable);
        base.add(scrollPane, c);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        JButton backButton = new JButton("Volver");
        backButton.addActionListener(e -> {
            this.dispose();
        });
        buttonPanel.add(backButton);
        btDelete = new JButton("Borrar seleccionado");
        btDelete.addActionListener(e -> {
            int selectedRow = innerTable.getSelectedRow();
            if(selectedRow > 0)
                dao.delete(items.get(selectedRow));
        });
        btDelete.setEnabled(false);
        buttonPanel.add(btDelete);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        base.add(buttonPanel, c);
    }

    public void reloadData(List<T> items){
        try {
            String[][] data = new String[items.size()][fieldsToPrint.size()];
            //Building the data for the table
            int i = 0;
            for (T item : items) {
                int j = 0;
                for (String s : fieldsToPrint) {
                    Field f = c.getDeclaredField(s);
                    if (Modifier.isPrivate(f.getModifiers())) {
                        f.setAccessible(true);
                    }
                    String info = String.valueOf(f.get(item));
                    data[i][j] = info;
                    //                System.out.println("i: " + i + " j : " + j + " field: " + f.getName());
                    j++;
                }
                i++;
            }
            if (innerTable != null) {
                ((DefaultTableModel) innerTable.getModel()).setRowCount(0);
                ((DefaultTableModel) innerTable.getModel()).setDataVector(data, fieldsToPrint.toArray());
            }else{
                DefaultTableModel dtm = new DefaultTableModel(data, fieldsToPrint.toArray());
                innerTable = new JTable(dtm);
                innerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                innerTable.setFillsViewportHeight(true);
                innerTable.setDefaultEditor(Object.class, null);
                innerTable.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (e.getClickCount() != 2)
                            return;
                        JTable t = (JTable) e.getSource();
                        Object o = items.get(t.rowAtPoint(e.getPoint()));
                        EditItemWindow<T> itemWindow = new EditItemWindow<>(o, ListItemsWindow.this);
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(btDelete != null)
                            btDelete.setEnabled(innerTable.getSelectedRowCount() > 0);
                    }
                });
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
