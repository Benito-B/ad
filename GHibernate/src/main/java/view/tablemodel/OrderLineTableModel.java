package view.tablemodel;

import model.Article;

import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Vector;

/**
 * -GHibernate-
 *
 * @author Benito-B - 01/02/2020
 */
public class OrderLineTableModel extends DefaultTableModel {

    private final static String[] columnNames = {"Artículo", "Cantidad", "Precio", "Total"};

    public OrderLineTableModel(Object rowData[][]){
        super(rowData, columnNames);
        this.addTableModelListener(e -> {
            if(e.getColumn() > -1) {
                int row = e.getFirstRow();
                Vector oldVector = (Vector) getDataVector().elementAt(row);
                BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(oldVector.get(1).toString()));
                BigDecimal price = BigDecimal.valueOf(Double.parseDouble(oldVector.get(2).toString()));
                BigDecimal total = amount.multiply(price);
                oldVector.set(3, total.setScale(2, RoundingMode.HALF_UP).toString());
                getDataVector().set(row, oldVector);
                fireTableDataChanged();
            }
        });
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }



    @Override
    public boolean isCellEditable(int row, int column) {
        return column > 0 && column < getColumnCount() - 1;
    }
}
