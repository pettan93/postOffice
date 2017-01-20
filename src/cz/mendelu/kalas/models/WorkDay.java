package cz.mendelu.kalas.models;

/**
 * Created by Pettan on 20.01.2017.
 */
public class WorkDay {



    private Integer minutes = 0;

    public boolean isEnd(){
        minutes = minutes + 1;
        if (minutes.equals(540)) return true;
        return false;
    }

    public Integer howLong(){
        return minutes;
    }


}
