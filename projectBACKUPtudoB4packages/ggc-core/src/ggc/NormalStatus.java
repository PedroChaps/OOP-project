package ggc;

public class NormalStatus extends Status {

    public NormalStatus(Partner partner, double points){
        super(partner, points);
    }
    public void nonDelayedSell(double value){
        addPoints(value * 10);
        if ( getPoints() >2500)
            getPartner().setStatus(new EliteStatus(getPartner(),getPoints()));
        if (getPoints() > 2000){
            getPartner().setStatus(new SelectionStatus(getPartner(),getPoints()));
        }
    }

    public void delayedSell(double delay){
        losePoints(getPoints());
    }

    @Override
    public double getDiscountOrFine(int limitDate, int currentDate, int N) {
        switch (getPeriod(limitDate, currentDate, N)){
            case 1 -> {
                return 0.9;
            }
            case 2 -> {
                return 1;
            }
            case 3 -> {
                return 1 + (0.05 * (N - limitDate));
            }
            case 4 -> {
                return 1 + (0.1 * (N - limitDate));
            }
            default -> {return 0;} // Never happens
        }

    }

    public String getName(){return "NORMAL";}
}
