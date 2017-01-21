package cz.mendelu.kalas;

import java.util.Random;

/**
 * Created by Pettan on 20.01.2017.
 */
public class Utils {

    /**
     * Generates random number number beetween min and max, rounded to given decimal places
     * @return random number beetween min and max
     */
    public static Double getRandom(Double min, Double max, Integer round){
        Random r = new Random();
        double randomValue = (min + (max - min) * r.nextDouble());
        return Math.round(randomValue * (round * 10.0)) / (round * 10.0);
    }
}
