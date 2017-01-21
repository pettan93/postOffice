package cz.mendelu.kalas;

import cz.mendelu.kalas.enums.ServiceType;
import cz.mendelu.kalas.models.DispatchCategory;
import cz.mendelu.kalas.models.PostOffice;
import cz.mendelu.kalas.models.WorkDay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Pettan on 21.01.2017.
 */
public class AnalysisBox {

    private PostOffice p;

    private WorkDay w;


    private HashMap<ServiceType, HashMap<DispatchCategory, ArrayList<Integer>>> stats = new HashMap<>();


    public AnalysisBox(PostOffice postOffice, WorkDay workDay) {
        this.p = postOffice;
        this.w = workDay;
    }


    public void printBrief() {
        System.out.println("Status : pocet volnych prepazek [" + p.geAllDesks().stream().filter(desk -> !desk.isOn()).collect(Collectors.toList()).size() + "]");
        System.out.println("Status : pocet zakazniku ve fronte [" + p.getQueueCostumerCont() + "] + " + p.getQueueCostumer());
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

    public void printServiceInfo() {

        System.out.println("Simulation info ---------------->");
        System.out.println("- Services overview ---->");
        ArrayList<Integer> allDispatchTimes = new ArrayList<>();
        for (ServiceType serviceType : stats.keySet()) {
            System.out.println("-  service[" + serviceType.name() + "]");
            HashMap<DispatchCategory, ArrayList<Integer>> dispatches = stats.get(serviceType);

            ArrayList<Integer> allServiceDispatchTimes = new ArrayList<>();

            for (DispatchCategory dispatchCategory : dispatches.keySet()) {
                ArrayList<Integer> dispatchTimes = dispatches.get(dispatchCategory);
                Double average = dispatchTimes.stream().mapToInt(val -> val).average().getAsDouble();
                System.out.println("     - category[" + dispatchCategory.name()+"]");
                System.out.println("       - count["+dispatchTimes.size()+"]");
                System.out.println("       - avg["+average+"]");
                allServiceDispatchTimes.addAll(dispatchTimes);
            }

            Double average = allServiceDispatchTimes.stream().mapToInt(val -> val).average().getAsDouble();
            System.out.println(" - count["+allServiceDispatchTimes.size()+"]");
            System.out.println(" - avg["+average+"]");

            allDispatchTimes.addAll(allServiceDispatchTimes);
        }
        System.out.println("- Services overview End ----<");
        Double average = allDispatchTimes.stream().mapToInt(val -> val).average().getAsDouble();
        System.out.println("- SUMMARY");
        System.out.println(" - count["+allDispatchTimes.size()+"]");
        System.out.println(" - avg["+average+"]");
        System.out.println("Simulation info End -------------<");

    }


}
