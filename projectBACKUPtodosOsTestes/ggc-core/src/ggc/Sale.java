package ggc;

public class Sale extends Transaction{

    private int _paymentDeadline;
    public Sale (int id, int amount, double finalPrice, Partner partner, Product product, int paymentDeadline){
        super(id, amount, finalPrice, partner, product);
        _paymentDeadline = paymentDeadline;
        partner.addValueSold(finalPrice);
    }

    public double calculatePrice(int currentDate){
        Partner p = getAssociatedPartner();
        Product pr = getAssociatedProduct();

        double calculatedPrice = getFinalPrice() * p.getDiscountOrFine(_paymentDeadline, currentDate, pr.getOffsetN());
        return calculatedPrice;
    }

    @Override
    public void pay(int date){
        if (isPaid()) return;
        setPaymentDate(date);

        Partner p = getAssociatedPartner();

        double calculatedPrice = calculatePrice(date);

        setPaidPrice(calculatedPrice);

        if (_paymentDeadline >= date)
            p.registerNonDelayedSell(calculatedPrice);
        else
            p.registerDelayedSell(date - _paymentDeadline);

        p.addValuePaid(calculatedPrice);

        gotPaid();
    }


    public String getName(){
        return "VENDA";
    }
    public double getCurrentPayingPrice(int date){
        if (isPaid())
            return getFinalPrice();
        else
            return calculatePrice(date);
    }

    @Override
    public String displayTransaction(int date){
        String s =  super.toString() + "|" + Math.round(getFinalPrice()) + "|" + Math.round(getCurrentPayingPrice(date)) + "|" + _paymentDeadline;
        if (isPaid())
            s += "|" + getPaymentDate();
        return s;
    }

    @Override
    public String toString(){
        String s =  super.toString() + "|" + Math.round(getFinalPrice()) + "|" + Math.round(getPaidPrice()) + "|" + _paymentDeadline + "|" + getPaymentDate();
        return s;
    }

    public void acceptSalesAndBreakDownVisitor(SalesAndBreakDownVisitor v){
        v.showTransaction(this);
    }
}
