package cz.mendelu.kalas.tools;

import cz.mendelu.kalas.Utils;
import cz.mendelu.kalas.enums.DispatchCategory;
import cz.mendelu.kalas.enums.ServiceType;
import cz.mendelu.kalas.models.*;

import java.util.*;
import java.util.stream.Collectors;

public class AnalysisBox {


    private PostOffice p;

    private WorkDay w;

    private HashMap<ServiceType, HashMap<DispatchCategory, ArrayList<Integer>>> stats = new HashMap<>();

    private HashMap<ServiceType,Integer> notServed = new HashMap<>();


    private List<Integer> queueNumbers = new ArrayList<>();
    private Integer maxQueue = 0;
    private String maxTime;


    public AnalysisBox(PostOffice postOffice, WorkDay workDay) {
        this.p = postOffice;
        this.w = workDay;
    }

    public void addNotServed(ServiceType st){
        notServed.putIfAbsent(st, 1);
        notServed.put(st,notServed.get(st)+1);
    }

    /**
     * Called after each postOffice simulation minute
     */
    public void checkout() {
        if(p.getQueueCostumer().size() > maxQueue) {
            maxQueue = p.getQueueCostumer().size();
            this.maxTime = w.getClock();
        }
        queueNumbers.add(p.getQueueCostumerCont());

        System.out.println("Status : free desks [" + p.geAllDesks().stream().filter(desk -> !desk.isOn()).collect(Collectors.toList()).size() + "]");
        System.out.println("Status : customers queue [" + p.getQueueCostumerCont() + "]");
        System.out.println("-------------");
    }


    public void addServiceDispatch(ServiceType st, DispatchCategory dc, Integer dtt) {

        HashMap dispatchesCategory;
        if (stats.get(st) == null) {
            dispatchesCategory = new HashMap<DispatchCategory, ArrayList<Integer>>();
        } else {
            dispatchesCategory = stats.get(st);
        }

        ArrayList dispatchesTimes;
        if (dispatchesCategory.get(dc) == null) {
            dispatchesTimes = new ArrayList<Integer>();
        } else {
            dispatchesTimes = (ArrayList) dispatchesCategory.get(dc);
        }

        dispatchesTimes.add(dtt);
        dispatchesCategory.put(dc, dispatchesTimes);

        stats.put(st, (HashMap<DispatchCategory, ArrayList<Integer>>) dispatchesCategory);
    }

    public void printProbabilites() {
        // Customer arrival timeline
        Calendar calendar = Calendar.getInstance();
        calendar = Utils.resetGenericWorkDay(calendar);
        System.out.println("- Probability overview ------------>");
        System.out.println("  - Customer arrival timeline ---->");
        for (Object o : w.getCustomerProbabilityTimeline().getKeySet()) {
            Double d = (Double) o;
            String startDate = Utils.getPrettyTime(calendar);
            calendar.add(Calendar.MINUTE, 60);
            System.out.println("    - " + startDate + "-" + Utils.getPrettyTime(calendar) + "[" + Utils.round((Double) w.getCustomerProbabilityTimeline().getObject(d)) + "]");
        }
        System.out.println("  - Customer arrival timeline END --<");



        // Post-office services choices
        System.out.println("  - Services pick probabilities ---->");
        for (Object o : p.getServices().getKeySet()) {
            Double d = (Double) o;
            Service s = (Service) p.getServices().getObject(d);
            System.out.println("    - " + s.getName() + "[" + Utils.round(d) + "]");
            for (Object o1 : s.getDispatchTimes().getKeySet()) {
                Double dd = (Double) o1;
                System.out.println("" + ((DispatchCategory) s.getDispatchTimes().getObject(dd)).name() + ";" + Utils.round(dd) + "");

            }
        }
        System.out.println("  - Services pick probabilities --<");

        System.out.println("- Probability overview End ----------<");
        System.out.println("\n");
    }


    public void printServiceInfo() {
        Integer skipped = 0;
        for (ServiceType serviceType : notServed.keySet()) {
            skipped = skipped + notServed.get(serviceType);
        }



        System.out.println("- Services overview ---->");
        ArrayList<Integer> allDispatchTimes = new ArrayList<>();
        for (ServiceType serviceType : stats.keySet()) {
            System.out.println(" - service[" + serviceType.name() + "]");
            HashMap<DispatchCategory, ArrayList<Integer>> dispatches = stats.get(serviceType);

            ArrayList<Integer> allServiceDispatchTimes = new ArrayList<>();

            for (DispatchCategory dispatchCategory : dispatches.keySet()) {
                ArrayList<Integer> dispatchTimes = dispatches.get(dispatchCategory);
                Double average = dispatchTimes.stream().mapToInt(val -> val).average().getAsDouble();
                System.out.println("     - category[" + dispatchCategory.name() + "]");
                System.out.println("       - count[" + dispatchTimes.size() + "]");
                System.out.println("       - avg[" + Utils.round(average) + "]");
                allServiceDispatchTimes.addAll(dispatchTimes);
            }

            Double average = allServiceDispatchTimes.stream().mapToInt(val -> val).average().getAsDouble();
            System.out.println(" - count[" + allServiceDispatchTimes.size() + "]");
            System.out.println(" - avg[" + Utils.round(average) + "]");

            allDispatchTimes.addAll(allServiceDispatchTimes);
        }

        Double average = allDispatchTimes.stream().mapToInt(val -> val).average().getAsDouble();
        System.out.println("- SUMMARY");
        System.out.println(" - count[" + allDispatchTimes.size() + "]");
        System.out.println(" - skipped[" + skipped + "]");
        System.out.println(" - avg[" + Utils.round(average) + "]");
        System.out.println("- Services overview End ----<");
        //System.out.println("Simulation info End -------------<");
        System.out.println("\n");
    }

    public void printDesksInfo() {
        System.out.println("- Desks overview ---->");
        for (Desk desk : this.p.geAllDesks()) {
            System.out.println(" - desk[" + desk.getDeskNumber() + "]");
            System.out.println("   - idle[" + desk.getIdleTime() + "]");
            Integer utilization = (100 - (desk.getIdleTime() / (540 / 100))) > 0 ? (100 - (desk.getIdleTime() / (540 / 100)))  : 0;
            System.out.println("   - utilization[" + utilization+ "]");
        }
        System.out.println("- Desks overview End");
        System.out.println("\n");
    }

    public void printQueueInfo() {
        Double average = this.queueNumbers.stream().mapToInt(val -> val).average().getAsDouble();
        System.out.println("- Queue overview ---->");
        System.out.println(" - max[" + this.maxQueue + "]- "+this.maxTime);
        System.out.println(" - avg[" + Utils.round(average) + "]");
        System.out.println("- Queue overview End");
        System.out.println("\n");
    }

    public void printSkippedServiceInfo() {
        System.out.println("- Disabled services overview ---->");
        for (ServiceType serviceType : notServed.keySet()) {
            System.out.println(" - service["+serviceType.name()+"] skipped "+notServed.get(serviceType)+"x");
        }

        System.out.println("- Disabled services overview End");
        System.out.println("\n");
    }






}
