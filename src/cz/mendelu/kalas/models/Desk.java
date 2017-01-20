package cz.mendelu.kalas.models;

/**
 * Created by Pettan on 20.01.2017.
 */
public class Desk {

    private Boolean busy = false;

    private Integer idleTime = 0;

    public void setOn(){
        this.busy = true;
    }

    public void setOff(){
        this.busy = false;
    }

    public Boolean isOn(){
        return busy;
    }


}
