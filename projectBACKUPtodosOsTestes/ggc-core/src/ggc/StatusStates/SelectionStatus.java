package ggc.StatusStates;

import ggc.Status;

public class SelectionStatus extends StatusState {

    public SelectionStatus(Status status, int points){
        super(status, points);
    }

    public String getName(){
        return "SELECTION";
    }
}
