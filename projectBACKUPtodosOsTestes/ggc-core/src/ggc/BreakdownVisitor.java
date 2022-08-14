package ggc;

public class BreakdownVisitor extends ProductVisitor{
    public BreakdownVisitor(Warehouse warehouse){super(warehouse);}

    public Breakdown sellBatches(DerivedProduct pr, Partner p, int amount) {
        Warehouse wh = getWarehouse();

        Double priceToPay = wh.sellBatches(pr, amount);
        Double priceReturned = wh.createdBatchesFromDerivedProduct(pr, p, amount);

        Double finalPrice = priceToPay - priceReturned;

        Breakdown b = new Breakdown(wh.getTransactionsID(), amount, finalPrice, p, pr, wh.craftExtendedRecipe(pr, amount));
        b.pay(wh.getDate());

        return b;
    }

    public double aggregate(DerivedProduct p){
        throw new UnsupportedOperationException();
    }
}
/*

    public double calculatePrice(int currentDate){
        Partner p = getAssociatedPartner();
        Product pr = getAssociatedProduct();

        double calculatedPrice = getFinalPrice() * p.getDiscountOrFine(_paymentDeadline, currentDate, pr.getOffsetN());
        return calculatedPrice;
    }
*/
