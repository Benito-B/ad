package view.window;

import controller.dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginWindow extends JFrame {

    private JPanel panel;

    public LoginWindow(){
        super("CutreJest - Login");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        addButtons();
        setContentPane(panel);
        pack();
        this.setVisible(true);
    }

    private void addButtons() {
        JLabel lbUser = new JLabel("Usuario: ");
        lbUser.setSize(100, 30);
        panel.add(lbUser);
        JTextField tfUser = new JTextField();
        tfUser.setPreferredSize(new Dimension(200,30));
        panel.add(tfUser);
        JLabel lbPass = new JLabel("Contraseña: ");
        lbPass.setSize(100, 30);
        lbPass.setPreferredSize(new Dimension(100, 30));
        panel.add(lbPass);
        JPasswordField tfPass = new JPasswordField();
        tfPass.setPreferredSize(new Dimension(200,30));
        panel.add(tfPass);
        JButton btExit = new JButton("Salir");
        btExit.setSize(100, 30);
        btExit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(btExit);
        JButton btLogin = new JButton("Entrar");
        btLogin.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserDAO userDAO = new UserDAO();
                User loginUser = new User();
                loginUser.setUsername(tfUser.getText().trim());
                loginUser.setPassword(String.valueOf(tfPass.getPassword()));
                User logged = userDAO.login(loginUser);
                if(logged != null){
                    MainWindow w = new MainWindow(logged);
                    LoginWindow.this.dispose();
                }
            }
        });
        panel.add(btLogin);
    }
}
