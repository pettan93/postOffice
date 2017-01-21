package cz.mendelu.kalas.models;

/**
 * Created by Pettan on 20.01.2017.
 */
public class Desk {

    private Integer deskNumber;

    private Boolean busy = false;

    private Integer idleTime = 0;

    private Integer serviceDispatchTime;

    public Desk(Integer deskNumber) {
        this.deskNumber = deskNumber;
    }

    public void setBusy(Integer dispatchTime) {
        //System.out.println("Desk [" + this.getDeskNumber() + "] - set busy!");
        this.busy = true;
        this.serviceDispatchTime = dispatchTime;
    }

    public Integer getDeskNumber() {
        return deskNumber;
    }

    public Integer getIdleTime(){ return idleTime;}

    public void checkStatus() {
        System.out.println("Desk[" + this.hashCode() + "], total idle time[" + idleTime + "]");
        if (busy) {
            //System.out.println(" - busy, remaining time [" + serviceDispatchTime + "]");
            if (serviceDispatchTime.equals(0)) this.setFree();
            serviceDispatchTime--;
        } else {
            //System.out.println(" - idle");
            this.incIdle();
        }
    }

    private void setFree() {
       // System.out.println("Desk [" + this.getDeskNumber() + "] - set free!");
        this.busy = false;
    }


    public Boolean isOn() {
        return busy;
    }

    public void incIdle() {
        this.idleTime++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Desk desk = (Desk) o;

        return deskNumber.equals(desk.deskNumber);
    }

    @Override
    public int hashCode() {
        return deskNumber.hashCode();
    }


}
