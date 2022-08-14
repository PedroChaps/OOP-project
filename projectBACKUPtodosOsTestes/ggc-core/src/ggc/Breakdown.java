package ggc;

import java.util.List;

public class Breakdown extends Transaction {
    private String _extendedRecipe;

    public Breakdown (int id, int amount, double finalPrice, Partner partner, Product product, String extendedRecipe){
        super(id,amount,finalPrice,partner, product);
        _extendedRecipe = extendedRecipe;
    }


    @Override
    public void pay(int date){
        if (isPaid()) return;
        setPaymentDate(date);

        Partner p = getAssociatedPartner();
        Product pr = getAssociatedProduct();

        double calculatedPrice = getFinalPrice() * p.getDiscountOrFine(date, date, pr.getOffsetN());

        setPaidPrice(calculatedPrice);

        p.registerNonDelayedSell(calculatedPrice);

        p.addValuePaid(calculatedPrice);

        gotPaid();
    }


    public String getName(){
        return "DESAGREGAÇÃO";
    }


    @Override
    public String displayTransaction(int date){
        return super.toString() +  "|" + Math.round(getFinalPrice()) + "|"
                + Math.round(getPaidPrice()) + "|" + getPaymentDate() +
                "|" + _extendedRecipe;
    }

    @Override
    public String toString(){
        return super.toString() +  "|" + Math.round(getFinalPrice()) + "|"
                + Math.round(getPaidPrice()) + "|" + getPaymentDate() +
                "|" + _extendedRecipe;
    }




    public void acceptSalesAndBreakDownVisitor(SalesAndBreakDownVisitor v){
        v.showTransaction(this);
    }
}


