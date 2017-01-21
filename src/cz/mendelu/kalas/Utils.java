package cz.mendelu.kalas;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Pettan on 20.01.2017.
 */
public class Utils {

    /**
     * Generates random number number beetween min and max, rounded to given decimal places
     *
     * @return random number beetween min and max
     */
    public static Double getRandom(Double min, Double max, Integer round) {
        Random r = new Random();
        double randomValue = (min + (max - min) * r.nextDouble());
        return Math.round(randomValue * (round * 10.0)) / (round * 10.0);
    }

    /**
     * Generates random number number beetween min and max, rounded to given decimal places
     *
     * @return random number beetween min and max
     */
    public static Integer getRandom(Integer min, Integer max) {
        Random r = new Random();
        int randomNum = r.nextInt(max - min + 1) + min;
        return randomNum;
    }


    public static Calendar resetGenericWorkDay(Calendar c) {
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.HOUR, 8);
        c.set(Calendar.AM_PM, Calendar.AM);
        c.set(Calendar.MONTH, Calendar.DECEMBER);
        c.set(Calendar.DAY_OF_MONTH, 21);
        c.set(Calendar.YEAR, 2015);
        return c;
    }


    public static String getPrettyTime(Calendar c) {
        SimpleDateFormat f = new SimpleDateFormat("HH:mm");
        return f.format(c.getTime());

    }
}
