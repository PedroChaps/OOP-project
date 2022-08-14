package ggc;

import java.io.Serializable;

public abstract class Status implements Serializable {
    private double _points;
    private Partner _partner;

    public Status(Partner partner,double points){
        _partner = partner;
        _points = points;
    }

    public Partner getPartner() {
        return _partner;
    }

    //private StatusState _state = new NormalStatus(this, 0);

    //protected void setState(StatusState state){
        //_state = state;
   // }

    public abstract String getName();

    public double getPoints(){
        return _points;
    }
    public void addPoints(double slack){_points += slack;}
    public void losePoints(double slack){_points -= slack;}
    public int getPeriod(int limitDate, int currentDate, int N){
        if (limitDate - currentDate >= N)
            return 1;
        else if (0 <= limitDate - currentDate && limitDate - currentDate < N)
            return 2;
        else if (0 < currentDate - limitDate && currentDate - limitDate <= N)
            return 3;
        else // if (currentDate - limitDate > N)
            return 4;
    }

    public abstract void nonDelayedSell(double value);
    public abstract void delayedSell(double delay);
    public abstract double getDiscountOrFine(int limitDate, int currentDate, int N);


    // TODO métodos gerais que se podem fazer em cada estado: somar pontos, subtrair, etc.
}
