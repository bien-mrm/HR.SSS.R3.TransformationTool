package r3source;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;
import jon.SampleUtils;

public class Validator {
    public static boolean isSssNumberValid(String string) {
        boolean bl = true;
        try {
            if (string.length() < 10 || string.length() > 10) {
                bl = false;
                return false;
            }
            for (int i = 0; i < string.length(); ++i) {
                if (!Character.isLetter(string.charAt(i)) && !Character.isWhitespace(string.charAt(i))) continue;
                bl = false;
                return false;
            }
            if (!Validator.validateFirst2Digits(string)) {
                bl = false;
                return false;
            }
            double d = Double.parseDouble(string);
            if (d == 0.0) {
                bl = false;
                return false;
            }
            int n = Integer.parseInt(String.valueOf(string.charAt(0)));
            int n2 = Integer.parseInt(String.valueOf(string.charAt(1)));
            int n3 = Integer.parseInt(String.valueOf(string.charAt(2)));
            int n4 = Integer.parseInt(String.valueOf(string.charAt(3)));
            int n5 = Integer.parseInt(String.valueOf(string.charAt(4)));
            int n6 = Integer.parseInt(String.valueOf(string.charAt(5)));
            int n7 = Integer.parseInt(String.valueOf(string.charAt(6)));
            int n8 = Integer.parseInt(String.valueOf(string.charAt(7)));
            int n9 = Integer.parseInt(String.valueOf(string.charAt(8)));
            int n10 = Integer.parseInt(String.valueOf(string.charAt(9)));
            int n11 = n + n4 + n7 + (n2 + n5 + n8) * 3 + (n3 + n6 + n9) * 7;
            String string2 = String.valueOf(n11);
            int n12 = Integer.parseInt(String.valueOf(string2.charAt(string2.length() - 1)));
            if (n12 != 0) {
                if ((n12 = 10 - n12) != n10) {
                    bl = false;
                    return false;
                }
            } else if (n12 != n10) {
                bl = false;
                return false;
            }
        }
        catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
            bl = false;
            return false;
        }
        catch (Exception exception) {
            bl = false;
            return false;
        }
        return bl;
    }

    public static boolean isNameValid(String string) {
        boolean bl = true;
        bl = string.length() != 0;
        return bl;
    }

    private static boolean validateFirst2Digits(String string) {
        String string2 = string.substring(0, 2);
        SampleUtils su = new SampleUtils();
        Properties p = su.loadConfigProp("r3.properties");
        
//        String[] arrstring = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "91", "96", "97", "33", "34", "70", "20", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "80", "35"};
        String[] arrstring = p.getProperty("valid.series").split(",");
        Vector<String> vector = new Vector<String>(50);
        for (int i = 0; i < arrstring.length; ++i) {
            vector.addElement(arrstring[i].trim());
        }
        return vector.contains(string2);
    }

    public static boolean isValidDate(String string) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddyyyy");
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(string);
        }
        catch (ParseException parseException) {
            return false;
        }
        return true;
    }

    public static boolean isDateValidForNow(String string) {
        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddyyyy");
        try {
            simpleDateFormat.setLenient(false);
            date = simpleDateFormat.parse(string);
        }
        catch (ParseException parseException) {
            return false;
        }
        Date date2 = new Date();
        if (date.after(date2)) {
            return false;
        }
        return true;
    }
}

