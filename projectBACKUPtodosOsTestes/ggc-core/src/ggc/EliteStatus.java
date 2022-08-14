package ggc;

import ggc.Status;

public class EliteStatus extends Status {

    public EliteStatus(Partner partner, double points){
        super(partner, points);
    }
    public void nonDelayedSell(double value) {
        addPoints(10 * value);
    }

    public void delayedSell(double delay){
        if (delay > 15){
            losePoints(getPoints()*.75);
            getPartner().setStatus(new SelectionStatus(getPartner(), getPoints()));
        }
    }

    @Override
    public double getDiscountOrFine(int limitDate, int currentDate, int N) {
        switch (getPeriod(limitDate, currentDate, N)){
            case 1, 2 -> {
                return 0.9;
            }
            case 3 -> {
                return 0.95;
            }
            case 4 -> {
                return 1;
            }
            default -> {return 0;} // Never happens
        }
    }

    public String getName(){return "ELITE";}
}
