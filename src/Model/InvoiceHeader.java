package Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InvoiceHeader {

    private int invoiceNumber;
    private String customerName;
    private Date invoiceDate;
  
    private ArrayList<InvoiceLine> allInvoiceLines;

    public InvoiceHeader(int invNum, String cusName, Date invDate) {
        this.invoiceNumber = invNum;
        this.customerName = cusName;
        this.invoiceDate = invDate;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
  
    @Override
    public String toString() {
        String str = "InvoiceHeader{" + "invNum=" + invoiceNumber + ", customerName=" + customerName + ", invDate=" + invoiceDate + '}';
        for (InvoiceLine line : getAllInvoiceLines()) {
            str += "\n\t" + line;
        }
        return str;
    }
    

    public ArrayList<InvoiceLine> getAllInvoiceLines() {
        if (allInvoiceLines == null) {
            allInvoiceLines = new ArrayList<>();
        }
        return allInvoiceLines;
    }

    public void setAllInvoiceLines(ArrayList<InvoiceLine> allInvoiceLines) {
        this.allInvoiceLines = allInvoiceLines;
    }
    
        public double getInvTotal() {
        double total = 0.0;
        for (InvoiceLine line : getAllInvoiceLines()) {
            total += line.getLineTotal();
        }
        return total;
    }
    
    public void addLine(InvoiceLine line) {
        getAllInvoiceLines().add(line);
    }
    
        public String getDataAsCSV() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return "" + getInvoiceNumber() + "," + df.format(getInvoiceDate()) + "," + getCustomerName();
    }
}