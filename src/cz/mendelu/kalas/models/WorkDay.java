package cz.mendelu.kalas.models;

import cz.mendelu.kalas.tools.RangeMap;
import cz.mendelu.kalas.Utils;

import java.util.Calendar;


public class WorkDay {


    private Calendar calendar = Calendar.getInstance();

    private RangeMap customerProbabilityTimeline = new RangeMap<Double>();

    private Integer minutes = 0;

    public WorkDay(int[] customerTimeline) {
        for (int i = 0; i < 9 ; i++) {
            customerProbabilityTimeline.putObject((i+1)*60.0,customerTimeline[i]/60.0);
        }
        Utils.resetGenericWorkDay(calendar);
    }

    public RangeMap getCustomerProbabilityTimeline() {
        return customerProbabilityTimeline;
    }

    public Double getProbabilityForTime(int time){
        return (Double) customerProbabilityTimeline.getObject((double) time);
    }

    public boolean isEnd(){
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

    public Integer howLong(){
        return minutes;
    }

    public String getClock(){
        return Utils.getPrettyTime(calendar);
    }




}
