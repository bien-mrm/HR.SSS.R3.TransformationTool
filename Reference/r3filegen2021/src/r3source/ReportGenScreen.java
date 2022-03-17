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
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import r3source.ApplicablePeriod;
import r3source.MainScreen;
import r3source.R3File;
import r3source.Utility;

public class ReportGenScreen
extends JPanel {
    private JButton btnPrintTransmittalCert;
    private JButton btnPrintEmployeeFile;
    private JButton btnBackToMain;
    private JFrame frame;
    private Container container;
    private R3File r3File;
    private JLabel lblMessage;
    private static final long serialVersionUID = 55L;

    public ReportGenScreen(JFrame f, R3File r, Container c) {
        this.frame = f;
        this.r3File = r;
        this.container = c;
        this.setOpaque(true);
        this.add(this.createGUI());
    }

    private JPanel createGUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add((Component)this.northPanel(), "North");
        panel.add((Component)this.centerPanel(), "Center");
        panel.add((Component)this.panelMessage(), "South");
        panel.add((Component)new JPanel(), "East");
        panel.add((Component)new JPanel(), "West");
        return panel;
    }

    private JPanel panelMessage() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Message"));
        String message = "<html> <font color=blue size=-2><b> Select the button for your report.</b> </font>";
        this.lblMessage = new JLabel(message);
        panel.add(this.lblMessage);
        return panel;
    }

    private JPanel centerPanel() {
        String[] captions = new String[]{"<html> <font color=blue size=+2><b>Print Transmittal Certificate</b> </font>", "<html> <font color=blue size=+2><b>Print Employee File</b> </font>", "<html> <font color=blue size=+2><b>Back to Main Menu</b> </font>"};
        this.btnPrintTransmittalCert = new JButton(captions[0]);
        this.btnPrintTransmittalCert.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ReportGenScreen.this.onPrintTransCert();
            }
        });
        this.btnPrintEmployeeFile = new JButton(captions[1]);
        this.btnPrintEmployeeFile.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ReportGenScreen.this.onPrintEmployeeRec();
            }
        });
        this.btnBackToMain = new JButton(captions[2]);
        this.btnBackToMain.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ReportGenScreen.this.onBackToMain();
            }
        });
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 15, 15));
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""), BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        panel.add(this.btnPrintTransmittalCert);
        panel.add(this.btnPrintEmployeeFile);
        panel.add(this.btnBackToMain);
        return panel;
    }

    private JPanel northPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JLabel label = new JLabel("Report Generation", new ImageIcon("image/sss.gif"), 0);
        label.setFont(new Font("Tahoma", 1, 26));
        panel.add(label);
        return panel;
    }

    private void onPrintTransCert() {
        if (!this.r3File.isCompleted()) {
            ApplicablePeriod appPeriod = this.r3File.getAppPeriod();
            String message = "<html> <font color=blue size=-2><b> The SSS text file of this R3File with Applicable period " + appPeriod.getAppMo() + "-" + appPeriod.getAppYear() + ",<br>might not have been generated. You may not continue<br>creating reports.</b> </font>";
            JLabel label = new JLabel(message);
            JOptionPane.showMessageDialog(this.frame, label, "STOP", 0);
            String messages = "<html> <font color=blue size=-2><b> Select the button for your report.</b> </font>";
            this.lblMessage.setText(messages);
            return;
        }
        try {
            File file = this.r3File.createTransmittalReport();
            if (file != null) {
                String message;
                int returnVal = Utility.showFile(file);
                if (returnVal == 1) {
                    message = "<html> <font color=blue size=-2><b> Can't open the Transmittal Report. Pls. look for file, " + file.getName().toUpperCase() + "," + "<br>and open it with any word processor application (Notepad, JEdit etc..).</b> </font>";
                    JLabel label = new JLabel(message);
                    JOptionPane.showMessageDialog(this.frame, label, "STOP", 0);
                }
                message = "<html> <font color=blue size=-2><b> Select the button for your report.</b> </font>";
                this.lblMessage.setText(message);
            } else {
                String message = "<html> <font color=red size=-2><b> The SSS text file of this R3File you are trying to print<br> might not have been generated.</b> </font>";
                this.lblMessage.setText(message);
            }
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void onPrintEmployeeRec() {
        try {
            File file = this.r3File.createEmployeeReport();
            if (file != null) {
                String message;
                int returnVal = Utility.showFile(file);
                if (returnVal == 1) {
                    message = "<html> <font color=blue size=-2><b> Can't open the Transmittal Report. Pls. look for file, " + file.getName().toUpperCase() + "," + "<br>and open it with any word processor application (Notepad, JEdit etc..).</b> </font>";
                    JLabel label = new JLabel(message);
                    JOptionPane.showMessageDialog(this.frame, label, "STOP", 0);
                }
                message = "<html> <font color=blue size=-2><b> Select the button for your report.</b> </font>";
                this.lblMessage.setText(message);
            } else {
                String message = "<html> <font color=red size=-2><b> The R3File you are trying to print does not have any employee details.<br>Please add employee records and try again.</b> </font>";
                this.lblMessage.setText(message);
            }
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void onBackToMain() {
        this.container.remove(0);
        this.container.add(new MainScreen(this.frame, this.container));
        this.container.validate();
    }

}

