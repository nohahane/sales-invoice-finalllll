
package SIG.View;

import javax.swing.*;
import java.awt.*;


public class InvoiceDialog extends JDialog {
    
    private JTextField customerField;
    private JTextField dateField;
    private JLabel customerLab1;
    private JLabel dateLab1 ;
    private JButton okBtn ;
    private JButton cancelBtn ;        
            
    public InvoiceDialog(SalesInvoiceWindow fr){
        customerLab1 = new JLabel ("customer: ");
        customerField = new JTextField(15);
        dateLab1 = new JLabel ("Date");
        dateField = new JTextField(40);
        okBtn = new JButton ("ok");
        cancelBtn = new JButton("cancel");
        
        okBtn.setActionCommand("InvoiceCreated");
        cancelBtn.setActionCommand("NoInvoiceCreated");
        okBtn.addActionListener(fr.getHandler());
        cancelBtn.addActionListener(fr.getHandler());
        setLayout(new GridLayout(3,2));
        
    add(customerField);
    add(dateField) ;
    add(customerLab1);
    add(dateLab1) ;
    add(okBtn) ;
    add(cancelBtn);
            pack();
    }

    public JTextField getCustomerField() {
        return customerField;
    }

    public JTextField getDateField() {
        return dateField;
    }

    public JLabel getCustomerLab1() {
        return customerLab1;
    }

    public JLabel getDateLab1() {
        return dateLab1;
    }

    public JButton getOkBtn() {
        return okBtn;
    }

    public JButton getCancelBtn() {
        return cancelBtn;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
