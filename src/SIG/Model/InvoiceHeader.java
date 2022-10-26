
package SIG.Model;

import java.util.ArrayList;


public class InvoiceHeader {
    private int num;
    private String customer;
    private String date;
    private double total_price;
    private ArrayList<InvoiceLines>lines;

    public InvoiceHeader(int num, String customer, String date) {
        this.num = num;
        this.customer = customer;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public ArrayList<InvoiceLines> getLines() {
        if(lines==null){
            lines=new ArrayList<>(); }

        return lines;
    }

    public void setLines(ArrayList<InvoiceLines> lines) {
        this.lines = lines;
    }

    public double getTotal_price() {
        double total=0;
        if(this.lines != null){
            for (InvoiceLines line : lines) {
                total=total+ line.getTotal_line();
            }
        }
        return total;
    }


    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String saveCsv() {
        return num +","+ date+","+customer ;
    }





}
