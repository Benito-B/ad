package view.window;

import model.User;
import view.menu.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {

    /**
     * Constructor de la ventana. La inicia, le setea los valores por defecto y la pone visible.
     * Está a pantalla completa y si se consigue cambiar, estará con un tamaño que ocupa el espacio disponible
     */
    public MainWindow(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setAlwaysOnTop(true);
        this.setUndecorated(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //Cojo el entorno para poder obtener los valores de tamaño de la pantalla y se los seteo a la ventana
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        this.setMaximizedBounds(env.getMaximumWindowBounds());
        this.setSize(env.getMaximumWindowBounds().width, env.getMaximumWindowBounds().height);
        //También cojo el GraphicsDevice que me permite mostrar la ventana como fullscreen
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        device.setFullScreenWindow(this);
        this.setJMenuBar(new MainMenu(new User()));//TODO sustituir este user por uno sacado de la ventana de login
        this.setVisible(true);
    }
}
