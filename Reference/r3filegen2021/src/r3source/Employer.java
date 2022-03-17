package r3source;

import java.io.Serializable;
import r3source.Utility;
import r3source.Validator;

public class Employer
implements Serializable,
Comparable<Employer> {
    private String erSssNumber;
    private String erName;
    private static final long serialVersionUID = 44L;

    public Employer() {
    }

    public Employer(String erSssNumber, String erName) {
        this.setErSssNumber(erSssNumber);
        this.setErName(erName);
    }

    public String getErSssNumber() {
        return this.erSssNumber;
    }

    public String getErName() {
        return Utility.ensureLengthFor(this.erName.toUpperCase(), 30);
    }

    public void setErSssNumber(String erSssNumber) {
        if (Validator.isSssNumberValid(erSssNumber)) {
            this.erSssNumber = erSssNumber;
        }
    }

    public void setErName(String erName) {
        if (Validator.isNameValid(erName)) {
            this.erName = erName;
        }
    }

    public boolean equals(Employer er) {
        return er.getErSssNumber().equals(this.getErSssNumber());
    }

    public String toString() {
        return this.erSssNumber + ": " + this.erName;
    }

    @Override
    public int compareTo(Employer o) {
        return this.toString().compareTo(o.toString());
    }
}

