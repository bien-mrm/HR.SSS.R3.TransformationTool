package r3source;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import r3source.Utility;
import scheduler.YearSched;

public class ApplicablePeriod
implements Serializable,
Comparable<ApplicablePeriod> {
    private int appMo;
    private int appYear;
    private static final long serialVersionUID = 81L;

    public ApplicablePeriod() {
    }

    public ApplicablePeriod(int appMo, int appYear) {
        this.appMo = appMo;
        this.appYear = appYear;
    }

    public int getAppMo() {
        return this.appMo;
    }

    public int getAppYear() {
        return this.appYear;
    }

    @Override
    public int compareTo(ApplicablePeriod o) {
        return this.getAsDate().compareTo(o.getAsDate());
    }

    public boolean equals(Object appPeriod) {
        if (this.appMo == ((ApplicablePeriod)appPeriod).getAppMo() && this.appYear == ((ApplicablePeriod)appPeriod).getAppYear()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.appMo ^ this.appYear;
    }

    public String toString() {
        String appPeriod = "";
        return String.format("%02d%04d", this.appMo, this.appYear);
    }

    public String strImage() {
        String appPeriod = "";
        appPeriod = this.appMo < 10 ? "0" + this.appMo + this.appYear : "" + this.appMo + "" + this.appYear;
        return String.format("%02d%04d", this.appMo, this.appYear);
    }

    public String strImageMonth() {
        String appPeriod = "";
        return String.format("%02d", this.appMo);
    }

    public Date getAsDate() {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MMyyyy");
        try {
            date = sdf.parse(this.strImage());
        }
        catch (ParseException pe) {
            // empty catch block
        }
        return date;
    }

    public String getAppPeriod() {
        return String.format("%04d", this.appYear) + String.format("%02d", this.appMo);
    }

    public YearSched getYearSched() {
        Set<Integer> keySet = Utility.getEffectYearSet();
        for (Integer key : keySet) {
            YearSched ys = Utility.getYearSched(key);
            if (Integer.valueOf(this.getAppPeriod()) < ys.getYearOfEffectivity() || Integer.valueOf(this.getAppPeriod()) > ys.getYearEndOfEffectivity()) continue;
            return ys;
        }
        return null;
    }
}

