package r3source;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import r3source.ApplicablePeriod;
import r3source.Employer;
import r3source.FileMaintenanceScreen;
import r3source.Payment;
import r3source.R3File;
import r3source.Utility;
import r3source.Validator;

public class PaymentRecordScreen
extends JPanel {
    private JButton btnContinue;
    private JButton btnCancel;
    private JButton btnClose;
    private JTextField txtDateOfPayment;
    private JTextField txtSbrNo;
    private JTextField txtAmountPaid;
    private JLabel lblCompany;
    private JLabel lblSSSNumber;
    private JLabel lblAppPeriod;
    private JLabel lblMessage;
    private JLabel lblPayment;
    private Container container;
    private JFrame frame;
    private JCheckBox chkPay;
    private R3File r3File;
    private JProgressBar progressBar;
    private static final long serialVersionUID = 54L;
    private boolean selected;

    public PaymentRecordScreen(JFrame parent, R3File r3File, Container c) {
        this.r3File = r3File;
        this.container = c;
        this.frame = parent;
        this.setOpaque(true);
        this.add(this.createGUI());
    }

    private JPanel createGUI() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setLayout(new BorderLayout());
        panel.add((Component)this.northPanel(), "North");
        panel.add((Component)this.centerPanel(), "Center");
        panel.add((Component)this.progressPanel(), "South");
        return panel;
    }

    private JPanel progressPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEtchedBorder());
        this.progressBar = new JProgressBar(0, 100);
        this.progressBar.setValue(0);
        this.progressBar.setStringPainted(true);
        this.progressBar.setString("Ready");
        panel.add(this.progressBar);
        return panel;
    }

    private JPanel centerPanel() {
        this.chkPay = new JCheckBox();
        this.txtDateOfPayment = new JTextField(10);
        this.txtSbrNo = new JTextField(10);
        this.txtDateOfPayment.setEditable(false);
        this.txtSbrNo.setEditable(false);
        this.txtAmountPaid = new JTextField(10);
        this.txtAmountPaid.setText("0.00");
        this.selected = false;
        String[] caption = new String[]{"<html> <font color=black size=-1><b> Employer Name: </b> </font><font color=blue size=+1 > <b>" + this.r3File.getEmployer().getErName() + "</b> </font>", "<html> <font color=black size=-1><b> Employer Number: </b> </font><font color=blue size=+1 > <b>" + Utility.formatSSnum(this.r3File.getEmployer().getErSssNumber()) + "</b> </font>", "<html> <font color=black size=-1><b> Applicable Period: </b> </font><font color=blue size=+1 > <b>" + this.r3File.getAppPeriod().toString() + "</b> </font>", "<html><font color=black size=-1><b>Tick box if payment was already made</b></font>"};
        JPanel panelNorth = new JPanel();
        panelNorth.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelNorth.setLayout(new GridLayout(4, 1, 1, 10));
        this.lblCompany = new JLabel(caption[0]);
        panelNorth.add(this.lblCompany);
        this.lblSSSNumber = new JLabel(caption[1]);
        panelNorth.add(this.lblSSSNumber);
        this.lblAppPeriod = new JLabel(caption[2]);
        panelNorth.add(this.lblAppPeriod);
        JPanel paypan = new JPanel();
        paypan.setLayout(new FlowLayout(0));
        this.lblPayment = new JLabel(caption[3]);
        paypan.add(this.lblPayment);
        this.chkPay.requestFocusInWindow();
        paypan.add(this.chkPay);
        this.chkPay.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractButton absbut = (AbstractButton)e.getSource();
                PaymentRecordScreen.this.selected = absbut.getModel().isSelected();
                if (PaymentRecordScreen.this.selected) {
                    PaymentRecordScreen.this.txtDateOfPayment.setEditable(true);
                    PaymentRecordScreen.this.txtSbrNo.setEditable(true);
                    PaymentRecordScreen.this.txtAmountPaid.setEditable(true);
                    PaymentRecordScreen.this.txtSbrNo.setText("");
                    PaymentRecordScreen.this.txtDateOfPayment.setText("");
                } else {
                    PaymentRecordScreen.this.txtDateOfPayment.setEditable(false);
                    PaymentRecordScreen.this.txtSbrNo.setEditable(false);
                    PaymentRecordScreen.this.txtAmountPaid.setEditable(false);
                }
            }
        });
        this.chkPay.doClick();
        chkPay.setEnabled(false);
        panelNorth.add(paypan);
        JPanel panelSbr = new JPanel();
        panelSbr.setBorder(BorderFactory.createEtchedBorder());
        panelSbr.setLayout(new GridLayout(3, 1));
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(2));
        panel1.add(new JLabel("Date of Payment(mmddyyyy): "));
        this.txtDateOfPayment.setFont(new Font("area", 1, 13));
        this.txtDateOfPayment.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                PaymentRecordScreen.this.onEnter(e);
            }
        });
        this.txtDateOfPayment.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent f) {
                PaymentRecordScreen.this.onFocusGained(f);
            }
        });
        this.txtDateOfPayment.requestFocusInWindow();
        panel1.add(this.txtDateOfPayment);
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(2));
        panel2.add(new JLabel("TR/SBR Number: "));
        this.txtSbrNo.setFont(new Font("area", 1, 13));
        this.txtSbrNo.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                PaymentRecordScreen.this.onEnter(e);
            }
        });
        this.txtSbrNo.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent f) {
                PaymentRecordScreen.this.onFocusGained(f);
            }
        });
        this.txtSbrNo.addFocusListener(new FocusAdapter(){

            @Override
            public void focusLost(FocusEvent f) {
                PaymentRecordScreen.this.onFocusLost(f);
            }
        });
        panel2.add(this.txtSbrNo);
        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(2));
        panel3.add(new JLabel("Amount Paid: "));
        this.txtAmountPaid.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                PaymentRecordScreen.this.onEnter(e);
            }
        });
        this.txtAmountPaid.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent f) {
                PaymentRecordScreen.this.onFocusGained(f);
            }

            @Override
            public void focusLost(FocusEvent f) {
                PaymentRecordScreen.this.onFocusLost(f);
            }
        });
        this.txtAmountPaid.setFont(new Font("area", 1, 13));
        panel3.add(this.txtAmountPaid);
        panelSbr.add(panel1);
        panelSbr.add(panel2);
        panelSbr.add(panel3);
        JPanel panelSbrButtons = new JPanel();
        panelSbrButtons.setLayout(new BorderLayout());
        panelSbrButtons.add((Component)new JPanel(), "North");
        panelSbrButtons.add((Component)panelSbr, "Center");
        panelSbrButtons.add((Component)this.eastPanel(), "East");
        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new BorderLayout());
        panelCenter.add((Component)panelNorth, "North");
        panelCenter.add((Component)panelSbrButtons, "Center");
        return panelCenter;
    }

    private JPanel eastPanel() {
        String[] captions = new String[]{"<html> <font color=blue size=-1><b>Continue</b> </font>", "<html> <font color=blue size=-1><b>Cancel</b> </font>", "<html> <font color=blue size=-1><b>Close</b> </font>"};
        this.btnContinue = new JButton(captions[0]);
        this.btnContinue.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == 10) {
                    PaymentRecordScreen.this.onContinue();
                }
            }
        });
        this.btnContinue.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                PaymentRecordScreen.this.onContinue();
            }
        });
        this.btnCancel = new JButton(captions[1]);
        this.btnCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                PaymentRecordScreen.this.onCancel();
            }
        });
        this.btnClose = new JButton(captions[2]);
        this.btnClose.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                PaymentRecordScreen.this.onClose();
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
        panel.add((Component)this.btnContinue, c);
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
        panel.setBorder(BorderFactory.createTitledBorder("Message"));
        String message = "<html> <font color=blue size=-2><b>Please enter payment<br> details to create R3 file.</b> </font>";
        this.lblMessage = new JLabel(message);
        panel.add(this.lblMessage);
        return panel;
    }

    private JPanel northPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setLayout(new FlowLayout(0));
        String caption = "<html> <font color=blue size=+1><b> File Maintenance <br>PAYMENT RECORD</b> </font>";
        JLabel label = new JLabel(caption, new ImageIcon("image/sss.gif"), 0);
        panel.add(label);
        return panel;
    }

    private void onContinue() {
        if (!this.selected) {
            this.txtSbrNo.setText("NOPAY");
            SimpleDateFormat sd = new SimpleDateFormat("MMddyyyy");
            this.txtDateOfPayment.setText(sd.format(new Date()));
            this.txtAmountPaid.setText(Double.toString(this.r3File.getTotalOfSSContribution() + this.r3File.getTotalOfECContribution()));
        }
        if (!this.validateFields()) {
            return;
        }
        Payment payment = new Payment(this.r3File.getAppPeriod(), this.txtSbrNo.getText(), Double.parseDouble(this.txtAmountPaid.getText().replaceAll(",", "")), this.txtDateOfPayment.getText());
        this.r3File.setPayment(payment);
        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmm");
        String extension = sdf.format(new Date());
        String fileName = "R3" + this.r3File.getEmployer().getErSssNumber() + this.r3File.getAppPeriod().strImage() + "." + extension;
        File file = new File(fileName);
        JFileChooser chooser = new JFileChooser("a:/");
        chooser.setFileSelectionMode(0);
        chooser.setDialogTitle("SAVE R3File: Choose DIRECTORY location");
        chooser.setSelectedFile(file);
        int returnVal = chooser.showSaveDialog(this.frame);
        if (returnVal == 0) {
            File selectedFile = chooser.getSelectedFile();
            if (!selectedFile.getName().equals(fileName)) {
                JOptionPane.showMessageDialog(this, "R3File with fileName, " + fileName + " will be created." + "\nR3File file name cannot be changed to " + selectedFile.getName(), "STOP", 2);
                File f = new File(chooser.getCurrentDirectory().getAbsolutePath() + "/" + fileName);
                this.onCreatingR3File(f);
            } else {
                this.onCreatingR3File(chooser.getSelectedFile());
            }
        }
    }

    private void onCancel() {
        this.txtDateOfPayment.setText("");
        this.txtSbrNo.setText("");
        this.txtAmountPaid.setText("");
        String message = "<html> <font color=blue size=-2><b>Please enter the payment<br> details to create R3 file.</b> </font>";
        this.lblMessage.setText(message);
    }

    private void onClose() {
        this.container.remove(0);
        this.container.add(new FileMaintenanceScreen(this.frame, this.r3File, this.container));
        this.container.validate();
    }

    private boolean validateFields() {
        String mmddyyyy = this.txtDateOfPayment.getText();
        String sbrNo = this.txtSbrNo.getText();
        String amtPaid = this.txtAmountPaid.getText().replaceAll(",", "");
        try {
            Double.parseDouble(amtPaid);
            Double.parseDouble(mmddyyyy);
        }
        catch (NumberFormatException nfe) {
            String message = "<html> <font color=red size=-2><b>Invalid values. Correct<br>the error and try again. <br> " + nfe.getMessage() + "</b> </font>";
            this.lblMessage.setText(message);
            return false;
        }
        if (mmddyyyy.length() != 8) {
            String message = "<html> <font color=red size=-2><b>Invalid value for date. Correct<br>the error and try again.</b> </font>";
            this.lblMessage.setText(message);
            this.txtDateOfPayment.requestFocusInWindow();
            return false;
        }
        if (!Validator.isDateValidForNow(mmddyyyy)) {
            String message = "<html> <font color=red size=-2><b>Invalid value for date. Correct<br>the error and try again.</b> </font>";
            this.lblMessage.setText(message);
            this.txtDateOfPayment.requestFocusInWindow();
            return false;
        }
        if (sbrNo.length() == 0) {
            String message = "<html> <font color=red size=-2><b>Invalid value for SBR NO. <br>Correct the error & try again.</b> </font>";
            this.lblMessage.setText(message);
            this.txtSbrNo.requestFocusInWindow();
            return false;
        }
        double total = this.r3File.getTotalOfSSContribution() + this.r3File.getTotalOfECContribution();
        if (Double.parseDouble(amtPaid) < total) {
            String message = "<html> <font color=red size=-2><b>Insufficient R3 payment. Correct<br>the error and try again.</b> </font>";
            this.lblMessage.setText(message);
            this.txtAmountPaid.requestFocusInWindow();
            return false;
        }
        if (Double.parseDouble(amtPaid) > total) {
            int respond = JOptionPane.showConfirmDialog(this.frame, "Payment is greater than the Encoded amount.\nDo you want to continue creating R3File?", "WARNING", 0, 2);
            if (respond == 0) {
                return true;
            }
            return false;
        }
        return true;
    }

    private void onCreatingR3File(File file) {
        this.progressBar.setIndeterminate(true);
        this.progressBar.setStringPainted(true);
        this.progressBar.setString("Creating R3 file. Please wait...");
        this.frame.setEnabled(false);
        this.container.setCursor(Cursor.getPredefinedCursor(3));
        CreateSSSTextFileTask task = new CreateSSSTextFileTask(file);
        task.execute();
    }

    private void taskDone() {
        Toolkit.getDefaultToolkit().beep();
        this.progressBar.setValue(this.progressBar.getMaximum());
        this.progressBar.setStringPainted(true);
        this.progressBar.setString("R3 file generated...");
        this.progressBar.setIndeterminate(false);
        this.frame.setEnabled(true);
        this.container.setCursor(Cursor.getPredefinedCursor(0));
        JOptionPane.showMessageDialog(this.frame, "R3File is successfully created. You may submit the file to SSS.");
        this.onClose();
    }

    private void onFocusGained(FocusEvent f) {
        JTextField txt = (JTextField)f.getSource();
        if (txt.getText().length() > 0) {
            txt.selectAll();
        }
    }

    private void onFocusLost(FocusEvent f) {
        if (f.getSource() == this.txtSbrNo) {
            this.txtSbrNo.setText(this.txtSbrNo.getText().toUpperCase());
            return;
        }
        JTextField txt = (JTextField)f.getSource();
        String str = txt.getText();
        try {
            str = new DecimalFormat("#,###,##0.00").format(Double.parseDouble(str));
            txt.setText(str);
        }
        catch (NumberFormatException nfe) {
            // empty catch block
        }
        String message = "<html> <font color=blue size=-2><b>Please press Continue button<br> to create R3 file.</b> </font>";
        this.lblMessage.setText(message);
    }

    private void onEnter(ActionEvent e) {
        JTextField txt = (JTextField)e.getSource();
        txt.transferFocus();
    }

    class CreateSSSTextFileTask
    extends SwingWorker<Void, Void> {
        private File fileName;

        public CreateSSSTextFileTask(File fileName) {
            this.fileName = fileName;
        }

        @Override
        public Void doInBackground() {
            try {
                PaymentRecordScreen.this.r3File.createTextFile(this.fileName);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void done() {
            PaymentRecordScreen.this.taskDone();
        }
    }

}

