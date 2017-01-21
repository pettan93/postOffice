package cz.mendelu.kalas.models;

import cz.mendelu.kalas.RangeMap;
import cz.mendelu.kalas.Utils;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Pettan on 20.01.2017.
 */
public class PostOffice {

    private RangeMap services = new RangeMap<Service>();

    private ArrayList<Desk> desks = new ArrayList<>();

    /**
     * Creates new post office with given number of desks
     *
     * @param desks
     */
    public PostOffice(Integer desks) {
        for (int i = 0; i < desks; i++) {
            this.desks.add(new Desk());
        }
    }

    public Desk getFirstFreeDesk() {
        for (Desk desk : desks) {
            if (desk.isOn()) return desk;
        }
        return null;
    }

    public Service pickService() {
        Double r = Utils.getRandom(0.0, 1.0, 2);
        return (Service) services.getObject(r);
    }

    public Service pickService(Double d) {
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
