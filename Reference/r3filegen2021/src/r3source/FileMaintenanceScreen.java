package r3source;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import r3source.ApplicablePeriod;
import r3source.EmployeeRecordScreen;
import r3source.Employer;
import r3source.EmployerAppPeriodRecordScreen;
import r3source.MainScreen;
import r3source.PaymentRecordScreen;
import r3source.R3File;
import r3source.R3FileGen;
import r3source.Utility;

import scheduler.YearSched;

public class FileMaintenanceScreen
extends JPanel {
    private JButton btnEmployerRecordMaintenance;
    private JButton btnEmployeeRecordMaintenance;
    private JButton btnInquireTotals;
    private JButton btnCreateSSSTextFile;
    private JButton btnBackToMain;
    private R3File r3File;
    private JFrame frame;
    private Container container;
    private JLabel header;
    private static final long serialVersionUID = 51L;

    public FileMaintenanceScreen(JFrame frame, R3File r3File, Container container) {
        this.frame = frame;
        this.container = container;
        this.r3File = r3File;
        this.setOpaque(true);
        this.add(this.createGUI());
    }

    private JPanel createGUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add((Component)this.northPanel(), "North");
        panel.add((Component)this.centerPanel(), "Center");
        panel.add((Component)new JPanel(), "South");
        panel.add((Component)new JPanel(), "East");
        panel.add((Component)new JPanel(), "West");
        return panel;
    }

    private JPanel centerPanel() {
        String[] captions = new String[]{"<html> <font color=blue size=+2><b><u>E</u>mployer Record Maintenance</b> </font>", "<html> <font color=blue size=+2><b>Employee Record <u>M</u>aintenance</b> </font>", "<html> <font color=blue size=+2><b><u>I</u>nquire Totals</b> </font>", "<html> <font color=blue size=+2><b><u>C</u>reate SSS Text File</b> </font>", "<html> <font color=blue size=+2><b><u>B</u>ack to Main Menu</b> </font>"};
        this.btnEmployerRecordMaintenance = new JButton(captions[0]);
        this.btnEmployerRecordMaintenance.setMnemonic(69);
        this.btnEmployerRecordMaintenance.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                FileMaintenanceScreen.this.onEmployerMaintenance();
            }
        });
        this.btnEmployeeRecordMaintenance = new JButton(captions[1]);
        this.btnEmployeeRecordMaintenance.setMnemonic(77);
        this.btnEmployeeRecordMaintenance.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                FileMaintenanceScreen.this.onEmployeeMaintenance();
            }
        });
        this.btnInquireTotals = new JButton(captions[2]);
        this.btnInquireTotals.setMnemonic(73);
        this.btnInquireTotals.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                FileMaintenanceScreen.this.onInquireTotals();
            }
        });
        this.btnCreateSSSTextFile = new JButton(captions[3]);
        this.btnCreateSSSTextFile.setMnemonic(67);
        this.btnCreateSSSTextFile.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                FileMaintenanceScreen.this.onCreateSSSTextFile();
            }
        });
        this.btnBackToMain = new JButton(captions[4]);
        this.btnBackToMain.setMnemonic(66);
        this.btnBackToMain.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                FileMaintenanceScreen.this.onBackToMain();
            }
        });
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 5, 5));
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("File Maintenance Menu"), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        panel.add(this.btnEmployerRecordMaintenance);
        panel.add(this.btnEmployeeRecordMaintenance);
        panel.add(this.btnInquireTotals);
        panel.add(this.btnCreateSSSTextFile);
        panel.add(this.btnBackToMain);
        return panel;
    }

    private JPanel northPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        String c = "<html> <font color=blue size=+1><b><u>R3 PROJECT<br>Applicable Period: " + this.r3File.getAppPeriod().toString() + "</b> </font>";
        this.header = new JLabel(c);
        this.header.setFont(new Font("Tahoma", 1, 26));
        this.header.setIcon(new ImageIcon("image/sss.gif"));
        this.header.setHorizontalAlignment(0);
        panel.add(this.header);
        return panel;
    }

    private void onEmployerMaintenance() {
        EmployerAppPeriodRecordScreen ers = new EmployerAppPeriodRecordScreen(this.frame, this.r3File);
        ers.setVisible(true);
        if (ers.getIsUpdateSelected()) {
            Employer er = ers.getEmployer();
            Utility.updateER(er);
            this.r3File.setEmployer(er);
            this.r3File.setApplicablePeriod(ers.getAppPeriod());

            String c = "<html> <font color=blue size=+1><b><u>R3 PROJECT<br>Applicable Period: " + this.r3File.getAppPeriod().toString() + "</b> </font>";
            this.header.setText(c);
            this.r3File.setCompleted(false);

            //ADDED BY CGCANONG 02/07/2019
            boolean isUpdatedMSC = this.r3File.validateContribution();

            if(isUpdatedMSC == false){
                
//                JOptionPane.showMessageDialog(null, "Applicable Month entered ("+this.r3File.getAppPeriod().getAppMo()+"-"+this.r3File.getAppPeriod().getAppYear()+") and the previous Applicable Month (04-2019)  are not within the same SS/EC Schedule range." +
//                    "\nThe Application will automatically use the SS/EC Schedule corresponding the new Applicable Month. \nPlease update Employee's SS/EC Contribution amount."); 

                JOptionPane.showMessageDialog(null, "The Application detected that the Employee's SS/EC contribution amount is not within the SS/EC Schedule range/out-of-bracket " +
                    " in the selected Applicable Month("+this.r3File.getAppPeriod().getAppMo()+"-"+this.r3File.getAppPeriod().getAppYear()+")" +
                    "\nPlease update Employee's SS/EC Contribution amount."); 
            }
            
        }
    }

    private void onInquireTotals() {
        //ADDED BY CGCANONG 02/07/2019
        boolean isUpdatedMSC = this.r3File.validateContribution();

        if(isUpdatedMSC == false){
            int opt = JOptionPane.showConfirmDialog(null, 
                                              "Employee's have Incorrect Amount of Contribution/EC according to Schedule" +
                                               "\n Do you still want to continue to Inquire Totals?", 
                                              "Continue?", 
                                              JOptionPane.YES_NO_OPTION); 
            if (opt == JOptionPane.NO_OPTION) {  return; }                                
        }

        this.showTotals();
    }

    private void onEmployeeMaintenance() {
        this.container.remove(0);
        this.container.add(new EmployeeRecordScreen(this.frame, this.r3File, this.container));
        this.container.validate();
    }

    private void onCreateSSSTextFile() {                
        if (this.r3File.getTotalEEs() != 0) {        

            boolean isUpdatedMSC = this.r3File.validateContribution();

            if(isUpdatedMSC == false){
                JOptionPane.showMessageDialog(this, 
                //"The Application detected that the Employee's SS/EC contribution amount is not within the SS/EC Schedule range/out-of-bracket." +
                    "Please review the applicable period and the amount of SS/EC.", 
                                                  "Warning",0); 
                return;                                
            }
 
            this.container.remove(0);
            this.container.add(new PaymentRecordScreen(this.frame, this.r3File, this.container));
            this.container.validate();
        } else {
            JOptionPane.showMessageDialog(this, "This R3File, with applicable period, " + this.r3File.getAppPeriod().toString() + " does not have any employee details." + "\nPlease add employee/s information and try again.", "Stop", 0);
        }
    }
    
    private void onBackToMain() {
        this.updateR3File();
        this.container.remove(0);
        this.container.add(new MainScreen(this.frame, this.container));
        this.container.validate();
    }

    private void showTotals() {
        JOptionPane.showMessageDialog(this.frame, this.totalPanel(), "Social Security System", 1);
    }

    private JPanel totalPanel() {
        String[] caption = new String[]{"<html> <font color=blue size=+1><b>Applicable Period: </b> </font>", "<html> <font color=blue size=+1><b>SS: </b> </font>", "<html> <font color=blue size=+1><b>EC: </b> </font>", "<html> <font color=blue size=+1><b>Total Amount: </b> </font>", "<html> <font color=blue size=+1><b>Total # of EE: </b> </font>"};
        JPanel panel = new JPanel();
        panel.add(new JLabel(caption[0]));
        JTextField txtAppPeriod = new JTextField(10);
        txtAppPeriod.setEditable(false);
        txtAppPeriod.setHorizontalAlignment(0);
        txtAppPeriod.setFont(new Font("area", 1, 17));
        panel.add(txtAppPeriod);
        JPanel panelLabel = new JPanel();
        panelLabel.setLayout(new FlowLayout(0));
        panelLabel.add(new JLabel(caption[3]));
        JPanel panelSSAmt = new JPanel();
        panelSSAmt.add(new JLabel(caption[1]));
        JTextField txtSSSTotal = new JTextField(10);
        txtSSSTotal.setEditable(false);
        txtSSSTotal.setHorizontalAlignment(0);
        txtSSSTotal.setFont(new Font("area", 1, 17));
        panelSSAmt.add(txtSSSTotal);
        panelSSAmt.add(new JLabel(caption[2]));
        JTextField txtECTotal = new JTextField(8);
        txtECTotal.setEditable(false);
        txtECTotal.setHorizontalAlignment(0);
        txtECTotal.setFont(new Font("area", 1, 17));
        panelSSAmt.add(txtECTotal);
        JTextField txtEETotal = new JTextField(8);
        txtEETotal.setEditable(false);
        txtEETotal.setHorizontalAlignment(0);
        txtEETotal.setFont(new Font("area", 1, 17));
        JPanel panelEE = new JPanel(new FlowLayout(0));
        panelEE.add(new JLabel(caption[4]));
        panelEE.add(txtEETotal);
        JPanel pane = new JPanel();
        pane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("<< TOTALS >>"), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
        pane.setLayout(new GridLayout(4, 1));
        pane.add(panel);
        pane.add(panelEE);
        pane.add(panelLabel);
        pane.add(panelSSAmt);
        if (this.r3File != null) {
            txtAppPeriod.setText(this.r3File.getAppPeriod().toString());
            txtSSSTotal.setText(Utility.formatter(this.r3File.getTotalOfSSContribution()));
            txtECTotal.setText(Utility.formatter(this.r3File.getTotalOfECContribution()));
            txtEETotal.setText(String.valueOf(this.r3File.getTotalEEs()));
        } else {
            System.out.println("r3File is null here in Inquire total");
        }
        return pane;
    }

    private void updateR3File() {
        if (this.r3File != null) {
            R3FileGen.r3File = this.r3File;
        }
    }

}

