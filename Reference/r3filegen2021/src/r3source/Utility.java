package r3source;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Set;
import javax.swing.text.MaskFormatter;
import r3source.ApplicablePeriod;
import r3source.Employee;
import r3source.Employer;
import r3source.R3File;
import scheduler.YearSched;

public class Utility {
    public static boolean updateER(Employer er) {
        File file = new File("er.dat");
        return Utility.writeObject(er, file);
    }

    public static boolean writeObject(Object object, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.flush();
            oos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Employer readER() {
        Employer er = null;
        File file = new File("er.dat");
        try {
            ObjectInputStream oos = new ObjectInputStream(new FileInputStream(file));
            er = (Employer)oos.readObject();
            oos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return er;
    }

    private static Object readObject(File file) {
        Object o = null;
        try {
            ObjectInputStream oos = new ObjectInputStream(new FileInputStream(file));
            o = oos.readObject();
            oos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return o;
    }

    public static R3File getR3File(ApplicablePeriod appPeriod) {
        File file = new File("r3FileDB.dat");
        Hashtable ht = null;
        if (file.exists()) {
            try {
                ObjectInputStream oos = new ObjectInputStream(new FileInputStream(file));
                ht = (Hashtable)oos.readObject();
                oos.close();
                if (ht.containsKey(appPeriod.toString())) {
                    return (R3File)ht.get(appPeriod.toString());
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static Hashtable<String, R3File> getR3FileDB() {
        File file = new File("r3FileDB.dat");
        Hashtable ht = null;
        if (file.exists()) {
            try {
                ObjectInputStream oos = new ObjectInputStream(new FileInputStream(file));
                ht = (Hashtable)oos.readObject();
                oos.close();
                return ht;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static Hashtable<String, Employee> getEEDB() {
        File file = new File("eeDB.dat");
        Hashtable t = null;
        try {
            if (file.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                t = (Hashtable)ois.readObject();
                ois.close();
                return t;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static R3File getR3File() {
        File file = new File("r3File.dat");
        R3File r3File = null;
        try {
            if (file.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                r3File = (R3File)ois.readObject();
                ois.close();
                return r3File;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static YearSched getYearSched(int effectivityYear) {
        File file = new File("image/library.dat");
        YearSched ys = null;
        try {
            if (file.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                Hashtable ht = (Hashtable)ois.readObject();
                ois.close();
                if (ht.containsKey(effectivityYear)) {
                    ys = (YearSched)ht.get(effectivityYear);
                    return ys;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Set<Integer> getEffectYearSet() {
        File file = new File("image/library.dat");
        try {
            if (file.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                                                                                                                                                                                                                                                                                         Hashtable ht = (Hashtable)ois.readObject();
                ois.close();
                Set<Integer> set = ht.keySet();
                return set; 
            }
            System.out.println("Schedule library does not exist.");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Collection<YearSched> getAllSched() {
        File file = new File("image/library.dat");
        try {
            if (file.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                Hashtable ht = (Hashtable)ois.readObject();
                ois.close();
                Collection<YearSched> set = ht.values();
                return set;
            }
            System.out.println("Schedule library does not exist.");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updateEEDB(Hashtable<String, Employee> ht) {
        File file = new File("eeDB.dat");
        return Utility.writeObject(ht, file);
    }

    public static String[] getYears() {
        Date today = new Date();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(today);
        int year = cal.get(1);
        int range = year - 1990;
        String[] years = new String[range];
        for (int x = 0; x < years.length; ++x) {
            years[x] = String.valueOf(year);
            --year;
        }
        return years;
    }

    public static String ensureLengthFor(String name, int size) {
        int length = 0;
        length = name.length() > size ? size : name.length();
        char[] ch = new char[size];
        for (int count = 0; count < length; ++count) {
            ch[count] = name.charAt(count);
        }
        return new String(ch).replace('\u0000', ' ');
    }

    public static String padIt(String eName, int indicator) {
        char[] name = null;
        if (indicator == 1) {
            name = new char[15];
        }
        if (indicator == 2) {
            eName = Utility.formatter(Double.parseDouble(Utility.testForDecimal(eName)));
            eName = eName.replaceAll(",", "");
            name = new char[7];
        }
        if (indicator == 3) {
            eName = Utility.formatter(Double.parseDouble(Utility.testForDecimal(eName)));
            name = new char[5];
        }
        if (indicator == 4) {
            eName = eName.replaceAll(",", "");
            eName = Utility.formatter(Double.parseDouble(Utility.testForDecimal(eName)));
            name = new char[15];
        }
        int differential = name.length - eName.length();
        int i = 0;
        int x = 0;
        i = name.length - 1;
        x = eName.length() - 1;
        while (i >= differential) {
            name[i] = eName.charAt(x);
            --i;
            --x;
        }
        return new String(name).replace('\u0000', ' ');
    }

    public static String formatter(double value) {
        DecimalFormat df = new DecimalFormat("##,###,###,##0.00");
        String output = df.format(value);
        return output;
    }

    public static String formatSSnum(String ssnumber) {
        String formatted = "";
        for (int m = 0; m < ssnumber.length(); ++m) {
            formatted = m == 2 || m == 9 ? formatted + "-" + ssnumber.charAt(m) : formatted + ssnumber.charAt(m);
        }
        return formatted;
    }

    public static String testForDecimal(String sscEc) {
        String ssec = "";
        if (sscEc.indexOf(".") == -1) {
            String firstHalf = sscEc.substring(1, sscEc.length() - 2);
            String scondHalf = sscEc.substring(firstHalf.length() + 1);
            ssec = firstHalf + "." + scondHalf;
        } else {
            ssec = sscEc;
        }
        return ssec;
    }

    public static String formatDate(String mmddyyyy) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
        try {
            sdf.setLenient(false);
            date = sdf.parse(mmddyyyy);
            return new SimpleDateFormat("MMM dd, yyyy").format(date);
        }
        catch (ParseException pe) {
            pe.printStackTrace();
            return null;
        }
    }

    public static int showFile(File file) {
        int exitVal = 0;
        try {
            String command = "image/wordpad " + file.getName();
            Process child = Runtime.getRuntime().exec(command);
        }
        catch (Exception e) {
            e.printStackTrace();
            exitVal = 1;
        }
        return exitVal;
    }

    public static MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
            formatter.setValueContainsLiteralCharacters(false);
        }
        catch (ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
        }
        return formatter;
    }

    public static String formatSSEC(double d, int size) {
        DecimalFormat df = new DecimalFormat("0.00");
        String strAmt = df.format(d);
        char[] ch = new char[size];
        int x = size - 1;
        int i = strAmt.length() - 1;
        while (i >= 0) {
            ch[x] = strAmt.charAt(i);
            --i;
            --x;
        }
        return new String(ch).replace('\u0000', ' ');
    }    
}

