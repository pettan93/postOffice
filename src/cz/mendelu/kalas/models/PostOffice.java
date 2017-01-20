package cz.mendelu.kalas.models;

import cz.mendelu.kalas.ProbabilityRangeMap;
import cz.mendelu.kalas.Utils;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Pettan on 20.01.2017.
 */
public class PostOffice {

    private ProbabilityRangeMap services = new ProbabilityRangeMap<Service>();

    private ArrayList<Desk> desks;

    public Service pickService(){
        Double r = Utils.getRandom();
        //System.out.println("random number = " + r);
        return (Service) services.getObject(r);
    }

    public Service pickService(Double d){
        //System.out.println("choosed number = " + d);
        return (Service) services.getObject(d);
    }

    public void addService(int c, Service s) {
        this.services.putObject((double) c, s);
    }


    public void normalize() {
        Set<Double> keys = services.getKeys();
        for (Double key : keys) {
            ((Service) services.getObject(key)).getDispatchTimes().normalize();
        }
        services.normalize();
    }


    @Override
    public String toString() {
        return "PostOffice{\n" +
                "services=" + services.toString() + "\n" +
                "}\n";
    }
}