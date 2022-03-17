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
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import r3source.ApplicablePeriod;
import r3source.Employer;
import r3source.R3File;
import r3source.Utility;
import r3source.Validator;

public class EmployerAppPeriodRecordScreen
extends JDialog {
    private JButton btnUpdate;
    private JButton btnCancel;
    private JButton btnClose;
    private JTextField txtEmployerName;
    private JFormattedTextField txtEmployerId;
    private boolean isUpdateSelected;
    private Employer er;
    private JLabel lblMessage;
    private R3File r3File;
    private ApplicablePeriod appPeriod;
    private JComboBox cmbMonth;
    private JComboBox cmbYear;
    private static final long serialVersionUID = 50L;

    public EmployerAppPeriodRecordScreen(JFrame parent) {
        super(parent, "Social Security Sytem - R3", true);
        this.initialize();
        this.setDefaultCloseOperation(0);
        this.setLocationRelativeTo(parent);
    }

    public EmployerAppPeriodRecordScreen(JFrame parent, R3File r3File) {
        this(parent);
        this.r3File = r3File;
        this.appPeriod = r3File.getAppPeriod();
        this.populateFields();
    }

    private void initialize() {
        this.setSize(500, 300);
        this.setContentPane(this.createGUI());
    }

    private JPanel createGUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add((Component)this.northPanel(), "North");
        panel.add((Component)this.centerPanel(), "Center");
        panel.add((Component)this.appPeriodPanel(), "South");
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
                EmployerAppPeriodRecordScreen.this.onEnter(e);
            }
        });
        this.txtEmployerId.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent f) {
                EmployerAppPeriodRecordScreen.this.onFocusGained(f);
            }
        });
        this.txtEmployerId.setFont(new Font("area", 1, 15));
        panel.add(this.txtEmployerId);
        panel.add(new JLabel("Employer Name: "));
        this.txtEmployerName = new JTextField(24);
        this.txtEmployerName.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                EmployerAppPeriodRecordScreen.this.onEnter(e);
            }
        });
        this.txtEmployerName.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent f) {
                EmployerAppPeriodRecordScreen.this.onFocusGained(f);
            }

            @Override
            public void focusLost(FocusEvent f) {
                EmployerAppPeriodRecordScreen.this.onFocusLost(f);
            }
        });
        this.txtEmployerName.setFont(new Font("area", 1, 15));
        panel.add(this.txtEmployerName);
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
                    EmployerAppPeriodRecordScreen.this.onUpdate();
                }
            }
        });
        this.btnUpdate.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                EmployerAppPeriodRecordScreen.this.onUpdate();
            }
        });
        this.btnCancel = new JButton(captions[1]);
        this.btnCancel.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == 10) {
                    EmployerAppPeriodRecordScreen.this.onCancel();
                }
            }
        });
        this.btnCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                EmployerAppPeriodRecordScreen.this.onCancel();
            }
        });
        this.btnClose = new JButton(captions[2]);
        this.btnClose.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == 10) {
                    EmployerAppPeriodRecordScreen.this.onClose();
                }
            }
        });
        this.btnClose.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                EmployerAppPeriodRecordScreen.this.onClose();
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

    private JPanel appPeriodPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("MONTH ( mm ): "));
        String[] month = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        this.cmbMonth = new JComboBox(month);
        this.cmbMonth.setFont(new Font("area", 1, 14));
        panel.add(this.cmbMonth);
        panel.add(new JLabel("YEAR ( yyyy ): "));
        this.cmbYear = new JComboBox(Utility.getYears());
        this.cmbYear.setFont(new Font("area", 1, 14));
        panel.add(this.cmbYear);
        JPanel pane = new JPanel();
        pane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Applicable Period"), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
        pane.setLayout(new BorderLayout());
        pane.add((Component)panel, "Center");
        return pane;
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
        JLabel label = new JLabel(caption, new ImageIcon("r3source/image/sss.gif"), 0);
        panel.add(label);
        return panel;
    }

    private void onUpdate() {
        this.isUpdateSelected = true;
        String id = (String)this.txtEmployerId.getValue();
        String name = this.txtEmployerName.getText();
        if (Validator.isSssNumberValid(id)) {
            if (!this.isCorrect()) {
                String message = "<html> <font color=red size=-2><b>Invalid values for Applicable Period.</b> </font>";
                this.lblMessage.setText(message);
                return;
            }
            if (name.length() != 0) {
                this.er = new Employer(id, name.trim().toUpperCase());
                this.lblMessage.setText("");
                int appMo = Integer.parseInt(String.valueOf(this.cmbMonth.getSelectedItem()));
                int appYear = Integer.parseInt(String.valueOf(this.cmbYear.getSelectedItem()));
                this.appPeriod = new ApplicablePeriod(appMo, appYear);

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

    public Employer getEmployer() {
        return this.er;
    }

    public ApplicablePeriod getAppPeriod() {
        return this.appPeriod;
    }

    private void populateFields() {
        if (this.r3File != null) {
            Employer er = this.r3File.getEmployer();
            this.txtEmployerName.setText(er.getErName());
            this.txtEmployerId.setText(er.getErSssNumber());
            this.insertAppPeriod();
        } else {
            this.txtEmployerName.setText("");
        }
    }

    private void insertAppPeriod() {
        this.cmbYear.setSelectedItem(String.valueOf(this.appPeriod.getAppYear()));
        this.cmbMonth.setSelectedItem(this.appPeriod.strImageMonth());
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

    private boolean isCorrect() {
        boolean valid = true;
        
        int appMo = Integer.parseInt(String.valueOf(this.cmbMonth.getSelectedItem()));
        int appYear = Integer.parseInt(String.valueOf(this.cmbYear.getSelectedItem()));
        ApplicablePeriod ap = new ApplicablePeriod(appMo, appYear);
        Date dateOfApp = ap.getAsDate();
        Date now = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(dateOfApp);
        int m = calendar.get(2);
        int y = calendar.get(1);
        calendar.setTime(now);
        int n = calendar.get(2);
        int z = calendar.get(1);
        if (y == z && m == n) {
            return false;
        }
        
        
        if (dateOfApp.after(now)) {
            return false;
        }
     
        return valid;
    }

}

