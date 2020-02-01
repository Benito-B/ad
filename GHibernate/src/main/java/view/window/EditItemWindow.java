package view.window;

import controller.dao.ArticleDAO;
import controller.dao.CategoryDAO;
import controller.dao.ClientDAO;
import controller.dao.PersistenceDAO;
import model.Article;
import model.Category;
import model.Client;
import model.EditableItem;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class EditItemWindow<T> extends JDialog {

    private Long id;
    private Class c;
    private JPanel base;
    private Map<String, JComponent> jFields;
    private PersistenceDAO dao;
    private Object receivedObject;
    private Window parent;

    public EditItemWindow(Object receivedObject, Window parent){
        this.parent = parent;
        if(parent != null)
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
        this.receivedObject = receivedObject;
        try{
            Field f = receivedObject.getClass().getDeclaredField("id");
            if(Modifier.isPrivate(f.getModifiers()))
                f.setAccessible(true);
            this.id = (Long)f.get(receivedObject);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if(receivedObject instanceof Category){
            this.c = Category.class;
            this.dao = new CategoryDAO();
        }else if(receivedObject instanceof Article){
            this.c = Article.class;
            this.dao = new ArticleDAO();
        }else{
            this.c = Client.class;
            this.dao = new ClientDAO();
        }
        init();
        if(id != null) {
            this.setTitle("Modificar objeto - " + c.getSimpleName());
            fillData();
        }else
            this.setTitle("Nuevo objeto - " + c.getSimpleName());
    }

    private void init(){
        this.jFields = new HashMap<>();
        base = new JPanel();
        base.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.add(base);
        GridLayout layout = new GridLayout(c.getDeclaredFields().length, 2);
        layout.setHgap(5);
        base.setLayout(layout);
        //System.out.println(Arrays.toString(c.getDeclaredFields()));
        for(Field f: c.getDeclaredFields()){
            PersistenceDAO innerDAO;
            if(!f.getName().equals("id")){
                if(Modifier.isPrivate(f.getModifiers()))
                    f.setAccessible(true);
                base.add(new JLabel(f.getName()));
                if(Number.class.isAssignableFrom(f.getType())){
                    SpinnerNumberModel model = new SpinnerNumberModel(5.0, 0.0, 999.99, 0.01);
                    JSpinner sp = new JSpinner(model);
                    jFields.put(f.getName(), sp);
                    base.add(sp);
                }else if(String.class.isAssignableFrom(f.getType())){
                    JTextField jtf = new JTextField();
                    base.add(jtf);
                    jFields.put(f.getName(), jtf);
                }else if(EditableItem.class.isAssignableFrom(f.getType())){
                    if(f.getType().equals(Category.class)) {
                        innerDAO = new CategoryDAO();
                    }else if(f.getType().equals(Client.class)){
                        innerDAO = new ClientDAO();
                    }else{
                        innerDAO = new ArticleDAO();
                    }
                    if(f.getType().equals(List.class)){
                        System.out.println("HEY, THIS IS A FUCKING LINE BRO");
                    }
                    JComboBox comboBox = new JComboBox(innerDAO.getAll().toArray());
                    base.add(comboBox);
                    jFields.put(f.getName(), comboBox);
                }
                //Print field type to debug
                //System.out.println("Field type: " + f.getType());
            }
        }
        JButton btBack = new JButton("Volver");
        btBack.addActionListener(e -> EditItemWindow.this.dispose());
        JButton btAccept = new JButton("Aceptar");
        btAccept.addActionListener(e -> {
            for(String key: jFields.keySet()){
                JComponent component = jFields.get(key);
                try{
                    Field f = receivedObject.getClass().getDeclaredField(key);
                    if(Modifier.isPrivate(f.getModifiers()))
                        f.setAccessible(true);
                    if (component instanceof JTextField) {
                        Object val = ((JTextField) component).getText();
                        f.set(receivedObject, val);
                    }else if(component instanceof JSpinner){
                        Object val = ((JSpinner)component).getValue();
                        f.set(receivedObject, BigDecimal.valueOf((double)val));
                    }else if(component instanceof JComboBox){
                        Object val = ((JComboBox)component).getSelectedItem();
                        f.set(receivedObject, val);
                    }
                } catch (IllegalAccessException | NoSuchFieldException ex) {
                    ex.printStackTrace();
                }
            }
            closeAndUpdate();
        });
        base.add(btBack);
        base.add(btAccept);
        pack();
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void closeAndUpdate(){
        dao.persist(receivedObject);
        if(parent instanceof ListItemsWindow) {
            parent.setEnabled(true);
            ((ListItemsWindow) parent).addItem(receivedObject);
            ((ListItemsWindow) parent).reloadData(dao.getAll());
        }
        dispose();
    }

    private void fillData() {
        Object o;
        o = dao.findById(id);
        System.out.println("Object: " + o);
        for(String key: jFields.keySet()){
            System.out.println("KEY: " + key);
            try {
                JComponent component = jFields.get(key);
                Field f = c.getDeclaredField(key);
                if(Modifier.isPrivate(f.getModifiers()))
                    f.setAccessible(true);
                if (component instanceof JTextField) {
                    ((JTextField) component).setText(String.valueOf(f.get(o)));
                }else if(component instanceof JSpinner){
                    if(f.get(o) instanceof BigDecimal)
                        ((JSpinner)component).setValue((((BigDecimal) f.get(o)).doubleValue()));
                    else
                        ((JSpinner)component).setValue(f.get(o));
                }else if(component instanceof JComboBox){
                    ((JComboBox)component).setSelectedItem(f.get(o));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
