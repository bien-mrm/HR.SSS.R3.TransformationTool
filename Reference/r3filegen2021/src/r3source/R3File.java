package r3source;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.TreeSet;
import r3source.ApplicablePeriod;
import r3source.Contribution;
import r3source.Employee;
import r3source.Employer;
import r3source.Payment;
import r3source.Utility;

import scheduler.YearSched;

public class R3File
implements Serializable {
    private Employer er;
    private Payment payment;
    private Hashtable<String, Employee> employees;
    private ApplicablePeriod appPeriod;
    private boolean completed;
    private String nameOfFile;
    private static final long serialVersionUID = 43L;

    public R3File() {
    }

    public R3File(ApplicablePeriod appPeriod, Employer er) {
        this.appPeriod = appPeriod;
        this.er = er;
    }

    public R3File(ApplicablePeriod appPeriod, Employer er, Hashtable<String, Employee> employees, Payment payment) {
        this(appPeriod, er);
        this.employees = employees;
        this.payment = payment;
    }

    public Hashtable<String, Employee> getEmployees() {
        return this.employees;
    }

    public ApplicablePeriod getAppPeriod() {
        return this.appPeriod;
    }

    public Payment getPayment() {
        return this.payment;
    }

    public Employer getEmployer() {
        return this.er;
    }

    public int getTotalEEs() {
        if (this.employees != null) {
            return this.employees.size();
        }
        return 0;
    }

    public void setEmployer(Employer er) {
        this.er = er;
    }

    public void setApplicablePeriod(ApplicablePeriod appPeriod) {
        this.appPeriod = appPeriod;
        this.setAppPeriodOfContriForEveryEE(appPeriod);
    }

    private void setAppPeriodOfContriForEveryEE(ApplicablePeriod appPeriod) {
        for (Employee e : this.employees.values()) {
            Contribution contri = e.getContribution();
            contri.setApplicablePeriod(appPeriod);
        }
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setEmployees(Hashtable<String, Employee> employees) {
        this.employees = employees;
    }

    public double getTotalOfSSContribution() {
        double ssTotal = 0.0;
        if (this.employees == null) {
            return 0.0;
        }
        for (Employee e : this.employees.values()) {
            Contribution contri = e.getContribution();
            ssTotal += contri.getSSSAmt();
        }
        return ssTotal;
    }

    public double getTotalOfECContribution() {
        double ecTotal = 0.0;
        if (this.employees == null) {
            return 0.0;
        }
        for (Employee e : this.employees.values()) {
            Contribution contri = e.getContribution();
            ecTotal += contri.getECAmt();
        }
        return ecTotal;
    }

    public double getTotalOfSSandEC() {
        double total = 0.0;
        total = this.getTotalOfSSContribution() + this.getTotalOfECContribution();
        return total;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean b) {
        this.completed = b;
    }

    private String getHeaderImage() {
        String header = "";
        header = "00" + this.er.getErName() + this.appPeriod.strImage() + this.er.getErSssNumber();
        header = header + Utility.ensureLengthFor(this.getPayment().getSbrNo(), 10);
        header = header + this.getPayment().getSbrDate();
        header = header + new DecimalFormat("000000000.00").format(this.getPayment().getSbrAmt());
        return header;
    }

    private String getTrailerImage() {
        String trailer = "99 ";
        String strSSFormat = "       0.00";
        String strECFormat = "     0.00";
        String strSSSAMOUNT = Utility.formatSSEC(this.getTotalOfSSContribution(), 11);
        String strECAMOUNT = Utility.formatSSEC(this.getTotalOfECContribution(), 9);
        int appMo = this.appPeriod.getAppMo();
        if (appMo == 1 || appMo == 4 || appMo == 7 || appMo == 10) {
            trailer = trailer + strSSSAMOUNT + " " + strSSFormat + " " + strSSFormat + " " + strECFormat + " " + strECFormat + " " + strECFormat + " " + strECAMOUNT + " " + strECFormat + " " + strECFormat + "                    ";
        } else if (appMo == 2 || appMo == 5 || appMo == 8 || appMo == 11) {
            trailer = trailer + strSSFormat + " " + strSSSAMOUNT + " " + strSSFormat + " " + strECFormat + " " + strECFormat + " " + strECFormat + " " + strECFormat + " " + strECAMOUNT + " " + strECFormat + "                    ";
        } else if (appMo == 3 || appMo == 6 || appMo == 9 || appMo == 12) {
            trailer = trailer + strSSFormat + " " + strSSFormat + " " + strSSSAMOUNT + " " + strECFormat + " " + strECFormat + " " + strECFormat + " " + strECFormat + " " + strECFormat + " " + strECAMOUNT + "                    ";
        }
        return trailer;
    }

    public void createTextFile(File fileName) throws IOException {
        File r3File = new File("nr3001dk");
        PrintWriter ins = new PrintWriter(new BufferedWriter(new FileWriter(r3File)));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
        ins.println(this.getHeaderImage());
        out.println(this.getHeaderImage());
        TreeSet<Employee> ts = new TreeSet<Employee>(this.employees.values());
        for (Employee e : ts) {
            out.println("20" + e.getRecordImage());
            ins.println("20" + e.getRecordImage());
        }
        out.println(this.getTrailerImage());
        out.flush();
        out.close();
        ins.println(this.getTrailerImage());
        ins.flush();
        ins.close();
        this.nameOfFile = fileName.getName();
        this.completed = true;
    }

    public File createEmployeeReport() throws IOException {
        File fileName = null;
        if (this.employees != null) {
            fileName = new File("EMPLOYEE_LIST");
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            out.println(this.er.getErName() + " [" + this.nameOfFile + "]");
            out.println("Date: " + new SimpleDateFormat("MMM dd, yyyy").format(new Date()));
            out.println();
            out.println("FAMILY NAME     GIVEN NAME      MI  SS NUMBER       S.S.    E.C.   RMRK  DTHRD");
            out.println();
            TreeSet<Employee> ts = new TreeSet<Employee>(this.employees.values());
            for (Employee e : ts) {
                out.println(e.getPrintableRecord());
            }
            out.println();
            out.println();
            out.println("Total Number of Employees: " + ts.size());
            out.flush();
            out.close();
        }
        return fileName;
    }

    public File createTransmittalReport() throws IOException {
        File fileName = null;
        if (this.payment != null && this.nameOfFile != null) {
            fileName = new File("TRANSMITTAL_REPORT");
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            out.println();
            out.println();
            out.println("File name    : " + this.nameOfFile);
            out.println("Employer name: " + Utility.ensureLengthFor(this.er.getErName(), 30) + "\t   Date: " + new SimpleDateFormat("MMM dd, yyyy").format(new Date()));
            out.println("Employer No  : " + Utility.ensureLengthFor(Utility.formatSSnum(this.er.getErSssNumber()), 30) + "\t App. Period: " + this.getAppPeriod().toString());
            out.println();
            out.println();
            out.println("\t\t\t PAYMENT INFORMATION");
            out.println("\t\t\t TR/SBR NUMBER   :    " + this.payment.getSbrNo());
            out.println("\t\t\t DATE OF PAYMENT :    " + Utility.formatDate(this.payment.getSbrDate()));
            out.println("\t\t\t AMOUNT PAID     : " + Utility.padIt(Utility.formatter(this.payment.getSbrAmt()), 4));
            out.println();
            out.println();
            out.println("\t\t\t ENCODED INFORMATION");
            out.println("\t\t\t SS AMOUNT       : " + Utility.padIt(Utility.formatter(this.getTotalOfSSContribution()), 4));
            out.println("\t\t\t EC AMOUNT       : " + Utility.padIt(Utility.formatter(this.getTotalOfECContribution()), 4));
            out.println("\t\t\t TOTAL AMOUNT    : " + Utility.padIt(Utility.formatter(this.getTotalOfSSandEC()), 4));
            out.println();
            out.println("\t\t\t Total number of Employees: " + this.employees.size());
            out.println();
            out.println();
            out.println();
            out.println("CERTIFIED CORRECT AND PAID");
            out.println("RECEIVED BY   : _____________________________");
            out.println("DATE RECEIVED : _____________________________");
            out.println("TRANSACTION NO: _____________________________");
            out.flush();
            out.close();
        }
        return fileName;
    }
    
    //ADDED   by CGCANONG 02/07/2019
    //UPDATED by CGCANONG 02/27/2020 due to error encounterd
    public boolean validateContribution(){
        boolean isUpdatedMSC = true;
        Hashtable<String, Employee> htEE;
        Hashtable<Double, Double> htSchedule;
        YearSched ys;

        htEE = this.getEmployees();         
        
        if ((appPeriod = this.getAppPeriod()).getAppYear() > 2002 && (appPeriod.getAppYear() != 2003 || appPeriod.getAppMo() >= 3)) {
            ys = appPeriod.getYearSched();
            htSchedule = ys.getSchedule();
        }else{
            htSchedule = new Hashtable<Double, Double>();
            return isUpdatedMSC;
        }

        for(Employee ee :htEE.values()){
            
            Contribution cc = ee.getContribution();
            if( cc.getSSSAmt() == 0 && cc.getECAmt() == 0 ){
                if(!cc.getRemark().equals("3")){
                    isUpdatedMSC = false;
                    break;                    
                }
            }else{
                if( htSchedule.containsKey(cc.getSSSAmt()) == false ){ 
                    isUpdatedMSC = false;
                    break;
                }
                
                if( cc.getECAmt() != htSchedule.get(cc.getSSSAmt()) ){ 
                    isUpdatedMSC = false;
                    break;
                }                
            }
        }
        
        return isUpdatedMSC;
    }

}

