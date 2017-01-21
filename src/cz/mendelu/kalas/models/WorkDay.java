package cz.mendelu.kalas.models;

import cz.mendelu.kalas.RangeMap;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Pettan on 20.01.2017.
 */
public class WorkDay {


    private Calendar calendar = Calendar.getInstance();

    private RangeMap customerProbabilityTimeline = new RangeMap<Double>();

    private Integer minutes = 0;

    public WorkDay(int[] customerTimeline) {
        for (int i = 0; i < 9 ; i++) {
            customerProbabilityTimeline.putObject((i+1)*60.0,customerTimeline[i]/60.0);
        }
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.HOUR, 8);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 21);
        calendar.set(Calendar.YEAR, 2015);
    }

    public Double getProbabilityForTime(int time){
        return (Double) customerProbabilityTimeline.getObject((double) time);
    }

    public boolean isEnd(){
        minutes = minutes + 1;
        calendar.add(Calendar.MINUTE, 1);
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

    public String getClock(){
        SimpleDateFormat f = new SimpleDateFormat("HH:mm");
        return f.format(calendar.getTime());
    }




}
