package r3source;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import r3source.ApplicableMonthScreen;
import r3source.ApplicablePeriod;
import r3source.Employer;
import r3source.EmployerRecordScreen;
import r3source.MainScreen;
import r3source.R3File;
import r3source.Utility;

public class R3FileGen {
    private static Container container;
    public static R3File r3File;
    private static JFrame frame;

    public void initial() {
        File erFile = new File("er.dat");
        if (!erFile.exists()) {
            EmployerRecordScreen ers = new EmployerRecordScreen(frame);
            ers.setVisible(true);
            if (ers.getIsUpdateSelected()) {
                Utility.updateER(ers.getEmployer());
                JOptionPane.showMessageDialog(frame, "Employer details successfully updated.");
                this.callApplicableScreen(ers.getEmployer());
            } else {
                System.exit(0);                    
            }
        } else {
            File nr300File = new File("r3File.dat");
            if (nr300File.exists()) {
                r3File = Utility.getR3File();
            } else {
                this.callApplicableScreen(Utility.readER());
            }
        }
    }

    private void onNewR3File() {
        if (r3File.isCompleted()) {
            int option = JOptionPane.showConfirmDialog(frame, "CREATE new R3 file?", "Question", 0, 3);
            if (option == 0) {
                ApplicableMonthScreen ams = new ApplicableMonthScreen(frame, "You are currently encoding R3 For:");
                ams.setVisible(true);
                if (ams.getIsCancel()) {
                    System.exit(0);
                } else {
                    ApplicablePeriod appPeriod = ams.getAppPeriod();
                    r3File = new R3File(appPeriod, Utility.readER());
                }
            } else {
                r3File = Utility.getR3File();
            }
        }
    }

    private void callApplicableScreen(Employer er) {
        ApplicableMonthScreen ams = new ApplicableMonthScreen(frame, "You are currently encoding R3 For:");
        ams.setVisible(true);
        if (ams.getIsCancel()) {
            System.exit(0);
        } else {
            ApplicablePeriod appPeriod = ams.getAppPeriod();
            r3File = Utility.getR3File();
            if (r3File == null) {
                r3File = new R3File(appPeriod, er);
            }
        }
    }

    private static void createAndShowGUI() {
        frame = new JFrame("Social Security System");
        frame.setDefaultCloseOperation(0);
        frame.setResizable(false);
        new R3FileGen().initial();
        container = frame.getContentPane();
        container.setLayout(new BorderLayout());
        MainScreen newContentPane = new MainScreen(frame, container);
        newContentPane.setOpaque(true);
        container.add(newContentPane);
        frame.setSize(540, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                R3FileGen.createAndShowGUI();
             }
        });
    }

}

