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
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import r3source.ApplicablePeriod;
import r3source.MainScreen;
import r3source.R3File;
import r3source.R3FileGen;
import r3source.Utility;

public class DatabaseMaintenanceScreen
extends JPanel {
    private JButton btnDataBackup;
    private JButton btnRestoreData;
    private JButton btnBackToMain;
    private Container container;
    private JFrame frame;
    private JLabel lblMessage;
    private static final long serialVersionUID = 48L;

    public DatabaseMaintenanceScreen(JFrame parent, Container c) {
        this.container = c;
        this.frame = parent;
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
        String[] captions = new String[]{"<html> <font color=blue size=+2><b>Data Back Up</b> </font>", "<html> <font color=blue size=+2><b>Restore Data</b> </font>", "<html> <font color=blue size=+2><b>Back to Main Menu</b> </font>"};
        this.btnDataBackup = new JButton(captions[0]);
        this.btnDataBackup.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseMaintenanceScreen.this.onDataBackup();
            }
        });
        this.btnRestoreData = new JButton(captions[1]);
        this.btnRestoreData.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseMaintenanceScreen.this.onDataRestore();
            }
        });
        this.btnBackToMain = new JButton(captions[2]);
        this.btnBackToMain.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseMaintenanceScreen.this.onBackToMain();
            }
        });
        String message = "<html> <font color=red size=-2><b>Please select operation. </b> </font>";
        this.lblMessage = new JLabel(message);
        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createTitledBorder("Message"));
        p.add(this.lblMessage);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 15, 15));
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""), BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        panel.add(this.btnDataBackup);
        panel.add(this.btnRestoreData);
        panel.add(this.btnBackToMain);
        panel.add(p);
        return panel;
    }

    private JPanel northPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JLabel label = new JLabel("Database Maintenance", new ImageIcon("r3source/image/sss.gif"), 0);
        label.setFont(new Font("Tahoma", 1, 26));
        panel.add(label);
        return panel;
    }

    private void onDataBackup() {
        int option = JOptionPane.showConfirmDialog(this, "Back up your r3File.dat file? If YES, specify directory\n location and file name for your backup file.", "Warning", 0, 3);
        if (option == 0) {
            File file = new File("R3File_" + R3FileGen.r3File.getAppPeriod() + "_OLD.dat");
            JFileChooser chooser = new JFileChooser("a:/");
            chooser.setFileSelectionMode(0);
            chooser.setDialogTitle("Backup your R3File. Choose DIRECTORY location to backup.");
            chooser.setSelectedFile(file);
            chooser.setApproveButtonText("Backup");
            int returnVal = chooser.showSaveDialog(this.frame);
            if (returnVal == 0) {
                this.onBackUpR3DBFile(chooser.getSelectedFile());
            }
        }
    }

    private void onDataRestore() {
        int option = JOptionPane.showConfirmDialog(this, "Restore R3File_AppPeriod_OLD.dat file? If YES, specify directory location of your \n backup file. This will overwrite the existing r3File.dat file", "Warning", 0, 3);
        if (option == 0) {
            JFileChooser chooser = new JFileChooser("a:/");
            chooser.setFileSelectionMode(0);
            chooser.setDialogTitle("Restore: Look for R3File_AppPeriod_OLD.dat file: Choose the source DIRECTORY location");
            chooser.setApproveButtonText("Restore");
            int returnVal = chooser.showOpenDialog(this.frame);
            if (returnVal == 0) {
                this.onRestoreR3DBFile(chooser.getSelectedFile());
            }
        }
    }

    private void onBackToMain() {
        this.container.remove(0);
        this.container.add(new MainScreen(this.frame, this.container));
        this.container.validate();
    }

    public void onBackUpR3DBFile(File file) {
        File R3DBFile = new File("r3File.dat");
        if (!R3DBFile.exists()) {
            String message = "<html> <font color=red size=-2><b>R3FILE file (r3File.dat) does not exists. <br>Please try again later.</b> </font>";
            this.lblMessage.setText(message);
            return;
        }
        if (R3DBFile.renameTo(file)) {
            String message = "<html> <font color=blue size=-2><b>Successful backup of R3FILE file. </b> </font>";
            this.lblMessage.setText(message);
            JOptionPane.showMessageDialog(this, "Successful backup of R3File.");
        } else {
            String message = "<html> <font color=red size=-2><b>R3FILE file backup not successful. <br>Please try again later.</b> </font>";
            this.lblMessage.setText(message);
        }
    }

    private void onRestoreR3DBFile(File file) {
        File r3FileDB = new File("r3File.dat");
        if (r3FileDB.exists()) {
            if (r3FileDB.delete()) {
                if (!file.renameTo(new File("r3File.dat"))) {
                    String message = "<html> <font color=red size=-2><b>R3File file restoration failed. <br>Current file, r3File.dat cant be replace. <br>Please try again later.</b> </font>";
                    this.lblMessage.setText(message);
                } else {
                    String message = "<html> <font color=blue size=-2><b>R3File file restoration successful. </b> </font>";
                    this.lblMessage.setText(message);
                    R3FileGen.r3File = Utility.getR3File();
                    JOptionPane.showMessageDialog(this, "Successful restoration of R3File.");
                }
            } else {
                String message = "<html> <font color=red size=-2><b>R3File file restoration failed. <br>Current file, r3File.dat cant be deleted. <br>Please try again later.</b> </font>";
                this.lblMessage.setText(message);
            }
        } else if (!file.renameTo(new File("r3FileDB.dat"))) {
            String message = "<html> <font color=red size=-2><b>R3File file restoration failed. <br>Current file, r3File.dat cant be replace. <br>Please try again later.</b> </font>";
            this.lblMessage.setText(message);
        } else {
            String message = "<html> <font color=blue size=-2><b>R3File file restoration successful. </b> </font>";
            this.lblMessage.setText(message);
        }
    }

}

