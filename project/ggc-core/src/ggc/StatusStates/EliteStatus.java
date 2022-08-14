package ggc.StatusStates;

import ggc.Status;

public class EliteStatus extends StatusState {

    public EliteStatus(Status status, int points){
        super(status, points);
    }

    public String getName(){
        return "ELITE";
    }
}
