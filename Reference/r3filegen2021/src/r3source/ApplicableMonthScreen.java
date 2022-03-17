package r3source;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.Border;
import r3source.ApplicablePeriod;
import r3source.Utility;

public class ApplicableMonthScreen
extends JDialog {
    private JButton btnOk;
    private JButton btnCancel;
    private JComboBox cmbMonth;
    private JComboBox cmbYear;
    private ApplicablePeriod appPeriod;
    private boolean isCancel;
    private static final long serialVersionUID = 42L;

    public ApplicableMonthScreen(JFrame parent, String str) {
        super(parent, "Social Security Sytem - R3", true);
        this.setSize(500, 250);
        this.setDefaultCloseOperation(0);
        this.setLocationRelativeTo(parent);
        this.setContentPane(this.createGUI(str));
        this.getRootPane().setDefaultButton(this.btnOk);
    }

    public ApplicableMonthScreen(JFrame parent, String str, ApplicablePeriod appPeriod) {
        this(parent, str);
        this.appPeriod = appPeriod;
        this.setAppPeriod();
    }

    private JPanel createGUI(String str) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add((Component)this.northPanel(str), "North");
        panel.add((Component)this.centerPanel(), "Center");
        panel.add((Component)this.southPanel(), "South");
        panel.add((Component)new JPanel(), "East");
        panel.add((Component)new JPanel(), "West");
        panel.setOpaque(true);
        return panel;
    }

    private JPanel centerPanel() {
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
        pane.add((Component)new JPanel(), "North");
        pane.add((Component)panel, "Center");
        return pane;
    }

    private void setAppPeriod() {
        this.cmbYear.setSelectedItem(String.valueOf(this.appPeriod.getAppYear()));
        this.cmbMonth.setSelectedItem(this.appPeriod.strImageMonth());
    }

    private JPanel southPanel() {
        String[] captions = new String[]{"<html> <font color=blue size=-1><b>Ok</b> </font>", "<html> <font color=blue size=-1><b>Cancel</b> </font>"};
        this.btnOk = new JButton(captions[0]);
        this.btnOk.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ApplicableMonthScreen.this.onOk();
            }
        });
        this.btnCancel = new JButton(captions[1]);
        this.btnCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ApplicableMonthScreen.this.onCancel();
            }
        });
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(this.btnOk);
        panel.add(this.btnCancel);
        return panel;
    }

    private JPanel northPanel(String str) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(0));
        String caption = "<html> <font color=blue size=-1><b> " + str + ":</b> </font>";
        JLabel label = new JLabel(caption, new ImageIcon("image/sss.gif"), 0);
        panel.add(label);
        return panel;
    }

    private void onOk() {
        if (!this.isCorrect()) {
            JOptionPane.showMessageDialog(this, "Invalid values for applicable period.", "Error", 0);
           return;
        }
        int appMo = Integer.parseInt(String.valueOf(this.cmbMonth.getSelectedItem()));
        int appYear = Integer.parseInt(String.valueOf(this.cmbYear.getSelectedItem()));
        this.appPeriod = new ApplicablePeriod(appMo, appYear);
        this.isCancel = false;
        this.setVisible(false);
    }

    private void onCancel() {
        this.isCancel = true;
        this.setVisible(false);
    }

    public ApplicablePeriod getAppPeriod() {
        return this.appPeriod;
    }

    public boolean getIsCancel() {
        return this.isCancel;
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

