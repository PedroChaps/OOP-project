package ggc;

import ggc.Status;

import java.io.Serializable;

public abstract class StatusState implements Serializable {
    protected Status _status;
    private int _points;
    public StatusState(Status status, int points){
        _status = status;
        _points = points;
    }

    public int getPoints(){
        return _points;
    }

    public void setPoints(int points){
        _points = points;
    }

    public void updatePoints(int slack){
        _points += slack;
    }

}
