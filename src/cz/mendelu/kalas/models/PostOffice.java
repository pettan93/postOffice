package cz.mendelu.kalas.models;

import cz.mendelu.kalas.RangeMap;
import cz.mendelu.kalas.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class PostOffice {

    private RangeMap services = new RangeMap<Service>();

    private ArrayList<Desk> desks = new ArrayList<>();

    private Queue<Costumer> q = new LinkedList<>();

    /**
     * Creates new post office with given number of desks
     *
     * @param desks
     */
    public PostOffice(Integer desks) {
        for (int i = 1; i < desks+1; i++) {
            this.desks.add(new Desk(i));
        }
    }

    public Desk getFirstFreeDesk() {
        for (Desk desk : desks) {
            if (!desk.isOn()) return desk;
        }
        return null;
    }

    public Service pickService() {
        Double r = Utils.getRandom(0.0, 1.0, 2);
        return (Service) services.getObject(r);
    }

    public ArrayList<Desk> geAllDesks(){
        return this.desks;
    }

    public Service pickService(Double d) {
        //System.out.println("choosed number = " + d);
        return (Service) services.getObject(d);
    }

    public void addService(int c, Service s) {
        this.services.putObject((double) c, s);
    }

    public RangeMap getServices() {
        return services;
    }

    public void normalize() {
        Set<Double> keys = services.getKeys();
        for (Double key : keys) {
            ((Service) services.getObject(key)).getDispatchTimes().normalize();
        }
        services.normalize();
    }

    public Costumer getNextCostumer(){
        return q.poll();
    }

    public void costumerToQueue(Costumer c){
        q.add(c);
    }

    public Integer getQueueCostumerCont(){
        return q.size();
    }

    public Queue getQueueCostumer(){
        return q;
    }


    @Override
    public String toString() {
        return "PostOffice{\n" +
                "services=" + services.toString() + "\n" +
                "}\n";
    }
}
