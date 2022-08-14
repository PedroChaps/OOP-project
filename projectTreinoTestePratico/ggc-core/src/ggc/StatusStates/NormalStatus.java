package ggc.StatusStates;

import ggc.Status;

public class NormalStatus extends StatusState {

    public NormalStatus(Status status, int points){
        super(status, points);
    }

    public String getName(){
        return "NORMAL";
    }
}
