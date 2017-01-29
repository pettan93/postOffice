package cz.mendelu.kalas;

import java.util.ArrayList;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

public class RangeMap<T> {
    NavigableMap<Double, T> map = new TreeMap<>();
    static double diff = 0.0000001;

    public T getObject(Double key) {
        return map.ceilingEntry(key) != null ? map.ceilingEntry(key).getValue() : map.floorEntry(key).getValue() ;
    }

    public void putObject(Double key, T o){
        diff=diff+0.0000001;
        this.map.put(key+diff,o);
    }

    public Set<Double> getKeys(){
        return map.keySet();
    }

    public void normalize(){
        ArrayList<Double> keys = new ArrayList<>(map.keySet());
        Double sum = keys.stream().mapToDouble(Double::doubleValue).sum();
        Double part = 0.0;

        for (Double key : keys) {
            T obj = map.remove(key);
            map.put(part+(key/sum), obj);
            part = part + (key/sum);
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
