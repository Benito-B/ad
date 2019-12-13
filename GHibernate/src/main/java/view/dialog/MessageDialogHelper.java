package view.dialog;

import javax.swing.*;

public class MessageDialogHelper {

    public static void ShowErrorMessage(String msg, String title){
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
    }
}
