package view.menu;

import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MainMenu extends JMenuBar{

    public MainMenu(User user){
        //Creo el submenú de Artículos
        JMenu miArticles = new JMenu("Artículos");
        this.add(miArticles);
        //Creo la opción para listar artículos y la añado al submenú correspondiente
        JMenuItem miListArticles = new JMenuItem("Listar artículos");
        miListArticles.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO listar artículos
            }
        });
        miArticles.add(miListArticles);
        //Creo la opción de salir y la añado a la barra principal
        JMenuItem miSalir = new JMenuItem("Salir");
        miSalir.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.add(miSalir);
    }
}
