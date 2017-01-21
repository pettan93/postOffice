package cz.mendelu.kalas.models;

import cz.mendelu.kalas.RangeMap;
import cz.mendelu.kalas.enums.ServiceType;
import cz.mendelu.kalas.Utils;

public class Service {

    private ServiceType type;

    private RangeMap dispatchTimes = new RangeMap<DispatchCategory>();

    /**
     * Creates instance of Service class, which represents possible customer action at post office.
     * @param type
     * @param dispatches
     */
    public Service(ServiceType type, int[] dispatches) {
        this.type = type;
        this.dispatchTimes.putObject((double) dispatches[0], DispatchCategory.VERY_FAST);
        this.dispatchTimes.putObject((double) dispatches[1], DispatchCategory.FAST);
        this.dispatchTimes.putObject((double) dispatches[2], DispatchCategory.SLOW);
        this.dispatchTimes.putObject((double) dispatches[3], DispatchCategory.SLOWEST);
    }

    public DispatchCategory pickDispatchTime(){
        Double r = Utils.getRandom(0.0,1.0,2);
        //System.out.println("random number = " + r);
        return (DispatchCategory) dispatchTimes.getObject(r);
    }

    public DispatchCategory pickDispatchTime(Double d){
        //System.out.println("choosed number = " + d);
        return (DispatchCategory) dispatchTimes.getObject(d);
    }


    public String getName(){
        return type.name();
    }

    public ServiceType getType() {
        return type;
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