
package SIG.Controller;

import SIG.Model.InvoiceHeader;
import SIG.Model.InvoiceLines;
import SIG.Model.InvoiceLinesTable;
import SIG.Model.InvoiceHeaderTable;
import SIG.View.InvoiceDialog;
import SIG.View.SalesInvoiceWindow;
import SIG.View.LineView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class Controller implements ActionListener, ListSelectionListener {

    private InvoiceDialog invDialog;
    private LineView lineDialog;
    private SalesInvoiceWindow frame;
    private  int selectHeaderline = -1 ;
    public Controller(SalesInvoiceWindow frame){
        this.frame=frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action Handling called!");
        switch (e.getActionCommand()) {
            case "New Invoice":
                System.out.println("New invoice");
                newInvoice();
                break;

            case "Delete Invoice":
                System.out.println("Delete invoice");
                deleteInvoice();
                break;

            case "Add Item":
                System.out.println("Add Item");
                addItem();
                break;

            case "Delete Item":
                System.out.println("Delete Item");
                deleteItem();
                break;


            case "Load file":
                System.out.println("Load file");
                loadFile();
                break;

            case "Save file":
                System.out.println("Save file");
                saveFile();
                break;

            case "InvoiceCreated":
                System.out.println("InvoiceCreated");
                InvoiceCreated();
                break;

            case "NoInvoiceCreated":
                System.out.println("NoInvoiceCreated");
                NoInvoiceCreated();
                break;

            case "LineCreated":
                System.out.println("LineCreated");
                LineCreated();
                break;

            case "NoLineCreated":
                System.out.println("NoLineCreated");
                NoLineCreated();
                break;


            default:
                throw new AssertionError();
        }


    }

    private void newInvoice() {
        invDialog = new InvoiceDialog(frame);
        invDialog.setVisible(true);

    }



    private void deleteInvoice() {
        int rowChoosen= frame.getHeaderTable().getSelectedRow();
        if (rowChoosen != -1){
            frame.getInvoicess().remove(rowChoosen);
            frame.getTableView().fireTableDataChanged();
        }
    }




    private void addItem() {
        lineDialog = new LineView(frame);
        lineDialog.setVisible(true);
    }

    private void deleteItem() {
        //int invoiceSelected = frame.getHeaderTable().getSelectedRow();
        int rowSelected = frame.getLineTable().getSelectedRow();

        if ( selectHeaderline > -1 && rowSelected != -1){
            InvoiceHeader invoiceHeader = frame.getInvoicess().get(selectHeaderline);
            invoiceHeader.getLines().remove(rowSelected);
            InvoiceLinesTable lineTableView = new InvoiceLinesTable(invoiceHeader.getLines());
            frame.getLineTable().setModel(lineTableView);

            lineTableView.fireTableDataChanged();
            frame.getTotalLabel().setText(""+invoiceHeader.getTotal_price());
            frame.getTableView().fireTableDataChanged();
        }
        else
        {
            System.out.println("please select Header row and invoice");
        }

    }


    private void saveFile() {
        ArrayList<InvoiceHeader> invoices = frame.getInvoicess();
        String invoiceHeader = "" ;
        String invoiceLines = "" ;
        for (InvoiceHeader invoiceHeaderss : invoices) {
            String csvInvoice = invoiceHeaderss.saveCsv();
            invoiceHeader = invoiceHeader + csvInvoice;
            invoiceHeader = invoiceHeader + "\n";

            for (InvoiceLines invoiceLine : invoiceHeaderss.getLines()) {
                String csvLine = invoiceLine.saveCsv();
                invoiceLines = invoiceLines + csvLine;
                invoiceLines = invoiceLines + "\n";
            }
        }
        try {
            JFileChooser fileChooser = new JFileChooser();
            int x = fileChooser.showSaveDialog(frame);
            if(x == JFileChooser.APPROVE_OPTION) {
                File invHeaderFile = fileChooser.getSelectedFile();
                FileWriter fileWriterHeader = new FileWriter(invHeaderFile);
                fileWriterHeader.write(invoiceHeader);
                fileWriterHeader.flush();
                fileWriterHeader.close();

                x = fileChooser.showSaveDialog(frame);
                if(x == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fileChooser.getSelectedFile();
                    FileWriter fileWriterLine = new FileWriter(lineFile);
                    fileWriterLine.write(invoiceLines);
                    fileWriterLine.flush();
                    fileWriterLine.close();
                }
            }
        }catch (Exception e) {
            // TODO: handle exception
        }
    }




    @Override
    public void valueChanged(ListSelectionEvent e) {
        int Index =frame.getHeaderTable().getSelectedRow();
        if (Index!= -1){
            selectHeaderline  =Index;
            InvoiceHeader selectedInvoice = frame.getInvoicess().get(Index);
            System.out.println("selection happened");

            frame.getNumLabel().setText(""+selectedInvoice.getNum());
            frame.getDateLabel().setText(""+selectedInvoice.getDate());
            frame.getCustomerLabel().setText(""+selectedInvoice.getCustomer());
            frame.getTotalLabel().setText(""+selectedInvoice.getTotal_price());
            InvoiceLinesTable lineTableView= new InvoiceLinesTable(selectedInvoice.getLines());
            frame.getLineTable().setModel(lineTableView);
            lineTableView.fireTableDataChanged();
        }
    }


    private void loadFile() {
        JFileChooser fChooser = new JFileChooser();
        try {
            int selection=fChooser.showOpenDialog(null);
            if(selection==JFileChooser.APPROVE_OPTION){
                File headerf=fChooser.getSelectedFile();
                Path headerPath=Paths.get(headerf.getAbsolutePath());
                List<String>headerLines = Files.readAllLines(headerPath);
                System.out.println("file read");
                ArrayList<InvoiceHeader>inv = new ArrayList<>();
                for(String headerline : headerLines){
                    try {
                        String[] splits = headerline.split(",");
                        int invNum = Integer.parseInt(splits[0]);
                        String invDate = splits[1];
                        String name = splits[2];
                        InvoiceHeader invoice = new InvoiceHeader(invNum, name, invDate);
                        inv.add(invoice);
                    }catch (Exception e){
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(frame,"please load correct file format","Error",JOptionPane.ERROR_MESSAGE);

                    }
                }

                selection=fChooser.showOpenDialog(null);
                if(selection==JFileChooser.APPROVE_OPTION){
                    File line = fChooser.getSelectedFile();
                    Path linePath = Paths.get(line.getAbsolutePath());
                    List<String>listLines=Files.readAllLines(linePath);
                    for (String listLine : listLines) {
                        try {
                            String[] lineSplit = listLine.split(",");
                            int num = Integer.parseInt(lineSplit[0]);
                            String product = lineSplit[1];
                            int price = Integer.parseInt(lineSplit[2]);
                            int count = Integer.parseInt(lineSplit[3]);
                            InvoiceHeader Inv = null;
                            for (InvoiceHeader invoice : inv) {
                                if (invoice.getNum() == num) {
                                    Inv = invoice;
                                    break;
                                }
                            }
                            InvoiceLines linesss = new InvoiceLines(num, product, price, count, Inv);
                            Inv.getLines().add(linesss);
                        }catch (Exception e){
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(frame,"please load correct file format","Error",JOptionPane.ERROR_MESSAGE);

                        }
                    }

                }

                frame.setInvoicess(inv);
                InvoiceHeaderTable tableView = new InvoiceHeaderTable(inv);
                frame.setTableView(tableView);
                frame.getHeaderTable().setModel(tableView);
                frame.getTableView().fireTableDataChanged();
            }

        }catch (IOException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame,"please load correct file format","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void InvoiceCreated() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = invDialog.getDateField().getText();
        String name = invDialog.getCustomerField().getText();
        int invNumber = frame.getMaxNumber();
try {
    dateFormat.parse(date);
    InvoiceHeader invoiceHeader = new InvoiceHeader(invNumber, name, date);
    frame.getInvoicess().add(invoiceHeader);
    frame.getTableView().fireTableDataChanged();
    invDialog.setVisible(false);
    invDialog.dispose();
    invDialog = null;
}catch (ParseException ex){
    JOptionPane.showMessageDialog(frame,"please enter correct date format","Error", JOptionPane.ERROR_MESSAGE);
}

    }

    private void NoInvoiceCreated() {
        invDialog.setVisible(false);
        invDialog.dispose();
        invDialog=null;
    }

    private void LineCreated() {

        String item = lineDialog.getItemField().getText();
        String count = lineDialog.getCountField().getText();
        String price = lineDialog.getPriceField().getText();
        int countUpd = 0;
        double  priceUpd =0;
        try {
            countUpd = Integer.parseInt(count);
            priceUpd = Double.parseDouble(price);
        }
        catch (NumberFormatException xe) {
            System.out.println("Please insert A valid count number or price");
            return;
        }


        int invoiceSelected = frame.getHeaderTable().getSelectedRow();
        if (invoiceSelected != -1){
            InvoiceHeader invoiceHeader= frame.getInvoicess().get(invoiceSelected);
            // InvoiceHeader selectedInvoice = frame.getInvoicess().get(Index);

            InvoiceLines invoiceLine = new InvoiceLines(item, priceUpd, countUpd, invoiceHeader);
            invoiceHeader.getLines().add(invoiceLine);
            InvoiceLinesTable lineTableView = (InvoiceLinesTable) frame.getLineTable().getModel();
            frame.getTotalLabel().setText(""+invoiceHeader.getTotal_price());
            lineTableView.fireTableDataChanged();
            frame.getTableView().fireTableDataChanged();
        }

        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null ;
    }

    private void NoLineCreated() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null ;
    }











}
