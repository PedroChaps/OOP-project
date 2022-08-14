package ggc;


public class SelectionStatus extends Status{

    public SelectionStatus(Partner partner, double points){
        super(partner, points);
    }
    public void nonDelayedSell(double value) {
        addPoints(10 * value);
        if (getPoints() > 2500){
            getPartner().setStatus(new EliteStatus(getPartner(),
                                                        getPoints()));
        }
    }
    public void delayedSell(double delay){
        if (delay > 2){
            losePoints(getPoints()*.9);
            getPartner().setStatus(new NormalStatus(getPartner(),
                                                        getPoints()));
        }
    }

    @Override
    public double getDiscountOrFine(int limitDate, int currentDate, int N) {
        switch (getPeriod(limitDate, currentDate, N)){
            case 1 -> {
                return 0.9;
            }
            case 2 -> {
                if (limitDate - currentDate >= 2)
                    return 0.95;
                else
                    return 1;
            }
            case 3 -> {
                if (currentDate - limitDate > 1)
                    return 1 + (0.02 * (currentDate - limitDate));
                else
                    return 1;
            }
            case 4 -> {
                return 1 + (0.05 * (N - limitDate));
            }
            default -> {return 0;} // Never happens
        }
    }

    public String getName(){return "SELECTION";}
}
