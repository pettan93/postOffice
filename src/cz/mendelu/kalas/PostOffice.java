package cz.mendelu.kalas;

import java.util.Random;
import java.util.Set;

/**
 * Created by Pettan on 20.01.2017.
 */
public class PostOffice {

    private ProbabilityRangeMap services = new ProbabilityRangeMap<Service>();

    public void pickService(){
        Random r = new Random();
        double randomValue = (0.1 + (1 - 0.1) * r.nextDouble());
        double twoDecimal = Math.round(randomValue * 100.0) / 100.0;


        System.out.println("random number = " + twoDecimal);
        System.out.println(services.getObject(twoDecimal));
    }


    public void pickService(Double d){

        System.out.println("choosed number = " + d);
        System.out.println(services.getObject(d));
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
