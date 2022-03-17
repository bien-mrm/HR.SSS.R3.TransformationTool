package scheduler;

import java.util.Collection;
import java.util.TreeSet;
import java.util.Hashtable;
import java.io.Serializable;

public class YearSched implements Serializable
{
    private int yearOfEffectivity;
    private int yearEndOfEffectivity;
    private double ssAmt;
    private double ecAmt;
    private Hashtable<Double, Double> htSchedule;
    private static final long serialVersionUID = 69L;
    
    public YearSched() {
    }
    
    public YearSched(final int yoe, final int yee, final Hashtable<Double, Double> ht) {
        this.yearOfEffectivity = yoe;
        this.yearEndOfEffectivity = yee;
        this.htSchedule = ht;
    }
    
    public Hashtable<Double, Double> getSchedule() {
        return this.htSchedule;
    }
    
    public void setSchedule(final Hashtable<Double, Double> ht) {
        this.htSchedule = ht;
    }
    
    public int getYearOfEffectivity() {
        return this.yearOfEffectivity;
    }
    
    public int getYearEndOfEffectivity() {
        return this.yearEndOfEffectivity;
    }
    
    public void setYearOfEffectivity(final int yoe) {
        this.yearOfEffectivity = yoe;
    }
    
    public void setYearEndOfEffectivity(final int yee) {
        this.yearEndOfEffectivity = yee;
    }
    
    public String toString() {
        return String.valueOf(this.yearOfEffectivity);
    }
    
    public double getMinimumEC() {
        if (this.htSchedule != null) {
            final TreeSet<Double> set = new TreeSet<Double>(this.htSchedule.values());
            return set.first();
        }
        return 10.0;
    }
    
    public double getMaximumEC() {
        if (this.htSchedule != null) {
            final TreeSet<Double> set = new TreeSet<Double>(this.htSchedule.values());
            return set.last();
        }
        return 10.0;
    }
    
    public boolean containsEC(final double ec) {
        if (this.htSchedule != null) {
            final TreeSet<Double> set = new TreeSet<Double>(this.htSchedule.values());
            return set.contains(ec);
        }
        return false;
    }
}
