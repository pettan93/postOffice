package cz.mendelu.kalas;

import java.util.ArrayList;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Pettan on 20.01.2017.
 */
public class RangeMap<T> {
    NavigableMap<Double, T> map = new TreeMap<>();

    public T getObject(Double key) {
        return map.ceilingEntry(key) != null ? map.ceilingEntry(key).getValue() : map.floorEntry(key).getValue() ;
    }

    public void putObject(Double key, T o){
        this.map.put(key,o);
    }

    public Set<Double> getKeys(){
        return map.keySet();
    }

    public void normalize(){
        ArrayList<Double> keys = new ArrayList<>(map.keySet());
        Double sum = keys.stream().mapToDouble(Double::doubleValue).sum();

        for (Double key : keys) {
            T obj = map.remove(key);
            map.put(((100/sum)*key)/100, obj);
        }
    }

    public Set<Double> getKeySet(){
        return map.keySet();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("{\n");
        for (Double key : map.keySet()) {
            sb.append(""+ key +" - " + this.getObject(key)+"\n");
        }
        sb.append("}") ;

        return sb.toString();
    }




}
