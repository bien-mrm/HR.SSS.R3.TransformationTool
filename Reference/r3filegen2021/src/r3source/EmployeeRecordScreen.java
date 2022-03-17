package r3source;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Hashtable;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.MaskFormatter;
import r3source.ApplicablePeriod;
import r3source.Contribution;
import r3source.Employee;
import r3source.Employer;
import r3source.FileMaintenanceScreen;
import r3source.R3File;
import r3source.Utility;
import r3source.Validator;
import scheduler.YearSched;

public class EmployeeRecordScreen
extends JPanel {
    private JButton btnUpdate;
    private JButton btnCancel;
    private JButton btnClose;
    private JButton btnAdd;
    private JButton btnDelete;
    private JButton btnChangeSS;
    private JTextField txtLastName;
    private JTextField txtFirstName;
    private JTextField txtSSAmt;
    private JTextField txtECAmt;
    private JTextField txtHSDate;
    private JFormattedTextField txtMidInit;
    private JFormattedTextField txtEmployeeId;
    private JFormattedTextField txtRemarks;
    private JLabel lblMessage;
    private R3File r3File;
    private Employee employee;
    private Hashtable<String, Employee> htEE;
    private Hashtable<String, Employee> eeDB;
    private JFrame frame;
    private Container container;
    private Hashtable<Double, Double> htSchedule;
    private YearSched ys;
    private static final long serialVersionUID = 49L;
    private JLabel jLabel1 = new JLabel();

    public EmployeeRecordScreen(JFrame frame, R3File r3File, Container container) {
        this.r3File = r3File;
        this.frame = frame;
        this.container = container;
        this.setOpaque(true);
        this.add(this.createGUI());
        this.setButtonsEnabled(false);
        this.initialize();
        this.txtEmployeeId.requestFocusInWindow();
    }

    public EmployeeRecordScreen() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        ApplicablePeriod appPeriod;
        this.htEE = this.r3File.getEmployees();
        if (this.htEE == null) {
            this.htEE = new Hashtable();
        }
        this.eeDB = Utility.getEEDB();
        if (this.eeDB == null) {
            this.eeDB = new Hashtable();
        }
        if ((appPeriod = this.r3File.getAppPeriod()).getAppYear() > 2002 && (appPeriod.getAppYear() != 2003 || appPeriod.getAppMo() >= 3)) {
            this.ys = appPeriod.getYearSched();
            this.htSchedule = this.ys.getSchedule();
        }
    }

    private JPanel createGUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add((Component)this.northPanel(), "North");
        panel.add((Component)this.centerPanel(), "Center");
        panel.add((Component)new JPanel(), "South");
        panel.add((Component)this.eastPanel(), "East");
        panel.add((Component)new JPanel(), "West");
        return panel;
    }

    private JPanel centerPanel() {
        JPanel[] panel = new JPanel[10];
        for (int i = 0; i < panel.length; ++i) {
            panel[i] = new JPanel();
            panel[i].setLayout(new FlowLayout(2));
        }
        panel[0].add(new JLabel("SS Number: "));
        this.txtEmployeeId = new JFormattedTextField(Utility.createFormatter("##-#######-#"));
        this.txtEmployeeId.setColumns(17);
        this.txtEmployeeId.setFont(new Font("area", 1, 14));
        this.txtEmployeeId.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent f) {
                EmployeeRecordScreen.this.onFocusGained(f);
            }
        });
        this.txtEmployeeId.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeRecordScreen.this.onEmployeeIdSearch();
            }
        });
        panel[0].add(this.txtEmployeeId);
        panel[1].add(new JLabel("Last Name: "));
        this.txtLastName = new JTextField(17);
        this.txtLastName.setFont(new Font("area", 1, 14));
        this.txtLastName.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeRecordScreen.this.onEnter(e);
            }
        });
        this.txtLastName.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent f) {
                EmployeeRecordScreen.this.onFocusGained(f);
            }

            @Override
            public void focusLost(FocusEvent f) {
                EmployeeRecordScreen.this.onFocusLost(f);
            }
        });
        panel[1].add(this.txtLastName);
        panel[2].add(new JLabel("First Name: "));
        this.txtFirstName = new JTextField(17);
        this.txtFirstName.setFont(new Font("area", 1, 14));
        this.txtFirstName.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeRecordScreen.this.onEnter(e);
            }
        });
        this.txtFirstName.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent f) {
                EmployeeRecordScreen.this.onFocusGained(f);
            }

            @Override
            public void focusLost(FocusEvent f) {
                EmployeeRecordScreen.this.onFocusLost(f);
            }
        });
        panel[2].add(this.txtFirstName);
        panel[3].add(new JLabel("Middle Initial: "));
        this.txtMidInit = new JFormattedTextField(this.createFormatter("?", 2));
        this.txtMidInit.setColumns(12);
        this.txtMidInit.setFont(new Font("area", 1, 14));
        this.txtMidInit.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeRecordScreen.this.onEnter(e);
            }
        });
        this.txtMidInit.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent f) {
                EmployeeRecordScreen.this.onFocusGained(f);
            }

            @Override
            public void focusLost(FocusEvent f) {
                EmployeeRecordScreen.this.onFocusLost(f);
            }
        });
        panel[3].add(this.txtMidInit);
        panel[4].add(new JLabel("SS Amount: "));
        this.txtSSAmt = new JTextField(12);
        this.txtSSAmt.setFont(new Font("area", 1, 14));
        this.txtSSAmt.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeRecordScreen.this.onEnter(e);
            }
        });
        this.txtSSAmt.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent f) {
                EmployeeRecordScreen.this.onFocusGained(f);
            }

            @Override
            public void focusLost(FocusEvent f) {
                EmployeeRecordScreen.this.onFocusLost(f);
            }
        });
        panel[4].add(this.txtSSAmt);
        panel[5].add(new JLabel("EC Amount: "));
        this.txtECAmt = new JTextField(12);
        this.txtECAmt.setFont(new Font("area", 1, 14));
        this.txtECAmt.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeRecordScreen.this.onEnter(e);
            }
        });
        this.txtECAmt.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent f) {
                EmployeeRecordScreen.this.onFocusGained(f);
            }

            @Override
            public void focusLost(FocusEvent f) {
                EmployeeRecordScreen.this.onFocusLost(f);
            }
        });
        panel[5].add(this.txtECAmt);
        panel[6].add(new JLabel("Remarks:  N-Normal  1-New Hire  2-Separated  3-No Earnings"));
        panel[7].setLayout(new FlowLayout());
        this.txtRemarks = new JFormattedTextField(this.createFormatter("A", 1));
        this.txtRemarks.setColumns(12);
        this.txtRemarks.setFont(new Font("area", 1, 14));
        this.txtRemarks.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeRecordScreen.this.onEnter(e);
            }
        });
        this.txtRemarks.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent f) {
                EmployeeRecordScreen.this.onFocusGained(f);
            }

            @Override
            public void focusLost(FocusEvent f) {
                EmployeeRecordScreen.this.onRemarksChange();
            }
        });
        panel[7].add(this.txtRemarks);
        panel[8].setLayout(new FlowLayout());
        panel[8].add(new JLabel("Date Hired/Separated: ( mmddyyyy )"));
        panel[9].setLayout(new FlowLayout());
        this.txtHSDate = new JTextField(12);
        this.txtHSDate.setFont(new Font("area", 1, 14));
        this.txtHSDate.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeRecordScreen.this.onEnter(e);
            }
        });
        this.txtHSDate.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent f) {
                EmployeeRecordScreen.this.onFocusGained(f);
            }
        });
        this.txtHSDate.setEditable(false);
        panel[9].add(this.txtHSDate);
        JPanel gridPanel = new JPanel();
        gridPanel.setBorder(BorderFactory.createEtchedBorder());
        gridPanel.setLayout(new GridLayout(10, 1));
        for (int i = 0; i < panel.length; ++i) {
            gridPanel.add(panel[i]);
        }
        this.isFieldsEditable(false);
        return gridPanel;
    }

    private JPanel eastPanel() {
        String[] captions = new String[]{"<html> <font color=blue size=-1><b>Add</b> </font>", "<html> <font color=blue size=-1><b>Delete</b> </font>", "<html> <font color=blue size=-1><b>Change SS No.</b> </font>", "<html> <font color=blue size=-1><b>Update Record</b> </font>", "<html> <font color=blue size=-1><b>Cancel</b> </font>", "<html> <font color=blue size=-1><b>Close</b> </font>"};
        this.btnAdd = new JButton(captions[0]);
        this.btnAdd.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == 10) {
                    EmployeeRecordScreen.this.onAdd();
                }
            }
        });
        this.btnAdd.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeRecordScreen.this.onAdd();
            }
        });
        this.btnDelete = new JButton(captions[1]);
        this.btnDelete.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeRecordScreen.this.onDelete();
            }
        });
        this.btnChangeSS = new JButton(captions[2]);
        this.btnChangeSS.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeRecordScreen.this.onChangeSS();
            }
        });
        this.btnUpdate = new JButton(captions[3]);
        this.btnUpdate.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == 10) {
                    EmployeeRecordScreen.this.onUpdate();
                }
            }
        });
        this.btnUpdate.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeRecordScreen.this.onUpdate();
            }
        });
        this.btnCancel = new JButton(captions[4]);
        this.btnCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeRecordScreen.this.onCancel();
            }
        });
        this.btnClose = new JButton(captions[5]);
        this.btnClose.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == 10) {
                    EmployeeRecordScreen.this.onClose();
                }
            }
        });
        this.btnClose.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeRecordScreen.this.onClose();
            }
        });
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setLayout(new GridLayout(6, 1, 1, 1));
        panel.add(this.btnAdd);
        panel.add(this.btnDelete);
        panel.add(this.btnChangeSS);
        panel.add(this.btnUpdate);
        panel.add(this.btnCancel);
        panel.add(this.btnClose);
        JPanel panelNorth = new JPanel();
        panelNorth.setLayout(new BorderLayout());
        panelNorth.add((Component)panel, "Center");
        panelNorth.add((Component)this.panelMessage(), "South");
        return panelNorth;
    }

    private JPanel panelMessage() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Message"), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
        String message = "<html> <font color=blue size=-2><b>Enter SSS Number and <br>press ENTER to continue</b> </font>";
        this.lblMessage = new JLabel(message);
        panel.add(this.lblMessage);
        return panel;
    }

    private JPanel northPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(0));
        String caption = "<html> <font color=blue size=+1><b> File Maintenance <br>EMPLOYEE RECORD</b> </font>";
        JLabel label = new JLabel(caption, new ImageIcon("r3source/image/sss.gif"), 0);
        panel.add(label);
        return panel;
    }

    private void onDelete() {
        Object[] options = new Object[]{"Delete", "Cancel", "Delete All"};
        int returnValue = JOptionPane.showOptionDialog(this, "Click DELETE button to delete this current record in this R3 File.\nClick DELETE ALL to delete all employee recrods in this R3 File.", "Delete", -1, 3, null, options, options[0]);
        if (returnValue == 0) {
            String id = (String)this.txtEmployeeId.getValue();
            this.htEE.remove(id);
            this.refresh();
            JOptionPane.showMessageDialog(this, "Record successfully deleted.");
        } else {
            int option;
            if (returnValue == 1) {
                return;
            }
            if (returnValue == 2 && (option = JOptionPane.showConfirmDialog(this, "Delete ALL employee records in this R3 File? \n Are you sure", "Warning", 0, 3)) == 0) {
                this.htEE.clear();
                this.refresh();
                JOptionPane.showMessageDialog(this, "All records successfully deleted.");
            }
        }
    }

    private void onChangeSS() {
        this.txtEmployeeId.setEditable(true);
        String message = "<html> <font color=blue size=-2><b>You can now make changes<br>with the SSS Number.</b> </font>";
        this.lblMessage.setText(message);
        this.txtEmployeeId.requestFocusInWindow();
    }

    private void onAdd() {
        this.addUpdate();
    }

    private void onUpdate() {
        this.addUpdate();
    }

    private void addUpdate() {
        if (!this.isFieldsValid()) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        String prevSSNo = null;
        if (this.btnChangeSS.isEnabled() && (prevSSNo = this.employee.getEeSssNumber()) != null) {
            Employee ee = this.htEE.remove(prevSSNo);
            this.eeDB.remove(prevSSNo);
            if (this.htEE.containsKey((String)this.txtEmployeeId.getValue())) {
                Employee eeAlready = this.htEE.get((String)this.txtEmployeeId.getValue());
                Object[] options = new Object[]{"Revert", "Overwrite"};
                int returnValue = JOptionPane.showOptionDialog(this, "This SSNumber, " + (String)this.txtEmployeeId.getValue() + " you are trying to use as \nreplacement already exists. See details below:\n\n" + "Existing Record: " + eeAlready.printInfo() + "\n\nCurrent Record: " + ee.printInfo() + "\n\nWhat do you want to do?", "STOP: Already exists", -1, 3, null, options, options[0]);
                if (returnValue == 0) {
                    this.txtEmployeeId.setText(ee.getEeSssNumber());
                    this.txtEmployeeId.setValue(ee.getEeSssNumber());
                    System.out.println("REVERT: txtEmployeeId value: " + this.txtEmployeeId.getValue());
                } else if (returnValue == 1) {
                    System.out.println("OVERWRITE: txtEmployeeId value: " + this.txtEmployeeId.getValue());
                } else if (returnValue == -1) {
                    this.txtEmployeeId.setText(ee.getEeSssNumber());
                    this.txtEmployeeId.setValue(ee.getEeSssNumber());
                }
            }
        }
        this.employee = new Employee((String)this.txtEmployeeId.getValue(), this.txtLastName.getText(), this.txtFirstName.getText(), this.r3File.getEmployer(), this.txtHSDate.getText());
        Contribution contri = new Contribution(this.r3File.getAppPeriod(), Double.parseDouble(this.txtSSAmt.getText()), Double.parseDouble(this.txtECAmt.getText()), this.txtRemarks.getText());
        this.employee.setContribution(contri);
        if (this.txtMidInit.getText().length() == 1) {
            this.employee.setMidInit(this.txtMidInit.getText());
        }
        this.htEE.put(this.employee.getEeSssNumber(), this.employee);
        this.eeDB.put(this.employee.getEeSssNumber(), this.employee);
        this.refresh();
        JOptionPane.showMessageDialog(this, "Record successfully updated/added.");
    }

    private void onCancel() {
        this.refresh();
    }

    private void onClose() {
        this.r3File.setEmployees(this.htEE);
        Utility.updateEEDB(this.eeDB);
        this.container.remove(0);
        this.container.add(new FileMaintenanceScreen(this.frame, this.r3File, this.container));
        this.container.validate();
    }

    public void onEmployeeIdSearch() {
        String id = (String)this.txtEmployeeId.getValue();
        if (Validator.isSssNumberValid(id)) {
            this.lblMessage.setText("");
            if (this.btnChangeSS.isEnabled()) {
                String message = "<html> <font color=blue size=-2><b>Complete the details and<br>press UPDATE button to<br>save this record.</b> </font>";
                this.lblMessage.setText(message);
                this.txtEmployeeId.setEditable(false);
                this.txtLastName.requestFocusInWindow();
                return;
            }
            if (this.htEE.containsKey(id)) {
                this.employee = this.htEE.get(id);
                this.displayEmployeeDetails(this.employee);
            } else if (this.eeDB.containsKey(id)) {
                int option = JOptionPane.showConfirmDialog(this, "Do you want to load previously deleted record?", "Warning", 0, 3);
                if (option == 0) {
                    this.employee = this.eeDB.get(id);
                    this.displayEmployeeDetails(this.employee);
                }
            } else {
                this.onEmployeeNotFound();
            }
        } else {
            String message = "<html> <font color=red size=-2><b>Invalid SS Number, correct<br> the error and try again</b> </font>";
            this.lblMessage.setText(message);
        }
    }

    private void displayEmployeeDetails(Employee e) {
        String message = "<html> <font color=blue size=-2><b>Record FOUND, press <br>UPDATE to save changes.</b> </font>";
        this.lblMessage.setText(message);
        this.txtEmployeeId.setEditable(false);
        this.isFieldsEditable(true);
        this.txtLastName.setText(e.getLastName());
        this.txtFirstName.setText(e.getFirstName());
        this.txtMidInit.setText(e.getMidInit());
        this.txtHSDate.setText(e.getHSDate());
        Contribution c = e.getContribution();
        this.txtSSAmt.setText(new DecimalFormat("0.00").format(c.getSSSAmt()));
        this.txtECAmt.setText(new DecimalFormat("0.00").format(c.getECAmt()));
        this.txtRemarks.setText(c.getRemark());
        this.setButtonsEnabled(true);
        this.btnAdd.setEnabled(false);
        this.txtHSDate.setEditable(false);
        this.txtSSAmt.requestFocusInWindow();
    }

    private void onEmployeeNotFound() {
        int option = JOptionPane.showConfirmDialog(this, "This SS Number does not exist. Add this new record?", "Warning", 0, 3);
        if (option == 0) {
            this.setButtonsEnabled(false);
            this.btnAdd.setEnabled(true);
            this.txtEmployeeId.setEditable(false);
            this.isFieldsEditable(true);
            this.txtHSDate.setEditable(false);
            this.initializeDefaults();
            String message = "<html> <font color=blue size=-2><b>Complete the details and<br>press UPDATE button to<br>save this record.</b> </font>";
            this.lblMessage.setText(message);
            this.txtLastName.requestFocusInWindow();
        } else {
            this.onCancel();
        }
    }

    private void setButtonsEnabled(boolean b) {
        this.btnUpdate.setEnabled(b);
        this.btnCancel.setEnabled(b);
        this.btnAdd.setEnabled(b);
        this.btnDelete.setEnabled(b);
        this.btnChangeSS.setEnabled(b);
    }

    private void refresh() {
        this.setButtonsEnabled(false);
        this.txtEmployeeId.setText("");
        this.txtEmployeeId.setValue(null);
        this.txtEmployeeId.setEditable(true);
        this.txtEmployeeId.requestFocusInWindow();
        this.txtLastName.setText("");
        this.txtFirstName.setText("");
        this.txtMidInit.setText("");
        this.txtSSAmt.setText("");
        this.txtECAmt.setText("");
        this.txtRemarks.setText("");
        this.txtHSDate.setText("");
        this.isFieldsEditable(false);
        String message = "<html> <font color=blue size=-2><b>Enter SSS Number and<br> press enter to continue</b> </font>";
        this.lblMessage.setText(message);
    }

    private void isFieldsEditable(boolean b) {
        this.txtLastName.setEditable(b);
        this.txtFirstName.setEditable(b);
        this.txtMidInit.setEditable(b);
        this.txtSSAmt.setEditable(b);
        this.txtECAmt.setEditable(b);
        this.txtRemarks.setEditable(b);
        this.txtHSDate.setEditable(b);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean isFieldsValid() {
        String r = this.txtRemarks.getText();
        if (!Validator.isSssNumberValid((String)this.txtEmployeeId.getValue())) {
            String message = "<html> <font color=red size=-2><b>Invalid SS Number,correct<br>the error & try again.</b> </font>";
            this.lblMessage.setText(message);
            this.txtEmployeeId.requestFocusInWindow();
            return false;
        }
        if (!Validator.isNameValid(this.txtLastName.getText())) {
            String message = "<html> <font color=red size=-2><b>Last name cant be<br>empty or null.</b> </font>";
            this.lblMessage.setText(message);
            this.txtLastName.requestFocusInWindow();
            return false;
        }
        if (!Validator.isNameValid(this.txtFirstName.getText())) {
            String message = "<html> <font color=red size=-2><b>First name cant be<br>empty or null.</b> </font>";
            this.lblMessage.setText(message);
            this.txtFirstName.requestFocusInWindow();
            return false;
        }
        if (!(r.equals("N") || r.equals("1") || r.equals("2") || r.equals("3"))) {
            String message = "<html> <font color=red size=-2><b>Invalid value for Remark field.<br>Correct the error and try again.</b> </font>";
            this.lblMessage.setText(message);
            this.txtRemarks.requestFocusInWindow();
            return false;
        }
        if (r.equals("1") || r.equals("2")) {
            if (this.txtHSDate.getText().length() != 8) {
                String message = "<html> <font color=red size=-2><b>Invalid value for<br>Hired-Separated date.</b> </font>";
                this.lblMessage.setText(message);
                this.txtHSDate.requestFocusInWindow();
                return false;
            }
            if (!Validator.isDateValidForNow(this.txtHSDate.getText())) {
                String message = "<html> <font color=red size=-2><b>Invalid value for<br>Hired-Separated date.</b> </font>";
                this.lblMessage.setText(message);
                this.txtHSDate.requestFocusInWindow();
                return false;
            }
        }
        try {
            double ecAmt = Double.parseDouble(this.txtECAmt.getText());
            double ssAmt = Double.parseDouble(this.txtSSAmt.getText());
            if (r.equals("3") && (ecAmt != 0.0 || ssAmt != 0.0)) {
                String message = "<html> <font color=red size=-2><b>If Remark is 3, SS & EC<br>should be 0.00. Correct<br>the error and try again.</b> </font>";
                this.lblMessage.setText(message);
                return false;
            }
            if (ecAmt == 0.0 && ssAmt == 0.0) {
                if (!r.equals("3")) {
                    String message = "<html> <font color=red size=-2><b>If SS & EC contribution are<br>0.00, Remark should be 3 or<br>vice-versa. Correct the error<br>and try again.</b> </font>";
                    this.lblMessage.setText(message);
                    return false;
                }
            } else if (ecAmt != 0.0 && ssAmt != 0.0) {
                if (this.r3File.getAppPeriod().getAppYear() > 2002) {
                    if (this.r3File.getAppPeriod().getAppYear() > 2003) {
                        System.out.println(this.htSchedule.toString());
                        if (!this.htSchedule.containsKey(ssAmt)) {
                            String message = "<html> <font color=red size=-2><b>Invalid amount for SS<br>contribution. Amount is<br>out-of-bracket. Correct the<br> error and try again.</b> </font>";
                            this.lblMessage.setText(message);
                            this.txtSSAmt.requestFocusInWindow();
                            return false;
                        }
                        double ecInHt = this.htSchedule.get(ssAmt);
                        if (ecInHt != ecAmt) {
                            String message = "<html> <font color=red size=-2><b>Invalid amount for EC<br>contribution. Amount is<br>out-of-bracket. Correct the<br> error and try again.</b> </font>";
                            this.lblMessage.setText(message);
                            this.txtECAmt.requestFocusInWindow();
                            return false;
                        }
                    } else if (this.r3File.getAppPeriod().getAppYear() >= 2003 && this.r3File.getAppPeriod().getAppMo() >= 3) {
                        if (!this.htSchedule.containsKey(ssAmt)) {
                            String message = "<html> <font color=red size=-2><b>Invalid amount for SS<br>contribution. Amount is<br>out-of-bracket. Correct the<br> error and try again.</b> </font>";
                            this.lblMessage.setText(message);
                            this.txtSSAmt.requestFocusInWindow();
                            return false;
                        }
                        double ecInHt = this.htSchedule.get(ssAmt);
                        if (ecInHt != ecAmt) {
                            String message = "<html> <font color=red size=-2><b>Invalid amount for EC<br>contribution. Amount is<br>out-of-bracket. Correct the<br> error and try again.</b> </font>";
                            this.lblMessage.setText(message);
                            this.txtECAmt.requestFocusInWindow();
                            return false;
                        }
                    }
                }
            } else {
                if (ecAmt == 0.0 && ssAmt != 0.0) {
                    String message = "<html> <font color=red size=-2><b>Invalid amount for EC<br>contribution. EC is mandatory.<br>Correct the error and try again.</b> </font>";
                    this.lblMessage.setText(message);
                    this.txtECAmt.requestFocusInWindow();
                    return false;
                }
                if (ecAmt != 0.0 && ssAmt == 0.0 && !this.ys.containsEC(ecAmt)) {
                    String message = "<html> <font color=red size=-2><b>Invalid amount for EC<br>contribution.EC must be equal to<br>the minimum EC value. Correct<br>the error and try again.</b> </font>";
                    this.lblMessage.setText(message);
                    this.txtECAmt.requestFocusInWindow();
                    return false;
                }
            }
        }
        catch (NumberFormatException nfe) {
            String message = "<html> <font color=red size=-2><b>Invalid amount for contri.<br> NumberFormatException: <br>" + nfe.getMessage() + ".</b> </font>";
            this.lblMessage.setText(message);
            return false;
        }
        if (this.txtMidInit.getText().length() != 0) {
            if (this.txtMidInit.getText().length() != 1) {
                return true;
            }
            char c = this.txtMidInit.getText().charAt(0);
            boolean b = Character.isLetter(c);
            if (!b) {
                return true;
            }
        }
        return true;
    }

    private void initializeDefaults() {
        this.txtHSDate.setText("0");
        this.txtRemarks.setText("N");
        this.txtSSAmt.setText("0.00");
        this.txtECAmt.setText("0.00");
    }

    private void onEnter(ActionEvent e) {
        if (e.getSource() == this.txtEmployeeId) {
            String id = (String)this.txtEmployeeId.getValue();
            if (Validator.isSssNumberValid(id)) {
                String message = "<html> <font color=blue size=-2><b>Complete the details and<br>press UPDATE button to<br>save this record.</b> </font>";
                this.lblMessage.setText(message);
                this.txtEmployeeId.transferFocus();
            } else {
                String message = "<html> <font color=red size=-2><b>Invalid value for SSS Number.</b> </font>";
                this.lblMessage.setText(message);
            }
        } else {
            ((JTextField)e.getSource()).transferFocus();
        }
    }

    private void onFocusGained(FocusEvent f) {
        JTextField txt = (JTextField)f.getSource();
        if (txt.getText().length() > 0) {
            txt.selectAll();
        }
    }

    private void onFocusLost(FocusEvent f) {
        if (f.getSource() == this.txtLastName || f.getSource() == this.txtFirstName || f.getSource() == this.txtMidInit) {
            JTextField txt = (JTextField)f.getSource();
            txt.setText(txt.getText().toUpperCase());
            String message = "<html> <font color=blue size=-2><b>Complete the details and<br>press UPDATE button to<br>save this record.</b> </font>";
            this.lblMessage.setText(message);
            return;
        }
        JTextField txt = (JTextField)f.getSource();
        String str = txt.getText();
        try {
            if (!this.lblMessage.getText().equals("<html> <font color=blue size=-2><b>Enter SSS Number and <br> press enter to continue</b> </font>")) {
                str = new DecimalFormat("0.00").format(Double.parseDouble(str));
                txt.setText(str);
                String message = "<html> <font color=blue size=-2><b>Complete the details and<br>press UPDATE button to<br>save this record.</b> </font>";
                this.lblMessage.setText(message);
            }
        }
        catch (NumberFormatException nfe) {
            txt.setText("0.00");
            this.lblMessage.setText(nfe.getMessage());
        }
    }

    private void onRemarksChange() {
        String remark = this.txtRemarks.getText().toUpperCase();
        this.txtRemarks.setText(remark);
        if (remark.equals("N")) {
            this.txtHSDate.setText("0");
            this.txtHSDate.setEditable(false);
        } else if (remark.equals("1") || remark.equals("2")) {
            this.txtHSDate.setEditable(true);
        } else if (remark.equals("3")) {
            this.txtHSDate.setText("0");
            this.txtHSDate.setEditable(false);
        } else {
            this.txtRemarks.setText("N");
            this.txtHSDate.setText("0");
            this.txtHSDate.setEditable(false);
        }
        String message = "<html> <font color=blue size=-2><b>Complete the details and<br>press UPDATE button to<br>save this record.</b> </font>";
        this.lblMessage.setText(message);
    }

    public MaskFormatter createFormatter(String s, int i) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
            if (i == 1) {
                formatter.setValidCharacters("123Nn");
            }
        }
        catch (ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
        }
        return formatter;
    }

    private void jbInit() throws Exception {
    }
}

