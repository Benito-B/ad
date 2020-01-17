package view.window;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

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
        try{
            init();
        }catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            System.out.println("[ERROR] Reflection no pudo acceder al campo: " + e.getMessage());
        }
        pack();
        this.setAlwaysOnTop(true);
        this.setVisible(true);
    }

    private void init() throws IllegalAccessException, NoSuchFieldException {
        JPanel base = new JPanel();
        this.add(base);
        base.setLayout(new FlowLayout());
        String[][] data = new String[items.size()][fieldsToPrint.size()+1];
        //Building the data for the table
        int i = 0;
        for(T item : items){
            int j = 0;
            for(String s: fieldsToPrint){
                Field f = c.getDeclaredField(s);
                if(Modifier.isPrivate(f.getModifiers())) {
                    f.setAccessible(true);
                }
                String info = String.valueOf(f.get(item));
                data[i][j] = info;
                System.out.println("i: " + i + " j : " + j + " field: " + f.getName());
                j++;
            }
            i++;
        }
        innerTable = new JTable(data, fieldsToPrint.toArray());
        innerTable.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(innerTable);
        base.add(scrollPane);
    }
}
