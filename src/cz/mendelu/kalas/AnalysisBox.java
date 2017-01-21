package cz.mendelu.kalas;

import cz.mendelu.kalas.enums.ServiceType;
import cz.mendelu.kalas.models.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Pettan on 21.01.2017.
 */
public class AnalysisBox {


    private PostOffice p;

    private WorkDay w;

    private HashMap<ServiceType, HashMap<DispatchCategory, ArrayList<Integer>>> stats = new HashMap<>();


    private Integer maxQueue = 0;
    private Integer time = 0;


    public AnalysisBox(PostOffice postOffice, WorkDay workDay) {
        this.p = postOffice;
        this.w = workDay;
    }


    public void checkout() {

        if(p.getQueueCostumer().size() > maxQueue) {
            maxQueue = p.getQueueCostumer().size();
            time = w.howLong();
        }

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
            System.out.println("    - " + startDate + "-" + Utils.getPrettyTime(calendar) + "[" + w.getCustomerProbabilityTimeline().getObject(d) + "]");
        }
        System.out.println("  - Customer arrival timeline END --<");



        // Post-office services choices
        System.out.println("  - Services pick probabilities ---->");
        for (Object o : p.getServices().getKeySet()) {
            Double d = (Double) o;
            Service s = (Service) p.getServices().getObject(d);
            System.out.println("    - " + s.getName() + "[" + d + "]");
            for (Object o1 : s.getDispatchTimes().getKeySet()) {
                Double dd = (Double) o1;
                System.out.println("           - " + ((DispatchCategory) s.getDispatchTimes().getObject(dd)).name() + "[" + dd + "]");

            }
        }
        System.out.println("  - Services pick probabilities --<");

        System.out.println("- Probability overview End ----------<");
        System.out.println("\n");
    }


    public void printServiceInfo() {
        //System.out.println("Simulation info ---------------->");
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
                System.out.println("       - avg[" + average + "]");
                allServiceDispatchTimes.addAll(dispatchTimes);
            }

            Double average = allServiceDispatchTimes.stream().mapToInt(val -> val).average().getAsDouble();
            System.out.println(" - count[" + allServiceDispatchTimes.size() + "]");
            System.out.println(" - avg[" + average + "]");

            allDispatchTimes.addAll(allServiceDispatchTimes);
        }

        Double average = allDispatchTimes.stream().mapToInt(val -> val).average().getAsDouble();
        System.out.println("- SUMMARY");
        System.out.println(" - count[" + allDispatchTimes.size() + "]");
        System.out.println(" - avg[" + average + "]");
        System.out.println("- Services overview End ----<");
        //System.out.println("Simulation info End -------------<");
        System.out.println("\n");
    }

    public void printDesksInfo() {
        System.out.println("- Desks overview ---->");
        for (Desk desk : this.p.geAllDesks()) {
            System.out.println(" - desk[" + desk.getDeskNumber() + "]");
            System.out.println("   - idle[" + desk.getIdleTime() + "]");
            System.out.println("   - utilization[" + (100 - ((desk.getIdleTime() / (540 / 100)))) + "]");
        }
        System.out.println("- Desks overview End ----<");
        System.out.println("\n");
    }



}
