package utility;


import java.sql.Timestamp;

/**
 * Date converter.
 */
public final class DateConversion {

    /**
     * String to Timestamp conversion.
     *
     * @param timestamp string to convert.
     * @return the respective timestamp.
     */
    public static Timestamp stringToTimestamp(String timestamp) {
        long tsTime2 = Long.parseLong(timestamp);
        return new Timestamp(tsTime2);
    }

    /**
     * Timestamp to String conversion.
     *
     * @param timestamp to convert.
     * @return the respective string.
     */
    public static String timestampToString(Timestamp timestamp) {
        return timestamp.toString();
    }

//    public static void main(String[] args) {
//
//        //Può sempre servire
//        Date today = new Date();
//        Timestamp ts1 = new Timestamp(today.getTime());
//        long tsTime1 = ts1.getTime();
//
//        System.out.println(tsTime1);
//
//
//        Timestamp time = new Timestamp(tsTime1);
//        System.out.println(timestampToString(time));
//
//
//        System.out.println(stringToTimestamp("1639991980209"));
//
//
//    }

}
