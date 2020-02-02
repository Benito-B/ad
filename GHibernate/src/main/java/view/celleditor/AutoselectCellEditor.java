package view.celleditor;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

/**
 * -GHibernate-
 *
 * @author Benito-B - 02/02/2020
 */
public class AutoselectCellEditor extends DefaultCellEditor {

    public AutoselectCellEditor(JTextField textField) {
        super(textField);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        Component c = super.getTableCellEditorComponent(table, value, isSelected, row, column);
        if(c instanceof JTextComponent){
            c.requestFocus();
            SwingUtilities.invokeLater(() -> ((JTextComponent) c).selectAll());
        }
        return c;
    }


}
