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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import r3source.DatabaseMaintenanceScreen;
import r3source.FileMaintenanceScreen;
import r3source.R3File;
import r3source.R3FileGen;
import r3source.ReportGenScreen;
import r3source.Utility;

public class MainScreen
extends JPanel {
    private JButton btnFileMaintenance;
    private JButton btnReportGen;
    private JButton btnSystemMaintenance;
    private JButton btnExit;
    private JFrame frame;
    private static final long serialVersionUID = 53L;
    private Container container;

    public MainScreen(JFrame frame, Container container) {
        super(new BorderLayout());
        this.frame = frame;
        this.container = container;
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
        String[] captions = new String[]{"<html> <font color=blue size=+2><b><u>F</u>ile Maintenance</b> </font>", "<html> <font color=blue size=+2><b><u>R</u>eport Generation</b> </font>", "<html> <font color=blue size=+2><b><u>S</u>ystem Maintenance</b> </font>", "<html> <font color=blue size=+2><b><u>E</u>xit</b> </font>"};
        this.btnFileMaintenance = new JButton(captions[0]);
        this.btnFileMaintenance.setMnemonic(70);
        this.btnFileMaintenance.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                MainScreen.this.onFileMaintenance();
            }
        });
        this.btnReportGen = new JButton(captions[1]);
        this.btnReportGen.setMnemonic(82);
        this.btnReportGen.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                MainScreen.this.onReportGen();
            }
        });
        this.btnSystemMaintenance = new JButton(captions[2]);
        this.btnSystemMaintenance.setMnemonic(83);
        this.btnSystemMaintenance.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                MainScreen.this.onSystemMaintenance();
            }
        });
        this.btnExit = new JButton(captions[3]);
        this.btnExit.setMnemonic(69);
        this.btnExit.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                MainScreen.this.onExit();
            }
        });
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 15, 15));
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Main Menu"), BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        panel.add(this.btnFileMaintenance);
        panel.add(this.btnReportGen);
        panel.add(this.btnSystemMaintenance);
        panel.add(this.btnExit);
        return panel;
    }

    private JPanel northPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JLabel label = new JLabel("R3 PROJECT v2021-1.0", new ImageIcon("image/SSS.gif"), 0);
        label.setFont(new Font("Tahoma", 1, 26));
        panel.add(label);
        return panel;
    }

    private void onFileMaintenance() {
        this.container.remove(0);
        this.container.add(new FileMaintenanceScreen(this.frame, R3FileGen.r3File, this.container));
        this.container.validate();
    }

    private void onSystemMaintenance() {
        this.container.remove(0);
        this.container.add(new DatabaseMaintenanceScreen(this.frame, this.container));
        this.container.validate();
    }

    private void onReportGen() {
        this.container.remove(0);
        this.container.add(new ReportGenScreen(this.frame, R3FileGen.r3File, this.container));
        this.container.validate();
    }

    private void onExit() {
        Utility.writeObject(R3FileGen.r3File, new File("r3File.dat"));
        System.exit(0);
    }

}

