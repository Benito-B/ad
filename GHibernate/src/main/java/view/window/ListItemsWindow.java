package view.window;

import controller.RowFilterUtil;
import controller.Utils;
import controller.dao.*;
import model.*;
import view.dialog.MessageDialogHelper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class ListItemsWindow<T> extends JDialog {

    private List<T> items;
    private Class c;
    private List<String> fieldsToPrint;
    private JTable innerTable;
    private DefaultTableModel detailTableModel;
    private JScrollPane scrollPane;
    private PersistenceDAO dao;
    private JButton btDelete;
    private User loggedUser;
    private JPanel base;

    public ListItemsWindow(List<T> items, Class c, String[] fieldsToPrint, User loggedUser){
        if(c.equals(Category.class))
            dao = new CategoryDAO();
        else if(c.equals(Article.class))
            dao = new ArticleDAO();
        else if(c.equals(Client.class))
            dao = new ClientDAO();
        else if(c.equals(Order.class))
            dao = new OrderDAO();
        this.items = items;
        this.c = c;
        this.fieldsToPrint = Arrays.asList(fieldsToPrint);
        this.loggedUser = loggedUser;
        init();
        pack();
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        this.setResizable(false);
        this.setTitle("Listando: " + c.getSimpleName());
    }

    private void init(){
        base = new JPanel();
        this.add(base);
        base.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        reloadData(items);
        scrollPane = new JScrollPane(innerTable);
        scrollPane.setPreferredSize(new Dimension(scrollPane.getSize().width,250));
        scrollPane.setMaximumSize(new Dimension(base.getSize().width,250));
        base.add(scrollPane, gbc);
        if(c.equals(Order.class)){
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridx = 0;
            gbc.gridy = 2;
            detailTableModel = new DefaultTableModel();
            JTable detailTable = new JTable(detailTableModel);
            detailTable.setFillsViewportHeight(true);
            JScrollPane detailScrollPane = new JScrollPane(detailTable);
            detailScrollPane.setPreferredSize(new Dimension(scrollPane.getSize().width,200));
            detailScrollPane.setMaximumSize(new Dimension(base.getSize().width,200));
            base.add(detailScrollPane, gbc);
        }
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        JButton newItemButton = new JButton("Nuevo objeto");
        newItemButton.addActionListener(e -> {
            try {
                if(c.equals(Order.class)) {
                    NewOrderWindow newOrderWindow = new NewOrderWindow(ListItemsWindow.this);
                    newOrderWindow.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            ListItemsWindow.this.reloadData(items);
                        }
                    });
                }
                else
                    new EditItemWindow<>(c.newInstance(), ListItemsWindow.this);
            } catch (InstantiationException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        });
        buttonPanel.add(newItemButton);
        btDelete = new JButton("Borrar seleccionado");
        btDelete.addActionListener(e -> {
            if(Utils.isAdminOnly(c) && !loggedUser.isAdmin()){
                MessageDialogHelper.ShowErrorMessage("Acceso restringido", "Solo administradores.");
                return;
            }
            int selectedRow = innerTable.getSelectedRow();
            selectedRow = innerTable.convertRowIndexToModel(selectedRow);
            if(selectedRow > -1 && selectedRow < innerTable.getModel().getRowCount()) {
                T object = items.get(selectedRow);
                ((DefaultTableModel)innerTable.getModel()).removeRow(selectedRow);
                dao.delete(object);
                items.remove(object);
                reloadData(items);
            }
        });
        btDelete.setEnabled(false);
        buttonPanel.add(btDelete);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 3;
        base.add(buttonPanel, gbc);
    }

    public void addItem(T item){
        items.add(item);
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
                    String info = "";
                    if(LocalDateTime.class.isAssignableFrom(f.getType())){
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");
                        info = ((LocalDateTime)f.get(item)).format(formatter);
                    }else {
                        info = String.valueOf(f.get(item));
                    }
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
                JTextField filterField = RowFilterUtil.createRowFilter(innerTable);
                GridBagConstraints gbc = new GridBagConstraints();
                JPanel filterPanel = new JPanel();
                filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                gbc.fill = GridBagConstraints.BOTH;
                gbc.gridx = 0;
                gbc.gridy = 0;
                base.add(filterPanel, gbc);
                filterPanel.add(new JLabel("Filtro: \t"));
                filterPanel.add(filterField);
                JButton btClearFilter = new JButton("Borrar filtro");
                btClearFilter.addActionListener(e -> {
                    filterField.setText("");
                });
                filterPanel.add(btClearFilter);
                innerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                innerTable.setFillsViewportHeight(true);
                innerTable.setDefaultEditor(Object.class, null);
                innerTable.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if(c.equals(Order.class) && e.getClickCount() == 1){
                            int pos = innerTable.convertRowIndexToModel(innerTable.rowAtPoint(e.getPoint()));
                            Object o = items.get(pos);
                            showLinesData((Order)o);
                        }
                        if (e.getClickCount() != 2 || c.equals(Order.class))
                            return;
                        int pos = innerTable.convertRowIndexToModel(innerTable.rowAtPoint(e.getPoint()));
                        Object o = items.get(pos);
                        new EditItemWindow<>(o, ListItemsWindow.this);
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

    private void showLinesData(Order order){
        String[] columns = {"id", "Artículo", "Precio", "Cantidad", "Total"};
        String[][] data = new String[order.getOrderLines().size()][5];
        int i = 0;
        for(OrderLine ol: order.getOrderLines()){
            data[i][0] = ol.getId().toString();
            data[i][1] = ol.getArticulo().getName();
            data[i][2] = ol.getPrice().toString();
            data[i][3] = ol.getAmount().toString();
            data[i][4] = ol.getImporte().toString();
            i++;
        }
        detailTableModel.setRowCount(0);
        detailTableModel.setDataVector(data, columns);
    }

}
