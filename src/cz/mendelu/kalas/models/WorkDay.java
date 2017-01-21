package cz.mendelu.kalas.models;

import cz.mendelu.kalas.RangeMap;

/**
 * Created by Pettan on 20.01.2017.
 */
public class WorkDay {


    private RangeMap customerProbabilityTimeline = new RangeMap<Double>();

    private Integer minutes = 0;

    public WorkDay(int[] customerTimeline) {
        for (int i = 0; i < 9 ; i++) {
            customerProbabilityTimeline.putObject((i+1)*60.0,customerTimeline[i]/60.0);
        }
    }

    public Double getProbabilityForTime(int time){
        return (Double) customerProbabilityTimeline.getObject((double) time);
    }

    public boolean isEnd(){
        minutes = minutes + 1;
        if (minutes.equals(540)) return true;
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


}
