package r3source;

import java.io.Serializable;
import r3source.ApplicablePeriod;

public class Payment
implements Serializable {
    private ApplicablePeriod appPeriod;
    private String sbrNo;
    private double sbrAmt;
    private String sbrDate;
    private static final long serialVersionUID = 45L;

    public Payment() {
    }

    public Payment(ApplicablePeriod appPeriod, String sbrNo, double sbrAmt, String sbrDate) {
        this.appPeriod = appPeriod;
        this.sbrNo = sbrNo;
        this.sbrAmt = sbrAmt;
        this.sbrDate = sbrDate;
    }

    public ApplicablePeriod getApplicablePeriod() {
        return this.appPeriod;
    }

    public String getSbrNo() {
        return this.sbrNo;
    }

    public double getSbrAmt() {
        return this.sbrAmt;
    }

    public String getSbrDate() {
        return this.sbrDate;
    }
}

