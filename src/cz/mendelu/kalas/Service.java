package cz.mendelu.kalas;

/**
 * Created by Pettan on 20.01.2017.
 */
public class Service {

    private ServiceType type;

    private ProbabilityRangeMap dispatchTimes = new ProbabilityRangeMap<DispatchTime>();

    public Service(ServiceType type, int[] dispatches) {
        this.type = type;
        this.dispatchTimes.putObject((double) dispatches[0],DispatchTime.VERY_FAST);
        this.dispatchTimes.putObject((double) dispatches[1],DispatchTime.FAST);
        this.dispatchTimes.putObject((double) dispatches[2],DispatchTime.SLOW);
        this.dispatchTimes.putObject((double) dispatches[3],DispatchTime.SLOWEST);
    }

    @Override
    public String toString() {
        return type + "\n" +
                "dispatchTimes=" + dispatchTimes;

    }

    public ProbabilityRangeMap getDispatchTimes(){
        return dispatchTimes;
    }
}