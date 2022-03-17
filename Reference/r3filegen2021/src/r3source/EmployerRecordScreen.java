package r3source;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import java.io.OutputStream;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import r3source.Employer;
import r3source.R3File;
import r3source.Utility;
import r3source.Validator;

public class EmployerRecordScreen
extends JDialog {
    private JButton btnUpdate;
    private JButton btnCancel;
    private JButton btnClose;
    private JTextField txtEmployerName;
    private JFormattedTextField txtEmployerId;
    private boolean isUpdateSelected;
    private boolean isImported;
    private Employer er;
    private JLabel lblMessage;
    private R3File r3File;
    private static final long serialVersionUID = 50L;
    
    private JButton btnImport;

    public EmployerRecordScreen(JFrame parent) {
        super(parent, "Social Security Sytem", true);
        this.initialize();
        this.setDefaultCloseOperation(0);
        this.setLocationRelativeTo(parent);
        this.setResizable(false); //ADDED BY CGCANONG 02/08/2019
    }

    public EmployerRecordScreen(JFrame parent, R3File r3File) {
        this(parent);
        this.r3File = r3File;
        this.populateFields();
    }

    private void initialize() {
        //ADDED by CGCANONG 02/08/2019
        File erFile = new File("er.dat");
        if (!erFile.exists()) {
            this.setSize(500, 260);                    
        }else{
            this.setSize(500, 250);            
        }
        
        //this.setSize(500, 250);            
        this.setContentPane(this.createGUI());
    }

    private JPanel createGUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add((Component)this.northPanel(), "North");
        panel.add((Component)this.centerPanel(), "Center");
        panel.add((Component)new JPanel(), "South");
        panel.add((Component)this.eastPanel(), "East");
        panel.add((Component)new JPanel(), "West");
        panel.setOpaque(true);
        return panel;
    }

    private JPanel centerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(0));
        panel.add(new JLabel("Employer Number: "));
        this.txtEmployerId = new JFormattedTextField(Utility.createFormatter("##-#######-#"));
        this.txtEmployerId.setColumns(24);
        this.txtEmployerId.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                EmployerRecordScreen.this.onEnter(e);
            }
        });
        this.txtEmployerId.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent f) {
                EmployerRecordScreen.this.onFocusGained(f);
            }
        });
        this.txtEmployerId.setFont(new Font("area", 1, 15));
        panel.add(this.txtEmployerId);
        panel.add(new JLabel("Employer Name: "));
        this.txtEmployerName = new JTextField(24);
        this.txtEmployerName.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                EmployerRecordScreen.this.onEnter(e);
            }
        });
        this.txtEmployerName.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent f) {
                EmployerRecordScreen.this.onFocusGained(f);
            }

            @Override
            public void focusLost(FocusEvent f) {
                EmployerRecordScreen.this.onFocusLost(f);
            }
        });
        this.txtEmployerName.setFont(new Font("area", 1, 15));
        panel.add(this.txtEmployerName);
        
        //ADDED BY CGCANONG 02/08/219
        File erFile = new File("er.dat");
        if (!erFile.exists()) {
            this.btnImport = new JButton("Import Old R3 File");
            this.btnImport.addActionListener(new ActionListener(){
    
                @Override
                public void actionPerformed(ActionEvent e) {
                    EmployerRecordScreen.this.onImport();
                }
            });
        }
        
        panel.add(this.btnImport);
        JPanel panel1 = new JPanel();
        panel1.setBorder(BorderFactory.createEtchedBorder());
        panel1.setLayout(new BorderLayout());
        panel1.add((Component)panel, "Center");
        panel1.add((Component)new JPanel(), "North");
        return panel1;
    }

    private JPanel eastPanel() {
        String[] captions = new String[]{"<html> <font color=blue size=-1><b>Update</b> </font>", "<html> <font color=blue size=-1><b>Cancel</b> </font>", "<html> <font color=blue size=-1><b>Close</b> </font>"};
        this.btnUpdate = new JButton(captions[0]);
        this.btnUpdate.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == 10) {
                    EmployerRecordScreen.this.onUpdate();
                }
            }
        });
        this.btnUpdate.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                EmployerRecordScreen.this.onUpdate();
            }
        });
        this.btnCancel = new JButton(captions[1]);
        this.btnCancel.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == 10) {
                    EmployerRecordScreen.this.onCancel();
                }
            }
        });
        this.btnCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                EmployerRecordScreen.this.onCancel();
            }
        });
        this.btnClose = new JButton(captions[2]);
        this.btnClose.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == 10) {
                    EmployerRecordScreen.this.onClose();
                }
            }
        });
        this.btnClose.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                EmployerRecordScreen.this.onClose();
            }
        });
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = 2;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        panel.add((Component)this.btnUpdate, c);
        c.gridx = 1;
        c.gridy = 0;
        panel.add((Component)this.btnCancel, c);
        c.weightx = 0.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        panel.add((Component)this.btnClose, c);
        JPanel panelNorth = new JPanel();
        panelNorth.setLayout(new BorderLayout());
        panelNorth.add((Component)panel, "North");
        panelNorth.add((Component)this.panelMessage(), "Center");
        return panelNorth;
    }

    private JPanel panelMessage() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Message"), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
        String message = "<html> <font color=blue size=-2><b>Complete Employer details<br>and click UPDATE to save<br>information.</b> </font>";
        this.lblMessage = new JLabel(message);
        panel.add(this.lblMessage);
        return panel;
    }

    private JPanel northPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(0));
        String caption = "<html> <font color=blue size=+1><b> File Maintenance <br>EMPLOYER RECORD</b> </font>";
        JLabel label = new JLabel(caption, new ImageIcon("image/sss.gif"), 0);
        panel.add(label);
        return panel;
    }

    private void onUpdate() {
        this.isUpdateSelected = true;
        String id = (String)this.txtEmployerId.getValue();
        String name = this.txtEmployerName.getText();
        if (Validator.isSssNumberValid(id)) {
            if (name.length() != 0) {
                this.er = new Employer(id, name.trim().toUpperCase());
                this.lblMessage.setText("");
                this.setVisible(false);
            } else {
                String message = "<html> <font color=red size=-2><b>Invalid value for Employer Name.</b> </font>";
                this.lblMessage.setText(message);
            }
        } else {
            String message = "<html> <font color=red size=-2><b>Invalid value for SSS Number.</b> </font>";
            this.lblMessage.setText(message);
        }
    }
    
    //ADDED BY CGCANONG 02/08/2019
    private void onImport(){
        try{
            JFileChooser oFldrPth = new JFileChooser();
            
            oFldrPth.setCurrentDirectory(new java.io.File("."));
            oFldrPth.setDialogTitle("Select R3Filegen directory path");
            oFldrPth.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            oFldrPth.setAcceptAllFileFilterUsed(false);
            
            if (oFldrPth.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
                File erFile = new File( oFldrPth.getSelectedFile() + "\\er.dat");

                if (erFile.exists()) {
 
                    copyFile(oFldrPth.getSelectedFile() + "\\er.dat", (new java.io.File(".")).toString() + "\\er.dat");
                    
                    erFile = new File( oFldrPth.getSelectedFile() + "\\eeDB.dat");
                    if (erFile.exists()) {
                        copyFile(oFldrPth.getSelectedFile() + "\\eeDB.dat", (new java.io.File(".")).toString() + "\\eeDB.dat");
                    }

                    erFile = new File( oFldrPth.getSelectedFile() + "\\r3File.dat");
                    if (erFile.exists()) {
                        copyFile(oFldrPth.getSelectedFile() + "\\r3File.dat", (new java.io.File(".")).toString() + "\\r3File.dat");
                    }

                    JOptionPane.showMessageDialog(null, "Old R3 File is successfully imported. \nThe Application will automatically close for the changes to take effect."); 
                    System.exit(0);
                }else{
                    JOptionPane.showMessageDialog(null, "No Old R3 File found in directory path.");                 
                }                                
            }
        }catch(Exception e){
            e.printStackTrace();
            String message = "<html> <font color=red size=-2><b>Failed to import Olf R3 files.</b> </font>";
            this.lblMessage.setText(message);
        }
    }
    
    private void copyFile(String srcFile, String dstFile) throws Exception {
        InputStream  is = null;
        OutputStream os = null;
        try{
            
            is = new FileInputStream(srcFile);
            os = new FileOutputStream(new File(dstFile));
            
            byte[] buffer = new byte[1024];
             int length;
             while ((length = is.read(buffer)) > 0) {
                 os.write(buffer, 0, length);
             }            
            }finally{
                is.close();
                os.close();                
            }
        
    }
    

    private void onCancel() {
        this.populateFields();
        String message = "<html> <font color=blue size=-2><b>Complete Employer details<br>and click UPDATE to save<br>information.</b> </font>";
        this.lblMessage.setText(message);
    }

    private void onClose() {
        this.isUpdateSelected = false;
        this.setVisible(false);
    }

    public boolean getIsUpdateSelected() {
        return this.isUpdateSelected;
    }

    public boolean getIsImported() {
        return isImported;
    }

    public Employer getEmployer() {
        return this.er;
    }

    private void populateFields() {
        if (this.r3File != null) {
            Employer er = this.r3File.getEmployer();
            this.txtEmployerName.setText(er.getErName());
            this.txtEmployerId.setText(er.getErSssNumber());
        } else {
            this.txtEmployerName.setText("");
        }
    }

    private void onFocusGained(FocusEvent f) {
        if (f.getSource() == this.txtEmployerId) {
            this.txtEmployerId.selectAll();
        } else if (f.getSource() == this.txtEmployerName) {
            this.txtEmployerName.selectAll();
        }
    }

    private void onFocusLost(FocusEvent f) {
        JTextField txt = (JTextField)f.getSource();
        String str = txt.getText();
        str = str.toUpperCase();
        txt.setText(str);
    }

    private void onEnter(ActionEvent e) {
        if (e.getSource() == this.txtEmployerId) {
            String id = (String)this.txtEmployerId.getValue();
            if (Validator.isSssNumberValid(id)) {
                String message = "<html> <font color=blue size=-2><b>Complete Employer details<br>and click UPDATE to save<br>information.</b> </font>";
                this.lblMessage.setText(message);
                this.txtEmployerId.transferFocus();
            } else {
                String message = "<html> <font color=red size=-2><b>Invalid value for SSS Number.</b> </font>";
                this.lblMessage.setText(message);
            }
        } else if (e.getSource() == this.txtEmployerName) {
            this.txtEmployerName.transferFocus();
        }
    }

}

