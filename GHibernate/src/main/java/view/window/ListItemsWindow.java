package view.window;

import model.Article;
import model.Category;
import model.Client;
import model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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

    public ListItemsWindow(List<T> items, Class c, String[] fieldsToPrint){
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
        base.setLayout(new FlowLayout());
        reloadData(items);
        scrollPane = new JScrollPane(innerTable);
        base.add(scrollPane);
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


                });
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
