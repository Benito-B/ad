package view.tablemodel;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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
        this.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if(e.getColumn() > -1) {
                    int row = e.getFirstRow();
                    Vector oldVector = (Vector) getDataVector().elementAt(row);
                    BigDecimal amount = BigDecimal.valueOf(Double.parseDouble((String)oldVector.get(1)));
                    BigDecimal price = BigDecimal.valueOf(Double.parseDouble((String)oldVector.get(2)));
                    BigDecimal total = amount.multiply(price);
                    oldVector.set(3, total.setScale(2, RoundingMode.HALF_UP).toString());
                    getDataVector().set(row, oldVector);
                    fireTableDataChanged();
                }
            }
        });
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column > 0 && column < getColumnCount() - 1;
    }
}
