package ggc;

public class Breakdown extends Transaction {

    private String _extendedRecipe;

    public String getName(){
        return "DESAGREGAÇÃO";
    }

    public Breakdown (int id, int amount, double finalPrice,
                                Partner partner, Product product,
                                               String extendedRecipe){
        super(id,amount,finalPrice,partner, product);
        _extendedRecipe = extendedRecipe;
    }


    @Override
    public void pay(int date){
        if (isPaid()) return;
        setPaymentDate(date);

        Partner p = getAssociatedPartner();
        Product pr = getAssociatedProduct();

        double calculatedPrice = getFinalPrice() *
                     p.getDiscountOrFine(date, date, pr.getOffsetN());

        setPaidPrice(calculatedPrice);

        p.registerNonDelayedSell(calculatedPrice);

        p.addValuePaid(calculatedPrice);

        gotPaid();
    }


    @Override
    public String displayTransaction(int date){
        int paidPrice = (int) Math.round(getPaidPrice());
        if (paidPrice < 0)
            paidPrice = 0;

        return super.toString() +  "|" +
                Math.round(getFinalPrice()) + "|" +
                paidPrice + "|" +
                getPaymentDate() + "|" +
                _extendedRecipe;
    }

    @Override
    public String toString(){
        int paidPrice = (int) Math.round(getPaidPrice());
        if (paidPrice < 0)
            paidPrice = 0;

        return super.toString() +  "|" +
                Math.round(getFinalPrice()) + "|" +
                paidPrice + "|" +
                getPaymentDate() + "|" +
                _extendedRecipe;
    }


    public void acceptSalesAndBreakDownVisitor(SalesAndBreakDownVisitor v){
        v.showTransaction(this);
    }
}


