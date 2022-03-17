package r3source;

import java.io.Serializable;
import r3source.Contribution;
import r3source.Employer;
import r3source.Utility;
import r3source.Validator;

public class Employee
implements Serializable,
Comparable<Employee> {
    private String eeSssNumber;
    private String lastName;
    private String firstName;
    private String midInit;
    private String hSDate;
    private Employer er;
    private Contribution contri;
    private static final long serialVersionUID = 46L;

    public Employee() {
    }

    public Employee(String eeSssNumber, String lastName, String firstName, String midInit) {
        this.setEeSssNumber(eeSssNumber);
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.midInit = midInit;
    }

    public Employee(String eeSssNumber, String lastName, String firstName, Employer er) {
        this(eeSssNumber, lastName, firstName, " ");
        this.er = er;
    }

    public Employee(String eeSssNumber, String lastName, String firstName, Employer er, String hSDate) {
        this(eeSssNumber, lastName, firstName, er);
        this.hSDate = hSDate;
    }

    public String getEeSssNumber() {
        return this.eeSssNumber;
    }

    public String getFullName() {
        return Utility.ensureLengthFor(this.getLastName(), 15) + Utility.ensureLengthFor(this.getFirstName(), 15) + this.getMidInit();
    }

    public String getLastName() {
        return this.lastName.toUpperCase();
    }

    public String getFirstName() {
        return this.firstName.toUpperCase();
    }

    public String getMidInit() {
        return this.midInit.toUpperCase();
    }

    public String getHSDate() {
        return this.hSDate;
    }

    public Employer getEmployer() {
        return this.er;
    }

    public void setEmployer(Employer er) {
        this.er = er;
    }

    public Contribution getContribution() {
        return this.contri;
    }

    public void setContribution(Contribution contri) {
        this.contri = contri;
    }

    public void setEeSssNumber(String eeSssNumber) {
        if (Validator.isSssNumberValid(eeSssNumber)) {
            this.eeSssNumber = eeSssNumber;
        }
    }

    public void setLastName(String lastName) {
        if (Validator.isNameValid(lastName)) {
            this.lastName = lastName;
        }
    }

    public void setFirstName(String firstName) {
        if (Validator.isNameValid(firstName)) {
            this.firstName = firstName;
        }
    }

    public void setMidInit(String m) {
        this.midInit = m;
    }

    @Override
    public int compareTo(Employee o) {
        return this.getFullName().compareTo(o.getFullName());
    }

    public boolean equals(Object e) {
        return this.getFullName().equals(((Employee)e).getFullName());
    }

    public int hashCode() {
        return this.lastName.length() ^ this.firstName.length();
    }

    public String getRecordImage() {
        String str = this.getFullName() + this.getEeSssNumber() + " " + this.contri.toString();
        str = this.hSDate.equals("0") ? str + "0       " : str + this.hSDate;
        return str.toUpperCase();
    }

    public String toString() {
        return this.getFullName() + this.getEeSssNumber();
    }

    public String getPrintableRecord() {
        return Utility.ensureLengthFor(this.getLastName(), 15) + " " + Utility.ensureLengthFor(this.getFirstName(), 15) + " " + this.getMidInit() + "   " + Utility.formatSSnum(this.getEeSssNumber()) + "   " + this.contri.getPrintableRecord() + this.hSDate;
    }

    public String printInfo() {
        String str = "\nSSNumber: " + this.eeSssNumber + "\nLastName: " + this.lastName + "\nFirstName: " + this.firstName;
        return str;
    }
}

