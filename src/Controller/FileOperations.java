/*
 * To change this license inv, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.InvHeaderModel;
import Model.InvoiceHeader;
import Model.InvoiceLine;
import View.InvoiceMainFrame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class FileOperations {
    
    private InvoiceMainFrame frameRef;
    private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    
    public FileOperations(InvoiceMainFrame frame) {
        this.frameRef = frame;
    }
    
    public void loadFiles() throws Exception {
        frameRef.getFilesData().clear();
        JOptionPane.showMessageDialog(frameRef, "Please select Invoice header file", "Invoice Header", JOptionPane.WARNING_MESSAGE);
        JFileChooser selectedFile = new JFileChooser();
        int result = selectedFile.showOpenDialog(frameRef);
        if (result == JFileChooser.APPROVE_OPTION) {
            File hFile = selectedFile.getSelectedFile();
      
            FileReader headerFr = new FileReader(hFile);
            BufferedReader headerBr = new BufferedReader(headerFr);
            String headerLine = null;
           
            while ((headerLine = headerBr.readLine()) != null) {
                String[] headerSegments = headerLine.split(",");
                String invNumStr = headerSegments[0];
                String invDateStr = headerSegments[1]; // "22-11-2020"
                String custName = headerSegments[2];
               
                int invNum = Integer.parseInt(invNumStr);
                Date invDate = df.parse(invDateStr);
               
                InvoiceHeader inv = new InvoiceHeader(invNum, custName, invDate);
                frameRef.getFilesData().add(inv); 
            }
            headerBr.close();
            headerFr.close();
            System.out.println("check");
            JOptionPane.showMessageDialog(frameRef, "Please select Invoice lines file", "Invoice Lines", JOptionPane.WARNING_MESSAGE);
            result = selectedFile.showOpenDialog(frameRef);
            if (result == JFileChooser.APPROVE_OPTION) {
                File linesFile = selectedFile.getSelectedFile();
                BufferedReader linesBr = new BufferedReader(new FileReader(linesFile));
              
                String linesLine = null;
                while ((linesLine = linesBr.readLine()) != null) {
                    String[] lineSegments = linesLine.split(",");
                    String invNumStr = lineSegments[0];
                    String item = lineSegments[1];
                    String priceStr = lineSegments[2];
                    String countStr = lineSegments[3];
                    
                    int invNum = Integer.parseInt(invNumStr);
                    double price = Double.parseDouble(priceStr);
                    int count = Integer.parseInt(countStr);
                    InvoiceHeader header = findByNum(invNum);
                    InvoiceLine invLine = new InvoiceLine(item, price, count, header);
                    header.addLine(invLine);
                }
                headerBr.close();
                headerFr.close();
                frameRef.setHeaderTableModel(new InvHeaderModel(frameRef.getFilesData()));
                frameRef.getHeaderTable().setModel(frameRef.getHeaderTableModel());
                frameRef.getHeaderTable().validate();
                
            }
        }
        
        for(int i=0;i<frameRef.getFilesData().size();i++)
                {
                    System.out.println("Invoice Number "+frameRef.getFilesData().get(i).getInvoiceNumber());
                    System.out.println("{");
                    System.out.println(frameRef.getFilesData().get(i).getInvoiceDate()+", "+frameRef.getFilesData().get(i).getCustomerName());
                    for(int j=0;j<frameRef.getFilesData().get(i).getAllInvoiceLines().size();j++)
                    {
                        System.out.println(frameRef.getFilesData().get(i).getAllInvoiceLines().get(j).getItemName()+", "+
                                frameRef.getFilesData().get(i).getAllInvoiceLines().get(j).getItemPrice()+", "+
                                frameRef.getFilesData().get(i).getAllInvoiceLines().get(j).getItemCount());
                    }
                    System.out.println("}");
                } 
    }
    
   
 
    public void saveData()  {
        JFileChooser chooser = new JFileChooser();
        int state = chooser.showSaveDialog(null);
        File file = chooser.getSelectedFile();
        if (file != null && state == JFileChooser.APPROVE_OPTION) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
                PrintWriter fWriter = new PrintWriter(bufferedWriter);

                for (int i = 0; i < frameRef.getHeaderTable().getRowCount(); ++i) {
                    for (int j = 0; j < frameRef.getHeaderTable().getColumnCount(); ++j) {
                        String s = frameRef.getHeaderTable().getValueAt(i, j).toString();
                        System.out.println(s);
                        fWriter.print(s);
                        fWriter.print(",");
                    }
                    fWriter.println("");
                }
                fWriter.close();
                JOptionPane.showMessageDialog(null, "Successfully Saved");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Failure");
            }
        }
        chooser = new JFileChooser();
        state = chooser.showSaveDialog(null);
        file = chooser.getSelectedFile();
        if (file != null && state == JFileChooser.APPROVE_OPTION) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
                PrintWriter fWriter = new PrintWriter(bufferedWriter);

                for (int i = 0; i < frameRef.getLineTable().getRowCount(); ++i) {
                    for (int j = 0; j < frameRef.getLineTable().getColumnCount(); ++j) {
                        String s = frameRef.getLineTable().getValueAt(i, j).toString();
                        System.out.println(s);
                        fWriter.print(s);
                        fWriter.print(",");
                    }
                    fWriter.println("");
                }
                fWriter.close();
                JOptionPane.showMessageDialog(null, "Successfully Saved");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Failure");
            }
        }
        
        for(int i=0;i<frameRef.getFilesData().size();i++)
                {
                    System.out.println("Invoice Number "+frameRef.getFilesData().get(i).getInvoiceNumber());
                    System.out.println("{");
                    System.out.println(frameRef.getFilesData().get(i).getInvoiceDate()+", "+frameRef.getFilesData().get(i).getCustomerName());
                    for(int j=0;j<frameRef.getFilesData().get(i).getAllInvoiceLines().size();j++)
                    {
                        System.out.println(frameRef.getFilesData().get(i).getAllInvoiceLines().get(j).getItemName()+", "+
                                frameRef.getFilesData().get(i).getAllInvoiceLines().get(j).getItemPrice()+", "+
                                frameRef.getFilesData().get(i).getAllInvoiceLines().get(j).getItemCount());
                    }
                    System.out.println("}");
                } 
    }
    
        private InvoiceHeader findByNum(int num) {
        InvoiceHeader header = null;
        for (InvoiceHeader inv : frameRef.getFilesData()) {
            if (num == inv.getInvoiceNumber()) {
                header = inv;
                break;
            }
        }
        return header;
    }
    
    
}
