package cz.mendelu.kalas;

import java.util.Random;

/**
 * Created by Pettan on 20.01.2017.
 */
public class Utils {

    /**
     * Returns random number
     * @return 0.01 - 1.00
     */
    public static Double getRandom(){
        Random r = new Random();
        double randomValue = (0.1 + (1 - 0.1) * r.nextDouble());
        return Math.round(randomValue * 100.0) / 100.0;
    }
}
