
package SIG.Model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class InvoiceHeaderTable extends AbstractTableModel {
    private ArrayList<InvoiceHeader> invoices;

    private String []table_Columns = {"Inv. no","Date","Name","Total"};
    
    public InvoiceHeaderTable(ArrayList<InvoiceHeader> invoicess) {
        this.invoices = invoicess;
    }

    @Override
    public String getColumnName(int column) {
        return table_Columns[column] ; 
    }
    


    
    @Override
    public int getRowCount() {
       return invoices.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceHeader invoice = invoices.get(rowIndex);
        switch (columnIndex){
            case 0: return invoice.getNum();
            case 1: return invoice.getDate();
            case 2: return invoice.getCustomer();
            case 3: return invoice.getTotal_price();
            default:return "";
        }

        
    }
}
