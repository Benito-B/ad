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
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class EditItemWindow<T> extends JDialog {

    private long id;
    private Class c;
    private JPanel base;
    private Map<String, JComponent> jFields;
    private PersistenceDAO dao;

    public EditItemWindow(Object o){
        if(o instanceof Category){
            this.id = ((Category)o).getId();
            this.c = Category.class;
        }else if(o instanceof Article){
            this.id = ((Article)o).getId();
            this.c = Article.class;
        }else{
            this.id = ((Client)o).getId();
            this.c = Client.class;
        }
        this.setTitle("Modificar objeto - " + c.getSimpleName());
        init();
        fillData();
    }

    public EditItemWindow(Class c){
        this.c = c;
        System.out.println(c.getSimpleName());
        this.setTitle("Crear objeto - " + c.getSimpleName());
        init();
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
            if(!f.getName().equals("id")){
                if(Modifier.isPrivate(f.getModifiers()))
                    f.setAccessible(true);
                base.add(new JLabel(f.getName()));
                if(Number.class.isAssignableFrom(f.getType())){
                    SpinnerNumberModel model = new SpinnerNumberModel(5.0, 0.0, 999.99, 1);
                    JSpinner sp = new JSpinner(model);
                    jFields.put(f.getName(), sp);
                    base.add(sp);
                }else if(String.class.isAssignableFrom(f.getType())){
                    JTextField jtf = new JTextField();
                    base.add(jtf);
                    jFields.put(f.getName(), jtf);
                }else if(EditableItem.class.isAssignableFrom(f.getType())){
                    if(f.getType().equals(Category.class)) {
                        dao = new CategoryDAO();
                    }else if(f.getType().equals(Client.class)){
                        dao = new ClientDAO();
                    }else{
                        dao = new ArticleDAO();
                    }
                    JComboBox comboBox = new JComboBox(dao.getAll().toArray());
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
                System.out.println(key);
            }
        });
        base.add(btBack);
        base.add(btAccept);
        pack();
        this.setAlwaysOnTop(true);
        this.setVisible(true);
    }

    private void fillData() {
        Object o;
        o = dao.findById(id);
        System.out.println(o);
        for(String key: jFields.keySet()){
            try {
                JComponent component = jFields.get(key);
                Field f = c.getDeclaredField(key);
                if(Modifier.isPrivate(f.getModifiers()))
                    f.setAccessible(true);
                if (component instanceof JTextField)
                    ((JTextField) component).setText(String.valueOf(f.get(o)));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
