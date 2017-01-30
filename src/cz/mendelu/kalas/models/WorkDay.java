package cz.mendelu.kalas.models;

import cz.mendelu.kalas.tools.RangeMap;
import cz.mendelu.kalas.Utils;

import java.util.Calendar;
import java.util.HashMap;


public class WorkDay {


    private Calendar calendar = Calendar.getInstance();

    private RangeMap customerProbabilityTimeline;

    private Integer minutes = 0;

    public WorkDay(int[] customerTimeline) {

        HashMap<Double, Integer> map = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            map.put(customerTimeline[i] / 60.0, (i + 1) * 60);
        }


        customerProbabilityTimeline = new RangeMap<Double>(map);

        Utils.resetGenericWorkDay(calendar);

    }

    public RangeMap getCustomerProbabilityTimeline() {
        return customerProbabilityTimeline;
    }

    public Double getProbabilityForTime(int time) {
        return (Double) customerProbabilityTimeline.getObject((double) time);
    }

    public boolean isEnd() {
        minutes = minutes + 1;
        calendar.add(Calendar.MINUTE, 1);
        if (minutes > 540) return true;
        return false;
    }

    @Override
    public String toString() {
        return "WorkDay{" +
                "customerProbabilityTimeline=" + customerProbabilityTimeline +
                ", minutes=" + minutes +
                '}';
    }

    public Integer howLong() {
        return minutes;
    }

    public String getClock() {
        return Utils.getPrettyTime(calendar);
    }


}
