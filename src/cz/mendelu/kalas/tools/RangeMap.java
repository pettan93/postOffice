package cz.mendelu.kalas.tools;

import java.util.*;

/**
 * Generic RangeMap, use for probability distribution
 *
 * For inicialization put map of some items and their occurrences
 *
 * Example
 *  Input
 *      key : spotted vehicle
 *      value : sum of occurrences
 *          {Blue car, 10}
 *          {Red bike, 5}
 *          {White truck, 5}
 *  RangeMap
 *      key : probability
 *      value : spotted vehicle
 *          {0.50, Blue car}
 *          {0.25, Red bike}
 *          {0.25, White truck}
 *
 * @param <T>
 */
public final class RangeMap<T> {

    NavigableMap<Double, T> map = new TreeMap<>();

    public RangeMap(HashMap<T, Integer> items) {
        double sum = 0.0, part = 0.0;
        for (T t : items.keySet()) {
            sum = sum + items.get(t);
        }

        for (T t : items.keySet()) {
            if(items.get(t).equals(0)) continue;
            map.put(part + (items.get(t) / sum), t);
            part = part + (items.get(t) / sum);
        }
    }


    public T getObject(Double key) {
        return map.ceilingEntry(key) != null ? map.ceilingEntry(key).getValue() : map.floorEntry(key).getValue();
    }

    public T randomPick(){
        Random r = new Random();
        double d = (0 + (1) * r.nextDouble());
        System.out.println(d);
        return getObject(d);
    }

    Set<Double> getKeySet() {
        return map.keySet();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("{\n");
        for (Double key : map.keySet()) {
            sb.append("" + key + " - " + this.getObject(key) + "\n");
        }
        sb.append("}");

        return sb.toString();
    }


}
