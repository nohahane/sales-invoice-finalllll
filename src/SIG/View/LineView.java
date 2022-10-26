
package SIG.View;

import javax.swing.*;
import java.awt.*;

public class LineView extends JDialog {
    
    private JTextField itemField;
    private JTextField countField;
    private JTextField priceField;
    private JLabel itemLab1;
    private JLabel countLab1 ;
    private JLabel priceLab1 ;
    private JButton okBtn ;
    private JButton cancelBtn ;  
    
    
    public LineView(SalesInvoiceWindow fr){
        itemField = new JTextField();
        itemLab1 = new JLabel("item");
        countField = new JTextField();
        countLab1 = new JLabel("count");
        priceField = new JTextField();
        priceLab1 = new JLabel("price");
        okBtn = new JButton("ok");
        cancelBtn = new JButton("cancel");
        
        okBtn.setActionCommand("LineCreated");
        cancelBtn.setActionCommand("NoLineCreated");
        
        okBtn.addActionListener(fr.getHandler());
        cancelBtn.addActionListener(fr.getHandler());
        setLayout(new GridLayout(4, 2));
        
        add(itemLab1);
        add(itemField);
        add(countLab1);
        add(countField);
        add(priceField);
        add(priceLab1);
        add(okBtn);
        add(cancelBtn);
        
        pack();
    }

    public JTextField getItemField() {
        return itemField;
    }

    public JTextField getCountField() {
        return countField;
    }

    public JTextField getPriceField() {
        return priceField;
    }

   
}
