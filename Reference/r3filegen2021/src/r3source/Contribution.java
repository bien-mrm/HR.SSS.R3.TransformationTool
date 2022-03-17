package r3source;

import java.io.Serializable;
import java.text.DecimalFormat;
import r3source.ApplicablePeriod;
import r3source.Utility;

public class Contribution
implements Serializable {
    private double sssAmt;
    private double ecAmt;
    private ApplicablePeriod appPeriod;
    private String remark;
    private static final long serialVersionUID = 47L;

    public Contribution() {
    }

    public Contribution(ApplicablePeriod appPeriod, double sssAmt, double ecAmt, String remark) {
        this.appPeriod = appPeriod;
        this.sssAmt = sssAmt;
        this.ecAmt = ecAmt;
        this.remark = remark;
    }

    public ApplicablePeriod getApplicablePeriod() {
        return this.appPeriod;
    }

    public double getSSSAmt() {
        return this.sssAmt;
    }

    public double getECAmt() {
        return this.ecAmt;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setApplicablePeriod(ApplicablePeriod appPeriod) {
        this.appPeriod = appPeriod;
    }

    public String toString() {
        String strContri = "";
        int appMo = this.appPeriod.getAppMo();
        String strSSSAmt = Utility.formatSSEC(this.getSSSAmt(), 7);
        String strEcAmt = Utility.formatSSEC(this.getECAmt(), 5);
        if (appMo == 1 || appMo == 4 || appMo == 7 || appMo == 10) {
            strContri = strSSSAmt + " " + "   0.00" + " " + "   0.00" + " " + " 0.00" + " " + " 0.00" + " " + " 0.00" + " " + strEcAmt + " " + " 0.00" + " " + " 0.00" + "      " + this.getRemark();
        } else if (appMo == 2 || appMo == 5 || appMo == 8 || appMo == 11) {
            strContri = "   0.00 " + strSSSAmt + " " + "   0.00" + " " + " 0.00" + " " + " 0.00" + " " + " 0.00" + " " + " 0.00" + " " + strEcAmt + " " + " 0.00" + "      " + this.getRemark();
        } else if (appMo == 3 || appMo == 6 || appMo == 9 || appMo == 12) {
            strContri = "   0.00    0.00 " + strSSSAmt + " " + " 0.00" + " " + " 0.00" + " " + " 0.00" + " " + " 0.00" + " " + " 0.00" + " " + strEcAmt + "      " + this.getRemark();
        }
        return strContri;
    }

    public String getPrintableRecord() {
        DecimalFormat df = new DecimalFormat("0.00");
        String strSSSAmt = String.valueOf(df.format(this.getSSSAmt()));
        String strEcAmt = String.valueOf(df.format(this.getECAmt()));
        return Utility.padIt(strSSSAmt, 2) + "  " + Utility.padIt(strEcAmt, 3) + "   " + this.remark + "   ";
    }
}

