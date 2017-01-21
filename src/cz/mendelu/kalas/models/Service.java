package cz.mendelu.kalas.models;

import cz.mendelu.kalas.RangeMap;
import cz.mendelu.kalas.enums.ServiceType;
import cz.mendelu.kalas.Utils;

/**
 * Created by Pettan on 20.01.2017.
 */
public class Service {

    private ServiceType type;

    private RangeMap dispatchTimes = new RangeMap<DispatchTime>();

    /**
     * Creates instance of Service class, which represents possible customer action at post office.
     * @param type
     * @param dispatches
     */
    public Service(ServiceType type, int[] dispatches) {
        this.type = type;
        this.dispatchTimes.putObject((double) dispatches[0],DispatchTime.VERY_FAST);
        this.dispatchTimes.putObject((double) dispatches[1],DispatchTime.FAST);
        this.dispatchTimes.putObject((double) dispatches[2],DispatchTime.SLOW);
        this.dispatchTimes.putObject((double) dispatches[3],DispatchTime.SLOWEST);
    }

    public DispatchTime pickDispatchTime(){
        Double r = Utils.getRandom(0.0,1.0,2);
        //System.out.println("random number = " + r);
        return (DispatchTime) dispatchTimes.getObject(r);
    }

    public DispatchTime pickDispatchTime(Double d){
        //System.out.println("choosed number = " + d);
        return (DispatchTime) dispatchTimes.getObject(d);
    }


    public String getName(){
        return type.name();
    }

    @Override
    public String toString() {
        return type + "\n" +
                "dispatchTimes=" + dispatchTimes;
    }

    public RangeMap getDispatchTimes(){
        return dispatchTimes;
    }
}